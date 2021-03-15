package com.devdojo.springboot;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.devdojo.springboot.model.Student;
import com.devdojo.springboot.repository.StudentRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@EnableAutoConfiguration
public class StudentEndpointTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	@MockBean
	private StudentRepository studentRepository;

	@Autowired
	private MockMvc mockMvc;

	Student student = new Student(1L, "Legolas", "legolas@lotr.com");

	@TestConfiguration
	static class Config {
		@Bean
		public RestTemplateBuilder restTemplateBuilder() {
			return new RestTemplateBuilder().basicAuthentication("alice", "devdojo");
		}
	}

	@Before
	public void setup() {
		Student student = new Student(1L, "Legolas", "legolas@lotr.com");
		when(studentRepository.findById(student.getId())).thenReturn(java.util.Optional.of(student));
	}

//	@Test
//	public void whenListStudentUsingIncorrectUsernameAndPassword_thenReturnStatusCode401Unauthorized() {
//		System.out.println(port);
//		restTemplate = restTemplate.withBasicAuth("1", "1");
//		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/v1/protected/students/",
//				String.class);
//		assertThat(response.getStatusCodeValue()).isEqualTo(401);
//	}
//
//	@Test
//	public void getStudentsByIdWhenUsernameAndPasswordIncorret_thenReturnStatusCode401Unauthorized() {
//		restTemplate = restTemplate.withBasicAuth("1", "1");
//		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/v1/protected/students/1",
//				String.class);
//		assertThat(response.getStatusCodeValue()).isEqualTo(401);
//	}
//
//	@Test
//	public void whenListStudentUsingCorrectUsernameAndPassword_thenReturnStatusCode200() {
//		List<Student> students = asList(new Student(1L, "Legolas", "legolas@lotr.com"),
//				new Student(2L, "Aragorn", "aragorn@lotr.com"));
//		when(studentRepository.findAll()).thenReturn(students);
//		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/v1/protected/students/",
//				String.class);
//		assertThat(response.getStatusCodeValue()).isEqualTo(200);
//	}
//
//	@Test
//	public void getStudentsByIdWhenUsernameAndPasswordCorrect_thenReturnStatusCode200() {
//		setup();
//		ResponseEntity<Student> response = restTemplate.getForEntity("http://localhost:8080/v1/protected/students/{id}",
//				Student.class, 4L);
//		assertThat(response.getStatusCodeValue()).isEqualTo(200);
//	}
//
//	@Test
//	public void getStudentsByIdWhenUsernameAndPasswordCorrectAndStudentDoesNotExists_thenReturnStatusCode404() {
//		ResponseEntity<Student> response = restTemplate.getForEntity("http://localhost:8080/v1/protected/students/{id}",
//				Student.class, -1);
//		assertThat(response.getStatusCodeValue()).isEqualTo(404);
//	}
//
//	@Test
//	public void createWhenNameIsNullShouldReturnStatusCode400() throws Exception {
//		Student student = new Student(1L, null, "legolas@lotr.com");
//		when(studentRepository.save(student)).thenReturn(student);
//		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/v1/admin/students/",
//				student, String.class);
//		assertThat(response.getStatusCodeValue()).isEqualTo(400);
//		assertThat(response.getBody()).contains("O campo nome do estudante é obrigatório");
//	}
//
//	@Test
//	public void createShoudPersistDataReturnStatusCode201() throws Exception {
//		setup();
//		when(studentRepository.save(student)).thenReturn(student);
//		ResponseEntity<Student> response = restTemplate.postForEntity("http://localhost:8080/v1/admin/students/",
//				student, Student.class);
//		assertThat(response.getStatusCodeValue()).isEqualTo(201);
//		assertThat(response.getBody().getId()).isNotNull();
//	}
//	
//	@Test
//	@WithMockUser(username = "xx", password = "xx", roles = { "USER", "ADMIN" })
//	public void updateWhenUserHasRoleAdminAndStudentExistsShouldReturnStatusCode200() throws Exception {
//		setup();
//		doNothing().when(studentRepository).deleteById(1L);
//		mockMvc.perform(delete("http://localhost:8080/v1/admin/students/{id}", -1L)).andExpect(status().isNotFound());
//		Student student = new Student(1L, "mel", "mel@gmail.com");
//		when(studentRepository.save(student)).thenReturn(student);
//		ResponseEntity<Student> response = restTemplate.postForEntity("http://localhost:8080/v1/admin/students/",
//				student, Student.class);
//		assertThat(response.getStatusCodeValue()).isEqualTo(201);
//		assertThat(response.getBody().getId()).isNotNull();
//	}
//	
//
//	@Test
//	public void deleteWhenUserHasRoleAdminAndStudentExistsShouldReturnStatusCode404() {
//		setup();
//		doNothing().when(studentRepository).deleteById(1L);
//		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/v1/admin/students/{id}",
//				HttpMethod.DELETE, null, String.class, 1L);
//		assertThat(response.getStatusCodeValue()).isEqualTo(404);
//	}

//	@Test
//	@WithMockUser(username = "xx", password = "xx", roles = { "USER", "ADMIN" })
//	public void deleteWhenUserHasRoleAdminAndStudentDoesNotExistsShouldReturnStatusCode200() throws Exception {
//		setup();
//		doNothing().when(studentRepository).deleteById(1L);
//		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/v1/admin/students/{id}",
//				HttpMethod.DELETE, null, String.class, -1);
//		assertThat(response.getStatusCodeValue()).isEqualTo(404);
//		mockMvc.perform(delete("http://localhost:8080/v1/admin/students/{id}", -1L)).andExpect(status().isNotFound());
//	}
//
//	@Test
//	@WithMockUser(username = "xx", password = "xx", roles = { "USER" })
//	public void deleteWhenUserDoesNotHaveRoleAdminShouldReturnStatusCode403() throws Exception {
//		setup();
//		doNothing().when(studentRepository).deleteById(1L);
//		mockMvc.perform(delete("http://localhost:8080/v1/admin/students/{id}", -1L)).andExpect(status().isForbidden());
//	}

}
