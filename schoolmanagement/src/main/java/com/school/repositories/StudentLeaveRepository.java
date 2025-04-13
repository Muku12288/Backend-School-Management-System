package com.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.school.dto.StudentLeaveDto;
import com.school.model.StudentLeave;
import java.util.List;



@Repository
@EnableJpaRepositories
public interface StudentLeaveRepository extends JpaRepository<StudentLeave, Integer>{

	List<StudentLeave> findAllByUserId(int studentId);

}
