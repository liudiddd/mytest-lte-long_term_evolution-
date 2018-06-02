package com.adee.myproject.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.adee.myproject.exception.CommonServiceException;
import com.adee.myproject.mappers.EmployeeMapper;
import com.adee.myproject.module.Employee;
import com.adee.myproject.service.EmployeeService;

public class EmployeeServiceImpl implements EmployeeService{
	
	@Resource
	private EmployeeMapper employeeMapper;
	
	public List<Employee> getList() {
		return employeeMapper.selectAll();
	}
	
	public Employee getById(int id) {
		return employeeMapper.selectByPrimaryKey(id);
	}
	
	public void add(Employee e) throws CommonServiceException {
		employeeMapper.insert(e);
	}
	
	public void update(Employee e) throws CommonServiceException {
		this.employeeMapper.updateByPrimaryKeySelective(e);
	}
	
	public void deleteById(int id) throws CommonServiceException {
		employeeMapper.deleteByPrimaryKey(id);
	}
}
