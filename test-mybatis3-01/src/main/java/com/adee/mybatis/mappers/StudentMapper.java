package com.adee.mybatis.mappers;

import java.util.List;
import java.util.Map;

import com.adee.mybatis.module.Student;

public interface StudentMapper {
	public int add(Student student);
	public int update(Student student);
	public int delete(Integer id);
	public Student findById(Integer id);
	public List<Student> findAll();
	public Student findStudentWithAddress(Integer id);
	public List<Student> findByGradeId(Integer gradeId);
	
	//
	public List<Student> searchStudents(Map<String, Object> map);
}
