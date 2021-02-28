package com.devdojo.springboot.javacliente;

import java.util.List;

import com.devdojo.springboot.model.Student;

public class JavaSpringClientTest {

	public static void main(String[] args) {

		Student studentPost = new Student();
		studentPost.setName("anderson");
		studentPost.setEmail("anderson.indiano@gmail.com");
		studentPost.setId(32L);
		
		JavaClientDAO dao = new JavaClientDAO();
//		dao.update(studentPost);
		dao.delete(32);
		
//		List<Student> students = dao.listAll();		
//		System.out.println(dao.findById(100));
//		System.out.println(dao.listAll());
//		System.out.println(students);
//		System.out.println(dao.save(studentPost));
	}
}
