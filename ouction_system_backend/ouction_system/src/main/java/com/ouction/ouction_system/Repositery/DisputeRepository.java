package com.ouction.ouction_system.Repositery;

import com.ouction.ouction_system.model.Dispute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DisputeRepository extends JpaRepository<Dispute, Long> {

    List<Dispute> findByStatus(String status);
}
