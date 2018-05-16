package com.adee.mybatis.mappers;

import java.util.List;
import java.util.Map;

import com.adee.mybatis.module.Student;

public interface StudentMapper {
	//
	public List<Student> searchStudents(Map<String, Object> map);
	public List<Student> searchStudents1(Map<String, Object> map);
	public List<Student> searchStudents2(Map<String, Object> map);
	public List<Student> searchStudents3(Map<String, Object> map);
	
	public void updateStudent(Student student);
	
	public int insertStudent(Student student);
	
	public Student getStudentById(Integer id);
}
