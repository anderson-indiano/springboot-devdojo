package com.devdojo.springboot.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.devdojo.springboot.model.DBUser;

public interface UserRepository extends PagingAndSortingRepository<DBUser, Long>{
	
	DBUser findByUsername(String username);
}
