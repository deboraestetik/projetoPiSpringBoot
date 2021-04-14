package com.projetopi.tlgne.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String nome;
    @NotNull
    private String sobrenome;
    @NotNull
    private long telefone;
    @NotNull
    @Column(unique = true)
    @CPF
    private String cpf;
    @NotNull
    private String sexo;
    @NotNull
    private Date dataNascimento;
    @OneToOne()
    @JoinColumn(name = "id_usuario", referencedColumnName = "usuario_id")
    private Usuario usuario;
    private EnderecoCliente enderecoCobranca;
    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private List<EnderecoCliente> endereco = new ArrayList<>();

    public void setEndereco(EnderecoCliente endereco) {
        this.endereco.add(endereco);
    }
}
