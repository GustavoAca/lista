package com.glaiss.lista.domain.model.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record HistoricoPrecoDTO(UUID id,
                                UUID itemOfertaId,
                                BigDecimal preco,
                                Boolean hasPromocaoAtiva) {
}
