package com.glaiss.lista.domain.service.vendedor;

import com.glaiss.core.domain.model.ResponsePage;
import com.glaiss.core.domain.service.BaseServiceImpl;
import com.glaiss.core.exception.RegistroNaoEncontradoException;
import com.glaiss.lista.controller.dto.EnderecoDTO;
import com.glaiss.lista.controller.dto.NovoEnderecoVendedorDTO;
import com.glaiss.lista.controller.dto.VendedorDTO;
import com.glaiss.lista.domain.mapper.VendedorMapper;
import com.glaiss.lista.domain.model.Vendedor;
import com.glaiss.lista.domain.repository.VendedorRepository;
import com.glaiss.lista.domain.service.endereco.EnderecoService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VendedorServiceImpl extends BaseServiceImpl<Vendedor, UUID, VendedorRepository> implements VendedorService {

    private final VendedorMapper vendedorMapper;
    private final EnderecoService enderecoService;

    protected VendedorServiceImpl(VendedorRepository repo,
                                  VendedorMapper vendedorMapper,
                                  EnderecoService enderecoService) {
        super(repo);
        this.vendedorMapper = vendedorMapper;
        this.enderecoService = enderecoService;
    }

    public ResponsePage<VendedorDTO> listarPaginaDTO(Pageable pageable) {
        Page<Vendedor> vendedorPage = repo.findAll(pageable);
        var listaDeVendedores = vendedorPage.getContent().stream().map(vendedorMapper::toDto).toList();
        return new ResponsePage<>(listaDeVendedores, pageable.getPageNumber(), pageable.getPageSize(), vendedorPage.getTotalElements());
    }

    public VendedorDTO buscaPorId(UUID id) {
        return vendedorMapper.toDto(super.buscarPorId(id).orElseThrow(() -> new RegistroNaoEncontradoException(id, "Vendedor")));
    }

    @Transactional
    public VendedorDTO salvar(VendedorDTO vendedorDTO) {
        List<EnderecoDTO> enderecos = vendedorDTO.enderecos();
        Vendedor vendedor = vendedorMapper.toEntity(vendedorDTO);
        vendedor.setEnderecos(null);
        vendedor = salvar(vendedor);

        Vendedor finalVendedor = vendedor;
        enderecos.forEach(endereco -> {
            EnderecoDTO enderecoDTO = new EnderecoDTO(endereco.complemento(),
                    endereco.cep(),
                    endereco.logradouro(),
                    endereco.bairro(),
                    endereco.cidade(),
                    endereco.numero(),
                    endereco.estado(),
                    null,
                    finalVendedor.getId());
            enderecoService.salvar(enderecoDTO);
        });
        return vendedorMapper.toDto(vendedor);
    }

    @Transactional
    public Boolean adicionarEndereco(NovoEnderecoVendedorDTO novoEnderecoVendedorDTO) {
        repo.findById(novoEnderecoVendedorDTO.vendedorId())
                .orElseThrow(() -> new RegistroNaoEncontradoException(novoEnderecoVendedorDTO.vendedorId(), "Vendedor"));

        novoEnderecoVendedorDTO.enderecos().forEach(endereco -> {
            EnderecoDTO enderecoDTO = new EnderecoDTO(endereco.complemento(),
                    endereco.cep(),
                    endereco.logradouro(),
                    endereco.bairro(),
                    endereco.cidade(),
                    endereco.numero(),
                    endereco.estado(),
                    null,
                    novoEnderecoVendedorDTO.vendedorId());
            enderecoService.salvar(enderecoDTO);
        });
        return Boolean.TRUE;
    }

    @Override
    public ResponsePage<VendedorDTO> buscarPorNomeDto(Pageable pageable, String nome) {
        Page<Vendedor> vendedores = repo.findByNomeContainingIgnoreCase(pageable, nome);
        List<VendedorDTO> listaCompraDto = vendedores.getContent().stream().map(vendedorMapper::toDto).toList();
        return new ResponsePage<>(listaCompraDto, pageable.getPageNumber(), pageable.getPageSize(), vendedores.getTotalElements());
    }

    @Override
    public VendedorDTO buscarPorIdDto(UUID id) {
        return vendedorMapper.toDto(super
                .buscarPorId(id).orElseThrow(() -> new RegistroNaoEncontradoException(id, "Vendedor")));
    }
}
