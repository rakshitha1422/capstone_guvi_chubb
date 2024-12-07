package com.ouction.ouction_system.Repositery;

import com.ouction.ouction_system.model.AuditLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


    @Repository
    public interface AuditLogsRepository extends JpaRepository<AuditLogs, Long> {
    }

