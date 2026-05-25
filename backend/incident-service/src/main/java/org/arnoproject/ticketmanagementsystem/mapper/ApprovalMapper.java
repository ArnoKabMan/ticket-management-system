package org.arnoproject.ticketmanagementsystem.mapper;

import org.arnoproject.ticketmanagementsystem.dto.ApprovalResponse;
import org.arnoproject.ticketmanagementsystem.entity.ApprovalRequest;
import org.springframework.stereotype.Component;

import java.util.List;

// mapper/ApprovalMapper.java
@Component
public class ApprovalMapper {

    public ApprovalResponse toResponse(ApprovalRequest approvalRequest) {
        return ApprovalResponse.builder()
                .id(approvalRequest.getId())
                .ticketId(approvalRequest.getTicket().getId())
                .ticketNumber(approvalRequest.getTicket().getTicketNumber())
                .requestedBy(approvalRequest.getRequestedBy().getName())
                .approvedBy(approvalRequest.getApprovedBy() != null
                        ? approvalRequest.getApprovedBy().getName() : null)
                .status(approvalRequest.getStatus())
                .comment(approvalRequest.getComment())
                .createdAt(approvalRequest.getCreatedAt())
                .updatedAt(approvalRequest.getUpdatedAt())
                .build();
    }

    public List<ApprovalResponse> toResponseList(List<ApprovalRequest> approvalRequests) {
        return approvalRequests.stream()
                .map(this::toResponse)
                .toList();
    }
}
