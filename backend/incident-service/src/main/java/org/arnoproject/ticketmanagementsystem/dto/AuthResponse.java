package org.arnoproject.ticketmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

// dto/AuthResponse.java
@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String role;
}
