package com.amigoscode.jpa;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class JpaMasterClassApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaMasterClassApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            Student maria = new Student(
                    "Maria",
                    "Jones",
                    "maria.jones@amigoscode.edu",
                    21);

            Student maria2 = new Student(
                    "Maria",
                    "Jones",
                    "maria2.jones@amigoscode.edu",
                    25);

            Student ahmed = new Student(
                    "Ahmed",
                    "Ali",
                    "ahmed.ali@amigoscode.edu",
                    18);

            System.out.println("Adding maria and ahmed");
            studentRepository.saveAll(List.of(maria, ahmed, maria2));

            studentRepository.findStudentByEmail("ahmed.ali@amigoscode.edu")
                    .ifPresentOrElse(System.out::println,
                            () -> System.out.println("Student with email ahmed.ali@amigoscode.edu not found"));

            studentRepository.findStudentsByFirstNameEqualsAndAgeIsGreaterThan("Maria", 18)
                    .forEach(System.out::println);

            System.out.println("Deleting Maria2");
            System.out.println(studentRepository.deleteStudentById(3L));
        };
    }

}
