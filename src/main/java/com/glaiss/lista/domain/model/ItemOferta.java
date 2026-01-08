package com.glaiss.lista.domain.model;

import com.glaiss.core.domain.model.EntityAbstract;
import com.glaiss.core.utils.anotacao.ValorBigDecimal;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
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
    @Builder.Default
    private Boolean hasPromocaoAtiva = Boolean.FALSE;

    @ValorBigDecimal
    private BigDecimal preco;

    @Column(name = "data_inicio_promocao")
    private LocalDateTime dataInicioPromocao;

    @Column(name = "data_final_promocao")
    private LocalDateTime dataFinalPromocao;

    @OneToMany(mappedBy = "itemOferta", cascade = CascadeType.ALL)
    private List<ItemLista> registrosEmListas = new LinkedList<>();

    @OneToMany(mappedBy = "itemOferta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PrecoReportadoPendente> precoReportadoPendentes = new LinkedList<>();

    @OneToMany(mappedBy = "itemOferta", cascade = CascadeType.ALL)
    private List<HistoricoPreco> historicoPrecos = new LinkedList<>();

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
}
