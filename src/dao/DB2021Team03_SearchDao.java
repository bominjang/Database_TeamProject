package dao;

import models.Keyword;
import models.Movies;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;


/**
 * 검색 기능에서 사용될 SQL처리를 위한 Class
 */
public class DB2021Team03_SearchDao {

    /**
     * SearchDao 객체 생성자
     */
    private DB2021Team03_SearchDao(){}
    private static DB2021Team03_SearchDao instance = new DB2021Team03_SearchDao();

    /**
     * SearchDao 객체를 생성해주는 함수
     * @return DB2021Team03_SearchDao 객체
     */
    public static DB2021Team03_SearchDao getInstance() {
        return instance;
    }

    /**
     * DB connection을 위한 필드
     */
    private static Connection conn;
    /**
     * Query를 매개변수를 이용해 동적으로 작성하기 위한 필드
     */
    private static PreparedStatement pstmt;
    /**
     * Query의 결과값을 받을 필드
     */
    private static ResultSet rs;


    /**
     * 검색 기능에서 사용될 키워드를 출력하는 메소드(콤보박스에 사용)
     *
     * @return Movie Vector<Keyword>를 반환한다. 실패시 null을 반환합니다.
     */
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


    /**
     * 검색 시 해당 데이터의 존재 유무를 확인하는 메소드
     *
     * @param keyword 검색 키워드
     * @param text 검색어
     * @return 검색 키워드로 검색어를 찾아보았을 때 객체가 있는지 유무를 boolean으로 반환한다. 실패 시 false
     */
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


    /**
     * 키워드와 검색어로 영화를 찾아주는 메소드
     *
     * @param keyword 검색 키워드
     * @param text 검색어
     * @return 키워드에 맞게 검색을 하여 조건에 맞는 영화들 Vector<Movies>를 반환한다. 그 외엔 null을 반환합니다.
     */
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

