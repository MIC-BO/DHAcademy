package com.digitalharbor.evaluation.bo.client;

import com.digitalharbor.evaluation.bo.client.model.Student;
import com.digitalharbor.evaluation.bo.client.repository.StudentRepository;
import com.digitalharbor.evaluation.bo.client.service.StudentService;
import com.digitalharbor.evaluation.bo.client.service.StudentServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;


@SpringBootTest
public class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;
    private StudentService studentService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        studentService = new StudentServiceImpl(studentRepository);
        Student student = Student.builder().firstName("ServiceF2").lastName("ServiceL2").build();

        Mockito.when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
    }

    @Test
    public void whenValidID_thenReturnProduct(){
        Student found = studentService.getStudent(1L);
        Assertions.assertThat(found.getFirstName()).isEqualTo("ServiceF2");
    }
}