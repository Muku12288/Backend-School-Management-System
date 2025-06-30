package com.school.model;

import java.util.Date;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.school.dto.StudentLeaveDto;
import com.school.dto.StudentLeaveResponseDto;
import com.school.enums.StudentLeaveStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class StudentLeave {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String subject;
	
	private String body;
	
	private Date date;
	
	private StudentLeaveStatus studentLeaveStatus;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false) //this StudentLeave belongs to one User
	@JoinColumn(name = "user_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE) //When the user is deleted, associated leave records are deleted too (@OnDelete)
	@JsonIgnore //The user field is ignored in JSON serialization (@JsonIgnore) — this avoids infinite loops
	private User user;
	
	//for get student all leaves api
	public StudentLeaveDto getStudentLeaveDto() {
		StudentLeaveDto studentLeaveDto = new StudentLeaveDto();
		
		studentLeaveDto.setId(id);
		studentLeaveDto.setSubject(subject);
		studentLeaveDto.setBody(body);
		studentLeaveDto.setDate(date);
		studentLeaveDto.setStudentLeaveStatus(studentLeaveStatus);
		studentLeaveDto.setUserid(user.getId());
		
		return studentLeaveDto;
	}
	//for get student all leaves api
	public StudentLeaveResponseDto getStudentLeaveResponseDto() {
		StudentLeaveResponseDto studentLeaveResponseDto = new StudentLeaveResponseDto();
		
		studentLeaveResponseDto.setId(id);
		studentLeaveResponseDto.setSubject(subject);
		studentLeaveResponseDto.setBody(body);
		studentLeaveResponseDto.setDate(date);
		studentLeaveResponseDto.setStudentLeaveStatus(studentLeaveStatus);
		studentLeaveResponseDto.setUserid(user.getId());
		studentLeaveResponseDto.setName(user.getName());
		studentLeaveResponseDto.setStudentClass(user.getStudentClass());
		
		return studentLeaveResponseDto;
	}
	
	
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public StudentLeaveStatus getStudentLeaveStatus() {
		return studentLeaveStatus;
	}

	public void setStudentLeaveStatus(StudentLeaveStatus studentLeaveStatus) {
		this.studentLeaveStatus = studentLeaveStatus;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	

}
