package com.glaiss.lista.domain.model;

import com.glaiss.core.domain.model.EntityAbstract;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "itens")
@Entity
public class Item extends EntityAbstract {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nome;
    private String peso;
    private String marca;

    @ManyToMany
    @JoinTable(
            name = "itens_tem_precos",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "preco_id")
    )
    @Builder.Default
    private List<Preco> precos = new LinkedList<>();

    @OneToMany(mappedBy = "item")
    @Builder.Default
    private List<ItemLista> itensLista = new LinkedList<>();
}
