package com.adee.mybatis.test_mybatis;

import java.util.List;

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
	public void testAdd() {
		logger.debug("添加学生");
		Student student=new Student("李四",11);
		int result=studentMapper.add(student);
		sqlSession.commit();
		if(result>0){
			logger.debug(" 添加成功");
		}
	}
	
	@Test
	public void testUpdate() {
		logger.debug("更新学生");
		Student student=new Student(1,"李四",11);
		int result=studentMapper.update(student);
		sqlSession.commit();
		if(result>0){
			logger.debug(" 更新成功");
		}
	}
	
	@Test
	public void testDelete() {
		logger.debug("删除学生");
		int result=studentMapper.delete(2);
		sqlSession.commit();
		if(result>0){
			logger.debug(" 删除成功");
		}
	}
	
	@Test
	public void testFindById() { //一对一
		logger.debug("根据id查询学生");
		Student st=studentMapper.findStudentWithAddress(5);
		logger.debug(st.toString());
	}
	
	
	@Test
	public void testFindByGradeId() { //一对多
		logger.debug("根据id查询学生");
		List<Student> sts =studentMapper.findByGradeId(4);
		for(Student st : sts) {
			logger.debug(st.toString());
		}
	}
	
	
	@Test
	public void testFindAll() {
		logger.debug("查询所有学生");
		List<Student> sts =  studentMapper.findAll();
		for(Student st : sts) {
			logger.debug(st.toString());
		}
		
	}
}
