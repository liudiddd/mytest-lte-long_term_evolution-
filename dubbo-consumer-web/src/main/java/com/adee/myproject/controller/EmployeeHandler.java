package com.adee.myproject.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.adee.myproject.exception.CommonServiceException;
import com.adee.myproject.module.Department;
import com.adee.myproject.module.Employee;
import com.adee.myproject.service.DepartmentService;
import com.adee.myproject.service.EmployeeService;

@Controller
public class EmployeeHandler  extends BaseController{
	
	@Resource
	private EmployeeService employeeService;
	
	@Resource
	private DepartmentService departmentService;
	
	@RequestMapping("/emps")
	public String list(Map<String, Object> map) {
		List<Employee> list = employeeService.getList();
		for(Employee e : list) {
			System.out.println(e.getDepartment());
		}
		map.put("emps", list);
		return LIST;
	}
	
	@RequestMapping(value="/emp", method=RequestMethod.GET)
	public String getDepts( Map<String, Object> map) {
		List<Department> list = departmentService.getList();
		map.put("depts", list);
		map.put("e", new Employee());
		return INPUT;
	}
	
	@RequestMapping(value="/emp", method=RequestMethod.POST)
	public String save( Employee e) {
		List<Department> list = departmentService.getList();
		try {
			employeeService.add(e);
		} catch (CommonServiceException e1) {
			e1.printStackTrace();
		}
		return "redirect:/emps";
	}
	
	@RequestMapping(value="/emp/{id}", method=RequestMethod.GET)
	public String getById(@PathVariable(value="id") Integer id, Map<String, Object> map) {
		Employee e = employeeService.getById(id);
		map.put("e", e);
		List<Department> list = departmentService.getList();
		map.put("depts", list);
		return EDIT;
	}
	
	@RequestMapping(value="/emp", method=RequestMethod.PUT)
	public String update(Employee e) {
		try {
			employeeService.update(e);
		} catch (CommonServiceException e1) {
			e1.printStackTrace();
		}
		return "redirect:/emps";
	}
	
	@RequestMapping(value="/emp/{id}", method=RequestMethod.DELETE)
	public String delete(@PathVariable(value="id") Integer id) {
		try {
			employeeService.deleteById(id);
		} catch (CommonServiceException e1) {
			e1.printStackTrace();
		}
		return "redirect:/emps";
	}
	
}
