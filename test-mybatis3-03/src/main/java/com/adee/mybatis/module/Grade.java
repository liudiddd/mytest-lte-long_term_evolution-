package com.adee.mybatis.module;

import java.util.List;

public class Grade {
	private Integer id;
	private String gradeName;
	private List<Student> students;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	public List<Student> getStudents() {
		return students;
	}
	public void setStudents(List<Student> students) {
		this.students = students;
	}
	
	
	@Override
	public String toString() {
		return new StringBuilder().append("[id:").append(this.getId())
				.append(", gradeName:").append(this.getGradeName())
				.append("]").toString();
	}
}
