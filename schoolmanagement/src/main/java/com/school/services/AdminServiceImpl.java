package com.school.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.ErrorManager;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.school.dto.SingleStudentDto;
import com.school.dto.SingleTeacherDto;
import com.school.dto.StudentDto;
import com.school.dto.StudentLeaveDto;
import com.school.dto.StudentLeaveResponseDto;
import com.school.dto.TeacherDto;
import com.school.enums.StudentLeaveStatus;
import com.school.enums.UserRole;
import com.school.jwtFilter.JwtService;
import com.school.model.StudentLeave;
import com.school.model.Teacher;
import com.school.model.User;
import com.school.repositories.StudentLeaveRepository;
import com.school.repositories.TeacherRepository;
import com.school.repositories.UserRepository;

import jakarta.annotation.PostConstruct;

// service layer for business logic.

@Service
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private StudentLeaveRepository studentLeaveRepository;
	
	@Autowired
	private TeacherRepository teacherRepository;
	
	
	// implementation of postStudent() method of AdminService interface
	@Override
	public StudentDto postStudent(StudentDto studentDto) {
		Optional<User> optionalUser = userRepository.findByEmail(studentDto.getEmail());
		if(optionalUser.isEmpty()) {
			User user = new User();
			//Copy the property values of the given source bean(studentDto) into the target bean(user).
			BeanUtils.copyProperties(studentDto, user);
			user.setPassword(new BCryptPasswordEncoder(12).encode(studentDto.getPassword()));
			user.setRole(UserRole.STUDENT);
			User createdUser = userRepository.save(user);
			
			StudentDto createdStudentDto = new StudentDto();
			createdStudentDto.setId(createdUser.getId());
			createdStudentDto.setEmail(createdUser.getEmail());
			
			return createdStudentDto;
		}
		return null;
	}
	
	// implementation of getAllStudents() method of AdminService interface
	
	@Override
	public List<StudentDto> getAllStudents() {
		
		return userRepository.findAllByRole(UserRole.STUDENT).stream().map(User::getStudentDto).collect(Collectors.toList());
	}
	
	
	
	@Override
	public void deleteStudent(int studentId) {
		userRepository.deleteById(studentId);
		
	}
	
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
	
	
	
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
	
	// admin create the user....
	//if there is no ADMIN present in db then @postConstruct will create admin for first time.
	//this annotated method no need to call it was run automatically at first
	@PostConstruct
	public void createAdminAccount() {
		
		User adminAccount =  userRepository.findByRole(UserRole.ADMIN);
		
		if(adminAccount == null) {
			
			User admin = new User();
			admin.setEmail("admin@test.com");
			admin.setName("admin");
			admin.setRole(UserRole.ADMIN);
			admin.setPassword(encoder.encode("admin"));
			userRepository.save(admin);
		}
		
		
	}

	
	@Override
	public List<StudentLeaveResponseDto> getAllAppliedLeaves() {
		
		return studentLeaveRepository.findAll().stream().map(StudentLeave::getStudentLeaveResponseDto).collect(Collectors.toList());

	}

	@Override
	public StudentLeaveDto changeLeaveStatus(int leaveId, String status) {
		
		Optional<StudentLeave> optionalStudentLeave = studentLeaveRepository.findById(leaveId);
		if(optionalStudentLeave.isPresent()) {
			StudentLeave studentLeave = optionalStudentLeave.get();
			if(Objects.equals(status, "Approve")) {
				studentLeave.setStudentLeaveStatus(StudentLeaveStatus.Approved);
			} else {
				
				studentLeave.setStudentLeaveStatus(StudentLeaveStatus.Disapproved);
			}
			
			// Requirement of these steps ?????
			StudentLeave updatedStudentLeave = studentLeaveRepository.save(studentLeave);
			StudentLeaveDto updatedStudentLeaveDto = new StudentLeaveDto();
			updatedStudentLeaveDto.setId(updatedStudentLeave.getId());
			
			return updatedStudentLeaveDto;
		}
		
		return null;
	}
	
	//####################### Teacher Operations #########################

	@Override
	public TeacherDto postTeacher(TeacherDto teacherDto) {
		

		Teacher teacher = new Teacher();
		BeanUtils.copyProperties(teacherDto, teacher);
	
		Teacher createdTeacher = teacherRepository.save(teacher);
			
		TeacherDto createdTeacherDto = new TeacherDto();
		createdTeacherDto.setId(createdTeacher.getId());
			
			
		return createdTeacherDto;
	}

	@Override
	public List<TeacherDto> getAllTeachers() {
//		return userRepository.findAllByRole(UserRole.STUDENT).stream().map(User::getStudentDto).collect(Collectors.toList());

		return teacherRepository.findAll().stream().map(Teacher::getTeacherDto).collect(Collectors.toList());
	}

	@Override
	public void deleteTeacher(int teacherId) {
		
		teacherRepository.deleteById(teacherId);
	}

	@Override
	public SingleTeacherDto getTeacherById(int teacherId) {
//		
		 Optional<Teacher> optionalTeacher = teacherRepository.findById(teacherId);
		 if(optionalTeacher.isPresent()) {
			 SingleTeacherDto singleTeacherDto = new SingleTeacherDto();
			 singleTeacherDto.setTeacherDto(optionalTeacher.get().getTeacherDto());
			 return singleTeacherDto;
		 }
		return null;
	}

	@Override
	public TeacherDto updateTeacher(int teacherId, TeacherDto teacherDto) {
		//step 1- Find or get the user by Id
		Optional<Teacher> optionalTeacher = teacherRepository.findById(teacherId);
		//step 2- if present then set its value in a new object and set updated value in new object
		
		if(optionalTeacher.isPresent()) {
			Teacher teacher = optionalTeacher.get();
			
			teacher.setName(teacherDto.getName());
			teacher.setDepartment(teacherDto.getDepartment());
			teacher.setQualification(teacherDto.getQualification());
			teacher.setDob(teacherDto.getDob());
			teacher.setAddress(teacherDto.getAddress());
			teacher.setGender(teacherDto.getGender());
			
			//step 3- save it using repository & save method return the entity object, we store it
			
			Teacher updatedTeacher = teacherRepository.save(teacher);
			//step 4- create TeacherDto object and set objects id as previous id because our return type is TeacherDto
			TeacherDto updatedTeacherDto = new TeacherDto();
			// we only set and return id because using id we check in frontend that teacher was update or not
			updatedTeacherDto.setId(updatedTeacher.getId());
			
			return updatedTeacherDto;
		}
		
		return null;
	}

	
	
	
	
	
// 1 Method*************************

//	public String verify(User user) {
//		
//		Authentication authentication = 
//				authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
//		
//		if(authentication.isAuthenticated()) {
//			return jwtService.generateToken(user.getName());
//		}
//		
//	 throw new BadCredentialsException("Incorrect UserId or Password");
//	}
	
	
	
	
	

}
