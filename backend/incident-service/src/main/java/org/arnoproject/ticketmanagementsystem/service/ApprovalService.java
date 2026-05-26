package org.arnoproject.ticketmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.arnoproject.ticketmanagementsystem.dto.ApprovalDecisionRequest;
import org.arnoproject.ticketmanagementsystem.dto.ApprovalRequestDto;
import org.arnoproject.ticketmanagementsystem.dto.ApprovalResponse;
import org.arnoproject.ticketmanagementsystem.entity.ApprovalRequest;
import org.arnoproject.ticketmanagementsystem.entity.Ticket;
import org.arnoproject.ticketmanagementsystem.entity.User;
import org.arnoproject.ticketmanagementsystem.enums.ApprovalStatus;
import org.arnoproject.ticketmanagementsystem.enums.AuditAction;
import org.arnoproject.ticketmanagementsystem.enums.TicketStatus;
import org.arnoproject.ticketmanagementsystem.mapper.ApprovalMapper;
import org.arnoproject.ticketmanagementsystem.repository.ApprovalRequestRepository;
import org.arnoproject.ticketmanagementsystem.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

// service/ApprovalService.java
@Service
@RequiredArgsConstructor
public class ApprovalService {

    private final ApprovalRequestRepository approvalRequestRepository;
    private final TicketRepository ticketRepository;
    private final ApprovalMapper approvalMapper;
    private final AuditLogService auditLogService;

    public ApprovalResponse requestApproval(ApprovalRequestDto request, User currentUser) {

        Ticket ticket = ticketRepository.findById(request.getTicketId())
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        if (ticket.getStatus() == TicketStatus.PENDING_APPROVAL) {
            throw new RuntimeException("Approval already requested for this ticket");
        }

        if (ticket.getAssignedTo() == null ||
                !ticket.getAssignedTo().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Only the assigned officer can request approval");
        }

        ApprovalRequest approvalRequest = ApprovalRequest.builder()
                .ticket(ticket)
                .requestedBy(currentUser)
                .status(ApprovalStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        approvalRequestRepository.save(approvalRequest);

        ticket.setStatus(TicketStatus.PENDING_APPROVAL);
        ticket.setUpdatedAt(LocalDateTime.now());
        ticketRepository.save(ticket);

        //log
        auditLogService.log(ticket, AuditAction.APPROVAL_REQUESTED,
                currentUser, TicketStatus.IN_PROGRESS.name(),
                TicketStatus.PENDING_APPROVAL.name());
        return approvalMapper.toResponse(approvalRequest);
    }

    public ApprovalResponse approveRequest(Long approvalId,
                                           ApprovalDecisionRequest request,
                                           User currentUser) {
        ApprovalRequest approvalRequest = approvalRequestRepository.findById(approvalId)
                .orElseThrow(() -> new RuntimeException("Approval request not found"));

        if (approvalRequest.getStatus() != ApprovalStatus.PENDING) {
            throw new RuntimeException("Approval request already processed");
        }

        approvalRequest.setStatus(ApprovalStatus.APPROVED);
        approvalRequest.setApprovedBy(currentUser);
        approvalRequest.setComment(request.getComment());
        approvalRequest.setUpdatedAt(LocalDateTime.now());

        approvalRequestRepository.save(approvalRequest);

        Ticket ticket = approvalRequest.getTicket();
        ticket.setStatus(TicketStatus.APPROVED);
        ticket.setUpdatedAt(LocalDateTime.now());
        ticketRepository.save(ticket);

        //log
        auditLogService.log(ticket, AuditAction.APPROVAL_APPROVED,
                currentUser, TicketStatus.PENDING_APPROVAL.name(),
                TicketStatus.APPROVED.name());

        return approvalMapper.toResponse(approvalRequest);
    }

    public ApprovalResponse rejectRequest(Long approvalId,
                                          ApprovalDecisionRequest request,
                                          User currentUser) {
        ApprovalRequest approvalRequest = approvalRequestRepository.findById(approvalId)
                .orElseThrow(() -> new RuntimeException("Approval request not found"));

        if (approvalRequest.getStatus() != ApprovalStatus.PENDING) {
            throw new RuntimeException("Approval request already processed");
        }

        approvalRequest.setStatus(ApprovalStatus.REJECTED);
        approvalRequest.setApprovedBy(currentUser);
        approvalRequest.setComment(request.getComment());
        approvalRequest.setUpdatedAt(LocalDateTime.now());

        approvalRequestRepository.save(approvalRequest);

        Ticket ticket = approvalRequest.getTicket();
        ticket.setStatus(TicketStatus.REJECTED);
        ticket.setUpdatedAt(LocalDateTime.now());
        ticketRepository.save(ticket);

        //log
        auditLogService.log(ticket, AuditAction.APPROVAL_REJECTED,
                currentUser, TicketStatus.PENDING_APPROVAL.name(),
                TicketStatus.REJECTED.name());

        return approvalMapper.toResponse(approvalRequest);
    }

    public List<ApprovalResponse> getPendingApprovals() {
        return approvalMapper.toResponseList(
                approvalRequestRepository.findByStatus(ApprovalStatus.PENDING)
        );
    }
}
