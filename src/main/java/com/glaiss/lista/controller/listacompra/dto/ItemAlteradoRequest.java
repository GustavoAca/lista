package com.glaiss.lista.controller.listacompra.dto;

import java.util.UUID;

public record ItemAlteradoRequest(UUID id,
                                  short quantidade){
}
