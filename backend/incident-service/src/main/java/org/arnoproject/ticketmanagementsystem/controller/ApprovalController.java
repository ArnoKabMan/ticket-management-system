package org.arnoproject.ticketmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.arnoproject.ticketmanagementsystem.dto.ApprovalDecisionRequest;
import org.arnoproject.ticketmanagementsystem.dto.ApprovalRequestDto;
import org.arnoproject.ticketmanagementsystem.dto.ApprovalResponse;
import org.arnoproject.ticketmanagementsystem.entity.User;
import org.arnoproject.ticketmanagementsystem.service.ApprovalService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// controller/ApprovalController.java
@RestController
@RequestMapping("/api/approvals")
@RequiredArgsConstructor
public class ApprovalController {

    private final ApprovalService approvalService;

    @PostMapping("/request")
    @PreAuthorize("hasRole('SUPPORT_OFFICER')")
    public ResponseEntity<ApprovalResponse> requestApproval(
            @RequestBody ApprovalRequestDto request,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(approvalService.requestApproval(request, currentUser));
    }

    @PostMapping("/{id}/approve")
    @PreAuthorize("hasAnyRole('SUPERVISOR', 'ADMIN')")
    public ResponseEntity<ApprovalResponse> approve(
            @PathVariable Long id,
            @RequestBody ApprovalDecisionRequest request,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(approvalService.approveRequest(id, request, currentUser));
    }

    @PostMapping("/{id}/reject")
    @PreAuthorize("hasAnyRole('SUPERVISOR', 'ADMIN')")
    public ResponseEntity<ApprovalResponse> reject(
            @PathVariable Long id,
            @RequestBody ApprovalDecisionRequest request,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(approvalService.rejectRequest(id, request, currentUser));
    }

    @GetMapping("/pending")
    @PreAuthorize("hasAnyRole('SUPERVISOR', 'ADMIN')")
    public ResponseEntity<List<ApprovalResponse>> getPendingApprovals() {
        return ResponseEntity.ok(approvalService.getPendingApprovals());
    }
}
