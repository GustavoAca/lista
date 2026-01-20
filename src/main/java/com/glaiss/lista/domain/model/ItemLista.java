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
@Table(name = "itens_lista")
@Entity
public class ItemLista extends EntityAbstract {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "preco_unitario", nullable = true)
    @ValorBigDecimal
    private BigDecimal precoUnitario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "listas_compra_id", nullable = false)
    private ListaCompra listaCompra;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_oferta_id", nullable = false)
    private ItemOferta itemOferta;

    private short quantidade;

    public UUID getListaCompraId(){
        if(Objects.isNull(this.getListaCompra()) || Objects.isNull(this.getListaCompra().getId())){
            return null;
        }
        return this.getListaCompra().getId();
    }

    public UUID getItemOfertaId(){
        if(Objects.isNull(this.getItemOferta()) || Objects.isNull(this.getItemOferta().getId())){
            return null;
        }
        return this.getItemOferta().getId();
    }

    public void alterarQuantidade(short quantidadeAdicional){
        this.quantidade = quantidadeAdicional;
    }

    public void adicionarQuantidade(short quantidade){
        this.quantidade += quantidade;
    }
}
