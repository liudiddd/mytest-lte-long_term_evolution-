package com.adee.myproject.dao;

import java.util.List;

import com.adee.myproject.module.Department;

public interface DepartmentDao {
	public List<Department> getList();
	public Department getById(int id);
	public void add(Department e);
	public void update(Department e);
	public void deleteById(int id);
}
