package com.adee.mybatis.module;

public class Student {
	private Integer id;
	private String name;
	private Integer age;
	
	private Address address;
	
	private Grade grade;
	
	
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Student(Integer id, String name, Integer age) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
	}
	
	public Student(String name, Integer age) {
		super();
		this.name = name;
		this.age = age;
	}



	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	
	@Override
	public String toString() {
		return new StringBuilder().append("[id:")
				.append(this.getId()).append(", name:")
				.append(this.getName()).append(", age:")
				.append(this.getAge()).append(", address:").append(this.getAddress())
				.append(", grade:").append(this.getGrade()).append("]").toString();
	}
}
