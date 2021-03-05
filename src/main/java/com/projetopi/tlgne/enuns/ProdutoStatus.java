package com.projetopi.tlgne.enuns;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum ProdutoStatus {
    @JsonProperty("Ativo")
    ATIVO(0),
    @JsonProperty("Inativo")
    INATIVO(1);

    private int valor;

    ProdutoStatus(int i) {
        this.valor = i;
    }

    public int getValor() {
        return valor;
    }
}
