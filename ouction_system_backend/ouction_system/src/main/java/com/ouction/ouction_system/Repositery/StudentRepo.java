package com.ouction.ouction_system.Repositery;

import com.ouction.ouction_system.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
 public interface StudentRepo extends JpaRepository<Student, Integer> {

}
