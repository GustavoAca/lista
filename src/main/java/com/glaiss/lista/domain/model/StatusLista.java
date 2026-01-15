package com.glaiss.lista.domain.model;

import com.glaiss.core.domain.model.EntityAbstract;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "status_lista")
@Entity
public class StatusLista extends EntityAbstract {

    @Id
    @Enumerated(EnumType.STRING)
    private EStatusLista codigo;

    private String descricao;

    @OneToMany(mappedBy = "statusLista", cascade = CascadeType.ALL)
    private List<ListaCompra> listaCompra = new LinkedList<>();
}
