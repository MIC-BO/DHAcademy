package com.digitalharbor.evaluation.bo.client.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.digitalharbor.evaluation.bo.client.model.Assignment;


public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    public List<Assignment> findByStudentID(Long studentID);
    public List<Assignment> findByCode(Long code);
}