package com.glaiss.lista.domain.model;

import com.glaiss.core.common.builder.IBuilder;
import com.glaiss.core.domain.model.EntityAbstract;
import com.glaiss.core.utils.anotacao.ValorBigDecimal;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Table(name = "itens_lista")
@Entity
public class ItemLista extends EntityAbstract {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "preco_unitario")
    @ValorBigDecimal(nullable = true)
    private BigDecimal precoUnitario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "listas_compra_id", nullable = false)
    private ListaCompra listaCompra;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_oferta_id", nullable = false)
    private ItemOferta itemOferta;

    private short quantidade;

    // Construtor protegido para JPA
    protected ItemLista() {
    }

    // Construtor privado para o Builder
    private ItemLista(ItemListaBuilder builder) {
        this.id = builder.id;
        this.precoUnitario = builder.precoUnitario;
        this.listaCompra = builder.listaCompra;
        this.itemOferta = builder.itemOferta;
        this.quantidade = builder.quantidade;
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

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public ListaCompra getListaCompra() {
        return listaCompra;
    }

    public void setListaCompra(ListaCompra listaCompra) {
        this.listaCompra = listaCompra;
    }

    public ItemOferta getItemOferta() {
        return itemOferta;
    }

    public void setItemOferta(ItemOferta itemOferta) {
        this.itemOferta = itemOferta;
    }

    public short getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(short quantidade) {
        this.quantidade = quantidade;
    }

    public UUID getListaCompraId() {
        if (Objects.isNull(this.getListaCompra()) || Objects.isNull(this.getListaCompra().getId())) {
            return null;
        }
        return this.getListaCompra().getId();
    }

    public UUID getItemOfertaId() {
        if (Objects.isNull(this.getItemOferta()) || Objects.isNull(this.getItemOferta().getId())) {
            return null;
        }
        return this.getItemOferta().getId();
    }

    public void alterarQuantidade(short quantidadeAdicional) {
        this.quantidade = quantidadeAdicional;
    }

    public void adicionarQuantidade(short quantidade) {
        this.quantidade += quantidade;
    }

    public Boolean hasPromocao() {
        return this.itemOferta.getHasPromocaoAtiva();
    }

    // Método estático para iniciar o builder
    public static ItemListaBuilder builder() {
        return new ItemListaBuilder();
    }

    // Implementação manual do Builder
    public static final class ItemListaBuilder implements IBuilder<ItemLista> {
        private UUID id;
        private BigDecimal precoUnitario;
        private ListaCompra listaCompra;
        private ItemOferta itemOferta;
        private short quantidade;
        private LocalDateTime createdDate;
        private String createdBy;
        private LocalDateTime modifiedDate;
        private String modifiedBy;
        private Long version;

        private ItemListaBuilder() {
        }

        public ItemListaBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public ItemListaBuilder precoUnitario(BigDecimal precoUnitario) {
            this.precoUnitario = precoUnitario;
            return this;
        }

        public ItemListaBuilder listaCompra(ListaCompra listaCompra) {
            this.listaCompra = listaCompra;
            return this;
        }

        public ItemListaBuilder itemOferta(ItemOferta itemOferta) {
            this.itemOferta = itemOferta;
            return this;
        }

        public ItemListaBuilder quantidade(short quantidade) {
            this.quantidade = quantidade;
            return this;
        }

        public ItemListaBuilder createdDate(LocalDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public ItemListaBuilder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public ItemListaBuilder modifiedDate(LocalDateTime modifiedDate) {
            this.modifiedDate = modifiedDate;
            return this;
        }

        public ItemListaBuilder modifiedBy(String modifiedBy) {
            this.modifiedBy = modifiedBy;
            return this;
        }

        public ItemListaBuilder version(Long version) {
            this.version = version;
            return this;
        }

        @Override
        public ItemLista build() {
            return new ItemLista(this);
        }
    }
}
