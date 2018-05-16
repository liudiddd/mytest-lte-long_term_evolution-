package com.adee.mybatis.test_mybatis;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adee.mybatis.mappers.StudentMapper;
import com.adee.mybatis.module.Student;
import com.adee.mybatis.util.SqlSessionFactoryUtil;

public class StudentTest {
	private static Logger logger = LoggerFactory.getLogger(StudentTest.class);
	private SqlSession sqlSession = null;
	private StudentMapper studentMapper = null;
	
	@Before
	public void setUp() throws Exception{
		sqlSession=SqlSessionFactoryUtil.openSession();
		studentMapper=sqlSession.getMapper(StudentMapper.class);
	}
	
	@After
	public void tearDown() throws Exception{
		sqlSession.close();
	}
	
	@Test
	public void testSearchStudents() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "%李%");
		List<Student> sts = studentMapper.searchStudents(map);
		for(Student st : sts) {
			logger.debug(st.toString());
		}
	}
	
	@Test
	public void testSearchStudents1() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "%李%");
		List<Student> sts = studentMapper.searchStudents1(map);
		for(Student st : sts) {
			logger.debug(st.toString());
		}
	}
	
	
	@Test
	public void testSearchStudents2() {
		Map<String, Object> map = new HashMap<String, Object>();
		//map.put("searchBy", "gradeId");
		//map.put("gradeId", "4");
		//map.put("searchBy", "name");
		//map.put("name", "王五");
		map.put("age", "11");
		List<Student> sts = studentMapper.searchStudents2(map);
		for(Student st : sts) {
			logger.debug(st.toString());
		}
	}
	
	@Test
	public void testSearchStudents3() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Integer> gradeIds = new ArrayList<Integer>();
		gradeIds.add(1);
		gradeIds.add(4);
		map.put("gradeIds", gradeIds);
		List<Student> sts = studentMapper.searchStudents3(map);
		for(Student st : sts) {
			logger.debug(st.toString());
		}
	}
	
	@Test
	public void updateStudent() {
		Student s = new Student();
		s.setId(3);
		s.setName("张三丰");
		studentMapper.updateStudent(s);
		sqlSession.commit();
	}
	
	@Test
	public void insetStudent() {
		Student s = new Student();
		s.setName("张三4");
		s.setAge(18);
		s.setRemark("很长的文本。。。");
		byte [] pic = null;
		InputStream is = null;
		try {
			File f = new File("d:/timg.jpg");
			is = new FileInputStream(f);
			pic = new byte[is.available()];
			is.read(pic);
			is.close();
		}catch(Exception e) {
			logger.error("insertStudent exception", e);
		}finally {
			if(is != null) {
				try {
					is.close();
				} catch (IOException ex) {
					logger.error("", ex);
				}
			}
		}
		studentMapper.insertStudent(s);
		sqlSession.commit();
	}
	
	
	
	
	
	
	
	
}
