package org.arnoproject.ticketmanagementsystem.dto;

import lombok.Builder;
import lombok.Data;
import org.arnoproject.ticketmanagementsystem.enums.ApprovalStatus;

import java.time.LocalDateTime;

// dto/ApprovalResponse.java
@Data
@Builder
public class ApprovalResponse {
    private Long id;
    private Long ticketId;
    private String ticketNumber;
    private String requestedBy;
    private String approvedBy;
    private ApprovalStatus status;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}