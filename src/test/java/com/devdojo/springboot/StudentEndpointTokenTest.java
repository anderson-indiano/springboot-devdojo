package com.devdojo.springboot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.devdojo.springboot.model.Student;
import com.devdojo.springboot.repository.StudentRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@EnableAutoConfiguration
public class StudentEndpointTokenTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StudentRepository studentRepository;

	private HttpEntity<Void> protectedHeader;
	private HttpEntity<Student> adminHeader;
	private HttpEntity<Void> wrongHeader;

	@BeforeEach
	public void configProtectedHeaders() {
		String str = "{\"username\":\"maysa isabella\",\"password\":\"devdojo\"}";
		HttpHeaders headers = restTemplate.postForEntity("http://localhost:8080/login", str, String.class).getHeaders();
		this.protectedHeader = new HttpEntity<>(headers);
	}

	@BeforeEach
	public void configAdminHeaders() {
		String str = "{\"username\":\"alice fernanda\",\"password\":\"devdojo\"}";
		HttpHeaders headers = restTemplate.postForEntity("http://localhost:8080/login", str, String.class).getHeaders();
		this.adminHeader = new HttpEntity<>(headers);
	}

	@BeforeEach
	public void configWrongHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "11111");
		this.wrongHeader = new HttpEntity<>(headers);
	}

	@BeforeEach
	public void setup() {
		Student student = new Student(3L, "Legolas", "legolas@lotr.com");
		when(studentRepository.findById(student.getId())).thenReturn(java.util.Optional.of(student));
	}

	@Test
	public void listStudentsWhenTokenIsIncorrectShouldReturnStatusCode403() {
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/v1/protected/students/", GET,
				wrongHeader, String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(403);
	}

	@Test
	public void getStudentByIdWhenTokenIsIncorrectShouldReturnStatusCode403() {
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/v1/protected/students/1", GET,
				wrongHeader, String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(403);
	}

	@Test
	public void getStudentByNameWhenTokenIsIncorrectShouldReturnStatusCode403() {
		ResponseEntity<String> response = restTemplate.exchange(
				"http://localhost:8080/v1/protected/students/findByName/Legolas", GET, wrongHeader, String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(403);
	}

	@Test
	public void listStudentWhenTokenIsCorrectShouldReturnStatusCode200() {
		ResponseEntity<Student> response = restTemplate.exchange("http://localhost:8080/v1/protected/students/", GET,
				protectedHeader, Student.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
	}

	@Test
	public void getStudentsByIdWhenTokenIsCorrenctShouldReturnStatusCode200() {
		ResponseEntity<Student> response = restTemplate.exchange("http://localhost:8080/v1/protected/students/5", GET,
				protectedHeader, Student.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
	}

	@Test
	public void getStudentsByIdWhenTokenIsCorrectAndStudentDoesNotExistsReturnStatusCode404() {
		ResponseEntity<Student> response = restTemplate.exchange("http://localhost:8080/v1/protected/students/-1", GET,
				protectedHeader, Student.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(404);
	}

	@Test
	public void whenSaveStudentUsingNameIsNullShouldReturnStatusCode400() throws Exception {
		Student student = new Student(3L, null, "legolas@lotr.com");
		when(studentRepository.save(student)).thenReturn(student);
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/v1/admin/students/", POST,
				new HttpEntity<>(student, adminHeader.getHeaders()), String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(400);
		assertThat(response.getBody()).contains("O campo nome do estudante é obrigatório");
	}

	@Test
	public void whenSaveStudentUsingIncorrentTokenShouldReturnStatusCode400() throws Exception {
		Student student = new Student(3L, "Legolas", "legolas@lotr.com");
		when(studentRepository.save(student)).thenReturn(student);
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/v1/admin/students/", POST,
				new HttpEntity<>(student, wrongHeader.getHeaders()), String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(403);
	}

	@Test
	public void whenSaveStudentShoudPersistDataReturnStatusCode201() throws Exception {
		Student student = new Student(3L, "Legolas", "legolas@lotr.com");
		when(studentRepository.save(student)).thenReturn(student);
		ResponseEntity<Student> response = restTemplate.exchange("http://localhost:8080/v1/admin/students/", POST,
				new HttpEntity<>(student, adminHeader.getHeaders()), Student.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(201);
		assertThat(response.getBody().getId()).isNotNull();
	}

	@Test
	public void whenUpdateStudentUsingCorrectTokenShouldReturnStatusCode200() throws Exception {
		Student student = new Student(4L, "Maria", "maria@gmail.com");
		adminHeader = new HttpEntity<>(student, adminHeader.getHeaders());
		ResponseEntity<String> response1 = restTemplate.exchange("http://localhost:8080/v1/admin/students/", POST,
				adminHeader, String.class);
		JSONObject studentJson = new JSONObject(response1.getBody());
		doNothing().when(studentRepository).deleteById(studentJson.getLong("id"));
		student = new Student(studentJson.getLong("id"), "Legolas", "legolas@lotr.com");
		adminHeader = new HttpEntity<>(student, adminHeader.getHeaders());
		restTemplate.exchange("http://localhost:8080/v1/admin/students/", POST, adminHeader, String.class);
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/v1/admin/students/", PUT,
				adminHeader, String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
	}

	@Test
	public void deleteWhenUserHasRoleAdminAndStudentDoesNotExistsShouldReturnStatusCode200() throws Exception {
//		String token = adminHeader.getHeaders().get("Authorization").get(1);
//		doNothing().when(studentRepository).deleteById(3L);
//		mockMvc.perform(delete("http://localhost:8080/v1/admin/students/{id}", -3L).header("Authorization", token))
//				.andExpect(status().isNotFound());
			
		doNothing().when(studentRepository).deleteById(3L);
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/v1/admin/students/-3", DELETE, adminHeader,
				String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(404);
	}

	@Test
	public void deleteWhenUserDoesNotHaveRoleAdminShouldReturnStatusCode403() throws Exception {
		doNothing().when(studentRepository).deleteById(3L);
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/v1/admin/students/3", DELETE, protectedHeader,
				String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(403);
	}
	
	@Test
	public void deleteWhenUserHasRoleAdminAndStudentExistsShouldReturnStatusCode404() throws JSONException {	
	       Student student = new Student("Legolas", "legolas@lotr.com");

	        adminHeader = new HttpEntity<>(student, adminHeader.getHeaders());

	        ResponseEntity<String> response1 = restTemplate.exchange("http://localhost:8080/v1/admin/students/", POST, adminHeader, String.class);

	        JSONObject studentJson = new JSONObject(response1.getBody());

	        doNothing().when(studentRepository).deleteById(studentJson.getLong("id"));

	        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/v1/admin/students/{id}", DELETE, adminHeader, String.class, studentJson.getLong("id"));

	        assertThat(response.getStatusCodeValue()).isEqualTo(200);
	}

}
