package com.projetopi.tlgne.entities;

public enum Meses {
    
    JAN(1, "Jan", "Janeiro"),
    FEV(2, "Fev", "Fevereiro"),
    MAR(3, "Mar", "Março"),
    ABR(4, "Abr", "Abril"),
    MAI(5, "Mai", "Maio"),
    JUN(6, "Jun", "Junho"),
    JUL(7, "Jul", "Julho"),
    AGO(1, "Ago", "Agosto"),
    SET(1, "Set", "Setembro"),
    OUT(1, "Out", "Outubro"),
    NOV(1, "Nov", "Novembro"),
    DEZ(1, "Dez", "Dezembro");

    private final int numeroMes;
    private final String nomeAbreviado;
    private final String nomeCompleto;

    private Meses(int numeroMes, String nomeAbreviado, String nomeCompleto) {
        this.numeroMes = numeroMes;
        this.nomeAbreviado = nomeAbreviado;
        this.nomeCompleto = nomeCompleto;
    }

    public int getNumeroMes() {
        return this.numeroMes;
    }

    public String getNomeAbreviado() {
        return this.nomeAbreviado;
    }

    public String getNomeCompleto() {
        return this.nomeCompleto;
    }

}
