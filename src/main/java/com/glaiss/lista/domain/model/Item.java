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

    private String descricao;

    @Column(name = "is_ativo")
    @Builder.Default
    private Boolean isAtivo = Boolean.TRUE;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<ItemOferta> itemPromocaos = new LinkedList<>();
}
