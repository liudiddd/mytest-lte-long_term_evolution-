package com.adee.myproject.pojo;

public class User1 {
	private int id;
	private String name;
	private String password;
	private String email;
	private int age;
	
	public User1(int id, String name, String password, String email, int age) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.email = email;
		this.age = age;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "User1 [id=" + id + ", name=" + name + ", password=" + password + ", email=" + email + ", age=" + age
				+ "]";
	}
	
}
