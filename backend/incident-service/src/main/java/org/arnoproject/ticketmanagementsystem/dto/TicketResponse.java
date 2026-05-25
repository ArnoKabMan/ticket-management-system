package org.arnoproject.ticketmanagementsystem.dto;

import lombok.Builder;
import lombok.Data;
import org.arnoproject.ticketmanagementsystem.enums.IssueType;
import org.arnoproject.ticketmanagementsystem.enums.Priority;
import org.arnoproject.ticketmanagementsystem.enums.TicketStatus;

import java.time.LocalDateTime;

// dto/TicketResponse.java
@Data
@Builder
public class TicketResponse {
    private Long id;
    private String ticketNumber;
    private IssueType issueType;
    private Priority priority;
    private TicketStatus status;
    private String description;
    private String affectedReference;
    private String company;
    private String site;
    private String createdBy;
    private String assignedTo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
