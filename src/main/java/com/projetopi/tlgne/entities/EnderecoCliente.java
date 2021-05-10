package com.projetopi.tlgne.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoCliente implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String logradouro;
    @NotNull
    private String cep;
    @NotNull
    private String cidade;
    @NotNull
    private String uf;
    @NotNull
    private boolean status;
    @NotNull
    private String bairro;
    private String complemento;
    @NotNull
    private int numero;
    @NotNull
    private boolean principal;
    private boolean enderecoCobranca;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;


}
