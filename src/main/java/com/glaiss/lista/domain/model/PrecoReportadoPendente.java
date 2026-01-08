package com.glaiss.lista.domain.model;

import com.glaiss.core.domain.model.EntityAbstract;
import com.glaiss.core.utils.anotacao.ValorBigDecimal;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
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

    @ValorBigDecimal
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

    public LocalDateTime getDataInicioPromocao(){
        if(!this.hasPromocaoAtiva){
            return null;
        }
        return this.dataInicioPromocao;
    }

    public LocalDateTime getDataFinalPromocao(){
        if(!this.hasPromocaoAtiva){
            return null;
        }
        return this.dataFinalPromocao;
    }
}
