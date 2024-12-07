package com.ouction.ouction_system.service;

import com.ouction.ouction_system.Repositery.AuditLogsRepository;
import com.ouction.ouction_system.model.AuditLogs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuditLogsService {

    @Autowired
    private AuditLogsRepository auditLogsRepository;

    public AuditLogs createLog(String userId, String action, String detail) {
        AuditLogs log = new AuditLogs();
        log.setUsername(userId);
        log.setAction(action);
        log.setTimestamp(LocalDateTime.now());
        log.setDetail(detail);

        return auditLogsRepository.save(log);
    }
}

