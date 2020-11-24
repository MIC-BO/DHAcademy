package com.digitalharbor.evaluation.bo.client.service;

import com.digitalharbor.evaluation.bo.client.model.AcademyClass;
import com.digitalharbor.evaluation.bo.client.repository.AcademyClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AcademyClassServiceImpl implements AcademyClassService{
    private final AcademyClassRepository academyClassRepository;

    @Override
    public List<AcademyClass> getAcademyClassesByTitleAndDescription(String title, String description) {
        if (title == null){
            return academyClassRepository.findByDescriptionContainingIgnoreCase(description);
        }
        else if (description == null){
            return academyClassRepository.findByTitleContainingIgnoreCase(title);
        }

        return academyClassRepository.findByTitleContainingIgnoreCaseAndDescriptionContainingIgnoreCase(title, description);
    }

    @Override
    public AcademyClass getAcademyClass(Long code) {
        return academyClassRepository.findById(code).orElse(null);
    }

    @Override
    public AcademyClass saveAcademyClass(AcademyClass academyClass) {
        if (academyClass.getCode() == null){
            return academyClassRepository.save(academyClass);
        }

        AcademyClass academyClassDB = getAcademyClass(academyClass.getCode());

        if (academyClassDB == null){
            return null;
        }

        academyClassDB.setDescription(academyClass.getDescription());
        academyClassDB.setTitle(academyClass.getTitle());
        return academyClassRepository.save(academyClassDB);
    }

    @Override
    public void deleteAcademyClass(AcademyClass academyClass) {
        academyClassRepository.delete(academyClass);
    }
}

