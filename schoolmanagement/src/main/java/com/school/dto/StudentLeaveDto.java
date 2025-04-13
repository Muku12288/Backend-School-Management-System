package com.school.dto;

import java.util.Date;

import com.school.enums.StudentLeaveStatus;

import lombok.Data;

@Data
public class StudentLeaveDto {
	
	private int id;
	
	private String subject;
	
	private String body;
	
	private Date date;
	
	private StudentLeaveStatus studentLeaveStatus;
	
	private int userid;

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

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}
	
	

}
