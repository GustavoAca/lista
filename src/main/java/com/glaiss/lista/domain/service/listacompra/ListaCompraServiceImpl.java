package com.glaiss.lista.domain.service.listacompra;

import com.glaiss.core.domain.model.ResponsePage;
import com.glaiss.core.domain.service.BaseServiceImpl;
import com.glaiss.core.exception.RegistroNaoEncontradoException;
import com.glaiss.core.utils.SecurityContextUtils;
import com.glaiss.lista.controller.listacompra.dto.*;
import com.glaiss.lista.domain.mapper.ListaCompraMapper;
import com.glaiss.lista.domain.model.*;
import com.glaiss.lista.domain.model.dto.PrecoReportadoPendenteDTO;
import com.glaiss.lista.domain.model.dto.projection.listacompra.ItemListaProjection;
import com.glaiss.lista.domain.repository.ListaCompraRepository;
import com.glaiss.lista.domain.service.itemlista.ItemListaService;
import com.glaiss.lista.domain.service.itemoferta.ItemOfertaService;
import com.glaiss.lista.domain.service.precoreportado.PrecoReportadoPendenteService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ListaCompraServiceImpl extends BaseServiceImpl<ListaCompra, UUID, ListaCompraRepository> implements ListaCompraService {

    private final ListaCompraMapper listaCompraMapper;
    private final ItemListaService itemListaService;
    private final ItemOfertaService itemOfertaService;
    private final PrecoReportadoPendenteService precoReportadoPendenteService;
    private final EntityManager em;

    protected ListaCompraServiceImpl(ListaCompraRepository repo,
                                     ListaCompraMapper listaCompraMapper,
                                     ItemListaService itemListaService,
                                     ItemOfertaService itemOfertaService,
                                     PrecoReportadoPendenteService precoReportadoPendenteService,
                                     EntityManager em) {
        super(repo);
        this.listaCompraMapper = listaCompraMapper;
        this.itemListaService = itemListaService;
        this.itemOfertaService = itemOfertaService;
        this.precoReportadoPendenteService = precoReportadoPendenteService;
        this.em = em;
    }

    @Override
    @Transactional
    public void criarLista(ListaCompraRequest listaCompraRequest) {
        ListaCompra listaCompra = listaCompraMapper.toEntity(listaCompraRequest);
        listaCompra.setId(null);
        listaCompra.setUsuarioId(SecurityContextUtils.getId());
        listaCompra.setValorTotal(calcularTotal(listaCompraRequest));

        List<ItemLista> itemListas = listaCompra.getItensLista();

        // Agrupamento de itens duplicados para evitar violação de constraint
        Map<UUID, ItemLista> itensAgrupados = new HashMap<>();
        if (itemListas != null) {
            for (ItemLista item : itemListas) {
                UUID itemOfertaId = item.getItemOfertaId();
                if (itensAgrupados.containsKey(itemOfertaId)) {
                    ItemLista itemExistente = itensAgrupados.get(itemOfertaId);
                    itemExistente.setQuantidade((short) (itemExistente.getQuantidade() + item.getQuantidade()));
                } else {
                    itensAgrupados.put(itemOfertaId, item);
                }
            }
        }
        List<ItemLista> itemListasFinal = new ArrayList<>(itensAgrupados.values());

        listaCompra.setItensLista(null);
        listaCompra.setTotalItens((short) itemListasFinal.size());
        listaCompra.setStatusLista(em.getReference(StatusLista.class, EStatusLista.AGUARDANDO));
        listaCompra = repo.save(listaCompra);

        ListaCompra finalListaCompra = listaCompra;

        itemListasFinal.forEach(itemLista -> {
            ItemOferta itemOferta = em.find(ItemOferta.class, itemLista.getItemOfertaId());
            if (itemOferta == null) {
                throw new RegistroNaoEncontradoException(itemLista.getItemOfertaId(), "Item Oferta");
            }
            itemLista.setItemOferta(itemOferta);
            itemLista.setListaCompra(finalListaCompra);

            itemLista.setPrecoUnitario(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
        });

        itemListaService.salvarAll(itemListasFinal);
    }

    private BigDecimal calcularTotal(ListaCompraRequest listaCompraRequest) {
        BigDecimal total;
        Map<UUID, Short> itemQuantidade = new LinkedHashMap<>();
        if (listaCompraRequest.itensLista() != null) {
            listaCompraRequest.itensLista().forEach(itemLista -> {
                itemQuantidade.put(itemLista.itemOfertaId(), itemLista.quantidade());
            });
        }
        total = itemOfertaService.calcularValoresItens(itemQuantidade);
        if (listaCompraRequest.valorTotal().subtract(total).compareTo(BigDecimal.ZERO) == 0) {
            return listaCompraRequest.valorTotal();
        }
        return total;
    }

    @Override
    public ResponsePage<ItemListaProjection> listarItensPorListaCompraIdPaginaDTO(Pageable pageable, UUID listId) {
        return itemListaService.listarItensPorListaCompraIdPaginaDTO(pageable, listId);
    }

    @Override
    public List<ItemListaRequest> adicionarItemLista(UUID listaId, List<ItemAdicionadoRequest> itemDTO) {
        List<ItemListaRequest> itens = itemListaService.adicionaLista(listaId, itemDTO);
        atualizarTotaisLista(listaId);
        return itens;
    }

    @Override
    public ResponsePage<ListaCompraRequest> listar(Pageable pageable) {
        Page<ListaCompra> listaCompra = repo.findAllByUsuarioId(pageable, SecurityContextUtils.getId());
        List<ListaCompraRequest> listaCompraRequest = listaCompra.getContent().stream().map(listaCompraMapper::toDto).toList();
        return new ResponsePage<>(listaCompraRequest, pageable.getPageNumber(), pageable.getPageSize(), listaCompra.getTotalElements());
    }

    @Override
    public ListaCompraRequest buscarPorIdDto(UUID listaId) {
        return listaCompraMapper.toDto(repo.findById(listaId).orElseThrow(() -> new RegistroNaoEncontradoException(listaId, "Lista de compra")));
    }

    private void atualizarTotaisLista(UUID listaId) {
        ListaCompra listaCompra = repo.findById(listaId)
                .orElseThrow(() ->
                        new RegistroNaoEncontradoException(listaId, "Lista de compra"));

        BigDecimal novoValorTotal = BigDecimal.ZERO;
        int novoTotalItens = 0;

        for (ItemLista item : listaCompra.getItensLista()) {
            BigDecimal precoItem = item.getItemOferta().getPreco();
            BigDecimal subtotal = precoItem.multiply(
                    BigDecimal.valueOf(item.getQuantidade())
            );

            novoValorTotal = novoValorTotal.add(subtotal);
            novoTotalItens++;
        }

        listaCompra.setValorTotal(novoValorTotal);
        listaCompra.setTotalItens((short) novoTotalItens);

        repo.save(listaCompra);
    }

    @Override
    public Boolean deletar(UUID id) {
        ListaCompra listaCompra = repo.findById(id).orElseThrow(() -> new RegistroNaoEncontradoException(id, "Lista de compra"));
        if (!listaCompra.getUsuarioId().equals(SecurityContextUtils.getId())) {
            return false;
        }
        return super.deletar(listaCompra.getId());
    }

    @Override
    public Boolean alterarItens(UUID listaId, List<ItemAlteradoRequest> itensLista) {
        Boolean removido = itemListaService.alterarItens(listaId, itensLista);
        if (removido) {
            atualizarTotaisLista(listaId);
        }
        return removido;
    }

    @Override
    public void concluirLista(@Valid ConcluirListaRequestDTO concluirListaRequestDTO) {
        List<PrecoReportadoPendenteDTO> precoReportadoPendenteDTOs = new LinkedList<>();
        ListaCompra listaCompras = listaCompraMapper.toConcluirListaCompraToEntity(concluirListaRequestDTO);

        listaCompras.getItensLista().forEach(itemLista -> {
            itemLista.setListaCompra(em.getReference(ListaCompra.class, itemLista.getListaCompraId()));
            LocalDateTime dataInicioPromocao = itemLista.hasPromocao() ? LocalDateTime.now() : null;
            LocalDateTime dataFinalPromocao = itemLista.hasPromocao() ? LocalDateTime.now().plusDays(1) : null;
            precoReportadoPendenteDTOs.add(new PrecoReportadoPendenteDTO(null, itemLista.getItemOfertaId(), EStatusPrecoReportado.AGUARDANDO, itemLista.getItemOferta().getPreco(), null, (short) 0, itemLista.hasPromocao(), dataInicioPromocao, dataFinalPromocao));
        });

        itemListaService.salvarAllConcluindoLista(listaCompras.getItensLista());
        precoReportadoPendenteService.salvarAll(precoReportadoPendenteDTOs);
        atualizarTotaisListaConcluida(concluirListaRequestDTO.id());
    }

    private void atualizarTotaisListaConcluida(UUID listaId) {
        ListaCompra listaCompra = repo.findById(listaId)
                .orElseThrow(() ->
                        new RegistroNaoEncontradoException(listaId, "Lista de compra"));

        BigDecimal novoValorTotal = BigDecimal.ZERO;
        int novoTotalItens = 0;

        for (ItemLista item : listaCompra.getItensLista()) {
            BigDecimal precoItem = item.getPrecoUnitario();
            BigDecimal subtotal = precoItem.multiply(
                    BigDecimal.valueOf(item.getQuantidade())
            );

            novoValorTotal = novoValorTotal.add(subtotal);
            novoTotalItens++;
        }

        listaCompra.setValorTotal(novoValorTotal);
        listaCompra.setTotalItens((short) novoTotalItens);
        listaCompra.setStatusLista(em.getReference(StatusLista.class, EStatusLista.FINALIZADA));

        repo.save(listaCompra);
    }

    @Override
    public ListaCompraRequest atualizar(ListaCompraEdicaoRequest listaCompraEdicaoRequest) {
        ListaCompra listaCompra = repo.findById(listaCompraEdicaoRequest.id())
                .orElseThrow(() -> new RegistroNaoEncontradoException(listaCompraEdicaoRequest.id(), "Lista"));

        listaCompra.setNome(listaCompraEdicaoRequest.nome());
        if(listaCompra.getVersion().equals(listaCompraEdicaoRequest.version())){
            listaCompra = repo.save(listaCompra);
        }

        return listaCompraMapper.toDto(listaCompra);
    }
}
