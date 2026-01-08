package com.glaiss.lista.domain.service.listacompra;

import com.glaiss.core.domain.model.ResponsePage;
import com.glaiss.core.domain.service.BaseServiceImpl;
import com.glaiss.core.exception.RegistroNaoEncontradoException;
import com.glaiss.core.utils.SecurityContextUtils;
import com.glaiss.lista.controller.dto.ItemListaDTO;
import com.glaiss.lista.controller.dto.ListaCompraDTO;
import com.glaiss.lista.domain.mapper.ListaCompraMapper;
import com.glaiss.lista.domain.model.EStatusPrecoReportado;
import com.glaiss.lista.domain.model.ItemLista;
import com.glaiss.lista.domain.model.ListaCompra;
import com.glaiss.lista.domain.model.dto.PrecoReportadoPendenteDTO;
import com.glaiss.lista.domain.repository.ListaCompraRepository;
import com.glaiss.lista.domain.service.itemlista.ItemListaService;
import com.glaiss.lista.domain.service.itemoferta.ItemOfertaService;
import com.glaiss.lista.domain.service.precoreportado.PrecoReportadoPendenteService;
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

    protected ListaCompraServiceImpl(ListaCompraRepository repo,
                                     ListaCompraMapper listaCompraMapper,
                                     ItemListaService itemListaService,
                                     ItemOfertaService itemOfertaService,
                                     PrecoReportadoPendenteService precoReportadoPendenteService) {
        super(repo);
        this.listaCompraMapper = listaCompraMapper;
        this.itemListaService = itemListaService;
        this.itemOfertaService = itemOfertaService;
        this.precoReportadoPendenteService = precoReportadoPendenteService;
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
        listaCompra = repo.save(listaCompra);

        ListaCompra finalListaCompra = listaCompra;
        List<PrecoReportadoPendenteDTO> precoReportadoPendenteDTOs = new LinkedList<>();

        itemListas.forEach(itemLista -> {
            itemLista.setListaCompra(finalListaCompra);
            precoReportadoPendenteDTOs.add(new PrecoReportadoPendenteDTO(null, itemLista.getItemOfertaId(), EStatusPrecoReportado.AGUARDANDO, itemOfertaService.buscarValorPorItemOfertaId(itemLista.getItemOfertaId()), null, (short) 0, Boolean.FALSE, null, null));
        });

        itemListaService.salvarAll(itemListas);
        precoReportadoPendenteService.salvarAll(precoReportadoPendenteDTOs);
    }

    private BigDecimal calcularTotal(ListaCompraDTO listaCompraDTO){
        BigDecimal total;
        Map<UUID, Short> itemQuantidade = new LinkedHashMap<>();
        listaCompraDTO.itensLista().forEach(itemLista -> {
            itemQuantidade.put(itemLista.itemOfertaId(), itemLista.quantidade());
        });
        total = itemOfertaService.calcularValoresItens(itemQuantidade);
        if(listaCompraDTO.valorTotal().equals(total)){
            return listaCompraDTO.valorTotal();
        }
        return total;
    }

    @Override
    public ResponsePage<ItemListaDTO> listarItensPorListaCompraIdPaginaDTO(Pageable pageable, UUID listId) {
        return itemListaService.listarItensPorListaCompraIdPaginaDTO(pageable, listId);
    }

    @Override
    public List<ItemListaDTO> adicionarItemLista(UUID listaId, List<ItemListaDTO> itemDTO) {
        return itemListaService.adicionaLista(listaId, itemDTO);
    }

    @Override
    public ResponsePage<ListaCompraDTO> listar(Pageable pageable) {
        Page<ListaCompra> listaCompra = repo.findAllByUsuarioId(pageable, SecurityContextUtils.getId());
        List<ListaCompraDTO> listaCompraDto = listaCompra.getContent().stream().map(listaCompraMapper::toDto).toList();
        return new ResponsePage<>(listaCompraDto, pageable.getPageNumber(), pageable.getPageSize(), listaCompra.getTotalElements());
    }

    @Override
    public Boolean removerDaLista(UUID listaId, List<UUID> itensLista) {
        return itemListaService.removerDaLista(listaId, itensLista);
    }

    @Override
    public Boolean deletar(UUID id) {
        ListaCompra listaCompra = repo.findById(id).orElseThrow(() -> new RegistroNaoEncontradoException(id, "Lista de compra"));
        if (listaCompra.getUsuarioId().equals(SecurityContextUtils.getId())) {
            return false;
        }
        return super.deletar(listaCompra.getId());
    }
}
