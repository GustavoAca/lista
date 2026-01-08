package com.glaiss.lista.domain.mapper;

import com.glaiss.lista.domain.model.Item;
import com.glaiss.lista.domain.model.ItemOferta;
import com.glaiss.lista.domain.model.Vendedor;
import com.glaiss.lista.domain.model.dto.ItemOfertaDTO;
import org.springframework.stereotype.Component;

@Component
public class ItemOfertaMapper {

    public ItemOfertaDTO toDto(ItemOferta itemOferta){
        return new ItemOfertaDTO(itemOferta.getId(),
                itemOferta.getItemId(),
                itemOferta.getVendedorId(),
                itemOferta.getHasPromocaoAtiva(),
                itemOferta.getPreco(),
                itemOferta.getDataInicioPromocao(),
                itemOferta.getDataFinalPromocao());
    }

    public ItemOferta toEntity(ItemOfertaDTO itemOfertaDTO){
        return ItemOferta.builder()
                .id(itemOfertaDTO.id())
                .hasPromocaoAtiva(itemOfertaDTO.hasPromocaoAtiva())
                .preco(itemOfertaDTO.preco())
                .item(Item.builder().id(itemOfertaDTO.itemId()).build())
                .vendedor(Vendedor.builder().id(itemOfertaDTO.vendedorId()).build())
                .dataInicioPromocao(itemOfertaDTO.dataInicioPromocao())
                .dataFinalPromocao(itemOfertaDTO.dataFinalPromocao())
                .build();
    }
}
