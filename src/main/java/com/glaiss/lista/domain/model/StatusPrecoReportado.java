package com.glaiss.lista.domain.model;

import com.glaiss.core.common.builder.IBuilder;
import com.glaiss.core.domain.model.EntityAbstract;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Table(name = "status_preco_reportado")
@Entity
public class StatusPrecoReportado extends EntityAbstract {

    @Id
    @Enumerated(EnumType.STRING)
    private EStatusPrecoReportado codigo;

    private String descricao;

    @OneToMany(mappedBy = "statusPrecoReportado", cascade = CascadeType.ALL)
    private List<PrecoReportadoPendente> precoReportadoPendentes = new LinkedList<>();

    // Construtor protegido para JPA
    protected StatusPrecoReportado() {
    }

    // Construtor privado para o Builder
    private StatusPrecoReportado(StatusPrecoReportadoBuilder builder) {
        this.codigo = builder.codigo;
        this.descricao = builder.descricao;
        this.precoReportadoPendentes = builder.precoReportadoPendentes;
        this.setCreatedDate(builder.createdDate);
        this.setCreatedBy(builder.createdBy);
        this.setModifiedDate(builder.modifiedDate);
        this.setModifiedBy(builder.modifiedBy);
        this.setVersion(builder.version);
    }

    // Getters e Setters manuais
    public EStatusPrecoReportado getCodigo() {
        return codigo;
    }

    public void setCodigo(EStatusPrecoReportado codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<PrecoReportadoPendente> getPrecoReportadoPendentes() {
        return precoReportadoPendentes;
    }

    public void setPrecoReportadoPendentes(List<PrecoReportadoPendente> precoReportadoPendentes) {
        this.precoReportadoPendentes = precoReportadoPendentes;
    }

    // Método estático para iniciar o builder
    public static StatusPrecoReportadoBuilder builder() {
        return new StatusPrecoReportadoBuilder();
    }

    // Implementação manual do Builder
    public static final class StatusPrecoReportadoBuilder implements IBuilder<StatusPrecoReportado> {
        private EStatusPrecoReportado codigo;
        private String descricao;
        private List<PrecoReportadoPendente> precoReportadoPendentes = new LinkedList<>();
        private LocalDateTime createdDate;
        private String createdBy;
        private LocalDateTime modifiedDate;
        private String modifiedBy;
        private Long version;

        private StatusPrecoReportadoBuilder() {
        }

        public StatusPrecoReportadoBuilder codigo(EStatusPrecoReportado codigo) {
            this.codigo = codigo;
            return this;
        }

        public StatusPrecoReportadoBuilder descricao(String descricao) {
            this.descricao = descricao;
            return this;
        }

        public StatusPrecoReportadoBuilder precoReportadoPendentes(List<PrecoReportadoPendente> precoReportadoPendentes) {
            this.precoReportadoPendentes = precoReportadoPendentes;
            return this;
        }

        public StatusPrecoReportadoBuilder createdDate(LocalDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public StatusPrecoReportadoBuilder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public StatusPrecoReportadoBuilder modifiedDate(LocalDateTime modifiedDate) {
            this.modifiedDate = modifiedDate;
            return this;
        }

        public StatusPrecoReportadoBuilder modifiedBy(String modifiedBy) {
            this.modifiedBy = modifiedBy;
            return this;
        }

        public StatusPrecoReportadoBuilder version(Long version) {
            this.version = version;
            return this;
        }

        @Override
        public StatusPrecoReportado build() {
            return new StatusPrecoReportado(this);
        }
    }
}
