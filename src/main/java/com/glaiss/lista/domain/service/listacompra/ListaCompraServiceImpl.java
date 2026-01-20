package com.glaiss.lista.domain.service.listacompra;

import com.glaiss.core.domain.model.ResponsePage;
import com.glaiss.core.domain.service.BaseServiceImpl;
import com.glaiss.core.exception.RegistroNaoEncontradoException;
import com.glaiss.core.utils.SecurityContextUtils;
import com.glaiss.lista.controller.dto.ItemAdicionadoDTO;
import com.glaiss.lista.controller.dto.ItemAlteradoDTO;
import com.glaiss.lista.controller.dto.ItemListaDTO;
import com.glaiss.lista.controller.dto.ListaCompraDTO;
import com.glaiss.lista.domain.mapper.ListaCompraMapper;
import com.glaiss.lista.domain.model.*;
import com.glaiss.lista.domain.model.dto.PrecoReportadoPendenteDTO;
import com.glaiss.lista.domain.model.dto.projection.ItemListaProjection;
import com.glaiss.lista.domain.repository.ListaCompraRepository;
import com.glaiss.lista.domain.service.itemlista.ItemListaService;
import com.glaiss.lista.domain.service.itemoferta.ItemOfertaService;
import com.glaiss.lista.domain.service.precoreportado.PrecoReportadoPendenteService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    public void criarLista(ListaCompraDTO listaCompraDTO) {
        ListaCompra listaCompra = listaCompraMapper.toEntity(listaCompraDTO);
        listaCompra.setId(null);
        listaCompra.setUsuarioId(SecurityContextUtils.getId());
        listaCompra.setValorTotal(calcularTotal(listaCompraDTO));
        List<ItemLista> itemListas = listaCompra.getItensLista();
        listaCompra.setItensLista(null);
        listaCompra.setTotalItens((short) itemListas.size());
        listaCompra.setStatusLista(em.getReference(StatusLista.class, EStatusLista.AGUARDANDO));
        listaCompra = repo.save(listaCompra);

        ListaCompra finalListaCompra = listaCompra;
        List<PrecoReportadoPendenteDTO> precoReportadoPendenteDTOs = new LinkedList<>();

        itemListas.forEach(itemLista -> {
            itemLista.setItemOferta(em.getReference(ItemOferta.class, itemLista.getItemOfertaId()));
            itemLista.setListaCompra(finalListaCompra);
            precoReportadoPendenteDTOs.add(new PrecoReportadoPendenteDTO(null, itemLista.getItemOfertaId(), EStatusPrecoReportado.AGUARDANDO, itemOfertaService.buscarValorPorItemOfertaId(itemLista.getItemOfertaId()), null, (short) 0, Boolean.FALSE, null, null));
        });

        itemListaService.salvarAll(itemListas);
        precoReportadoPendenteService.salvarAll(precoReportadoPendenteDTOs);
    }

    private BigDecimal calcularTotal(ListaCompraDTO listaCompraDTO) {
        BigDecimal total;
        Map<UUID, Short> itemQuantidade = new LinkedHashMap<>();
        listaCompraDTO.itensLista().forEach(itemLista -> {
            itemQuantidade.put(itemLista.itemOfertaId(), itemLista.quantidade());
        });
        total = itemOfertaService.calcularValoresItens(itemQuantidade);
        if (listaCompraDTO.valorTotal().subtract(total).compareTo(BigDecimal.ZERO) == 0) {
            return listaCompraDTO.valorTotal();
        }
        return total;
    }

    @Override
    public ResponsePage<ItemListaProjection> listarItensPorListaCompraIdPaginaDTO(Pageable pageable, UUID listId) {
        return itemListaService.listarItensPorListaCompraIdPaginaDTO(pageable, listId);
    }

    @Override
    public List<ItemListaDTO> adicionarItemLista(UUID listaId, List<ItemAdicionadoDTO> itemDTO) {
        List<ItemListaDTO> itens = itemListaService.adicionaLista(listaId, itemDTO);
        atualizarTotaisLista(listaId);
        return itens;
    }

    @Override
    public ResponsePage<ListaCompraDTO> listar(Pageable pageable) {
        Page<ListaCompra> listaCompra = repo.findAllByUsuarioId(pageable, SecurityContextUtils.getId());
        List<ListaCompraDTO> listaCompraDto = listaCompra.getContent().stream().map(listaCompraMapper::toDto).toList();
        return new ResponsePage<>(listaCompraDto, pageable.getPageNumber(), pageable.getPageSize(), listaCompra.getTotalElements());
    }

    @Override
    public ListaCompraDTO buscarPorIdDto(UUID listaId) {
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
        if (listaCompra.getUsuarioId().equals(SecurityContextUtils.getId())) {
            return false;
        }
        return super.deletar(listaCompra.getId());
    }

    @Override
    public Boolean alterarItens(UUID listaId, List<ItemAlteradoDTO> itensLista) {
        Boolean removido = itemListaService.alterarItens(listaId, itensLista);
        if (removido) {
            atualizarTotaisLista(listaId);
        }
        return removido;
    }
}
