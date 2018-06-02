package com.adee.myproject.service;

import java.util.List;

import com.adee.myproject.exception.CommonServiceException;
import com.adee.myproject.module.Employee;

public interface EmployeeService {
	public List<Employee> getList();
	public Employee getById(int id);
	public void add(Employee e) throws CommonServiceException;
	public void update(Employee e) throws CommonServiceException;
	public void deleteById(int id) throws CommonServiceException;
}
