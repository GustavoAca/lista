package com.glaiss.lista.domain.service.listacompra;

import com.glaiss.core.domain.model.ResponsePage;
import com.glaiss.core.exception.RegistroNaoEncontradoException;
import com.glaiss.core.utils.SecurityContextUtils;
import com.glaiss.lista.controller.listacompra.dto.*;
import com.glaiss.lista.domain.mapper.ListaCompraMapper;
import com.glaiss.lista.domain.model.*;
import com.glaiss.lista.domain.repository.ListaCompraRepository;
import com.glaiss.lista.domain.service.itemlista.ItemListaService;
import com.glaiss.lista.domain.service.itemoferta.ItemOfertaService;
import com.glaiss.lista.domain.service.precoreportado.PrecoReportadoPendenteService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
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
class ListaCompraServiceImplTest {

    @Mock
    private ListaCompraRepository repo;

    @Mock
    private ListaCompraMapper listaCompraMapper;

    @Mock
    private ItemListaService itemListaService;

    @Mock
    private ItemOfertaService itemOfertaService;

    @Mock
    private PrecoReportadoPendenteService precoReportadoPendenteService;

    @Mock
    private EntityManager em;

    @InjectMocks
    private ListaCompraServiceImpl service;

    private UUID userId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
    }

    private ListaCompraRequest createListaCompraRequest() {
        return new ListaCompraRequest(UUID.randomUUID(), userId, "Lista", BigDecimal.ZERO, (short) 0, Collections.emptyList(), 0L, EStatusLista.AGUARDANDO, null, null);
    }

    @Nested
    class CriarLista {

        @Test
        void dado_uma_lista_valida_quando_criar_lista_entao_deve_salvar_com_sucesso() {
            try (MockedStatic<SecurityContextUtils> mockedSecurity = mockStatic(SecurityContextUtils.class)) {
                mockedSecurity.when(SecurityContextUtils::getId).thenReturn(userId);

                ListaCompraRequest request = createListaCompraRequest();
                ListaCompra listaCompra = ListaCompra.builder()
                        .itensLista(new ArrayList<>())
                        .build();
                
                when(listaCompraMapper.toEntity(request)).thenReturn(listaCompra);
                when(em.getReference(eq(StatusLista.class), any())).thenReturn(StatusLista.builder().build());
                when(repo.save(any())).thenReturn(listaCompra);

                service.criarLista(request);

                verify(repo).save(listaCompra);
                verify(itemListaService).salvarAll(any());
                assertEquals(userId, listaCompra.getUsuarioId());
            }
        }

        @Test
        void dado_uma_lista_com_itens_quando_criar_lista_entao_deve_agrupar_e_salvar() {
            try (MockedStatic<SecurityContextUtils> mockedSecurity = mockStatic(SecurityContextUtils.class)) {
                mockedSecurity.when(SecurityContextUtils::getId).thenReturn(userId);

                ListaCompraRequest request = createListaCompraRequest();
                UUID itemOfertaId = UUID.randomUUID();
                
                ItemLista item1 = ItemLista.builder()
                        .itemOferta(ItemOferta.builder().id(itemOfertaId).build())
                        .quantidade((short) 2)
                        .build();
                
                ItemLista item2 = ItemLista.builder()
                        .itemOferta(ItemOferta.builder().id(itemOfertaId).build())
                        .quantidade((short) 3)
                        .build();

                ListaCompra listaCompra = ListaCompra.builder()
                        .itensLista(new ArrayList<>(Arrays.asList(item1, item2)))
                        .build();

                ItemOferta itemOferta = mock(ItemOferta.class);
                
                when(listaCompraMapper.toEntity(request)).thenReturn(listaCompra);
                when(em.find(ItemOferta.class, itemOfertaId)).thenReturn(itemOferta);
                when(em.getReference(eq(StatusLista.class), any())).thenReturn(StatusLista.builder().build());
                when(repo.save(any())).thenReturn(listaCompra);

                service.criarLista(request);

                verify(repo).save(any());
                verify(itemListaService).salvarAll(argThat(list -> list.size() == 1 && list.get(0).getQuantidade() == 5));
            }
        }

        @Test
        void dado_item_oferta_nao_encontrado_quando_criar_lista_entao_deve_lancar_excecao() {
            try (MockedStatic<SecurityContextUtils> mockedSecurity = mockStatic(SecurityContextUtils.class)) {
                mockedSecurity.when(SecurityContextUtils::getId).thenReturn(userId);

                ListaCompraRequest request = createListaCompraRequest();
                UUID itemOfertaId = UUID.randomUUID();
                ItemLista item = ItemLista.builder()
                        .itemOferta(ItemOferta.builder().id(itemOfertaId).build())
                        .build();
                
                ListaCompra listaCompra = ListaCompra.builder()
                        .itensLista(Collections.singletonList(item))
                        .build();

                when(listaCompraMapper.toEntity(request)).thenReturn(listaCompra);
                when(em.find(ItemOferta.class, itemOfertaId)).thenReturn(null);

                assertThrows(RegistroNaoEncontradoException.class, () -> service.criarLista(request));
            }
        }
    }

    @Nested
    class ListarItensPorListaCompraIdPaginaDTO {
        @Test
        void quando_listar_itens_entao_deve_chamar_item_lista_service() {
            Pageable pageable = PageRequest.of(0, 10);
            UUID listId = UUID.randomUUID();
            
            service.listarItensPorListaCompraIdPaginaDTO(pageable, listId);
            
            verify(itemListaService).listarItensPorListaCompraIdPaginaDTO(pageable, listId);
        }
    }

    @Nested
    class AdicionarItemLista {
        @Test
        void quando_adicionar_item_entao_deve_recalcular_totais_e_salvar() {
            UUID listaId = UUID.randomUUID();
            List<ItemAdicionadoRequest> items = new ArrayList<>();
            ListaCompra listaCompra = spy(ListaCompra.builder().build());
            
            when(repo.findById(listaId)).thenReturn(Optional.of(listaCompra));
            
            service.adicionarItemLista(listaId, items);
            
            verify(itemListaService).adicionaLista(listaId, items);
            verify(listaCompra).recalcularTotais();
            verify(repo).save(listaCompra);
        }
    }

    @Nested
    class Listar {
        @Test
        void quando_listar_entao_deve_retornar_pagina_de_requests() {
            try (MockedStatic<SecurityContextUtils> mockedSecurity = mockStatic(SecurityContextUtils.class)) {
                mockedSecurity.when(SecurityContextUtils::getId).thenReturn(userId);
                Pageable pageable = PageRequest.of(0, 10);
                ListaCompra lista = ListaCompra.builder().build();
                Page<ListaCompra> page = new PageImpl<>(Collections.singletonList(lista), pageable, 1);
                
                when(repo.findAllByUsuarioId(pageable, userId)).thenReturn(page);
                when(listaCompraMapper.toDto(lista)).thenReturn(createListaCompraRequest());
                
                ResponsePage<ListaCompraRequest> result = service.listar(pageable);
                
                assertNotNull(result);
                assertEquals(1, result.getContent().size());
            }
        }
    }

    @Nested
    class BuscarPorIdDto {
        @Test
        void quando_buscar_por_id_entao_deve_retornar_dto() {
            UUID id = UUID.randomUUID();
            ListaCompra lista = ListaCompra.builder().build();
            when(repo.findById(id)).thenReturn(Optional.of(lista));
            when(listaCompraMapper.toDto(lista)).thenReturn(createListaCompraRequest());
            
            assertNotNull(service.buscarPorIdDto(id));
        }
    }

    @Nested
    class Deletar {
        @Test
        void quando_deletar_lista_do_usuario_entao_deve_retornar_verdadeiro() {
            try (MockedStatic<SecurityContextUtils> mockedSecurity = mockStatic(SecurityContextUtils.class)) {
                mockedSecurity.when(SecurityContextUtils::getId).thenReturn(userId);
                UUID id = UUID.randomUUID();
                ListaCompra lista = ListaCompra.builder()
                        .id(id)
                        .usuarioId(userId)
                        .build();
                
                when(repo.findById(id)).thenReturn(Optional.of(lista));
                when(repo.existsById(id)).thenReturn(false);
                
                assertTrue(service.deletar(id));
                verify(repo).deleteById(id);
            }
        }

        @Test
        void quando_deletar_lista_de_outro_usuario_entao_deve_retornar_falso() {
            try (MockedStatic<SecurityContextUtils> mockedSecurity = mockStatic(SecurityContextUtils.class)) {
                mockedSecurity.when(SecurityContextUtils::getId).thenReturn(userId);
                UUID id = UUID.randomUUID();
                ListaCompra lista = ListaCompra.builder()
                        .usuarioId(UUID.randomUUID())
                        .build();
                
                when(repo.findById(id)).thenReturn(Optional.of(lista));
                
                assertFalse(service.deletar(id));
                verify(repo, never()).deleteById(any());
            }
        }
    }

    @Nested
    class AlterarItens {
        @Test
        void quando_alterar_itens_com_sucesso_entao_deve_recalcular_e_salvar() {
            UUID id = UUID.randomUUID();
            List<ItemAlteradoRequest> itens = new ArrayList<>();
            ListaCompra lista = spy(ListaCompra.builder().build());
            
            when(itemListaService.alterarItens(id, itens)).thenReturn(true);
            when(repo.findById(id)).thenReturn(Optional.of(lista));
            
            assertTrue(service.alterarItens(id, itens));
            verify(lista).recalcularTotais();
            verify(repo).save(lista);
        }
    }

    @Nested
    class ConcluirLista {
        @Test
        void quando_concluir_lista_entao_deve_atualizar_status_e_pendencias() {
            UUID id = UUID.randomUUID();
            ConcluirListaRequestDTO request = new ConcluirListaRequestDTO(id, BigDecimal.ZERO, "Lista", (short)0, Collections.emptyList(), 0L);
            ListaCompra lista = spy(ListaCompra.builder()
                    .itensLista(new ArrayList<>())
                    .build());
            
            when(repo.findById(id)).thenReturn(Optional.of(lista));
            when(em.getReference(eq(StatusLista.class), any())).thenReturn(StatusLista.builder().build());
            
            service.concluirLista(request);
            
            verify(itemListaService).salvarAllConcluindoLista(any());
            verify(precoReportadoPendenteService).salvarAll(any());
            verify(repo).save(lista);
        }
    }

    @Nested
    class Atualizar {
        @Test
        void quando_atualizar_com_versao_correta_entao_deve_salvar() {
            UUID id = UUID.randomUUID();
            ListaCompraEdicaoRequest request = new ListaCompraEdicaoRequest(id, "Novo Nome", 1L);
            ListaCompra lista = ListaCompra.builder()
                    .version(1L)
                    .build();
            
            when(repo.findById(id)).thenReturn(Optional.of(lista));
            when(repo.save(lista)).thenReturn(lista);
            when(listaCompraMapper.toDto(lista)).thenReturn(createListaCompraRequest());
            
            assertNotNull(service.atualizar(request));
            assertEquals("Novo Nome", lista.getNome());
            verify(repo).save(lista);
        }
    }

    @Nested
    class RemoverItem {
        @Test
        void quando_remover_item_com_sucesso_entao_deve_recalcular_e_salvar() {
            UUID listaId = UUID.randomUUID();
            UUID itemId = UUID.randomUUID();
            ListaCompra lista = spy(ListaCompra.builder().build());
            
            when(itemListaService.removerItem(listaId, itemId)).thenReturn(true);
            when(repo.findById(listaId)).thenReturn(Optional.of(lista));
            
            assertTrue(service.removerItem(listaId, itemId));
            verify(lista).recalcularTotais();
            verify(repo).save(lista);
        }
    }
}
