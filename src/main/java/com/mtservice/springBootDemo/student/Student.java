package com.mtservice.springBootDemo.student;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@ToString
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Student {

    public Student(String name, String email, Gender gender) {
        this.name = name;
        this.email = email;
        this.gender = gender;
    }
    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence"
    )
    @GeneratedValue(
            generator = "student_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;
    @NotBlank(message = "Name is blank")
    @Column(nullable = false)
    private String name;
    @Email(message = "Not valid email")
    @Column(nullable = false, unique = true)
    private String email;
    @NotNull(message = "Gender is mandatory")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

}
