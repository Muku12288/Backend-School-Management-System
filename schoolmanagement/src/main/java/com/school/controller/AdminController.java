package com.school.controller;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.school.dto.SingleStudentDto;
import com.school.dto.SingleTeacherDto;
import com.school.dto.StudentDto;
import com.school.dto.StudentLeaveDto;
import com.school.dto.TeacherDto;
import com.school.services.AdminService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/admin")
public class AdminController<T> {
	
	@Autowired
	private AdminService adminService;
	
	
	
	@PostMapping("/student")
	public ResponseEntity<?> addStudent(@RequestBody StudentDto studentDto){
		//postStudent() interface method was implemented in AdminService class
		
		StudentDto createStudentDto = adminService.postStudent(studentDto);
	
		
		if(createStudentDto == null) {
			return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
		}
			
		return ResponseEntity.status(HttpStatus.CREATED).body(createStudentDto);
	}

	
	@GetMapping("/students")
	public ResponseEntity<List<StudentDto>> getAllStudent(){
		List<StudentDto> allStudents=adminService.getAllStudents();
		return ResponseEntity.ok(allStudents);
	}
	
//	
	@DeleteMapping("/student/{studentId}")
	public ResponseEntity<Void> deleteStudent(@PathVariable int studentId){
		
		adminService.deleteStudent(studentId);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/student/{studentId}")
	public ResponseEntity<SingleStudentDto> getStudentById(@PathVariable int studentId){
		SingleStudentDto singleStudentDto =adminService.getStudentById(studentId);
		
		if(singleStudentDto == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(singleStudentDto);
	}
	
	@PutMapping("/student/{studentId}")
	public ResponseEntity<?> updateStudent(@PathVariable int studentId, @RequestBody StudentDto studentDto){
		//postStudent() interface method was implemented in AdminService class
		
		StudentDto createStudentDto = adminService.updateStudent(studentId, studentDto);
	
		
		if(createStudentDto == null) {
			return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
		}
			
		return ResponseEntity.status(HttpStatus.CREATED).body(createStudentDto);
	}
	
	// Admin get all leaves details, which is applied by student
	@GetMapping("/leaves")
	public ResponseEntity<List<StudentLeaveDto>> getAllAppliedLeaves(){
		List<StudentLeaveDto> studentLeaveDtos =adminService.getAllAppliedLeaves();
		
		if(studentLeaveDtos == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(studentLeaveDtos);
	}
	
	//API for changing leave status
	@GetMapping("/leave/{leaveId}/{status}")
	public ResponseEntity<?> changeLeaveStatus(@PathVariable int leaveId, @PathVariable String status){
		StudentLeaveDto studentLeaveDto =adminService.changeLeaveStatus(leaveId, status);
		
		if(studentLeaveDto == null) {
			return new ResponseEntity<>("Something Went Wrong", HttpStatus.BAD_REQUEST);
		}
		
		return ResponseEntity.ok(studentLeaveDto);
	}
	
	//#####################Teachers Operations #################################
	
	@PostMapping("/teacher")
	public ResponseEntity<?> addTeacher(@RequestBody TeacherDto teacherDto){
		//postStudent() interface method was implemented in AdminService class
		
		TeacherDto createTeacherDto = adminService.postTeacher(teacherDto);
	
		
		if(createTeacherDto == null) {
			return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
		}
			
		return ResponseEntity.status(HttpStatus.CREATED).body(createTeacherDto);
	}
	
	
	@GetMapping("/teachers")
	public ResponseEntity<List<TeacherDto>> getAllTeachers(){
		List<TeacherDto> allTeachers = adminService.getAllTeachers();
		return ResponseEntity.ok(allTeachers);
	}
	
	@DeleteMapping("/teacher/{teacherId}")
	public ResponseEntity<?> deleteTeacher(@PathVariable int teacherId){
		
		adminService.deleteTeacher(teacherId);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/teacher/{teacherId}")
	public ResponseEntity<SingleTeacherDto> getTeacherById(@PathVariable int teacherId){
		
		SingleTeacherDto singleTeacherDto = adminService.getTeacherById(teacherId);
		if(singleTeacherDto == null) return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(singleTeacherDto);
	}
	
	@PutMapping("/teacher/{teacherId}")
	public ResponseEntity<?> updateTeacher(@PathVariable int teacherId, @RequestBody TeacherDto teacherDto){
		
		TeacherDto updatedTeacherDto = adminService.updateTeacher(teacherId, teacherDto);
		
		if(updatedTeacherDto == null) {
			return new ResponseEntity<>("SomethingWent Wrong", HttpStatus.BAD_REQUEST);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(updatedTeacherDto);
	}
	
	
}
