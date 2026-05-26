package org.arnoproject.ticketmanagementsystem.repository;

import org.arnoproject.ticketmanagementsystem.entity.AuditLog;
import org.arnoproject.ticketmanagementsystem.entity.Ticket;
import org.arnoproject.ticketmanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// repository/AuditLogRepository.java
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findByTicket(Ticket ticket);
    List<AuditLog> findByPerformedBy(User user);
}
