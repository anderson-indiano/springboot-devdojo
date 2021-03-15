package com.devdojo.springboot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devdojo.springboot.model.Student;
import com.devdojo.springboot.repository.StudentRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StudentRepositoryTest {

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private EntityManager entityManager;

	@Test
	public void createShouldPersistData() {
		Student student = new Student("William", "william@gmail.com");
		this.studentRepository.save(student);
		assertThat(student.getId()).isNotNull();
		assertThat(student.getName()).isEqualTo("William");
		assertThat(student.getEmail()).isEqualTo("william@gmail.com");
	}

	@Test
	public void deleteShouldRemoveData() {
		Student student = new Student("William", "william@gmail.com");
		this.studentRepository.save(student);
		studentRepository.delete(student);
		assertThat(studentRepository.findById(student.getId())).isEmpty();
	}

	@Test
	public void updateShouldChangePersistData() {
		Student student = new Student("William", "william@gmail.com");
		this.studentRepository.save(student);
		student.setName("Maria Silva");
		student.setEmail("mariasilva@gmail.com");
		this.studentRepository.save(student);
		student = studentRepository.findById(student.getId()).orElse(null);
		assertThat(student.getName()).isEqualTo("Maria Silva");
		assertThat(student.getEmail()).isEqualTo("mariasilva@gmail.com");
	}

	@Test
	public void findByNameIgnoreCaseContainingShouldIgnoreCase() {
		Student student1 = new Student("William", "william@gmail.com");
		Student student2 = new Student("william", "william2@gmail.com");
		this.studentRepository.save(student1);
		this.studentRepository.save(student2);
		List<Student> studentList = studentRepository.findByNameIgnoreCaseContaining("william");
		assertThat(studentList.size()).isEqualTo(2);

	}

	@Test
	public void whenNameisEmpty_thenThrowsException() {
		Student student = new Student(" ", "maria@gmail.com");
		Exception exception = assertThrows(ConstraintViolationException.class, () -> {
			studentRepository.save(student);
			entityManager.flush();
		});
		System.out.println(exception);
		assertTrue(exception.getMessage().contains("O campo nome do estudante é obrigatório"));
	}

	@Test
	public void whenEmailIsEmpty_thenThrowsException() {
		Student student = new Student("maria", "");
		Exception exception = assertThrows(ConstraintViolationException.class, () -> {
			studentRepository.save(student);
			entityManager.flush();
		});
		System.out.println(exception);
		assertTrue(exception.getMessage().contains("Digite um email válido"));
	}

}
