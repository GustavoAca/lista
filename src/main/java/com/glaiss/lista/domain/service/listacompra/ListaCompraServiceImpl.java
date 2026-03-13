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

        List<ItemLista> itemListas = listaCompra.getItensLista();

        // Agrupamento de itens duplicados para evitar violação de constraint
        List<ItemLista> itemListasFinal = new ArrayList<>();
        if (itemListas != null) {
            Map<UUID, Short> itensAgrupados = itemListas.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                    ItemLista::getItemOfertaId,
                    java.util.stream.Collectors.summingInt(ItemLista::getQuantidade)
                )).entrySet().stream()
                .collect(java.util.stream.Collectors.toMap(
                    Map.Entry::getKey,
                    e -> e.getValue().shortValue()
                ));

            itensAgrupados.forEach((itemOfertaId, quantidade) -> {
                ItemOferta itemOferta = em.find(ItemOferta.class, itemOfertaId);
                if (itemOferta == null) {
                    throw new RegistroNaoEncontradoException(itemOfertaId, "Item Oferta");
                }
                itemListasFinal.add(ItemLista.builder()
                    .itemOferta(itemOferta)
                    .quantidade(quantidade)
                    .listaCompra(null) // Será setado abaixo
                    .precoUnitario(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP))
                    .build());
            });
        }

        listaCompra.setItensLista(itemListasFinal);
        listaCompra.setStatusLista(em.getReference(StatusLista.class, EStatusLista.AGUARDANDO));
        listaCompra.recalcularTotais();
        
        ListaCompra finalListaCompra = repo.save(listaCompra);
        itemListasFinal.forEach(item -> item.setListaCompra(finalListaCompra));
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
    @Transactional
    public List<ItemListaRequest> adicionarItemLista(UUID listaId, List<ItemAdicionadoRequest> itemDTO) {
        List<ItemListaRequest> itens = itemListaService.adicionaLista(listaId, itemDTO);
        
        ListaCompra listaCompra = repo.findById(listaId)
                .orElseThrow(() -> new RegistroNaoEncontradoException(listaId, "Lista de compra"));
        
        listaCompra.recalcularTotais();
        repo.save(listaCompra);
        
        return itens;
    }

    @Override
    @Transactional
    public ResponsePage<ListaCompraRequest> listar(Pageable pageable) {
        Page<ListaCompra> listaCompra = repo.findAllByUsuarioId(pageable, SecurityContextUtils.getId());
        List<ListaCompraRequest> listaCompraRequest = listaCompra.getContent().stream().map(listaCompraMapper::toDto).toList();
        return new ResponsePage<>(listaCompraRequest, pageable.getPageNumber(), pageable.getPageSize(), listaCompra.getTotalElements());
    }

    @Override
    @Transactional
    public ListaCompraRequest buscarPorIdDto(UUID listaId) {
        return listaCompraMapper.toDto(repo.findById(listaId).orElseThrow(() -> new RegistroNaoEncontradoException(listaId, "Lista de compra")));
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
    @Transactional
    public Boolean alterarItens(UUID listaId, List<ItemAlteradoRequest> itensLista) {
        Boolean alterado = itemListaService.alterarItens(listaId, itensLista);
        if (alterado) {
            ListaCompra listaCompra = repo.findById(listaId)
                .orElseThrow(() -> new RegistroNaoEncontradoException(listaId, "Lista de compra"));
            listaCompra.recalcularTotais();
            repo.save(listaCompra);
        }
        return alterado;
    }

    @Override
    @Transactional
    public void concluirLista(@Valid ConcluirListaRequestDTO concluirListaRequestDTO) {
        List<PrecoReportadoPendenteDTO> precoReportadoPendenteDTOs = new LinkedList<>();
        ListaCompra listaCompra = repo.findById(concluirListaRequestDTO.id())
                .orElseThrow(() -> new RegistroNaoEncontradoException(concluirListaRequestDTO.id(), "Lista de compra"));

        listaCompra.getItensLista().forEach(itemLista -> {
            LocalDateTime dataInicioPromocao = itemLista.hasPromocao() ? LocalDateTime.now() : null;
            LocalDateTime dataFinalPromocao = itemLista.hasPromocao() ? LocalDateTime.now().plusDays(1) : null;
            precoReportadoPendenteDTOs.add(new PrecoReportadoPendenteDTO(null, itemLista.getItemOfertaId(), EStatusPrecoReportado.AGUARDANDO, itemLista.getItemOferta().getPreco(), null, (short) 0, itemLista.hasPromocao(), dataInicioPromocao, dataFinalPromocao));
        });

        itemListaService.salvarAllConcluindoLista(listaCompra.getItensLista());
        precoReportadoPendenteService.salvarAll(precoReportadoPendenteDTOs);
        
        listaCompra.setStatusLista(em.getReference(StatusLista.class, EStatusLista.FINALIZADA));
        listaCompra.recalcularTotais();
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

    @Override
    @Transactional
    public Boolean removerItem(UUID listaId, UUID itemId) {
        Boolean removido = itemListaService.removerItem(listaId, itemId);
        if (removido) {
            ListaCompra listaCompra = repo.findById(listaId)
                .orElseThrow(() -> new RegistroNaoEncontradoException(listaId, "Lista de compra"));
            listaCompra.recalcularTotais();
            repo.save(listaCompra);
        }
        return removido;
    }
}
