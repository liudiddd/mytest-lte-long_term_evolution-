package com.adee.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Driver;

public class Test {
	public static void main(String[] args)  throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver", true, Test.class.getClassLoader());  //加载类并解析
		Driver d;
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db1", "root", "");
		conn.setAutoCommit(false);
		PreparedStatement ps = conn.prepareStatement("select * from salgrade");
		ResultSet rs = ps.executeQuery();
		System.out.print("grade\t");
		System.out.print("losal\t\t");
		System.out.print("hisal\n");
		while(rs.next()) {
			System.out.print(rs.getInt(1) + "\t\t");
			System.out.print(rs.getBigDecimal(2) + "\t");
			System.out.print(rs.getBigDecimal(3) + "\n");
		}
		
	}
}
