package com.glaiss.lista.domain.service.vendedor;

import com.glaiss.core.domain.model.ResponsePage;
import com.glaiss.core.domain.service.BaseService;
import com.glaiss.lista.controller.dto.NovoEnderecoVendedorDTO;
import com.glaiss.lista.controller.dto.VendedorDTO;
import com.glaiss.lista.domain.model.Vendedor;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface VendedorService extends BaseService<Vendedor, UUID> {

    ResponsePage<VendedorDTO> listarPaginaDTO(Pageable pageable);

    VendedorDTO salvar(VendedorDTO vendedorDTO);

    Boolean adicionarEndereco(NovoEnderecoVendedorDTO novoEnderecoVendedorDTO);

    ResponsePage<VendedorDTO> buscarPorNomeDto(Pageable pageable, String nome);

    VendedorDTO buscarPorIdDto(UUID id);
}
