package com.glaiss.lista.domain.service.itemlista;

import com.glaiss.core.domain.model.ResponsePage;
import com.glaiss.core.exception.RegistroNaoEncontradoException;
import com.glaiss.lista.controller.listacompra.dto.ItemAdicionadoRequest;
import com.glaiss.lista.controller.listacompra.dto.ItemAlteradoRequest;
import com.glaiss.lista.controller.listacompra.dto.ItemListaRequest;
import com.glaiss.lista.domain.exception.AdicionarItemListaException;
import com.glaiss.lista.domain.mapper.ItemListaMapper;
import com.glaiss.lista.domain.model.ItemLista;
import com.glaiss.lista.domain.model.ItemOferta;
import com.glaiss.lista.domain.model.ListaCompra;
import com.glaiss.lista.domain.model.dto.projection.listacompra.ItemListaProjection;
import com.glaiss.lista.domain.repository.ItemListaRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemListaServiceImplTest {

    @Mock
    private ItemListaRepository repo;

    @Mock
    private ItemListaMapper itemListaMapper;

    @Mock
    private EntityManager em;

    @InjectMocks
    private ItemListaServiceImpl service;

    @Nested
    class ListarItensPorListaCompraIdPaginaDTO {
        @Test
        void quando_listar_itens_entao_deve_retornar_pagina() {
            Pageable pageable = PageRequest.of(0, 10);
            UUID listId = UUID.randomUUID();
            Page<ItemListaProjection> page = new PageImpl<>(Collections.emptyList(), pageable, 0);

            when(repo.findAllByListaCompra_Id(eq(pageable), eq(listId))).thenReturn(page);

            ResponsePage<ItemListaProjection> result = service.listarItensPorListaCompraIdPaginaDTO(pageable, listId);

            assertNotNull(result);
            verify(repo).findAllByListaCompra_Id(eq(pageable), eq(listId));
        }
    }

    @Nested
    class AdicionaLista {
        @Test
        void quando_adicionar_itens_novos_entao_deve_salvar_com_sucesso() {
            UUID listaId = UUID.randomUUID();
            UUID itemOfertaId = UUID.randomUUID();
            ItemAdicionadoRequest request = new ItemAdicionadoRequest(itemOfertaId, (short) 5);
            List<ItemAdicionadoRequest> itensDto = Collections.singletonList(request);
            
            ListaCompra listaCompraRef = mock(ListaCompra.class);
            ItemOferta itemOfertaRef = mock(ItemOferta.class);
            
            when(em.getReference(ListaCompra.class, listaId)).thenReturn(listaCompraRef);
            when(repo.findAllByListaCompra_IdAndItemOferta_IdIn(eq(listaId), anyList())).thenReturn(Collections.emptyList());
            when(em.getReference(ItemOferta.class, itemOfertaId)).thenReturn(itemOfertaRef);
            
            ItemLista itemSalvo = ItemLista.builder().build();
            when(repo.saveAll(anyList())).thenReturn(Collections.singletonList(itemSalvo));
            when(itemListaMapper.toDto(itemSalvo)).thenReturn(mock(ItemListaRequest.class));
            
            List<ItemListaRequest> result = service.adicionaLista(listaId, itensDto);
            
            assertNotNull(result);
            assertEquals(1, result.size());
            verify(repo).saveAll(anyList());
        }

        @Test
        void quando_adicionar_item_existente_entao_deve_atualizar_quantidade() {
            UUID listaId = UUID.randomUUID();
            UUID itemOfertaId = UUID.randomUUID();
            ItemAdicionadoRequest request = new ItemAdicionadoRequest(itemOfertaId, (short) 5);
            
            ItemLista itemExistente = spy(ItemLista.builder()
                    .quantidade((short) 2)
                    .itemOferta(ItemOferta.builder().id(itemOfertaId).build())
                    .build());
            
            when(em.getReference(eq(ListaCompra.class), any())).thenReturn(mock(ListaCompra.class));
            when(repo.findAllByListaCompra_IdAndItemOferta_IdIn(eq(listaId), anyList())).thenReturn(Collections.singletonList(itemExistente));
            
            when(repo.saveAll(anyList())).thenReturn(Collections.singletonList(itemExistente));
            
            service.adicionaLista(listaId, Collections.singletonList(request));
            
            verify(itemExistente).adicionarQuantidade((short) 5);
        }

        @Test
        void quando_ocorrer_erro_entao_deve_lancar_excecao() {
            UUID listaId = UUID.randomUUID();
            when(em.getReference(eq(ListaCompra.class), any())).thenThrow(new RuntimeException());
            
            assertThrows(RuntimeException.class, () -> service.adicionaLista(listaId, new ArrayList<>()));
        }
    }

    @Nested
    class AlterarItens {
        @Test
        void quando_alterar_quantidade_entao_deve_salvar() {
            UUID listaId = UUID.randomUUID();
            UUID itemId = UUID.randomUUID();
            ItemAlteradoRequest request = new ItemAlteradoRequest(itemId, (short) 10);
            
            ItemLista item = spy(ItemLista.builder()
                    .id(itemId)
                    .build());
            
            when(repo.findAllByIdInAndListaCompra_Id(anyList(), eq(listaId))).thenReturn(Collections.singletonList(item));
            
            assertTrue(service.alterarItens(listaId, Collections.singletonList(request)));
            
            verify(item).alterarQuantidade((short) 10);
            verify(repo).saveAll(anyList());
        }

        @Test
        void quando_quantidade_zero_ou_negativa_entao_deve_deletar() {
            UUID listaId = UUID.randomUUID();
            UUID itemId = UUID.randomUUID();
            ItemAlteradoRequest request = new ItemAlteradoRequest(itemId, (short) 0);
            
            ItemLista item = ItemLista.builder()
                    .id(itemId)
                    .quantidade((short) 0)
                    .build();
            
            when(repo.findAllByIdInAndListaCompra_Id(anyList(), eq(listaId))).thenReturn(Collections.singletonList(item));
            
            assertTrue(service.alterarItens(listaId, Collections.singletonList(request)));
            
            verify(repo).deleteAll(anyList());
        }
    }

    @Nested
    class SalvarAllConcluindoLista {
        @Test
        void quando_concluir_lista_entao_deve_setar_preco_unitario_e_salvar() {
            ItemOferta itemOferta = ItemOferta.builder()
                    .preco(new BigDecimal("10.50"))
                    .build();
            
            ItemLista item = ItemLista.builder()
                    .itemOferta(itemOferta)
                    .build();
            
            service.salvarAllConcluindoLista(Collections.singletonList(item));
            
            assertEquals(new BigDecimal("10.50"), item.getPrecoUnitario());
            verify(repo).save(item);
        }
    }

    @Nested
    class RemoverItem {
        @Test
        void quando_remover_item_da_lista_correta_entao_deve_deletar() {
            UUID listaId = UUID.randomUUID();
            UUID itemId = UUID.randomUUID();
            
            ListaCompra lista = ListaCompra.builder().id(listaId).build();
            
            ItemLista item = ItemLista.builder()
                    .listaCompra(lista)
                    .build();
            
            when(repo.findById(itemId)).thenReturn(Optional.of(item));
            
            assertTrue(service.removerItem(listaId, itemId));
            verify(repo).delete(item);
        }

        @Test
        void quando_remover_item_de_lista_diferente_entao_deve_retornar_falso() {
            UUID listaId = UUID.randomUUID();
            UUID itemId = UUID.randomUUID();
            
            ListaCompra lista = ListaCompra.builder().id(UUID.randomUUID()).build();
            
            ItemLista item = ItemLista.builder()
                    .listaCompra(lista)
                    .build();
            
            when(repo.findById(itemId)).thenReturn(Optional.of(item));
            
            assertFalse(service.removerItem(listaId, itemId));
            verify(repo, never()).delete(any());
        }
    }
}
