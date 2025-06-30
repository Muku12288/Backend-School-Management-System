package com.school.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.dto.SingleStudentDto;
import com.school.dto.StudentDto;
import com.school.dto.StudentLeaveDto;
import com.school.dto.StudentLeaveResponseDto;
import com.school.enums.StudentLeaveStatus;
import com.school.model.StudentLeave;
import com.school.model.User;
import com.school.repositories.StudentLeaveRepository;
import com.school.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 		// For making constructor of this class
public class StudentServiceImpl implements StudentService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private StudentLeaveRepository studentLeaveRepository;
	
	
	@Override
	public SingleStudentDto getStudentById(int studentId) {
		
		Optional<User> optionalUser = userRepository.findById(studentId);
		if(optionalUser.isPresent()) {
			
			SingleStudentDto singleStudentDto = new SingleStudentDto();
			singleStudentDto.setStudentDto(optionalUser.get().getStudentDto());
			return singleStudentDto;
		}
			
		
		return null;
	}
	
	
	//
	@Override
	public StudentLeaveDto applyLeave(StudentLeaveDto studentLeaveDto) {
		
		Optional<User> optionalUser = userRepository.findById(studentLeaveDto.getUserid());
		if(optionalUser.isPresent()) {
			
			StudentLeave studentLeave = new StudentLeave();
			
			studentLeave.setSubject(studentLeaveDto.getSubject());
			studentLeave.setBody(studentLeaveDto.getBody());
			studentLeave.setDate(new Date());
			studentLeave.setStudentLeaveStatus(StudentLeaveStatus.Pending);
			studentLeave.setUser(optionalUser.get());
			
			StudentLeave SubmittedStudentLeave =  studentLeaveRepository.save(studentLeave);
			
			// create StudentLeaveDto object to return and setId from SubmittedStudentLeave object
			StudentLeaveDto studentLeaveDto1 = new StudentLeaveDto();
			studentLeaveDto1.setId(SubmittedStudentLeave.getId());
			
			return studentLeaveDto1;
		}
		
		return null;
	}
	
	
	

	@Override
	public List<StudentLeaveDto> getAllAppliedLeavesByStudentId(int studentId) {
		
		
		return studentLeaveRepository.findAllByUserId(studentId).stream().map(StudentLeave::getStudentLeaveDto).collect(Collectors.toList());
	}


	@Override
	public StudentDto updateStudent(int studentId, StudentDto studentDto) {
		
		
			Optional<User> optionalUser = userRepository.findById(studentId);
			if(optionalUser.isPresent()) {
				User user = optionalUser.get();
				
				user.setName(studentDto.getName());
				user.setAddress(studentDto.getAddress());
				user.setEmail(studentDto.getEmail());
				user.setGender(studentDto.getGender());
				user.setDob(studentDto.getDob());
				user.setStudentClass(studentDto.getStudentClass());
				user.setFatherName(studentDto.getFatherName());
				user.setMotherName(studentDto.getMotherName());
				
				User updatedStudent = userRepository.save(user);
				StudentDto updatedStudentDto = new StudentDto();
				updatedStudentDto.setId(updatedStudent.getId());
				
				return updatedStudentDto;
			}
			
			return null;
	}
	
}
