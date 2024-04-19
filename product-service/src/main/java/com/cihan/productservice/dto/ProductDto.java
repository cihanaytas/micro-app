package com.cihan.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProductDto {
    @Min(value = 4)
    @Max(value = 20)
    @NotNull
    private String name;
    @Min(value = 10)
    @Max(value = 100)
    @NotNull
    private String description;
    @NotNull
    private BigDecimal price;
}
