package com.glaiss.lista.domain.model;

import com.glaiss.core.common.builder.IBuilder;
import com.glaiss.core.domain.model.EntityAbstract;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Table(name = "itens")
@Entity
public class Item extends EntityAbstract {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;

    private String descricao;

    @Column(name = "is_ativo")
    private Boolean isAtivo = Boolean.TRUE;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<ItemOferta> itemPromocaos = new LinkedList<>();

    // Construtor protegido para JPA
    protected Item() {
    }

    // Construtor privado para o Builder
    private Item(ItemBuilder builder) {
        this.id = builder.id;
        this.nome = builder.nome;
        this.descricao = builder.descricao;
        this.isAtivo = builder.isAtivo;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getIsAtivo() {
        return isAtivo;
    }

    public void setIsAtivo(Boolean isAtivo) {
        this.isAtivo = isAtivo;
    }

    public List<ItemOferta> getItemPromocaos() {
        return itemPromocaos;
    }

    public void setItemPromocaos(List<ItemOferta> itemPromocaos) {
        this.itemPromocaos = itemPromocaos;
    }

    // Método estático para iniciar o builder
    public static ItemBuilder builder() {
        return new ItemBuilder();
    }

    // Implementação manual do Builder
    public static final class ItemBuilder implements IBuilder<Item> {
        private UUID id;
        private String nome;
        private String descricao;
        private Boolean isAtivo = Boolean.TRUE;
        private List<ItemOferta> itemPromocaos = new LinkedList<>();
        private LocalDateTime createdDate;
        private String createdBy;
        private LocalDateTime modifiedDate;
        private String modifiedBy;
        private Long version;

        private ItemBuilder() {
        }

        public ItemBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public ItemBuilder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public ItemBuilder descricao(String descricao) {
            this.descricao = descricao;
            return this;
        }

        public ItemBuilder isAtivo(Boolean isAtivo) {
            this.isAtivo = isAtivo;
            return this;
        }

        public ItemBuilder itemPromocaos(List<ItemOferta> itemPromocaos) {
            this.itemPromocaos = itemPromocaos;
            return this;
        }

        public ItemBuilder createdDate(LocalDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public ItemBuilder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public ItemBuilder modifiedDate(LocalDateTime modifiedDate) {
            this.modifiedDate = modifiedDate;
            return this;
        }

        public ItemBuilder modifiedBy(String modifiedBy) {
            this.modifiedBy = modifiedBy;
            return this;
        }

        public ItemBuilder version(Long version) {
            this.version = version;
            return this;
        }

        @Override
        public Item build() {
            return new Item(this);
        }
    }
}
