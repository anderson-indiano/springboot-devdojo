package com.devdojo.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Anderson Indiano for DevDojo on 23/11/2020
 */

//@ComponentScan(basePackages = "com.devdodjo.springboot.controller") -> busca o endopoint dentro do pacote onde esta definido na classe.
//@EnableAutoConfiguration -> configura a aplicação de acordo com as dependencias do pom.xml
//@Configuration //configuração das classes que retorna xml para objetos em java.
@SpringBootApplication
public class SpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}

}
