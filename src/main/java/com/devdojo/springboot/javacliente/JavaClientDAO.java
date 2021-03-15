package com.devdojo.springboot.javacliente;

import java.util.List;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.devdojo.springboot.handler.RestReponseExceptionHandler;
import com.devdojo.springboot.model.PageableResponse;
import com.devdojo.springboot.model.Student;

public class JavaClientDAO {
	private RestTemplate restTemplate = new RestTemplateBuilder()
			.rootUri("http://localhost:8080/v1/protected/students")
			.basicAuthentication("maysa isabella", "devdojo")
			.errorHandler(new RestReponseExceptionHandler())
			.build();

	private RestTemplate restTemplateAdmin = new RestTemplateBuilder()
			.rootUri("http://localhost:8080/v1/admin/students")
			.basicAuthentication("alice fernanda", "devdojo")
			.errorHandler(new RestReponseExceptionHandler())
			.build();
		
	
	public Student findById(long id) {
		return restTemplate.getForObject("/{id}", Student.class, id);
		//ResponseEntity<Student> responseEntity = restTemplate.getForEntity("/{id}", Student.class);
	}
	
	public List<Student> listAll() {
		ResponseEntity<PageableResponse<Student>> exchange = restTemplate.exchange("/",
		HttpMethod.GET, null, new ParameterizedTypeReference<PageableResponse<Student>>() {
		});
        return exchange.getBody().getContent();
	}
	
	public Student save(Student student) {
		ResponseEntity<Student> exchangePost = restTemplateAdmin.exchange("/", 
				HttpMethod.POST, new HttpEntity<>(student, createJSONHeader()), Student.class);
		return exchangePost.getBody();
	}
	
	public void update(Student student) {
		restTemplateAdmin.put("/", student);
	}
	
	public void delete(long id) {
		restTemplateAdmin.delete("/{id}", id);
	}
	
	private static HttpHeaders createJSONHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}
}
