package org.arnoproject.ticketmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arnoproject.ticketmanagementsystem.enums.AuditAction;

import java.time.LocalDateTime;

// entity/AuditLog.java
@Entity
@Table(name = "audit_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @Enumerated(EnumType.STRING)
    private AuditAction action;

    @ManyToOne
    @JoinColumn(name = "performed_by")
    private User performedBy;

    private String oldValue;
    private String newValue;

    private LocalDateTime timestamp;
}
