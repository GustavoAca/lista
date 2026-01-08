package com.glaiss.lista.domain.service.precoreportado;

import com.glaiss.core.domain.model.ResponsePage;
import com.glaiss.core.domain.service.BaseServiceImpl;
import com.glaiss.core.exception.RegistroNaoEncontradoException;
import com.glaiss.lista.domain.mapper.PrecoReportadoPendenteMapper;
import com.glaiss.lista.domain.model.EStatusPrecoReportado;
import com.glaiss.lista.domain.model.PrecoReportadoPendente;
import com.glaiss.lista.domain.model.dto.PrecoReportadoPendenteDTO;
import com.glaiss.lista.domain.repository.PrecoReportadoPendenteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PrecoReportadoPendenteServiceImpl extends BaseServiceImpl<PrecoReportadoPendente, UUID, PrecoReportadoPendenteRepository> implements PrecoReportadoPendenteService {

    private final PrecoReportadoPendenteMapper precoReportadoPendenteMapper;

    protected PrecoReportadoPendenteServiceImpl(PrecoReportadoPendenteRepository repo, PrecoReportadoPendenteMapper precoReportadoPendenteMapper) {
        super(repo);
        this.precoReportadoPendenteMapper = precoReportadoPendenteMapper;
    }

    public void salvar(PrecoReportadoPendenteDTO precoReportadoPendenteDTO) {
        salvar(precoReportadoPendenteMapper.toEntity(precoReportadoPendenteDTO));
    }

    public void salvarAll(List<PrecoReportadoPendenteDTO> precoReportadoPendenteDTO) {
        repo.saveAll(precoReportadoPendenteDTO.stream().map(precoReportadoPendenteMapper::toEntity).toList());
    }

    public ResponsePage<PrecoReportadoPendenteDTO> listarPorStatusPrecoReportado(Pageable pageable, List<EStatusPrecoReportado> eStatusPrecoReportados) {
        Page<PrecoReportadoPendente> precoReportadoPendentes = repo.findAllByStatusPrecoReportado_CodigoIn(pageable, eStatusPrecoReportados);
        List<PrecoReportadoPendenteDTO> precoReportadoPendenteDTOS = precoReportadoPendentes.getContent().stream().map(precoReportadoPendenteMapper::toDto).toList();
        return new ResponsePage<>(precoReportadoPendenteDTOS, pageable.getPageNumber(), pageable.getPageSize(), precoReportadoPendentes.getTotalElements());
    }

    @Override
    public void atualizarTentativas(PrecoReportadoPendenteDTO precoReportadoPendenteDTO) {
        PrecoReportadoPendente precoReportadoPendente = repo.findById(precoReportadoPendenteDTO.id())
                .orElseThrow(() -> new RegistroNaoEncontradoException(precoReportadoPendenteDTO.id(), "Preco reportado pendente"));
        precoReportadoPendente.setTentativas((short) (precoReportadoPendente.getTentativas() + 1));
        precoReportadoPendente.setMensagemErro(precoReportadoPendenteDTO.mensagemErro());
        repo.save(precoReportadoPendente);
    }

    @Override
    public void excluir(UUID id) {
        repo.deleteById(id);
    }

    @Override
    public void concluirProcessamento(PrecoReportadoPendenteDTO dto) {
        PrecoReportadoPendente precoReportadoPendente = repo.findById(dto.id())
                .orElseThrow(() -> new RegistroNaoEncontradoException(dto.id(), "Preco reportado pendente"));
        precoReportadoPendente.getStatusPrecoReportado().setCodigo(EStatusPrecoReportado.FINALIZADO);
        repo.save(precoReportadoPendente);
    }
}
