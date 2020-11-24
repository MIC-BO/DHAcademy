package com.digitalharbor.evaluation.bo.client.service;

import com.digitalharbor.evaluation.bo.client.model.Assignment;
import com.digitalharbor.evaluation.bo.client.repository.AssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {
    private final AssignmentRepository assignmentRepository;

    @Override
    public List<Assignment> getAssignmentsByStudentID(Long studentID) {
        return assignmentRepository.findByStudentID(studentID);
    }

    @Override
    public List<Assignment> getAssignmentsByCode(Long code) {
        return assignmentRepository.findByCode(code);
    }

    @Override
    public Assignment getAssignment(Long id) {
        return assignmentRepository.findById(id).orElse(null);
    }

    @Override
    public Assignment createAssignment(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }

    @Override
    public void deleteAssignment(Assignment assignment) {
        assignmentRepository.delete(assignment);
    }
}
