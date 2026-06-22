package net.hackyourfuture.WeekFourRestAPI.product.dto.request;

import net.hackyourfuture.WeekFourRestAPI.category.dto.request.CreateCategoryRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateProductRequest(
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must be 100 characters or fewer")
    String title,

    @Positive(message = "Price must be greater than 0")
    @Max(value = 100000, message = "Price must be 100000 or less")
    BigDecimal price,

    @Valid
    @NotNull(message = "Category is required")
    CreateCategoryRequest category) {
}