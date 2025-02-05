package com.glaiss.lista.client.users;

import com.glaiss.lista.client.users.dto.ListaCompraDto;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class UserServiceImpl implements UsersService {

    @Override
    public ListaCompraDto atualizarValorTotal(ListaCompraDto listaCompraDto) {
        return null;
    }
}
