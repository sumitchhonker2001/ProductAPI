package com.zest.productapi.dto;
import java.time.Instant;
public record ProductResponse(Long id,String productName,String createdBy,Instant createdOn,String modifiedBy,Instant modifiedOn) {}
