package com.glaiss.lista.domain.service.endereco;

import com.glaiss.lista.ListaApplicationTests;
import com.glaiss.lista.controller.vendedor.dto.EnderecoDTO;
import com.glaiss.lista.domain.mapper.EnderecoMapper;
import com.glaiss.lista.domain.model.Endereco;
import com.glaiss.lista.domain.repository.EnderecoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EnderecoServiceImplTest extends ListaApplicationTests {

    @Mock
    private EnderecoRepository repo;

    @Mock
    private EnderecoMapper enderecoMapper;

    private EnderecoServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new EnderecoServiceImpl(repo, enderecoMapper);
    }

    @Test
    void salvar_deveRetornarDto_quandoSalvarComSucesso() {
        EnderecoDTO inputDto = new EnderecoDTO("complemento","0999999","rua","bairro","cidade","1", "SP", null, null, 0);
        Endereco entity = new Endereco();
        Endereco savedEntity = new Endereco();
        EnderecoDTO expectedDto = new EnderecoDTO("complemento","0999999","rua","bairro","cidade","1", "SP", null, null, 0);

        when(enderecoMapper.toEntity(inputDto)).thenReturn(entity);
        when(repo.save(entity)).thenReturn(savedEntity);
        when(enderecoMapper.toDto(savedEntity)).thenReturn(expectedDto);

        EnderecoDTO result = service.salvar(inputDto);

        assertSame(expectedDto, result);
        verify(enderecoMapper).toEntity(inputDto);
        verify(repo).save(entity);
        verify(enderecoMapper).toDto(savedEntity);
    }

    @Test
    void salvar_devePropagarExcecao_quandoMapperFalhar() {
        EnderecoDTO inputDto = new EnderecoDTO("complemento","0999999","rua","bairro","cidade","1", "SP", null, null, 0);

        when(enderecoMapper.toEntity(inputDto)).thenThrow(new IllegalArgumentException("map error"));

        assertThrows(IllegalArgumentException.class, () -> service.salvar(inputDto));
        verify(enderecoMapper).toEntity(inputDto);
        verifyNoInteractions(repo);
    }

    @Test
    void salvar_devePropagarExcecao_quandoRepositorioFalhar() {
        EnderecoDTO inputDto = new EnderecoDTO("complemento","0999999","rua","bairro","cidade","1", "SP", null, null, 0);
        Endereco entity = new Endereco();

        when(enderecoMapper.toEntity(inputDto)).thenReturn(entity);
        when(repo.save(entity)).thenThrow(new RuntimeException("db error"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.salvar(inputDto));
        assertEquals("db error", ex.getMessage());
        verify(enderecoMapper).toEntity(inputDto);
        verify(repo).save(entity);
        verify(enderecoMapper, never()).toDto(any());
    }
}
