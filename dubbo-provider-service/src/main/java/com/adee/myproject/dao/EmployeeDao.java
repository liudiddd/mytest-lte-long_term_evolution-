package com.adee.myproject.dao;

import java.util.List;

import com.adee.myproject.module.Employee;

public interface EmployeeDao {
	public List<Employee> getList();
	public Employee getById(int id);
	public void add(Employee e);
	public void update(Employee e);
	public void deleteById(int id);
}
