package org.arnoproject.ticketmanagementsystem.dto;

import lombok.Builder;
import lombok.Data;
import org.arnoproject.ticketmanagementsystem.enums.AuditAction;

import java.time.LocalDateTime;

// dto/AuditLogResponse.java
@Data
@Builder
public class AuditLogResponse {
    private Long id;
    private String ticketNumber;
    private AuditAction action;
    private String performedBy;
    private String oldValue;
    private String newValue;
    private LocalDateTime timestamp;
}
