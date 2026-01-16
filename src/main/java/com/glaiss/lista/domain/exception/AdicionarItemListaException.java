package com.glaiss.lista.domain.exception;

import com.glaiss.core.exception.GlaissException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class AdicionarItemListaException extends GlaissException {

    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        pb.setTitle("Erro ao adicionar item na lista");
        pb.setDetail("Algo deu errado");
        return pb;
    }
}
