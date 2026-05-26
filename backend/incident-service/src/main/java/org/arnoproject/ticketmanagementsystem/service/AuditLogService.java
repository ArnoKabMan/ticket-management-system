package org.arnoproject.ticketmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.arnoproject.ticketmanagementsystem.dto.AuditLogResponse;
import org.arnoproject.ticketmanagementsystem.entity.AuditLog;
import org.arnoproject.ticketmanagementsystem.entity.Ticket;
import org.arnoproject.ticketmanagementsystem.entity.User;
import org.arnoproject.ticketmanagementsystem.enums.AuditAction;
import org.arnoproject.ticketmanagementsystem.mapper.AuditLogMapper;
import org.arnoproject.ticketmanagementsystem.repository.AuditLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

// service/AuditLogService.java
@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final AuditLogMapper auditLogMapper;

    public void log(Ticket ticket, AuditAction action, User performedBy,
                    String oldValue, String newValue) {
        AuditLog auditLog = AuditLog.builder()
                .ticket(ticket)
                .action(action)
                .performedBy(performedBy)
                .oldValue(oldValue)
                .newValue(newValue)
                .timestamp(LocalDateTime.now())
                .build();
        auditLogRepository.save(auditLog);
    }

    public List<AuditLogResponse> getLogsByTicket(Ticket ticket) {
        return auditLogMapper.toResponseList(
                auditLogRepository.findByTicket(ticket)
        );
    }

    public List<AuditLogResponse> getAllLogs() {
        return auditLogMapper.toResponseList(auditLogRepository.findAll());
    }
}
