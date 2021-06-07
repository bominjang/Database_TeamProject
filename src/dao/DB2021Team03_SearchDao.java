package dao;

import models.Keyword;
import models.Movies;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

// 검색 기능에서 사용될 SQL처리를 위한 Class
public class DB2021Team03_SearchDao {
    private DB2021Team03_SearchDao(){}
    private static DB2021Team03_SearchDao instance = new DB2021Team03_SearchDao();

    public static DB2021Team03_SearchDao getInstance() {
        return instance;
    }

    private static Connection conn;
    private static PreparedStatement pstmt;
    private static ResultSet rs;

    // 검색 기능에서 사용될 키워드를 출력하는 메소드(콤보박스에 사용)
    public Vector<Keyword> setCombo() {
        Vector<Keyword> combos = new Vector<>();

        Keyword movie = new Keyword();
        movie.setKey("movie");
        movie.setValue("영화제목");
        combos.add(movie);

        Keyword direc = new Keyword();
        direc.setKey("director");
        direc.setValue("감독이름");
        combos.add(direc);

        Keyword actor = new Keyword();
        actor.setKey("actor");
        actor.setValue("배우이름");
        combos.add(actor);

        return combos;
    }

    // 검색 시 해당 데이터의 존재 유무를 확인하는 메소드
    public boolean dataExist(String keyword, String text) {
        String sql, view;

        if(keyword.equals("movie")) {
            sql = "SELECT * FROM DB2021_titleExist WHERE title = ?";
        } else if(keyword.equals("director")) {
            sql = "SELECT * FROM DB2021_DnameExist WHERE name = ?";
        } else if(keyword.equals("actor")) {
            sql = "SELECT * FROM DB2021_AnameExist WHERE name = ?";
        } else {
            sql = "";
        }

        conn = DB2021Team03_DBConnection.getConnection();
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, text);
            rs = pstmt.executeQuery();

            if(rs.next()){
                conn.close();
                return true;
            } else {
                conn.close();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 키워드와 검색어로 영화를 찾아주는 메소드
    public Vector<Movies> search(String keyword, String text) {
        Vector<Movies> results = new Vector<>();
        String sql;

        //keyword에 따른 검색 쿼리문
        if(keyword.equals("movie")) {
            sql = "SELECT * FROM DB2021_Movie WHERE title = ?";
        } else if(keyword.equals("director")) {
            sql = "SELECT * FROM DB2021_Movie WHERE director = ?";
        } else if(keyword.equals("actor")) {
            sql = "SELECT * FROM DB2021_Movie WHERE title in (SELECT movie FROM DB2021_Actor_Movie WHERE actor = ?)";
        } else {
            sql = "";
        }

        conn = DB2021Team03_DBConnection.getConnection();

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, text);
            rs = pstmt.executeQuery();

            while(rs.next()){
                //검색한 결과들을 results에 담음.
                Movies movie = new Movies();
                movie.setId(rs.getInt("ID"));
                movie.setTitle(rs.getString("title"));
                movie.setGenre(rs.getString("genre"));
                movie.setCountry(rs.getString("country"));

                movie.setRunningTime(rs.getInt("running_time"));
                movie.setOpening_date(rs.getDate("opening_date"));

                movie.setDirector(rs.getString("director"));
                movie.setPlot(rs.getString("plot"));
                movie.setRating(rs.getFloat("rating"));
                movie.setAge(rs.getInt("age"));

                results.add(movie);
            }

            if(rs.getRow() != 0) {
                conn.close();
                return null;
            } else {
                conn.close();
                return results;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}

