package com.devdojo.springboot;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devdojo.springboot.model.Student;
import com.devdojo.springboot.repository.StudentRepository;


@ExtendWith(SpringExtension.class)
@DataJpaTest
public class StudentRepositoryTest {
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Test
	public void createShouldPersistData() {
		Student student = new Student("William", "william@gmail.com");
		this.studentRepository.save(student);
		assertThat(student.getId()).isNotNull();
		assertThat(student.getName()).isEqualTo("William");
		assertThat(student.getEmail()).isEqualTo("william@gmail.com");
	}
 }
