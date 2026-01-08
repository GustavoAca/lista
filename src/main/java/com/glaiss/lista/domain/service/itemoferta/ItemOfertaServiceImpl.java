package com.glaiss.lista.domain.service.itemoferta;

import com.glaiss.core.domain.model.ResponsePage;
import com.glaiss.core.domain.service.BaseServiceImpl;
import com.glaiss.core.exception.RegistroNaoEncontradoException;
import com.glaiss.lista.domain.mapper.ItemOfertaMapper;
import com.glaiss.lista.domain.model.ItemOferta;
import com.glaiss.lista.domain.model.dto.HistoricoPrecoDTO;
import com.glaiss.lista.domain.model.dto.ItemOfertaDTO;
import com.glaiss.lista.domain.model.dto.PrecoReportadoPendenteDTO;
import com.glaiss.lista.domain.repository.ItemOfertaRepository;
import com.glaiss.lista.domain.service.historicopreco.HistoricoPrecoService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ItemOfertaServiceImpl extends BaseServiceImpl<ItemOferta, UUID, ItemOfertaRepository> implements ItemOfertaService {

    private final ItemOfertaMapper itemOfertaMapper;
    private final HistoricoPrecoService historicoPrecoService;

    protected ItemOfertaServiceImpl(ItemOfertaRepository repo,
                                    ItemOfertaMapper itemOfertaMapper,
                                    HistoricoPrecoService historicoPrecoService) {
        super(repo);
        this.itemOfertaMapper = itemOfertaMapper;
        this.historicoPrecoService = historicoPrecoService;
    }

    @Override
    public ItemOfertaDTO salvar(ItemOfertaDTO item) {
        ItemOferta itemEntity = itemOfertaMapper.toEntity(item);
        return itemOfertaMapper.toDto(salvar(itemEntity));
    }

    @Override
    public BigDecimal calcularValoresItens(Map<UUID, Short> itensIds) {
        if (itensIds == null || itensIds.isEmpty()) {
            return BigDecimal.ZERO;
        }

        List<ItemOferta> ofertas = repo.findAllById(itensIds.keySet());

        if (ofertas.size() != itensIds.size()) {
            throw new RegistroNaoEncontradoException("Um ou mais itens da oferta não foram encontrados na base de dados.");
        }

        return ofertas.stream()
                .map(oferta -> {
                    Short quantidade = itensIds.get(oferta.getId());
                    return oferta.getPreco().multiply(BigDecimal.valueOf(quantidade));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal buscarValorPorItemOfertaId(UUID itemOfertaId) {
        return repo.findById(itemOfertaId)
                .orElseThrow(() -> new RegistroNaoEncontradoException(itemOfertaId, "Item oferta"))
                .getPreco();
    }

    @Override
    public ResponsePage<ItemOfertaDTO> listarPaginaDTO(org.springframework.data.domain.Pageable pageable) {
        Page<ItemOferta> itemPage = repo.findAll(pageable);
        var listaItem = itemPage.getContent().stream().map(itemOfertaMapper::toDto).toList();
        return new ResponsePage<>(listaItem, pageable.getPageNumber(), pageable.getPageSize(), itemPage.getTotalElements());
    }

    @Override
    @Transactional
    public Boolean atualizarPrecoReportado(UUID id, PrecoReportadoPendenteDTO precoReportadoPendenteDTO) {
        try {
            ItemOferta itemOferta = repo.findById(id)
                    .orElseThrow(() -> new RegistroNaoEncontradoException(id, "Item oferta"));
            itemOferta.setPreco(itemOferta.getPreco());
            if (precoReportadoPendenteDTO.hasPromocaoAtiva()) {
                itemOferta.setHasPromocaoAtiva(Boolean.TRUE);
                itemOferta.setDataInicioPromocao(precoReportadoPendenteDTO.dataInicioPromocao());
                itemOferta.setDataFinalPromocao(precoReportadoPendenteDTO.dataFinalPromocao());
            }
            historicoPrecoService.salvar(new HistoricoPrecoDTO(null, itemOferta.getId(), itemOferta.getPreco(), itemOferta.getHasPromocaoAtiva()));
            salvar(itemOferta);
            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public void atualizar(UUID id, ItemOfertaDTO itemOfertaDTO) {
        ItemOferta itemOferta = repo.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException(id, "Item oferta"));
        itemOferta.setPreco(itemOferta.getPreco());
        if (itemOfertaDTO.hasPromocaoAtiva()) {
            itemOferta.setHasPromocaoAtiva(Boolean.TRUE);
            itemOferta.setDataInicioPromocao(itemOfertaDTO.dataInicioPromocao());
            itemOferta.setDataFinalPromocao(itemOfertaDTO.dataFinalPromocao());
        }
        salvar(itemOferta);
    }

    @Override
    public ResponsePage<ItemOfertaDTO> listarPaginaPorItem(Pageable pageable, UUID itemId){
        Page<ItemOferta> itemPage = repo.findByItemId(itemId, pageable);
        var listaItem = itemPage.getContent().stream().map(itemOfertaMapper::toDto).toList();
        return new ResponsePage<>(listaItem, pageable.getPageNumber(), pageable.getPageSize(), itemPage.getTotalElements());
    }
}
