package org.arnoproject.ticketmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arnoproject.ticketmanagementsystem.enums.IssueType;
import org.arnoproject.ticketmanagementsystem.enums.Priority;
import org.arnoproject.ticketmanagementsystem.enums.TicketStatus;

import java.time.LocalDateTime;

// entity/Ticket.java
@Entity
@Table(name = "tickets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String ticketNumber;

    @Enumerated(EnumType.STRING)
    private IssueType issueType;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    private String description;

    private String affectedReference;

    private String company;

    private String site;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private User assignedTo;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
