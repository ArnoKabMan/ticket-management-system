package org.arnoproject.ticketmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.arnoproject.ticketmanagementsystem.dto.AssignTicketRequest;
import org.arnoproject.ticketmanagementsystem.dto.TicketRequest;
import org.arnoproject.ticketmanagementsystem.dto.TicketResponse;
import org.arnoproject.ticketmanagementsystem.entity.User;
import org.arnoproject.ticketmanagementsystem.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// controller/TicketController.java
@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<TicketResponse> createTicket(
            @RequestBody TicketRequest request,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(ticketService.createTicket(request, currentUser));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPPORT_OFFICER','SUPERVISORT','ADMIN')")
    public ResponseEntity<List<TicketResponse>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponse> getTicketById(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.getTicketById(id));
    }

    @GetMapping("/my-tickets")
    public ResponseEntity<List<TicketResponse>> getMyTickets(
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(ticketService.getMyTickets(currentUser));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPPORT_OFFICER','SUPERVISORT','ADMIN')")
    public ResponseEntity<TicketResponse> updateTicket(
            @PathVariable Long id,
            @RequestBody TicketRequest request) {
        return ResponseEntity.ok(ticketService.updateTicket(id, request));
    }

    // Only ADMIN can delete
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/assign")
    @PreAuthorize("hasAnyRole('SUPERVISOR', 'ADMIN')")
    public ResponseEntity<TicketResponse> assignTicket(
            @RequestBody AssignTicketRequest request) {
        return ResponseEntity.ok(ticketService.assignTicket(request));
    }
}
