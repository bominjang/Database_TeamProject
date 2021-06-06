package dao;

import models.Combo;
import models.Keyword;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class SearchDao {
    private SearchDao(){}
    private static SearchDao instance = new SearchDao();

    public static SearchDao getInstance() {
        return instance;
    }

    private static Connection conn;
    private static PreparedStatement pstmt;
    private static ResultSet rs;

    public Vector<Keyword> setCombo(String comboContent) {
        Vector<Keyword> combos = new Vector<>();
        String sql;

        if(comboContent.equals("search")) {
            Keyword movie = new Keyword();
            movie.setKey("제목");
            movie.setValue("movie");
            combos.add(movie);

            Keyword direc = new Keyword();
            direc.setKey("감독");
            direc.setValue("director");
            combos.add(direc);

            Keyword actor = new Keyword();
            actor.setKey("배우");
            actor.setValue("actor");
            combos.add(actor);

            return combos;
        } else if(comboContent.equals("seat")) {
            sql = "SELECT ID AS KEY, SEAT_TYPE AS VALUE FROM SEAT";
        } else if(comboContent.equals("discount")) {
            sql = "SELECT ID AS KEY, NAME||' ('||VAL||UNIT||')' AS VALUE FROM DISCOUNT";
        } else if(comboContent.equals("movie")) {
            sql = "SELECT ID AS 'KEY', TITLE AS 'VALUE' FROM DB2021_MOVIE";
        } else {
            sql = "";
        }
//
//        conn = DBConnection.getConnection();
//        try {
//            System.out.println(sql);
//            pstmt = conn.prepareStatement(sql);
//            rs = pstmt.executeQuery();
//
//            while(rs.next()){
//                Combo combo = new Combo();
//                combo.setKey(rs.getInt("KEY"));
//                combo.setValue(rs.getString("VALUE"));
//                combos.add(combo);
//            }
//
//            if(rs.getRow() != 0) {
//                conn.close();
//                return null;
//            } else {
//                conn.close();
//                return combos;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        return null;
    }
}

