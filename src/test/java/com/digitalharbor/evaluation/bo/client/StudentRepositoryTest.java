package com.digitalharbor.evaluation.bo.client;

import com.digitalharbor.evaluation.bo.client.model.Student;
import com.digitalharbor.evaluation.bo.client.repository.StudentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;


@DataJpaTest
public class StudentRepositoryTest {
    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void whenFindByLastName_thenReturnListOfStudents(){
        Student student = Student.builder().firstName("RepositoryF1").lastName("RepositoryL1").build();
        studentRepository.save(student);

        List<Student> founds = studentRepository.findAll();
        Assertions.assertThat(founds.size()).isEqualTo(1);
    }

}