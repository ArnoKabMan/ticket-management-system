package org.arnoproject.ticketmanagementsystem.mapper;

import org.arnoproject.ticketmanagementsystem.dto.AuditLogResponse;
import org.arnoproject.ticketmanagementsystem.entity.AuditLog;
import org.springframework.stereotype.Component;

import java.util.List;

// mapper/AuditLogMapper.java
@Component
public class AuditLogMapper {

    public AuditLogResponse toResponse(AuditLog auditLog) {
        return AuditLogResponse.builder()
                .id(auditLog.getId())
                .ticketNumber(auditLog.getTicket().getTicketNumber())
                .action(auditLog.getAction())
                .performedBy(auditLog.getPerformedBy().getName())
                .oldValue(auditLog.getOldValue())
                .newValue(auditLog.getNewValue())
                .timestamp(auditLog.getTimestamp())
                .build();
    }

    public List<AuditLogResponse> toResponseList(List<AuditLog> auditLogs) {
        return auditLogs.stream()
                .map(this::toResponse)
                .toList();
    }
}
