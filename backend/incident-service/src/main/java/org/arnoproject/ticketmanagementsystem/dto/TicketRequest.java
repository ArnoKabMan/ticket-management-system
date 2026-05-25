package org.arnoproject.ticketmanagementsystem.dto;

import lombok.Data;
import org.arnoproject.ticketmanagementsystem.enums.IssueType;
import org.arnoproject.ticketmanagementsystem.enums.Priority;

// dto/TicketRequest.java
@Data
public class TicketRequest {
    private IssueType issueType;
    private Priority priority;
    private String description;
    private String affectedReference;
    private String company;
    private String site;
}


