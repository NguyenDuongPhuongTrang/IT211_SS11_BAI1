package com.example.bai1.model.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    @NotBlank(message = "UserId không được để trống")
    private String userId;

    @NotBlank(message = "ProductId không được để trống")
    private String productId;

    @NotNull(message = "Quantity không được null")
    @Min(value = 1,message = "Quantity phải lớn hơn 0")
    private Integer quantity;
}
