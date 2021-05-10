package com.projetopi.tlgne.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Frete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String trasportadora;
    private double valorFrete;
    @OneToOne()
    @JoinColumn(name = "id_venda", referencedColumnName = "id")
    private Venda venda;
}
