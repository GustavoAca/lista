package com.glaiss.lista.domain.service.itemoferta;

import com.glaiss.core.domain.model.ResponsePage;
import com.glaiss.core.domain.service.BaseService;
import com.glaiss.lista.domain.model.ItemOferta;
import com.glaiss.lista.domain.model.dto.ItemOfertaDTO;
import com.glaiss.lista.domain.model.dto.PrecoReportadoPendenteDTO;
import com.glaiss.lista.domain.model.dto.projection.vendedor.ItemOfertaProjection;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

public interface ItemOfertaService extends BaseService<ItemOferta, UUID> {

    ResponsePage<ItemOfertaDTO> listarPaginaDTO(org.springframework.data.domain.Pageable pageable);

    ItemOfertaDTO salvar(ItemOfertaDTO item);

    BigDecimal calcularValoresItens(Map<UUID, Short> itensIds);

    BigDecimal buscarValorPorItemOfertaId(UUID itemOfertaId);

    Boolean atualizarPrecoReportado(UUID id, PrecoReportadoPendenteDTO precoReportadoPendenteDTO);

    void atualizar(UUID id, ItemOfertaDTO itemOfertaDTO);

    ResponsePage<ItemOfertaDTO> listarPaginaPorItem(Pageable pageable, UUID itemId);

    ResponsePage<ItemOfertaProjection> listarPaginaPorVendedor(Pageable pageable, UUID vendedorId);
}
