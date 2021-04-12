package com.projetopi.tlgne.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Venda implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double valorTotal;
    @ManyToOne
    @JoinColumn(name = "endereco_id")
    private EnderecoCliente enderecoCliente;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    @OneToMany(mappedBy = "venda")
    private List<DetalhesVenda> detalhesVenda;
}

