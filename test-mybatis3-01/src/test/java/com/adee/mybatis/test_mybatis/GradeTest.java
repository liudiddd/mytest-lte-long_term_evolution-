package com.adee.mybatis.test_mybatis;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adee.mybatis.mappers.GradeMapper;
import com.adee.mybatis.mappers.StudentMapper;
import com.adee.mybatis.module.Grade;
import com.adee.mybatis.module.Student;
import com.adee.mybatis.util.SqlSessionFactoryUtil;

public class GradeTest {
	private static Logger logger = LoggerFactory.getLogger(GradeTest.class);
	private SqlSession sqlSession = null;
	private GradeMapper gradeMapper = null;
	
	@Before
	public void setUp() throws Exception{
		sqlSession=SqlSessionFactoryUtil.openSession();
		gradeMapper = sqlSession.getMapper(GradeMapper.class);
	}
	
	@After
	public void tearDown() throws Exception{
		sqlSession.close();
	}
	

	@Test
	public void testFindById1() { //一对多
		logger.debug("根据id查询年级下所有学生");
		Grade gr = gradeMapper.findById(4);
		logger.debug(gr.toString());
		for(Student st : gr.getStudents()) {
			logger.debug(st.toString());
		}	
	

		
	}
}
