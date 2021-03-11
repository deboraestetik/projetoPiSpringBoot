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

    @Transient
    private byte[] imagem;

    private boolean imagemPrincipal;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    public Imagem() {
    }

    public Imagem(Long id, String caminho, byte[] imagem, boolean imagemPrincipal, Produto produto) {
        this.id = id;
        this.caminho = caminho;
        this.imagem = imagem;
        this.imagemPrincipal = imagemPrincipal;
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

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public boolean isImagemPrincipal() {
        return imagemPrincipal;
    }

    public void setImagemPrincipal(boolean imagemPrincipal) {
        this.imagemPrincipal = imagemPrincipal;
    }
}