package com.glaiss.lista.domain.model;

import com.glaiss.core.common.builder.IBuilder;
import com.glaiss.core.domain.model.EntityAbstract;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Table(name = "vendedores")
@Entity
public class Vendedor extends EntityAbstract {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;

    @OneToMany(mappedBy = "vendedor", cascade = CascadeType.ALL)
    private List<Endereco> enderecos = new LinkedList<>();

    @OneToMany(mappedBy = "vendedor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemOferta> itemPromocaos = new LinkedList<>();

    // Construtor protegido para JPA
    protected Vendedor() {
    }

    // Construtor privado para o Builder
    private Vendedor(VendedorBuilder builder) {
        this.id = builder.id;
        this.nome = builder.nome;
        this.enderecos = builder.enderecos;
        this.itemPromocaos = builder.itemPromocaos;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public List<ItemOferta> getItemPromocaos() {
        return itemPromocaos;
    }

    public void setItemPromocaos(List<ItemOferta> itemPromocaos) {
        this.itemPromocaos = itemPromocaos;
    }

    // Método estático para iniciar o builder
    public static VendedorBuilder builder() {
        return new VendedorBuilder();
    }

    // Implementação manual do Builder
    public static final class VendedorBuilder implements IBuilder<Vendedor> {
        private UUID id;
        private String nome;
        private List<Endereco> enderecos = new LinkedList<>();
        private List<ItemOferta> itemPromocaos = new LinkedList<>();
        private LocalDateTime createdDate;
        private String createdBy;
        private LocalDateTime modifiedDate;
        private String modifiedBy;
        private Long version;

        private VendedorBuilder() {
        }

        public VendedorBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public VendedorBuilder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public VendedorBuilder enderecos(List<Endereco> enderecos) {
            this.enderecos = enderecos;
            return this;
        }

        public VendedorBuilder itemPromocaos(List<ItemOferta> itemPromocaos) {
            this.itemPromocaos = itemPromocaos;
            return this;
        }

        public VendedorBuilder createdDate(LocalDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public VendedorBuilder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public VendedorBuilder modifiedDate(LocalDateTime modifiedDate) {
            this.modifiedDate = modifiedDate;
            return this;
        }

        public VendedorBuilder modifiedBy(String modifiedBy) {
            this.modifiedBy = modifiedBy;
            return this;
        }

        public VendedorBuilder version(Long version) {
            this.version = version;
            return this;
        }

        @Override
        public Vendedor build() {
            return new Vendedor(this);
        }
    }
}
