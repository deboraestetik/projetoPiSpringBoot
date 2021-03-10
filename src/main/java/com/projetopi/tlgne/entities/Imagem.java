package com.projetopi.tlgne.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Imagem implements Serializable {
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String caminho;

    @Lob
    public byte[] caminhoBlob;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    public Imagem() {
    }


    public Imagem(Long id, String caminho, byte[] blob, Produto produto) {
        this.id = id;
        this.caminho = caminho;
        this.caminhoBlob = blob;
        this.produto = produto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public byte[] getCaminhoBlob() {
        return caminhoBlob;
    }

    public void setCaminhoBlob(byte[] caminhoBlob) {
        this.caminhoBlob = caminhoBlob;
    }
}