package org.arnoproject.ticketmanagementsystem.mapper;

import org.arnoproject.ticketmanagementsystem.dto.TicketResponse;
import org.arnoproject.ticketmanagementsystem.entity.Ticket;
import org.springframework.stereotype.Component;

import java.util.List;

// mapper/TicketMapper.java
@Component
public class TicketMapper {

    public TicketResponse toResponse(Ticket ticket) {
        return TicketResponse.builder()
                .id(ticket.getId())
                .ticketNumber(ticket.getTicketNumber())
                .issueType(ticket.getIssueType())
                .priority(ticket.getPriority())
                .status(ticket.getStatus())
                .description(ticket.getDescription())
                .affectedReference(ticket.getAffectedReference())
                .company(ticket.getCompany())
                .site(ticket.getSite())
                .createdBy(ticket.getCreatedBy().getName())
                .assignedTo(ticket.getAssignedTo() != null ? ticket.getAssignedTo().getName() : null)
                .createdAt(ticket.getCreatedAt())
                .updatedAt(ticket.getUpdatedAt())
                .build();
    }

    public List<TicketResponse> toResponseList(List<Ticket> tickets) {
        return tickets.stream()
                .map(this::toResponse)
                .toList();
    }
}