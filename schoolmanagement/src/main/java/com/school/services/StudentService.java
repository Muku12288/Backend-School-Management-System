package com.school.services;

import java.util.List;

import com.school.dto.SingleStudentDto;
import com.school.dto.StudentDto;
import com.school.dto.StudentLeaveDto;


public interface StudentService {
	
	SingleStudentDto getStudentById(int studentId);

	StudentLeaveDto applyLeave(StudentLeaveDto studentLeaveDto);
	
	List<StudentLeaveDto> getAllAppliedLeavesByStudentId(int studentId);

	StudentDto updateStudent(int studentId, StudentDto studentDto);
}
