package com.digitalharbor.evaluation.bo.client.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.digitalharbor.evaluation.bo.client.model.Student;


public interface StudentRepository extends JpaRepository<Student, Long> {
    public List<Student> findByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCase(String firstName, String lastName);
    public List<Student> findByFirstNameContainingIgnoreCase(String firstName);
    public List<Student> findByLastNameContainingIgnoreCase(String lastName);
}