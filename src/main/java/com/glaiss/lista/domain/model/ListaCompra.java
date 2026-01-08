package com.glaiss.lista.domain.model;

import com.glaiss.core.domain.model.EntityAbstract;
import com.glaiss.core.utils.anotacao.ValorBigDecimal;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "listas_compra")
@Entity
public class ListaCompra extends EntityAbstract {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "usuario_id", nullable = false)
    private UUID usuarioId;

    @Column(name = "valor_total")
    @ValorBigDecimal
    @Builder.Default
    private BigDecimal valorTotal = BigDecimal.ZERO;

    @Column(name = "total_itens")
    private short totalItens;

    @OneToMany(mappedBy = "listaCompra", cascade = CascadeType.ALL)
    private List<ItemLista> itensLista = new LinkedList<>();
}
