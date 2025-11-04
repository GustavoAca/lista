package com.glaiss.lista.domain.service.listascompra;

import com.glaiss.core.domain.model.ResponsePage;
import com.glaiss.core.exception.RegistroNaoEncontradoException;
import com.glaiss.core.utils.SecurityContextUtils;
import com.glaiss.lista.ListaApplicationTests;
import com.glaiss.lista.MockFactory;
import com.glaiss.lista.domain.model.dto.ItemListaDto;
import com.glaiss.lista.domain.model.dto.ListaCompraDto;
import com.glaiss.lista.domain.service.itemlista.ItemListaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ListaCompraServiceImplTest extends ListaApplicationTests {

    @Autowired
    private ListaCompraService listaCompraService;

    @Autowired
    private ItemListaServiceImpl itemListaService;

    @Autowired
    private MockFactory mockFactory;

    @Nested
    class Dado_um_usuario_iniciando_uma_lista extends ListaApplicationTests {

        @Nested
        class Quando_iniciar extends ListaApplicationTests {
            private ListaCompraDto listaCompraDto;

            @BeforeEach
            void setup() {
                listaCompraDto = listaCompraService.salvar(List.of(ItemListaDto.builder()
                        .quantidade(1)
                        .preco(BigDecimal.ONE)
                        .item(UUID.fromString("58c101a7-b0bd-426a-a84c-f1bf89b89aa6"))
                        .build()));
            }

            @Test
            void Entao_deve_ser_criada_com_sucesso() {
                assertNotNull(listaCompraDto);
                assertNotNull(listaCompraDto.getUsuarioId());
                assertNotNull(listaCompraDto.getValorTotal());
                assertNotNull(listaCompraDto.getId());
            }
        }
    }

    @Nested
    class Dado_lista_de_compra_salva extends ListaApplicationTests {
        private ListaCompraDto listaCompraDto;

        @BeforeEach
        void setup() {
            listaCompraDto = listaCompraService.salvar(List.of(ItemListaDto.builder().listaCompraId(UUID.randomUUID()).id(UUID.randomUUID()).quantidade(1).preco(BigDecimal.ONE).build()));
        }

        @Nested
        class Quando_buscar_por_id extends ListaApplicationTests {
            private ListaCompraDto listaEncontrada;

            @BeforeEach
            void setup() {
                listaEncontrada = listaCompraService.buscarPorIdDto(listaCompraDto.getId());
            }

            @Test
            void Entao_deve_ter_sucesso() {
                assertEquals(listaCompraDto.getId(), listaEncontrada.getId());
            }
        }

        @Nested
        class Quando_buscar_por_id_inexistente extends ListaApplicationTests {

            @Test
            void Entao_deve_ter_disparado_excessao() {
                assertThrows(RegistroNaoEncontradoException.class, () -> listaCompraService.buscarPorIdDto(UUID.randomUUID()));
            }
        }
    }


    @Nested
    class Dado_listas_salvas extends ListaApplicationTests {

        @BeforeEach
        void setup() {
            for (int i = 0; i < 2; i++) {
                listaCompraService.salvar(List.of());
            }
        }

        @Nested
        class Quando_listar extends ListaApplicationTests {
            private ResponsePage<ListaCompraDto> listaCompraDto;
            private Boolean isListaDoMesmoUsuario;

            @BeforeEach
            void setup() {
                listaCompraDto = listaCompraService.listarPaginaDto(PageRequest.of(0, 2));
                isListaDoMesmoUsuario = listaCompraDto.getContent().stream().allMatch(l -> l.getUsuarioId().equals(SecurityContextUtils.getId()));
            }

            @Test
            void Entao_deve_ter_sucesso() {
                assertNotEquals(0L, listaCompraDto.getTotalElements());
                assertTrue(isListaDoMesmoUsuario);
            }
        }
    }
}