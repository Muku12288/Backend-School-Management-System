package com.school.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school.dto.TeacherDto;
import com.school.services.HomeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class HomeController {
	
	@Autowired
	private HomeService homeService;
	
	@GetMapping("/teachers")
	public ResponseEntity<List<TeacherDto>> getAllTeachers(){
		List<TeacherDto> allTeachers = homeService.getAllTeachers();
		return ResponseEntity.ok(allTeachers);
	}
}
