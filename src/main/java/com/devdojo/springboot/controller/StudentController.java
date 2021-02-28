package com.devdojo.springboot.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devdojo.springboot.error.ResourceNotFoundException;
import com.devdojo.springboot.model.Student;
import com.devdojo.springboot.repository.StudentRepository;

/**
 * Created by Anderson Indiano for DevDojo on 23/11/2020
 */

@RestController // @Controller + @ResponseBody retorna uma view como uma estrutura json
@RequestMapping("v1")
public class StudentController {

	private final StudentRepository studentDAO;

	@Autowired // instancia o objeto
	public StudentController(StudentRepository studentDAO) {
		this.studentDAO = studentDAO;
	}

//	@RequestMapping(method = RequestMethod.GET)
	@GetMapping(path = "protected/students")
	public ResponseEntity<?> listAll(Pageable pageable) {
//		System.out.println(dateUtil.formatLocalDateTime(LocalDateTime.now()));
		return new ResponseEntity<>(studentDAO.findAll(pageable), HttpStatus.OK);
	}
	
//	@RequestMapping(method = RequestMethod.GET)
	@GetMapping(path = "admin/students")
	public ResponseEntity<?> listAllAdmin(Pageable pageable) {
//		System.out.println(dateUtil.formatLocalDateTime(LocalDateTime.now()));
		return new ResponseEntity<>(studentDAO.findAll(pageable), HttpStatus.OK);
	}
	
//	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	@GetMapping(path = "protected/students/{id}")
	public ResponseEntity<?> getStudentById(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails) {	
		System.out.println(userDetails);
		verifiIfStudentExists(id);
		Optional<Student> student = studentDAO.findById(id);
		return new ResponseEntity<>(student, HttpStatus.OK);
	}
	
	@GetMapping(path = "protected/students/findByName/{name}")
	public ResponseEntity<?> findStudentByName(@PathVariable String name) {
		return new ResponseEntity<>(studentDAO.findByNameIgnoreCaseContaining(name), HttpStatus.OK);
	}
	
//	@RequestMapping(method = RequestMethod.POST)
	@PostMapping(path = "admin/students")
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<?> save(@Valid @RequestBody Student student) {		
		return new ResponseEntity<>(studentDAO.save(student), HttpStatus.CREATED);
	}
	
//	@RequestMapping(method = RequestMethod.DELETE)
	@DeleteMapping(path = "admin/students/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable Long id) {	
		verifiIfStudentExists(id);
		studentDAO.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
//	@RequestMapping(method = RequestMethod.PUT)
	@PutMapping(path = "admin/students")
	public ResponseEntity<?> update(@RequestBody Student student) {		
		verifiIfStudentExists(student.getId());
		studentDAO.save(student);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	private void verifiIfStudentExists(Long id) {
		Optional<Student> student = studentDAO.findById(id);
		if (!student.isPresent()) {		
			throw new ResourceNotFoundException("Student not found for ID: " + id);
		}
	}
}



















































