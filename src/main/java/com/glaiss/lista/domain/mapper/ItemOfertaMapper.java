package com.glaiss.lista.domain.mapper;

import com.glaiss.lista.controller.itemoferta.dto.ItemOfertaDTO;
import com.glaiss.lista.controller.listacompra.dto.ItemOfertaConcluirRequest;
import com.glaiss.lista.domain.model.Item;
import com.glaiss.lista.domain.model.ItemOferta;
import com.glaiss.lista.domain.model.Vendedor;
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
                itemOferta.getDataFinalPromocao(),
                itemOferta.getVersion());
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
                .version(itemOfertaDTO.version())
                .build();
    }

    public ItemOferta itemOfertaConcluirRequestToEntity(ItemOfertaConcluirRequest itemOfertaConcluirRequest) {
        return ItemOferta.builder()
                .id(itemOfertaConcluirRequest.id())
                .hasPromocaoAtiva(itemOfertaConcluirRequest.hasPromocaoAtiva())
                .preco(itemOfertaConcluirRequest.preco())
                .item(Item.builder().id(itemOfertaConcluirRequest.itemId()).build())
                .vendedor(Vendedor.builder().id(itemOfertaConcluirRequest.vendedorId()).build())
                .dataInicioPromocao(itemOfertaConcluirRequest.dataInicioPromocao())
                .dataFinalPromocao(itemOfertaConcluirRequest.dataFinalPromocao())
                .version(itemOfertaConcluirRequest.version())
                .build();
    }
}
