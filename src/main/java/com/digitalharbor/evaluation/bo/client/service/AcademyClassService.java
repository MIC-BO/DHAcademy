package com.digitalharbor.evaluation.bo.client.service;

import com.digitalharbor.evaluation.bo.client.model.AcademyClass;
import java.util.List;


public interface AcademyClassService {
    public List<AcademyClass> getAcademyClassesByTitleAndDescription(String title, String description);
    public AcademyClass getAcademyClass(Long code);
    public AcademyClass saveAcademyClass(AcademyClass academyClass);
    public void deleteAcademyClass(AcademyClass academyClass);
}