package com.glaiss.lista.client.users;

import com.glaiss.lista.client.users.dto.ListaCompraDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;

@Validated
@FeignClient(name = "users-svc", url = "${glaiss.services.users.url}", primary = false)
public interface UsersService {

    @PatchMapping("/lista-compra/atualizar-valor-total")
    ListaCompraDto atualizarValorTotal(ListaCompraDto listaCompraDto);
}
