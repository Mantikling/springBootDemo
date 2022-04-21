package com.mtservice.springBootDemo.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void selectExistsEmail_shouldCheck() {
        // given
        Student student = new Student("Jamila", "jamila@gmail.com", Gender.FEMALE);
        underTest.save(student);
        // when
        boolean result = underTest.selectExistsEmail(student.getEmail());
        // then
        assertThat(result).isTrue();
    }

    @Test
    void selectExistsEmail_shouldNotCheck() {
        // given
        String email =  "jamila@gmail.com";
        // when
        boolean result = underTest.selectExistsEmail(email);
        // then
        assertThat(result).isFalse();
    }
}