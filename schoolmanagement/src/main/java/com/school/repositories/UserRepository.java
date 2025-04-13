package com.school.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.school.dto.StudentDto;
import com.school.enums.UserRole;
import com.school.model.User;
import java.util.List;


@Repository
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User, Integer >{
	
	User findByName(String name);
	User findByRole(UserRole role);
	Optional<User> findByEmail(String email);
	List<User> findAllByRole(UserRole role);
}
