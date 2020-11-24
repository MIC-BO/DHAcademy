package com.digitalharbor.evaluation.bo.client.service;

import com.digitalharbor.evaluation.bo.client.model.Student;
import com.digitalharbor.evaluation.bo.client.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    @Override
    public List<Student> getStudentsByNames(String firstName, String lastName) {
        if (firstName == null){
            return studentRepository.findByLastNameContainingIgnoreCase(lastName);
        }
        else if (lastName == null){
            return studentRepository.findByFirstNameContainingIgnoreCase(firstName);
        }

        return studentRepository.findByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCase(firstName, lastName);
    }

    @Override
    public Student getStudent(Long studentID) {
        return studentRepository.findById(studentID).orElse(null);
    }


    @Override
    public Student saveStudent(Student student) {
        if (student.getStudentID() == null)
            return studentRepository.save(student);

        Student studentDB = getStudent(student.getStudentID());

        if (studentDB == null){
            return null;
        }

        studentDB.setFirstName(student.getFirstName());
        studentDB.setLastName(student.getLastName());
        return studentRepository.save(studentDB);
    }

    @Override
    public void deleteStudent(Student student) {
        studentRepository.delete(student);
    }
}