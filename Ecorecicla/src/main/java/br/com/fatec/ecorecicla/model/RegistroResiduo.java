package br.com.fatec.ecorecicla.model;

import jakarta.persistence.*;
import lombok.Setter;

@Entity
@Setter
public class RegistroResiduo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String municipio;

    private String estado;

    private Double quantidadeGerada;

    private Double taxaReciclagem;

    private Integer ano;

    // construtor vazio obrigatório
    public RegistroResiduo() {
    }

    public RegistroResiduo(Long id,
                           String municipio,
                           String estado,
                           Double quantidadeGerada,
                           Double taxaReciclagem,
                           Integer ano) {

        this.id = id;
        this.municipio = municipio;
        this.estado = estado;
        this.quantidadeGerada = quantidadeGerada;
        this.taxaReciclagem = taxaReciclagem;
        this.ano = ano;
    }

    public Long getId() {
        return id;
    }

    public String getMunicipio() {
        return municipio;
    }

    public String getEstado() {
        return estado;
    }

    public Double getQuantidadeGerada() {
        return quantidadeGerada;
    }

    public Double getTaxaReciclagem() {
        return taxaReciclagem;
    }

    public Integer getAno() {
        return ano;
    }
}