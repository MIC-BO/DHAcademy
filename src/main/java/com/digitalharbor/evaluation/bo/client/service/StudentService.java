package com.digitalharbor.evaluation.bo.client.service;

import com.digitalharbor.evaluation.bo.client.model.Student;
import java.util.List;


public interface StudentService {
    public List<Student> getStudentsByNames(String firstName, String lastName);
    public Student getStudent(Long studentID);
    public Student saveStudent(Student student);
    public void deleteStudent(Student student);
}
