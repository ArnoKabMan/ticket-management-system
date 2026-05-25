package org.arnoproject.ticketmanagementsystem.repository;

import org.arnoproject.ticketmanagementsystem.entity.ApprovalRequest;
import org.arnoproject.ticketmanagementsystem.entity.Ticket;
import org.arnoproject.ticketmanagementsystem.enums.ApprovalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// repository/ApprovalRequestRepository.java
public interface ApprovalRequestRepository extends JpaRepository<ApprovalRequest, Long> {
    List<ApprovalRequest> findByTicket(Ticket ticket);
    List<ApprovalRequest> findByStatus(ApprovalStatus status);
}
