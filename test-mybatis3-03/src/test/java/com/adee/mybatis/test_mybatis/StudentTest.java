package com.adee.mybatis.test_mybatis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
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
	private SqlSession sqlSession1 = null; //测试Mybatis二级缓存用
	private SqlSession sqlSession2 = null; //测试Mybatis二级缓存用
	private StudentMapper studentMapper = null;
	private StudentMapper studentMapper1 = null; //测试Mybatis二级缓存用
	private StudentMapper studentMapper2 = null; //测试Mybatis二级缓存用
	
	@Before
	public void setUp() throws Exception{
		sqlSession=SqlSessionFactoryUtil.openSession();
		sqlSession1=SqlSessionFactoryUtil.openSession();
		sqlSession2=SqlSessionFactoryUtil.openSession();
		studentMapper=sqlSession.getMapper(StudentMapper.class);
		studentMapper1=sqlSession1.getMapper(StudentMapper.class);
		studentMapper2=sqlSession2.getMapper(StudentMapper.class);
	}
	
	@After
	public void tearDown() throws Exception{
		sqlSession.close();
	}
	
	
	
	@Test
	public void insetStudent() {
		Student s = new Student();
		s.setName("张三4");
		s.setAge(18);
		s.setAddressId(3);
		s.setGradeId(3);
		s.setRemark("很长的文本");
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
		s.setPic(pic);
		studentMapper.insertStudent(s);
		sqlSession.commit();
	}
	
	
	@Test
	public void getStudentById() {
		byte [] pic = null;
		OutputStream os = null;
		try {
			Student st = studentMapper.getStudentById(9);
			pic = st.getPic();
			File f = new File("d:/timg1.jpg");
			os = new FileOutputStream(f);
			os.write(pic);
		}catch(Exception e) {
			logger.error("getStudent exception", e);
		}finally {
			if(os != null) {
				try {
					os.close();
				} catch (IOException ex) {
					logger.error("", ex);
				}
			}
		}
	}
		
	//测试Mybatis二级缓存
	@Test
	public void getStudent() {
		List<Student> sts = studentMapper.getStudent("张三丰", 11);
		sqlSession.close(); //将sqlSession的一级缓存写入二级缓存中，sqlSession1执行相同sql时就可以访问到二级缓存了
		List<Student> sts1 = studentMapper1.getStudent("张三丰", 11); //访问sqlSession生成的二级缓存，不会向数据库发送sql
		studentMapper1.insertStudent(new Student()); //在Student的namespace下执行跟新操作，commit后会清除namespace下的所有二级缓存
		sqlSession1.commit();
		sqlSession1.close();
		List<Student> sts2 = studentMapper2.getStudent("张三丰", 11); //二级缓存一被sqlSession1清除，需要向数据库发送sql了
		
		for(Student st : sts) {
			logger.debug(st.toString());
		}
		logger.debug("===================================");
		for(Student st : sts1) {
			logger.debug(st.toString());
		}
		logger.debug("===================================");
		for(Student st : sts2) {
			logger.debug(st.toString());
		}
		//try { Thread.sleep(30000); } catch (InterruptedException e) { }
	}
	
	/**
	 * RowBounds是逻辑分页，将数据都查回来，然后返回指定的页
	 */
	@Test
	public void getStudentByPage() {
		int offset = 0;
		int limit = 3;
		RowBounds rb = new RowBounds(offset, limit);
		List<Student> sts = studentMapper.getStudentsByPage(rb);
		for(Student st : sts) {
			logger.debug(st.toString());
		}
	}
	
	/**
	 * 物理分页
	 */
	@Test
	public void getStudentByPage2() {
		int offset = 0;
		int limit = 3;
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("start", offset);
		map.put("size", limit);
		List<Student> sts = studentMapper.getStudentsByPage2(map);
		for(Student st : sts) {
			logger.debug(st.toString());
		}
	}
	
	
	
}
