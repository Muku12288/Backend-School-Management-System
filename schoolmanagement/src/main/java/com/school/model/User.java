package com.school.model;
import java.util.Date;

import com.school.dto.StudentDto;
import com.school.enums.UserRole;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "school")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String name;
	
	private String email;
	
	private String password;
	
	private String fatherName;
	
	private String motherName;
	
	private String studentClass;
	
	private Date dob;
	
	private String address;
	
	private String gender;
	
	private UserRole role;
	
	
	
	
	
	public UserRole getRole() {
		return role;
	}
	public void setRole(UserRole Role) {
		this.role = Role;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFatherName() {
		return fatherName;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	public String getMotherName() {
		return motherName;
	}
	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}
	public String getStudentClass() {
		return studentClass;
	}
	public void setStudentClass(String studentClass) {
		this.studentClass = studentClass;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public StudentDto getStudentDto() {
		StudentDto studentDto = new StudentDto();
		studentDto.setId(id);
		studentDto.setName(name);
		studentDto.setEmail(email);
		studentDto.setAddress(address);
		studentDto.setDob(dob);
		studentDto.setStudentClass(studentClass);
		studentDto.setGender(gender);
		studentDto.setFatherName(fatherName);
		studentDto.setMotherName(motherName);
		
		return studentDto;
	}
	
}
