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
 * һ������
 * 		1.��ʾ����Ա����Ϣ
 * 			- URI��emps
 * 			- ����ʽ��GET
 * 			- ��ʾЧ����id��lastName��email��gender��department��edit��ť��delete��ť
 * 		2.���Ա��
 * 			- ���ҳ��
 * 				- URI��emp
 * 				- ����ʽ��GET
 * 				- ��ʾЧ����lastName��email��gender��department��submit��ť
 * 			- submit����
 * 				- URI��emp
 * 				- ����ʽ��POST
 * 				- ��ʾЧ���������ɣ��Ӷ���listҳ��
 * 		3.ɾ������
 * 			- URL��emp/{id}
 * 			- ����ʽ��DELETE
 * 			- ɾ����Ч�����ض���listҳ��
 * 		4.�޸Ĳ���
 * 			- ��ʾ�޸�ҳ�棨lastName���Բ��ܱ��޸ģ�������lastName��
 * 				- URI��emp/{id}
 * 				- ����ʽ��GET
 * 				- ��ʾЧ�����������ԣ�email��gender��department��submit��ť
 * 			- ���submit��ť��lastName���Բ��ܱ�update�����ݿ⣩
 * 				- URI��emp/{id}
 * 				- ����ʽ��PUT
 * 				- ��ʾЧ�����޸���ɺ��ض���listҳ��
 * 		5.�����
 * 			- ʵ���ࣺEmployee��Department
 * 			- Handler��EmployeeHandler
 * 			- Dao��EmployeeDao��DepartmentDao
 * 		6.���ҳ��
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
