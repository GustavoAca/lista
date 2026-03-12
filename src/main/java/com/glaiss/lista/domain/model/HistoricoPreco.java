package com.glaiss.lista.domain.model;

import com.glaiss.core.common.builder.IBuilder;
import com.glaiss.core.domain.model.EntityAbstract;
import com.glaiss.core.utils.anotacao.ValorBigDecimal;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Table(name = "historico_precos")
@Entity
public class HistoricoPreco extends EntityAbstract {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_oferta_id", nullable = false)
    private ItemOferta itemOferta;

    @ValorBigDecimal
    private BigDecimal preco;

    @Column(name = "has_promocao_ativa")
    private Boolean hasPromocaoAtiva;

    // Construtor protegido para JPA
    protected HistoricoPreco() {
    }

    // Construtor privado para o Builder
    private HistoricoPreco(HistoricoPrecoBuilder builder) {
        this.id = builder.id;
        this.itemOferta = builder.itemOferta;
        this.preco = builder.preco;
        this.hasPromocaoAtiva = builder.hasPromocaoAtiva;
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

    public ItemOferta getItemOferta() {
        return itemOferta;
    }

    public void setItemOferta(ItemOferta itemOferta) {
        this.itemOferta = itemOferta;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Boolean getHasPromocaoAtiva() {
        return hasPromocaoAtiva;
    }

    public void setHasPromocaoAtiva(Boolean hasPromocaoAtiva) {
        this.hasPromocaoAtiva = hasPromocaoAtiva;
    }

    public UUID getItemOfertaId() {
        if (Objects.isNull(this.getItemOferta()) || Objects.isNull(this.getItemOferta().getId())) {
            return null;
        }
        return this.getItemOferta().getId();
    }

    // Método estático para iniciar o builder
    public static HistoricoPrecoBuilder builder() {
        return new HistoricoPrecoBuilder();
    }

    // Implementação manual do Builder
    public static final class HistoricoPrecoBuilder implements IBuilder<HistoricoPreco> {
        private UUID id;
        private ItemOferta itemOferta;
        private BigDecimal preco;
        private Boolean hasPromocaoAtiva;
        private LocalDateTime createdDate;
        private String createdBy;
        private LocalDateTime modifiedDate;
        private String modifiedBy;
        private Long version;

        private HistoricoPrecoBuilder() {
        }

        public HistoricoPrecoBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public HistoricoPrecoBuilder itemOferta(ItemOferta itemOferta) {
            this.itemOferta = itemOferta;
            return this;
        }

        public HistoricoPrecoBuilder preco(BigDecimal preco) {
            this.preco = preco;
            return this;
        }

        public HistoricoPrecoBuilder hasPromocaoAtiva(Boolean hasPromocaoAtiva) {
            this.hasPromocaoAtiva = hasPromocaoAtiva;
            return this;
        }

        public HistoricoPrecoBuilder createdDate(LocalDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public HistoricoPrecoBuilder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public HistoricoPrecoBuilder modifiedDate(LocalDateTime modifiedDate) {
            this.modifiedDate = modifiedDate;
            return this;
        }

        public HistoricoPrecoBuilder modifiedBy(String modifiedBy) {
            this.modifiedBy = modifiedBy;
            return this;
        }

        public HistoricoPrecoBuilder version(Long version) {
            this.version = version;
            return this;
        }

        @Override
        public HistoricoPreco build() {
            return new HistoricoPreco(this);
        }
    }
}
