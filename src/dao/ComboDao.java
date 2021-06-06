package dao;

import java.security.Key;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import models.Combo;
import models.Keyword;

public class ComboDao {
    private ComboDao(){}
    private static ComboDao instance = new ComboDao();

    public static ComboDao getInstance() {
        return instance;
    }

    private static Connection conn;
    private static PreparedStatement pstmt;
    private static ResultSet rs;

    public Vector<Combo> setCombo(String comboContent) {
        Vector<Combo> combos = new Vector<>();
        String sql;

        if(comboContent.equals("movie")) {
            sql = "SELECT ID AS 'KEY', TITLE AS 'VALUE' FROM DB2021_MOVIE";
        } else {
            sql = "";
        }

        conn = DBConnection.getConnection();
        try {
            System.out.println(sql);
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while(rs.next()){
                Combo combo = new Combo();
                combo.setKey(rs.getInt("KEY"));
                combo.setValue(rs.getString("VALUE"));
                combos.add(combo);
            }

            if(rs.getRow() != 0) {
                conn.close();
                return null;
            } else {
                conn.close();
                return combos;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Vector<Combo> setCombo(String comboContent, int id) {
        Vector<Combo> combos = new Vector<>();
        String sql;

        sql = "SELECT ID AS KEY, NAME AS VALUE FROM THEATER WHERE PLACE_ID = ?";

        conn = DBConnection.getConnection();
        try {
            pstmt = conn.prepareStatement(sql);
            if(comboContent.equals("theater")) {
                pstmt.setInt(1, id);
            }
            rs = pstmt.executeQuery();

            while(rs.next()){
                Combo combo = new Combo();
                combo.setKey(rs.getInt("KEY"));
                combo.setValue(rs.getString("VALUE"));
                combos.add(combo);
            }

            if(rs.getRow() == 0) {
                conn.close();
                return null;
            } else {
                conn.close();
                return combos;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}