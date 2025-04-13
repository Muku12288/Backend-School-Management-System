package com.school.controller;

import java.net.http.HttpResponse;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.school.dto.SingleStudentDto;
import com.school.dto.StudentDto;
import com.school.dto.StudentLeaveDto;
import com.school.services.StudentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	
	// student get her own details in card format 
	@GetMapping("/{studentId}")
	public ResponseEntity<SingleStudentDto> getStudentById(@PathVariable int studentId){
		SingleStudentDto singleStudentDto =studentService.getStudentById(studentId);
		
		if(singleStudentDto == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(singleStudentDto);
	}
	
	@PutMapping("/{studentId}")
	public ResponseEntity<?> updateStudent(@PathVariable int studentId, @RequestBody StudentDto studentDto){
		//postStudent() interface method was implemented in AdminService class
		
		StudentDto createStudentDto = studentService.updateStudent(studentId, studentDto);
	
		
		if(createStudentDto == null) {
			return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
		}
			
		return ResponseEntity.status(HttpStatus.CREATED).body(createStudentDto);
	}
	
	
	
	// API for a student can apply  for leave 
	@PostMapping("/leave")
	public ResponseEntity<?> applyLeave(@RequestBody StudentLeaveDto studentLeaveDto){
		
		StudentLeaveDto submittedStudentLeaveDto = studentService.applyLeave(studentLeaveDto);
		if(submittedStudentLeaveDto == null) {
			return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(submittedStudentLeaveDto);
		
	}
	
	
	// get students all leaves details, which is applied by student
	@GetMapping("/leave/{studentId}")
	public ResponseEntity<List<StudentLeaveDto>> getAllAppliedLeavesByStudentId(@PathVariable int studentId){
		List<StudentLeaveDto> studentLeaveDtos =studentService.getAllAppliedLeavesByStudentId(studentId);
		
		if(studentLeaveDtos == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(studentLeaveDtos);
	}
	
}
