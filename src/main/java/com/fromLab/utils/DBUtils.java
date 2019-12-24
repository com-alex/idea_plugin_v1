package com.fromLab.utils;

import com.fromLab.configuration.Configuration;

import java.sql.*;

/**
 * @author wsh
 * @date 2019-11-05
 *
 * 本地数据测试库连接工具类
 */

public class DBUtils {
    private static String driver = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/plugin_test?useUnicode=true&characterEncoding=UTF-8";
    private static String username = "root";
    private static String password = "toor";
    private static Connection conn;

    public static Connection getConnection(){
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void closeAll(Connection conn, PreparedStatement p, ResultSet rs){
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(p != null){
            try {
                p.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
