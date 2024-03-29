package com.example.API.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "tas_prduto")

public class ProductsModels implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Min(value = 1, message = "o valor deve ser maior ou igual a 1")
    private BigDecimal valor_custo;
    private String data_referencia;
    @Size(min = 5, max = 250, message = "A descrição deve ter entre 5 e 250 caracteres")
    private String descricao;
    @Size(min = 5, max = 10, message = "O tamanho do SKU deve ter entre 5 e 10 caracteres")
    private String sku;
    @Min(value = 1, message = "O tipo deve ser menor ou igual a 5")
    @Max(value = 5, message = "O tipo deve ser menor ou igual a 5")
    private String tipo_produto;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData_referencia() {
        return data_referencia;
    }

    public void setData_referencia(String data_referencia) {
        this.data_referencia = data_referencia;
    }

    public BigDecimal getValor_custo() {
        return valor_custo;
    }

    public void setValor_custo(BigDecimal valor_custo) {
        this.valor_custo = valor_custo;
    }

    public String getTipo_produto() {
        return tipo_produto;
    }

    public void setTipo_produto(String tipo_produto) {
        this.tipo_produto = tipo_produto;
    }
}
