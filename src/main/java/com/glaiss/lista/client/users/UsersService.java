package com.glaiss.lista.client.users;

import com.glaiss.lista.client.users.dto.ListaCompraDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Validated
@FeignClient(name = "USERS", primary = false)
public interface UsersService {

    @PutMapping("/lista-compra/atualizar-valor-total")
    ListaCompraDto atualizarValorTotal(@RequestBody ListaCompraDto listaCompraDto);
}
