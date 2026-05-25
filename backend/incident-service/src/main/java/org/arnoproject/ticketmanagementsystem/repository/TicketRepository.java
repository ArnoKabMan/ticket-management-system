package org.arnoproject.ticketmanagementsystem.repository;

import org.arnoproject.ticketmanagementsystem.entity.Ticket;
import org.arnoproject.ticketmanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// repository/TicketRepository.java
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByCreatedBy(User user);
    List<Ticket> findByAssignedTo(User user);
    Optional<Ticket> findByTicketNumber(String ticketNumber);
}
