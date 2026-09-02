package com.zest.productapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProductRequest(@NotBlank @Size(max=255) String productName) {}
