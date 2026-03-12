package com.glaiss.lista.domain.model;

import com.glaiss.core.common.builder.IBuilder;
import com.glaiss.core.domain.model.EntityAbstract;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "enderecos")
@Entity
public class Endereco extends EntityAbstract {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String complemento;

    private String cep;

    private String logradouro;

    private String bairro;

    private String cidade;

    private String numero;

    private String estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendedor_id", nullable = false)
    private Vendedor vendedor;

    // Construtor protegido para JPA
    protected Endereco() {
    }

    // Construtor privado para o Builder
    private Endereco(EnderecoBuilder builder) {
        this.id = builder.id;
        this.complemento = builder.complemento;
        this.cep = builder.cep;
        this.logradouro = builder.logradouro;
        this.bairro = builder.bairro;
        this.cidade = builder.cidade;
        this.numero = builder.numero;
        this.estado = builder.estado;
        this.vendedor = builder.vendedor;
        this.setCreatedDate(builder.createdDate);
        this.setCreatedBy(builder.createdBy);
        this.setModifiedDate(builder.modifiedDate);
        this.setModifiedBy(builder.modifiedBy);
        this.setVersion(builder.version);
    }

    // Getters e Setters manuais
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    // Método estático para iniciar o builder
    public static EnderecoBuilder builder() {
        return new EnderecoBuilder();
    }

    // Implementação manual do Builder
    public static final class EnderecoBuilder implements IBuilder<Endereco> {
        private UUID id;
        private String complemento;
        private String cep;
        private String logradouro;
        private String bairro;
        private String cidade;
        private String numero;
        private String estado;
        private Vendedor vendedor;
        private LocalDateTime createdDate;
        private String createdBy;
        private LocalDateTime modifiedDate;
        private String modifiedBy;
        private Long version;

        private EnderecoBuilder() {
        }

        public EnderecoBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public EnderecoBuilder complemento(String complemento) {
            this.complemento = complemento;
            return this;
        }

        public EnderecoBuilder cep(String cep) {
            this.cep = cep;
            return this;
        }

        public EnderecoBuilder logradouro(String logradouro) {
            this.logradouro = logradouro;
            return this;
        }

        public EnderecoBuilder bairro(String bairro) {
            this.bairro = bairro;
            return this;
        }

        public EnderecoBuilder cidade(String cidade) {
            this.cidade = cidade;
            return this;
        }

        public EnderecoBuilder numero(String numero) {
            this.numero = numero;
            return this;
        }

        public EnderecoBuilder estado(String estado) {
            this.estado = estado;
            return this;
        }

        public EnderecoBuilder vendedor(Vendedor vendedor) {
            this.vendedor = vendedor;
            return this;
        }

        public EnderecoBuilder createdDate(LocalDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public EnderecoBuilder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public EnderecoBuilder modifiedDate(LocalDateTime modifiedDate) {
            this.modifiedDate = modifiedDate;
            return this;
        }

        public EnderecoBuilder modifiedBy(String modifiedBy) {
            this.modifiedBy = modifiedBy;
            return this;
        }

        public EnderecoBuilder version(Long version) {
            this.version = version;
            return this;
        }

        @Override
        public Endereco build() {
            return new Endereco(this);
        }
    }
}
