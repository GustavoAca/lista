package com.glaiss.lista.domain.service.itemlista;

import com.glaiss.core.domain.model.ResponsePage;
import com.glaiss.core.domain.service.BaseServiceImpl;
import com.glaiss.lista.controller.dto.ItemListaDTO;
import com.glaiss.lista.domain.mapper.ItemListaMapper;
import com.glaiss.lista.domain.model.ItemLista;
import com.glaiss.lista.domain.repository.ItemListaRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
public class ItemListaServiceImpl extends BaseServiceImpl<ItemLista, UUID, ItemListaRepository> implements ItemListaService {

    private final ItemListaMapper itemListaMapper;

    protected ItemListaServiceImpl(ItemListaRepository repo,
                                   ItemListaMapper itemListaMapper) {
        super(repo);
        this.itemListaMapper = itemListaMapper;
    }

    @Override
    public ResponsePage<ItemListaDTO> listarItensPorListaCompraIdPaginaDTO(Pageable pageable, UUID listId) {
        Page<ItemLista> itemPage = repo.findAllByListaCompraId(pageable, listId);
        List<ItemListaDTO> listaItem = itemPage.getContent().stream().map(itemListaMapper::toDto).toList();
        return new ResponsePage<>(listaItem, pageable.getPageNumber(), pageable.getPageSize(), itemPage.getTotalElements());
    }

    @Override
    public List<ItemListaDTO> adicionaLista(UUID listaId, List<ItemListaDTO> itemDTO) {
        List<ItemListaDTO> itens = new LinkedList<>();
        itemDTO.forEach( i-> {
            itens.add(new ItemListaDTO(i.id(), listaId, i.itemOfertaId(), i.quantidade()));
        });

        return repo.saveAll(itens.stream()
                        .map(itemListaMapper::toEntity)
                        .toList())
                .stream().map(itemListaMapper::toDto)
                .toList();
    }

    @Override
    public void salvarAll(List<ItemLista> itemListas) {
        repo.saveAll(itemListas);
    }

    @Override
    @Transactional
    public Boolean removerDaLista(UUID listaId, List<UUID> itensLista) {
        try{
            repo.deleteItensDaLista(listaId, itensLista);
            return Boolean.TRUE;
        } catch (Exception e){
            return Boolean.FALSE;
        }
    }
}
