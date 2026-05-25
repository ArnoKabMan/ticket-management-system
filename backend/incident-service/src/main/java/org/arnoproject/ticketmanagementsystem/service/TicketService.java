package org.arnoproject.ticketmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.arnoproject.ticketmanagementsystem.dto.AssignTicketRequest;
import org.arnoproject.ticketmanagementsystem.dto.TicketRequest;
import org.arnoproject.ticketmanagementsystem.dto.TicketResponse;
import org.arnoproject.ticketmanagementsystem.entity.Ticket;
import org.arnoproject.ticketmanagementsystem.entity.User;
import org.arnoproject.ticketmanagementsystem.enums.Role;
import org.arnoproject.ticketmanagementsystem.enums.TicketStatus;
import org.arnoproject.ticketmanagementsystem.mapper.TicketMapper;
import org.arnoproject.ticketmanagementsystem.repository.TicketRepository;
import org.arnoproject.ticketmanagementsystem.repository.UserRepository;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

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

    public TicketResponse updateTicket(Long id, TicketRequest request) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        ticket.setIssueType(request.getIssueType());
        ticket.setPriority(request.getPriority());
        ticket.setDescription(request.getDescription());
        ticket.setAffectedReference(request.getAffectedReference());
        ticket.setCompany(request.getCompany());
        ticket.setSite(request.getSite());
        ticket.setUpdatedAt(LocalDateTime.now());

        ticketRepository.save(ticket);
        return ticketMapper.toResponse(ticket);
    }

    // in TicketService.java
    public void deleteTicket(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
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

        ticket.setAssignedTo(officer);
        ticket.setStatus(TicketStatus.ASSIGNED);
        ticket.setUpdatedAt(LocalDateTime.now());

        ticketRepository.save(ticket);
        return ticketMapper.toResponse(ticket);
    }
}
