package com.glaiss.lista.domain.service.precoreportado;

import com.glaiss.core.domain.model.ResponsePage;
import com.glaiss.lista.domain.model.EStatusPrecoReportado;
import com.glaiss.lista.domain.model.dto.PrecoReportadoPendenteDTO;
import com.glaiss.lista.domain.service.itemoferta.ItemOfertaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PrecoReportadoScheduling {

    private final PrecoReportadoPendenteService precoReportadoPendenteService;
    private final ItemOfertaService itemOfertaService;

    private static final int TAMANHO_PAGINA = 20;

    public PrecoReportadoScheduling(PrecoReportadoPendenteService precoReportadoPendenteService,
                                    ItemOfertaService itemOfertaService) {
        this.precoReportadoPendenteService = precoReportadoPendenteService;
        this.itemOfertaService = itemOfertaService;
    }

    @Async
    @Scheduled(fixedRate = 300000)
    public void processar() {
        log.debug("--- Iniciando processamento de preços pendentes em: " + new java.util.Date() + " ---");

        int numeroPaginaAtual = 0;
        ResponsePage<PrecoReportadoPendenteDTO> pagina;

        do {
            Pageable pageable = PageRequest.of(numeroPaginaAtual, TAMANHO_PAGINA);
            pagina = precoReportadoPendenteService.listarPorStatusPrecoReportado(pageable, List.of(EStatusPrecoReportado.AGUARDANDO, EStatusPrecoReportado.ERRO));

            if (pagina.hasContent()) {
                pagina.getContent().forEach(dto -> {
                    try {
                        itemOfertaService.atualizarPrecoReportado(dto.itemOferta(), dto);
                        precoReportadoPendenteService.concluirProcessamento(dto);
                    } catch (Exception e) {
                        PrecoReportadoPendenteDTO PrecoReportadoPendenteDTOErro = new PrecoReportadoPendenteDTO(dto.id(),
                                dto.itemOferta(),
                                dto.statusPrecoReportadoId(),
                                dto.preco(),
                                e.getMessage(),
                                dto.tentativas(),
                                dto.hasPromocaoAtiva(),
                                dto.dataInicioPromocao(),
                                dto.dataFinalPromocao());
                        precoReportadoPendenteService.atualizarTentativas(PrecoReportadoPendenteDTOErro);
                    }
                });
            }

            numeroPaginaAtual++;

        } while (pagina.hasNext());

        log.debug("--- Finalizando leitura do banco de dados em: " + new java.util.Date() + " ---");
    }

    @Async
    @Scheduled(fixedRate = 400000)
    public void limparHistorico() {
        log.debug("--- Iniciando limpeza de preços finalizado em: " + new java.util.Date() + " ---");

        int numeroPaginaAtual = 0;
        ResponsePage<PrecoReportadoPendenteDTO> pagina;

        do {
            Pageable pageable = PageRequest.of(numeroPaginaAtual, TAMANHO_PAGINA);
            pagina = precoReportadoPendenteService.listarPorStatusPrecoReportado(pageable, List.of(EStatusPrecoReportado.FINALIZADO));

            if (pagina.hasContent()) {
                pagina.getContent().forEach(dto -> {
                    precoReportadoPendenteService.excluir(dto.id());
                });
            }

            numeroPaginaAtual++;

        } while (pagina.hasNext());

        log.debug("--- Finalizando limpeza de preços finalizado em: " + new java.util.Date() + " ---");
    }
}
