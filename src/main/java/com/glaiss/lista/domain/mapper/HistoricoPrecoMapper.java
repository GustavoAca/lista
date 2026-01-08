package com.glaiss.lista.domain.mapper;

import com.glaiss.lista.domain.model.HistoricoPreco;
import com.glaiss.lista.domain.model.ItemOferta;
import com.glaiss.lista.domain.model.dto.HistoricoPrecoDTO;
import org.springframework.stereotype.Component;

@Component
public class HistoricoPrecoMapper {

    public HistoricoPrecoDTO toDto(HistoricoPreco historicoPreco){
        return new HistoricoPrecoDTO(historicoPreco.getId(),
                historicoPreco.getItemOfertaId(),
                historicoPreco.getPreco(),
                historicoPreco.getHasPromocaoAtiva());
    }

    public HistoricoPreco toEntity(HistoricoPrecoDTO dto){
        return HistoricoPreco.builder()
                .id(dto.id())
                .itemOferta(ItemOferta.builder().id(dto.itemOfertaId()).build())
                .preco(dto.preco())
                .hasPromocaoAtiva(dto.hasPromocaoAtiva())
                .build();
    }
}
