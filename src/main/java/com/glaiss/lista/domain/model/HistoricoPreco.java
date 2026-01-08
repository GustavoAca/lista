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
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
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

    public UUID getItemOfertaId(){
        if(Objects.isNull(this.getItemOferta()) || Objects.isNull(this.getItemOferta().getId())){
            return null;
        }
        return this.getItemOferta().getId();
    }
}
