package com.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.model.Teacher;


@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer>{

}
