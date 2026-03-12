package com.glaiss.lista.domain.model;

import com.glaiss.core.common.builder.IBuilder;
import com.glaiss.core.domain.model.EntityAbstract;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Table(name = "status_lista")
@Entity
public class StatusLista extends EntityAbstract {

    @Id
    @Enumerated(EnumType.STRING)
    private EStatusLista codigo;

    private String descricao;

    @OneToMany(mappedBy = "statusLista", cascade = CascadeType.ALL)
    private List<ListaCompra> listaCompra = new LinkedList<>();

    // Construtor protegido para JPA
    protected StatusLista() {
    }

    // Construtor privado para o Builder
    private StatusLista(StatusListaBuilder builder) {
        this.codigo = builder.codigo;
        this.descricao = builder.descricao;
        this.listaCompra = builder.listaCompra;
        this.setCreatedDate(builder.createdDate);
        this.setCreatedBy(builder.createdBy);
        this.setModifiedDate(builder.modifiedDate);
        this.setModifiedBy(builder.modifiedBy);
        this.setVersion(builder.version);
    }

    // Getters e Setters manuais
    public EStatusLista getCodigo() {
        return codigo;
    }

    public void setCodigo(EStatusLista codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<ListaCompra> getListaCompra() {
        return listaCompra;
    }

    public void setListaCompra(List<ListaCompra> listaCompra) {
        this.listaCompra = listaCompra;
    }

    // Método estático para iniciar o builder
    public static StatusListaBuilder builder() {
        return new StatusListaBuilder();
    }

    // Implementação manual do Builder
    public static final class StatusListaBuilder implements IBuilder<StatusLista> {
        private EStatusLista codigo;
        private String descricao;
        private List<ListaCompra> listaCompra = new LinkedList<>();
        private LocalDateTime createdDate;
        private String createdBy;
        private LocalDateTime modifiedDate;
        private String modifiedBy;
        private Long version;

        private StatusListaBuilder() {
        }

        public StatusListaBuilder codigo(EStatusLista codigo) {
            this.codigo = codigo;
            return this;
        }

        public StatusListaBuilder descricao(String descricao) {
            this.descricao = descricao;
            return this;
        }

        public StatusListaBuilder listaCompra(List<ListaCompra> listaCompra) {
            this.listaCompra = listaCompra;
            return this;
        }

        public StatusListaBuilder createdDate(LocalDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public StatusListaBuilder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public StatusListaBuilder modifiedDate(LocalDateTime modifiedDate) {
            this.modifiedDate = modifiedDate;
            return this;
        }

        public StatusListaBuilder modifiedBy(String modifiedBy) {
            this.modifiedBy = modifiedBy;
            return this;
        }

        public StatusListaBuilder version(Long version) {
            this.version = version;
            return this;
        }

        @Override
        public StatusLista build() {
            return new StatusLista(this);
        }
    }
}
