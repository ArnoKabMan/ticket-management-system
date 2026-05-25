package org.arnoproject.ticketmanagementsystem.dto;

import lombok.Data;
import org.arnoproject.ticketmanagementsystem.enums.Role;

// dto/RegisterRequest.java
@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private Role role;
}




