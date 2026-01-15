package com.glaiss.lista.domain.model;

public enum EStatusLista {

    AGUARDANDO("AGUARDANDO", "Aguardando processamento"),
    CANCELADA("CANCELADA", "Cancelamento do processamento"),
    FINALIZADA("FINALIZADA", "Finalizado com sucesso");

    private String descricao;
    private String codigo;

    private EStatusLista(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public String getCodigo() {
        return this.codigo;
    }
}
