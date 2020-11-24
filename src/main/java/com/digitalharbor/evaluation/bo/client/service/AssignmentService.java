package com.digitalharbor.evaluation.bo.client.service;

import com.digitalharbor.evaluation.bo.client.model.Assignment;
import java.util.List;


public interface AssignmentService {
    public List<Assignment> getAssignmentsByStudentID(Long studentID);
    public List<Assignment> getAssignmentsByCode(Long code);
    public Assignment getAssignment(Long id);
    public Assignment createAssignment(Assignment assignment);
    public void deleteAssignment(Assignment assignment);
}