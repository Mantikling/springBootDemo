package com.mtservice.springBootDemo.student;

import com.mtservice.springBootDemo.student.exception.BadRequestException;
import com.mtservice.springBootDemo.student.exception.StudentNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock private StudentRepository studentRepository;
    private StudentService underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new StudentService(studentRepository);
    }

    @Test
    void getAllStudents_can() {
        //when
        underTest.getAllStudents();
        // then
        verify(studentRepository).findAll();

    }

    @Test
    void addStudent_can() {
        // given
        Student student = new Student("Jamila", "jamila@gmail.com", Gender.FEMALE
        );
        // when
        underTest.addStudent(student);
        // then
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentArgumentCaptor.capture());
        Student capturedStudent =  studentArgumentCaptor.getValue();
        assertThat(capturedStudent).isEqualTo(student);
    }

    @Test
    void addStudent_emailIsTaken() {
        // given
        Student student = new Student("Jamila", "jamila@gmail.com", Gender.FEMALE
        );
        given(studentRepository.selectExistsEmail(anyString())).willReturn(true);
        // when
        // then
        assertThatThrownBy(() -> underTest.addStudent(student))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Email " + student.getEmail() + " is already taken.");
        verify(studentRepository, never()).save(any());
    }

    @Test
    void deleteStudent_can() {
        // given
        Long studentId = 1L;
        given(studentRepository.existsById(anyLong())).willReturn(true);
        // when
        underTest.deleteStudent(studentId);
        // then
        ArgumentCaptor<Long> studentIdArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(studentRepository).deleteById(studentIdArgumentCaptor.capture());
        Long capturedStudentId =  studentIdArgumentCaptor.getValue();
        assertThat(capturedStudentId).isEqualTo(studentId);
    }

    @Test
    void deleteStudent_notExistsId() {
        // given
        Long studentId = 1L;
        given(studentRepository.existsById(anyLong())).willReturn(false);
        // when
        // then
        assertThatThrownBy(() -> underTest.deleteStudent(studentId))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("Student with id " + studentId + " does not exists");
        verify(studentRepository, never()).deleteById(any());
    }
}