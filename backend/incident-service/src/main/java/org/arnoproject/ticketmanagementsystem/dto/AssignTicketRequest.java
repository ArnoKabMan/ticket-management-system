package org.arnoproject.ticketmanagementsystem.dto;

import lombok.Data;

// dto/AssignTicketRequest.java
@Data
public class AssignTicketRequest {
    private Long ticketId;
    private Long officerId;
}
