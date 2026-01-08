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
@Table(name = "status_preco_reportado")
@Entity
public class StatusPrecoReportado extends EntityAbstract {

    @Id
    @Enumerated(EnumType.STRING)
    private EStatusPrecoReportado codigo;

    private String descricao;

    @OneToMany(mappedBy = "statusPrecoReportado", cascade = CascadeType.ALL)
    private List<PrecoReportadoPendente> precoReportadoPendentes = new LinkedList<>();
}
