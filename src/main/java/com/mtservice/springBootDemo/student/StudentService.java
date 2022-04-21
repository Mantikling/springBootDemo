package com.mtservice.springBootDemo.student;

import com.mtservice.springBootDemo.student.exception.BadRequestException;
import com.mtservice.springBootDemo.student.exception.StudentNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }


    public void addStudent(Student student) {
        //check if email is taken
        if (studentRepository.selectExistsEmail(student.getEmail())) {
            throw new BadRequestException(
                    "Email " + student.getEmail() + " is already taken.");
        }
        studentRepository.save(student);
    }

    @Transactional
    public void deleteStudent(Long studentId) {
        //check if student exists
        if (!studentRepository.existsById(studentId)) {
            throw new StudentNotFoundException(
                    "Student with id " + studentId + " does not exists.");
        }
        studentRepository.deleteById(studentId);
    }
}
