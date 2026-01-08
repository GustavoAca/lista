package com.glaiss.lista.domain.model.dto;


import com.glaiss.lista.domain.model.EStatusPrecoReportado;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PrecoReportadoPendenteDTO(UUID id,
                                        UUID itemOferta,
                                        EStatusPrecoReportado statusPrecoReportadoId,
                                        BigDecimal preco,
                                        String mensagemErro,
                                        short tentativas,
                                        Boolean hasPromocaoAtiva,
                                        LocalDateTime dataInicioPromocao,
                                        LocalDateTime dataFinalPromocao) {
}
