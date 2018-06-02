package com.adee.myproject.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.adee.myproject.module.Department;
import com.adee.myproject.service.DepartmentService;

/**
 * RESTful Springmvc CRUD
 * 一、需求
 * 		1.显示所有员工信息
 * 			- URI：emps
 * 			- 请求方式：GET
 * 			- 显示效果：id、lastName、email、gender、department、edit按钮、delete按钮
 * 		2.添加员工
 * 			- 添加页面
 * 				- URI：emp
 * 				- 请求方式：GET
 * 				- 显示效果：lastName、email、gender、department、submit按钮
 * 			- submit请求
 * 				- URI：emp
 * 				- 请求方式：POST
 * 				- 显示效果：添加完成，从定向到list页面
 * 		3.删除操作
 * 			- URL：emp/{id}
 * 			- 请求方式：DELETE
 * 			- 删除后效果：重定向到list页面
 * 		4.修改操作
 * 			- 显示修改页面（lastName属性不能被修改，不回显lastName）
 * 				- URI：emp/{id}
 * 				- 请求方式：GET
 * 				- 显示效果：回显属性：email、gender、department、submit按钮
 * 			- 点击submit按钮（lastName属性不能被update到数据库）
 * 				- URI：emp/{id}
 * 				- 请求方式：PUT
 * 				- 显示效果：修改完成后，重定向到list页面
 * 		5.相关类
 * 			- 实体类：Employee、Department
 * 			- Handler：EmployeeHandler
 * 			- Dao：EmployeeDao、DepartmentDao
 * 		6.相关页面
 * 			- list.jsp
 * 			- input.jsp
 * 			- edit.jsp
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="/empssss")
public class RESTfulController extends BaseController{
	
	@Resource
	private DepartmentService departmentService;
	
	
	
	@RequestMapping(value="/dept/{id}", method=RequestMethod.GET)
	public String getDempById(@PathVariable(value="id") Integer id, Map<String, Object> map) {
		Department d = departmentService.getById(id);
		map.put("dept", d);
		return LIST;
	}
}
