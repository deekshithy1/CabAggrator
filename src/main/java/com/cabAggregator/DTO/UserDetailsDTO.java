package com.cabAggregator.DTO;

public record UserDetailsDTO(
        String id,
        String email,
        String role // "USER" or "CAPTAIN"

) {}
