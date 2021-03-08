package com.projetopi.tlgne.enuns;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum ProdutoStatus {
    @JsonProperty("Inativo")
    INATIVO(0),
    @JsonProperty("Ativo")
    ATIVO(1);


    private int valor;

    ProdutoStatus(int i) {
        this.valor = i;
    }

    public int getValor() {
        return valor;
    }
}
