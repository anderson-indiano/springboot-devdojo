package com.devdojo.springboot.model;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity // mapea a classe Student para uma tabela Student
public class Student extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "O campo nome do estudante é obrigatório")
	private String name;

	@NotBlank(message = "O campo email do estudante é obrigatório")
	@Email
	private String email;

	public Student() {
		super();
	}

	public Student(@NotBlank(message = "O campo nome do estudante é obrigatório") String name,
			@NotBlank(message = "O campo email do estudante é obrigatório") @Email String email) {
		super();
		this.name = name;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Student [name=" + name + ", email=" + email + "]";
	}

}
