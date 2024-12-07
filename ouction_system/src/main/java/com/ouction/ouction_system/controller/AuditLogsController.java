package com.ouction.ouction_system.controller;

import com.ouction.ouction_system.service.AuditLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/audit-logs")
public class AuditLogsController {

    @Autowired
    private AuditLogsService auditLogsService;

    @PostMapping("/create")
    public ResponseEntity<String> createAuditLog(@RequestBody Map<String, Object> logData) {
        try {
            String username =  logData.get("username").toString();
            String action = logData.get("action").toString();
            String detail = logData.get("detail").toString();

            auditLogsService.createLog(username, action, detail);
            return ResponseEntity.ok("Audit log created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating audit log: " + e.getMessage());
        }
    }
}

