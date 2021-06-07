package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB2021Team03_DBConnection {
    public static Connection getConnection(){
        Connection conn = null;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db2021team03", "DB2021Team03", "DB2021Team03");
            return conn;
        } catch(Exception e){
            e.printStackTrace();
        }

        return conn;
    }
}
