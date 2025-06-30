package com.school.services;

import java.util.List;

import com.school.dto.SingleStudentDto;
import com.school.dto.SingleTeacherDto;
import com.school.dto.StudentDto;
import com.school.dto.StudentLeaveDto;
import com.school.dto.StudentLeaveResponseDto;
import com.school.dto.TeacherDto;

public interface AdminService {
	
	StudentDto postStudent(StudentDto studentDto);
	
	List<StudentDto>getAllStudents();
	
	void deleteStudent(int studentId);
	
	SingleStudentDto getStudentById(int studentId);
	
	StudentDto updateStudent(int studentId, StudentDto studentDto);

	List<StudentLeaveResponseDto> getAllAppliedLeaves();

	StudentLeaveDto changeLeaveStatus(int leaveId, String status);

	TeacherDto postTeacher(TeacherDto teacherDto);

	List<TeacherDto> getAllTeachers();

	void deleteTeacher(int teacherId);

	SingleTeacherDto getTeacherById(int teacherId);

	TeacherDto updateTeacher(int teacherId, TeacherDto teacherDto);
}
