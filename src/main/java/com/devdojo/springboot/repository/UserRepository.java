package com.devdojo.springboot.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.devdojo.springboot.model.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long>{
	
	User findByUsername(String username);
}
