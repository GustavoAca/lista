package com.glaiss.lista.domain.model;

public enum EStatusPrecoReportado {

    AGUARDANDO("AGUARDANDO", "Aguardando processamento"),
    ERRO("ERRO", "Erro no processamento"),
    FINALIZADO("FINALIZADO", "Processamento finalizado com sucesso");

    private String descricao;
    private String codigo;

    private EStatusPrecoReportado(String codigo, String descricao) {
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
