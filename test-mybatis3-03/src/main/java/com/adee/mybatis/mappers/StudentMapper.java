package com.adee.mybatis.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.adee.mybatis.module.Student;

public interface StudentMapper {
	//
	
	public int insertStudent(Student student);
	
	public Student getStudentById(Integer id);
	
	public List<Student>  getStudent(String name, Integer age);
	
	public List<Student> getStudentsByPage(RowBounds rb);
	
	//物理分页
	public List<Student> getStudentsByPage2(Map<String, Integer> map);
}
