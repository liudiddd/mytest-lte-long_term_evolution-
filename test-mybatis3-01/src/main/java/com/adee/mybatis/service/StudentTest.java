package com.adee.mybatis.service;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adee.mybatis.mappers.StudentMapper;
import com.adee.mybatis.module.Student;
import com.adee.mybatis.util.SqlSessionFactoryUtil;

public class StudentTest {
	
	private static Logger logger = LoggerFactory.getLogger(StudentTest.class);
	
	public static void main(String[] args) {
		SqlSession sqlSession=SqlSessionFactoryUtil.openSession();
		StudentMapper studentMapper=sqlSession.getMapper(StudentMapper.class);
		Student student=new Student("李四",11);
		int result=studentMapper.add(student);
		sqlSession.commit();
		if(result>0){
			logger.debug(" 添加成功");
		}
	}
}
