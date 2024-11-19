package com.glaiss.lista.domain.model.dto;

import com.glaiss.core.domain.model.dto.EntityAbstractDto;
import com.glaiss.lista.domain.model.Preco;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ItemDto extends EntityAbstractDto {

    private UUID id;
    private String nome;
    private String peso;
    private String marca;
    private List<Preco> precos = new ArrayList<>();
}
