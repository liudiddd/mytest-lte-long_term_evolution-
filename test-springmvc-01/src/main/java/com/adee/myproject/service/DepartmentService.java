package com.adee.myproject.service;

import java.util.List;

import com.adee.myproject.exception.CommonServiceException;
import com.adee.myproject.module.Department;

public interface DepartmentService {
	public List<Department> getList();
	public Department getById(int id);
	public void add(Department e) throws CommonServiceException;
	public void update(Department e) throws CommonServiceException;
	public void deleteById(int id) throws CommonServiceException;
}
