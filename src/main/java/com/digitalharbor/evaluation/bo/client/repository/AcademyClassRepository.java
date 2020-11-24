package com.digitalharbor.evaluation.bo.client.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.digitalharbor.evaluation.bo.client.model.AcademyClass;


public interface AcademyClassRepository extends JpaRepository<AcademyClass, Long> {
    public List<AcademyClass> findByTitleContainingIgnoreCaseAndDescriptionContainingIgnoreCase(String title, String description);
    public List<AcademyClass> findByTitleContainingIgnoreCase(String title);
    public List<AcademyClass> findByDescriptionContainingIgnoreCase(String description);
}