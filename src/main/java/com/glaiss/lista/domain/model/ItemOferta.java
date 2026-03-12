package com.glaiss.lista.domain.model;

import com.glaiss.core.common.builder.IBuilder;
import com.glaiss.core.domain.model.EntityAbstract;
import com.glaiss.core.utils.anotacao.ValorBigDecimal;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Table(name = "itens_oferta")
@Entity
public class ItemOferta extends EntityAbstract {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "vendedor_id")
    private Vendedor vendedor;

    @Column(name = "has_promocao_ativa")
    private Boolean hasPromocaoAtiva = Boolean.FALSE;

    @ValorBigDecimal
    private BigDecimal preco;

    @Column(name = "data_inicio_promocao")
    private LocalDateTime dataInicioPromocao;

    @Column(name = "data_final_promocao")
    private LocalDateTime dataFinalPromocao;

    @OneToMany(mappedBy = "itemOferta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemLista> registrosEmListas = new LinkedList<>();

    @OneToMany(mappedBy = "itemOferta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PrecoReportadoPendente> precoReportadoPendentes = new LinkedList<>();

    @OneToMany(mappedBy = "itemOferta", cascade = CascadeType.ALL)
    private List<HistoricoPreco> historicoPrecos = new LinkedList<>();

    // Construtor protegido para JPA
    protected ItemOferta() {
    }

    // Construtor privado para o Builder
    private ItemOferta(ItemOfertaBuilder builder) {
        this.id = builder.id;
        this.item = builder.item;
        this.vendedor = builder.vendedor;
        this.hasPromocaoAtiva = builder.hasPromocaoAtiva;
        this.preco = builder.preco;
        this.dataInicioPromocao = builder.dataInicioPromocao;
        this.dataFinalPromocao = builder.dataFinalPromocao;
        this.registrosEmListas = builder.registrosEmListas;
        this.precoReportadoPendentes = builder.precoReportadoPendentes;
        this.historicoPrecos = builder.historicoPrecos;
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

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public Boolean getHasPromocaoAtiva() {
        return hasPromocaoAtiva;
    }

    public void setHasPromocaoAtiva(Boolean hasPromocaoAtiva) {
        this.hasPromocaoAtiva = hasPromocaoAtiva;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public LocalDateTime getDataInicioPromocao() {
        return dataInicioPromocao;
    }

    public void setDataInicioPromocao(LocalDateTime dataInicioPromocao) {
        this.dataInicioPromocao = dataInicioPromocao;
    }

    public LocalDateTime getDataFinalPromocao() {
        return dataFinalPromocao;
    }

    public void setDataFinalPromocao(LocalDateTime dataFinalPromocao) {
        this.dataFinalPromocao = dataFinalPromocao;
    }

    public List<ItemLista> getRegistrosEmListas() {
        return registrosEmListas;
    }

    public void setRegistrosEmListas(List<ItemLista> registrosEmListas) {
        this.registrosEmListas = registrosEmListas;
    }

    public List<PrecoReportadoPendente> getPrecoReportadoPendentes() {
        return precoReportadoPendentes;
    }

    public void setPrecoReportadoPendentes(List<PrecoReportadoPendente> precoReportadoPendentes) {
        this.precoReportadoPendentes = precoReportadoPendentes;
    }

    public List<HistoricoPreco> getHistoricoPrecos() {
        return historicoPrecos;
    }

    public void setHistoricoPrecos(List<HistoricoPreco> historicoPrecos) {
        this.historicoPrecos = historicoPrecos;
    }

    public UUID getItemId() {
        if (Objects.isNull(this.getItem()) || Objects.isNull(this.getItem().getId())) {
            return null;
        }
        return this.getItem().getId();
    }

    public UUID getVendedorId() {
        if (Objects.isNull(this.getVendedor()) || Objects.isNull(this.getVendedor().getId())) {
            return null;
        }
        return this.getVendedor().getId();
    }

    // Método estático para iniciar o builder
    public static ItemOfertaBuilder builder() {
        return new ItemOfertaBuilder();
    }

    // Implementação manual do Builder
    public static final class ItemOfertaBuilder implements IBuilder<ItemOferta> {
        private UUID id;
        private Item item;
        private Vendedor vendedor;
        private Boolean hasPromocaoAtiva = Boolean.FALSE;
        private BigDecimal preco;
        private LocalDateTime dataInicioPromocao;
        private LocalDateTime dataFinalPromocao;
        private List<ItemLista> registrosEmListas = new LinkedList<>();
        private List<PrecoReportadoPendente> precoReportadoPendentes = new LinkedList<>();
        private List<HistoricoPreco> historicoPrecos = new LinkedList<>();
        private LocalDateTime createdDate;
        private String createdBy;
        private LocalDateTime modifiedDate;
        private String modifiedBy;
        private Long version;

        private ItemOfertaBuilder() {
        }

        public ItemOfertaBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public ItemOfertaBuilder item(Item item) {
            this.item = item;
            return this;
        }

        public ItemOfertaBuilder vendedor(Vendedor vendedor) {
            this.vendedor = vendedor;
            return this;
        }

        public ItemOfertaBuilder hasPromocaoAtiva(Boolean hasPromocaoAtiva) {
            this.hasPromocaoAtiva = hasPromocaoAtiva;
            return this;
        }

        public ItemOfertaBuilder preco(BigDecimal preco) {
            this.preco = preco;
            return this;
        }

        public ItemOfertaBuilder dataInicioPromocao(LocalDateTime dataInicioPromocao) {
            this.dataInicioPromocao = dataInicioPromocao;
            return this;
        }

        public ItemOfertaBuilder dataFinalPromocao(LocalDateTime dataFinalPromocao) {
            this.dataFinalPromocao = dataFinalPromocao;
            return this;
        }

        public ItemOfertaBuilder registrosEmListas(List<ItemLista> registrosEmListas) {
            this.registrosEmListas = registrosEmListas;
            return this;
        }

        public ItemOfertaBuilder precoReportadoPendentes(List<PrecoReportadoPendente> precoReportadoPendentes) {
            this.precoReportadoPendentes = precoReportadoPendentes;
            return this;
        }

        public ItemOfertaBuilder historicoPrecos(List<HistoricoPreco> historicoPrecos) {
            this.historicoPrecos = historicoPrecos;
            return this;
        }

        public ItemOfertaBuilder createdDate(LocalDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public ItemOfertaBuilder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public ItemOfertaBuilder modifiedDate(LocalDateTime modifiedDate) {
            this.modifiedDate = modifiedDate;
            return this;
        }

        public ItemOfertaBuilder modifiedBy(String modifiedBy) {
            this.modifiedBy = modifiedBy;
            return this;
        }

        public ItemOfertaBuilder version(Long version) {
            this.version = version;
            return this;
        }

        @Override
        public ItemOferta build() {
            return new ItemOferta(this);
        }
    }
}
