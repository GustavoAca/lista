package com.glaiss.lista.domain.model;

import com.glaiss.core.domain.model.EntityAbstract;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
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

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private BigDecimal preco;

    private Integer quantidade;

    private UUID listaCompra;
}
