package net.hackyourfuture.WeekFourRestAPI.category.dto.request;

import jakarta.validation.constraints.*;
public record CreateCategoryRequest(
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must be 100 characters or fewer")
    String name,
    
    @NotBlank(message = "Image is required")
    @Pattern(regexp = "^https?://.*", message = "Image must be a valid http(s) URL")
    String image) {}