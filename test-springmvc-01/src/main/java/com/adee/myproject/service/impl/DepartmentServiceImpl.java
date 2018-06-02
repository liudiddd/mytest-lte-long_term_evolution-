package com.adee.myproject.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.adee.myproject.exception.CommonServiceException;
import com.adee.myproject.mappers.DepartmentMapper;
import com.adee.myproject.module.Department;
import com.adee.myproject.service.DepartmentService;

public class DepartmentServiceImpl implements DepartmentService{
	
	@Resource
	private DepartmentMapper departmentMapper;
	
	public List<Department> getList() {
		return departmentMapper.getAll();
	}
	
	public Department getById(int id) {
		return departmentMapper.selectByPrimaryKey(id);
	}
	
	public void add(Department e) throws CommonServiceException {
		// TODO Auto-generated method stub
		
	}
	
	public void update(Department e) throws CommonServiceException {
		// TODO Auto-generated method stub
		
	}
	
	public void deleteById(int id) throws CommonServiceException {
		// TODO Auto-generated method stub
		
	}
}
