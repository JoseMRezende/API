package com.example.API.dtos;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ProductsDto(@NotNull BigDecimal valor_custo, String data_referencia, String descricao,
                          String sku, String tipo_produto) {


}
