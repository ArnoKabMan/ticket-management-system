package org.arnoproject.ticketmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.arnoproject.ticketmanagementsystem.dto.AssignTicketRequest;
import org.arnoproject.ticketmanagementsystem.dto.TicketRequest;
import org.arnoproject.ticketmanagementsystem.dto.TicketResponse;
import org.arnoproject.ticketmanagementsystem.entity.Ticket;
import org.arnoproject.ticketmanagementsystem.entity.User;
import org.arnoproject.ticketmanagementsystem.enums.AuditAction;
import org.arnoproject.ticketmanagementsystem.enums.Role;
import org.arnoproject.ticketmanagementsystem.enums.TicketStatus;
import org.arnoproject.ticketmanagementsystem.mapper.TicketMapper;
import org.arnoproject.ticketmanagementsystem.repository.TicketRepository;
import org.arnoproject.ticketmanagementsystem.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

// service/TicketService.java
@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final TicketMapper ticketMapper;
    private final AuditLogService auditLogService;

    public TicketResponse createTicket(TicketRequest request, User currentUser) {
        String ticketNumber = "TKT-" + System.currentTimeMillis();

        Ticket ticket = Ticket.builder()
                .ticketNumber(ticketNumber)
                .issueType(request.getIssueType())
                .priority(request.getPriority())
                .description(request.getDescription())
                .affectedReference(request.getAffectedReference())
                .company(request.getCompany())
                .site(request.getSite())
                .status(TicketStatus.CREATED)
                .createdBy(currentUser)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        ticketRepository.save(ticket);

        //log
        auditLogService.log(ticket, AuditAction.TICKET_CREATED,
                currentUser, null, TicketStatus.CREATED.name());
        return ticketMapper.toResponse(ticket);
    }

    public List<TicketResponse> getAllTickets() {
        return ticketMapper.toResponseList(ticketRepository.findAll());
    }

    public TicketResponse getTicketById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        return ticketMapper.toResponse(ticket);
    }

    public List<TicketResponse> getMyTickets(User currentUser) {
        return ticketMapper.toResponseList(ticketRepository.findByCreatedBy(currentUser));
    }

    public TicketResponse updateTicket(Long id, TicketRequest request, User currentUser) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        String oldValue = ticket.getStatus().name();
        ticket.setIssueType(request.getIssueType());
        ticket.setPriority(request.getPriority());
        ticket.setDescription(request.getDescription());
        ticket.setAffectedReference(request.getAffectedReference());
        ticket.setCompany(request.getCompany());
        ticket.setSite(request.getSite());
        ticket.setUpdatedAt(LocalDateTime.now());

        ticketRepository.save(ticket);

        //log
        auditLogService.log(ticket, AuditAction.TICKET_UPDATED,
                currentUser, oldValue, ticket.getStatus().name());
        return ticketMapper.toResponse(ticket);
    }

    // in TicketService.java
    public void deleteTicket(Long id, User currentUser) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        //log
        auditLogService.log(ticket, AuditAction.TICKET_DELETED,
                currentUser, ticket.getStatus().name(), null);
        ticketRepository.delete(ticket);
    }
    public TicketResponse assignTicket(AssignTicketRequest request) {
        Ticket ticket = ticketRepository.findById(request.getTicketId())
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        User officer = userRepository.findById(request.getOfficerId())
                .orElseThrow(() -> new RuntimeException("Officer not found"));

        if (officer.getRole() != Role.SUPPORT_OFFICER) {
            throw new RuntimeException("User is not a support officer");
        }

        String oldValue = ticket.getStatus().name();

        ticket.setAssignedTo(officer);
        ticket.setStatus(TicketStatus.ASSIGNED);
        ticket.setUpdatedAt(LocalDateTime.now());

        ticketRepository.save(ticket);

        //log
        auditLogService.log(ticket, AuditAction.TICKET_ASSIGNED,
                officer, oldValue, TicketStatus.ASSIGNED.name());
        return ticketMapper.toResponse(ticket);
    }
}
