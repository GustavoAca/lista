package com.glaiss.lista.domain.mapper;

import com.glaiss.lista.domain.model.ItemOferta;
import com.glaiss.lista.domain.model.PrecoReportadoPendente;
import com.glaiss.lista.domain.model.StatusPrecoReportado;
import com.glaiss.lista.domain.model.dto.PrecoReportadoPendenteDTO;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;

@Component
public class PrecoReportadoPendenteMapper {

    private final EntityManager em;

    public PrecoReportadoPendenteMapper(EntityManager em) {
        this.em = em;
    }


    public PrecoReportadoPendenteDTO toDto(PrecoReportadoPendente entity){
        return new PrecoReportadoPendenteDTO(entity.getId(),
                entity.getItemOfertaId(),
                entity.getStatusPrecoReportadoId(),
                entity.getPreco(),
                entity.getMensagemErro(),
                entity.getTentativas(),
                entity.getHasPromocaoAtiva(),
                entity.getDataInicioPromocao(),
                entity.getDataFinalPromocao());
    }

    public PrecoReportadoPendente toEntity(PrecoReportadoPendenteDTO dto){
        return PrecoReportadoPendente.builder()
                .id(dto.id())
                .preco(dto.preco())
                .itemOferta(em.getReference(ItemOferta.class,dto.itemOferta()))
                .statusPrecoReportado(em.getReference(StatusPrecoReportado.class, dto.statusPrecoReportadoId()))
                .mensagemErro(dto.mensagemErro())
                .tentativas(dto.tentativas())
                .hasPromocaoAtiva(dto.hasPromocaoAtiva())
                .dataInicioPromocao(dto.dataInicioPromocao())
                .dataFinalPromocao(dto.dataFinalPromocao())
                .build();
    }
}
