package com.adee.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Date;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws Exception{
		Connection conn = null;// conn用于连接数据库
        PreparedStatement pst = null;// stmt用于发送sql语句到数据库并执行sql语句
        // localhost:表示数据库服务器地址,如192.168.0.1
        // 3306表示端口号
        // test是数据库名称
        // user是数据库用户名
        // password是数据库的密码
        String connectionString = "jdbc:mysql://192.168.1.110:3306/myproject?user=root&password=root&useUnicode=true&characterEncoding=UTF-8";
        String sql = "INSERT INTO ifmp_draw_record (user_id, prize_id, draw_time, win) VALUES (1,1, \"2018/02/12\",0);";
        try {
            // 将数据驱动程序类加载到内存中
        	
            Class.forName("com.mysql.jdbc.Driver");
            
            // 通过驱动程序管理器DriverManager获取连接对象conn，conn连接的服务器和数据库信息在connectionString
            conn = DriverManager.getConnection(connectionString);
            conn.setAutoCommit(false);
            pst = conn.prepareStatement(sql);
            
            /*pst.setInt(1, 1);
            pst.setInt(2, 1);
            pst.setString(3, new java.util.Date().toString());
            //pst.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            pst.setString(4, "0");*/
            
            // 将sql语句发送到test数据中，并执行，i是影响的行数 >0表示成功 否则表示失败
            int i = pst.executeUpdate(sql);
            conn.commit();
            if (i == 1) {
                System.out.println("添加成功");
            } else {
                System.out.println("添加失败");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
        	pst.close();
        	conn.close();
        }

	}
}
