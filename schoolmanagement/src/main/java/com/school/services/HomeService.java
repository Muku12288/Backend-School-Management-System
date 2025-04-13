package com.school.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.dto.TeacherDto;
import com.school.model.Teacher;
import com.school.repositories.TeacherRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HomeService {
	
	@Autowired
	private TeacherRepository teacherRepository;
	
	public List<TeacherDto> getAllTeachers(){
		return teacherRepository.findAll().stream().map(Teacher::getTeacherDto).collect(Collectors.toList());
	}

}
