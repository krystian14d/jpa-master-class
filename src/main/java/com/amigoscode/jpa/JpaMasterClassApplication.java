package com.amigoscode.jpa;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class JpaMasterClassApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaMasterClassApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(
            StudentRepository studentRepository,
            StudentIdCardRepository studentIdCardRepository) {
        return args -> {
            Faker faker = new Faker();

            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@amigoscode.edu", firstName, lastName);
            Student student = new Student(
                    firstName,
                    lastName,
                    email,
                    faker.number().numberBetween(17, 55));

            student.addBook(new Book("Clean code", LocalDateTime.now().minusDays(4)));

            student.addBook(new Book("Think and grow rich", LocalDateTime.now()));

            student.addBook(new Book("Spring Data JPA", LocalDateTime.now().minusYears(1)));

            StudentIdCard studentIdCard = new StudentIdCard("123456789", student);

            student.setStudentIdCard(studentIdCard);

            student.addEnrolment(new Enrolment(
                    new EnrolmentId(1L, 1L),
                    student,
                    new Course("Computer Science", "IT"),
                    LocalDateTime.now()
            ));

            student.addEnrolment(new Enrolment(
                    new EnrolmentId(1L, 2L),
                    student,
                    new Course("Amigoscode Spring Data JPA", "IT"),
                    LocalDateTime.now().minusDays(18)
            ));

//            student.enrolToCourse(new Course("Computer Science", "IT"));
//            student.enrolToCourse(new Course("Amigoscode Spring Data JPA", "IT"));



            studentRepository.save(student);

            studentRepository.findById(1L)
                            .ifPresent(s -> {
                                System.out.println("fetch book lazy...");
                                List<Book> books = student.getBooks();
                                books.forEach(book -> {
                                    System.out.println(s.getFirstName() + " borrowed " + book.getBookName());
                                });
                            });
//
//            studentIdCardRepository.findById(1L)
//                    .ifPresent(System.out::println);

//            studentRepository.deleteById(1L);

        };
    }

    private void generateRandomStudents(StudentRepository studentRepository) {
        Faker faker = new Faker();

        for (int i = 0; i < 20; i++) {
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@amigoscode.edu", firstName, lastName);
            Student student = new Student(
                    firstName,
                    lastName,
                    email,
                    faker.number().numberBetween(17, 55));

            studentRepository.save(student);
        }
    }

}
