package com.glaiss.lista.domain.model;

import com.glaiss.core.common.builder.IBuilder;
import com.glaiss.core.domain.model.EntityAbstract;
import com.glaiss.core.utils.anotacao.ValorBigDecimal;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Table(name = "precos_reportados_pendentes")
@Entity
public class PrecoReportadoPendente extends EntityAbstract {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_oferta_id", nullable = false)
    private ItemOferta itemOferta;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_preco_reportado", nullable = false)
    private StatusPrecoReportado statusPrecoReportado;

    @ValorBigDecimal(nullable = true)
    private BigDecimal preco;

    @Column(name = "mensagem_erro")
    private String mensagemErro;

    private Short tentativas;

    @Column(name = "has_promocao_ativa")
    private Boolean hasPromocaoAtiva;

    @Column(name = "data_inicio_promocao")
    private LocalDateTime dataInicioPromocao;

    @Column(name = "data_final_promocao")
    private LocalDateTime dataFinalPromocao;

    // Construtor protegido para JPA
    protected PrecoReportadoPendente() {
    }

    // Construtor privado para o Builder
    private PrecoReportadoPendente(PrecoReportadoPendenteBuilder builder) {
        this.id = builder.id;
        this.itemOferta = builder.itemOferta;
        this.statusPrecoReportado = builder.statusPrecoReportado;
        this.preco = builder.preco;
        this.mensagemErro = builder.mensagemErro;
        this.tentativas = builder.tentativas;
        this.hasPromocaoAtiva = builder.hasPromocaoAtiva;
        this.dataInicioPromocao = builder.dataInicioPromocao;
        this.dataFinalPromocao = builder.dataFinalPromocao;
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

    public StatusPrecoReportado getStatusPrecoReportado() {
        return statusPrecoReportado;
    }

    public void setStatusPrecoReportado(StatusPrecoReportado statusPrecoReportado) {
        this.statusPrecoReportado = statusPrecoReportado;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public String getMensagemErro() {
        return mensagemErro;
    }

    public void setMensagemErro(String mensagemErro) {
        this.mensagemErro = mensagemErro;
    }

    public Short getTentativas() {
        return tentativas;
    }

    public void setTentativas(Short tentativas) {
        this.tentativas = tentativas;
    }

    public Boolean getHasPromocaoAtiva() {
        return hasPromocaoAtiva;
    }

    public void setHasPromocaoAtiva(Boolean hasPromocaoAtiva) {
        this.hasPromocaoAtiva = hasPromocaoAtiva;
    }

    public void setDataInicioPromocao(LocalDateTime dataInicioPromocao) {
        this.dataInicioPromocao = dataInicioPromocao;
    }

    public void setDataFinalPromocao(LocalDateTime dataFinalPromocao) {
        this.dataFinalPromocao = dataFinalPromocao;
    }

    public UUID getItemOfertaId() {
        if (Objects.isNull(this.getItemOferta()) || Objects.isNull(this.getItemOferta().getId())) {
            return null;
        }
        return this.getItemOferta().getId();
    }

    public EStatusPrecoReportado getStatusPrecoReportadoId() {
        if (Objects.isNull(this.getStatusPrecoReportado()) || Objects.isNull(this.getStatusPrecoReportado().getCodigo())) {
            return null;
        }
        return this.getStatusPrecoReportado().getCodigo();
    }

    public LocalDateTime getDataInicioPromocao() {
        if (this.hasPromocaoAtiva == null || !this.hasPromocaoAtiva) {
            return null;
        }
        return this.dataInicioPromocao;
    }

    public LocalDateTime getDataFinalPromocao() {
        if (this.hasPromocaoAtiva == null || !this.hasPromocaoAtiva) {
            return null;
        }
        return this.dataFinalPromocao;
    }

    // Método estático para iniciar o builder
    public static PrecoReportadoPendenteBuilder builder() {
        return new PrecoReportadoPendenteBuilder();
    }

    // Implementação manual do Builder
    public static final class PrecoReportadoPendenteBuilder implements IBuilder<PrecoReportadoPendente> {
        private UUID id;
        private ItemOferta itemOferta;
        private StatusPrecoReportado statusPrecoReportado;
        private BigDecimal preco;
        private String mensagemErro;
        private Short tentativas;
        private Boolean hasPromocaoAtiva;
        private LocalDateTime dataInicioPromocao;
        private LocalDateTime dataFinalPromocao;
        private LocalDateTime createdDate;
        private String createdBy;
        private LocalDateTime modifiedDate;
        private String modifiedBy;
        private Long version;

        private PrecoReportadoPendenteBuilder() {
        }

        public PrecoReportadoPendenteBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public PrecoReportadoPendenteBuilder itemOferta(ItemOferta itemOferta) {
            this.itemOferta = itemOferta;
            return this;
        }

        public PrecoReportadoPendenteBuilder statusPrecoReportado(StatusPrecoReportado statusPrecoReportado) {
            this.statusPrecoReportado = statusPrecoReportado;
            return this;
        }

        public PrecoReportadoPendenteBuilder preco(BigDecimal preco) {
            this.preco = preco;
            return this;
        }

        public PrecoReportadoPendenteBuilder mensagemErro(String mensagemErro) {
            this.mensagemErro = mensagemErro;
            return this;
        }

        public PrecoReportadoPendenteBuilder tentativas(Short tentativas) {
            this.tentativas = tentativas;
            return this;
        }

        public PrecoReportadoPendenteBuilder hasPromocaoAtiva(Boolean hasPromocaoAtiva) {
            this.hasPromocaoAtiva = hasPromocaoAtiva;
            return this;
        }

        public PrecoReportadoPendenteBuilder dataInicioPromocao(LocalDateTime dataInicioPromocao) {
            this.dataInicioPromocao = dataInicioPromocao;
            return this;
        }

        public PrecoReportadoPendenteBuilder dataFinalPromocao(LocalDateTime dataFinalPromocao) {
            this.dataFinalPromocao = dataFinalPromocao;
            return this;
        }

        public PrecoReportadoPendenteBuilder createdDate(LocalDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public PrecoReportadoPendenteBuilder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public PrecoReportadoPendenteBuilder modifiedDate(LocalDateTime modifiedDate) {
            this.modifiedDate = modifiedDate;
            return this;
        }

        public PrecoReportadoPendenteBuilder modifiedBy(String modifiedBy) {
            this.modifiedBy = modifiedBy;
            return this;
        }

        public PrecoReportadoPendenteBuilder version(Long version) {
            this.version = version;
            return this;
        }

        @Override
        public PrecoReportadoPendente build() {
            return new PrecoReportadoPendente(this);
        }
    }
}
