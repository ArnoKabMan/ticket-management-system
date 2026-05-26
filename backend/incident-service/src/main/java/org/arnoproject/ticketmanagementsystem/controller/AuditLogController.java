package org.arnoproject.ticketmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.arnoproject.ticketmanagementsystem.dto.AuditLogResponse;
import org.arnoproject.ticketmanagementsystem.entity.Ticket;
import org.arnoproject.ticketmanagementsystem.repository.TicketRepository;
import org.arnoproject.ticketmanagementsystem.service.AuditLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// controller/AuditLogController.java
@RestController
@RequestMapping("/api/audit")
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditLogService auditLogService;
    private final TicketRepository ticketRepository;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AuditLogResponse>> getAllLogs() {
        return ResponseEntity.ok(auditLogService.getAllLogs());
    }

    @GetMapping("/ticket/{ticketId}")
    @PreAuthorize("hasAnyRole('SUPPORT_OFFICER', 'SUPERVISOR', 'ADMIN')")
    public ResponseEntity<List<AuditLogResponse>> getLogsByTicket(@PathVariable Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        return ResponseEntity.ok(auditLogService.getLogsByTicket(ticket));
    }
}
