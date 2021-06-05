package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection getConnection(){
        Connection conn = null;

        try{
//            Class.forName("oracle.jdbc.driver.OracleDriver");
//            Class.forName("com.mysql.jdbc.Driver");
//            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:3306:db2021Team03", "DB2021Team03", "DB2021Team03");
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db2021team03", "DB2021Team03", "DB2021Team03");
            System.out.println("connect");
            return conn;
        } catch(Exception e){
            e.printStackTrace();
        }

        return conn;
    }
}
