package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import models.Movies;


/**
 * Movie 테이블 관련 SQL처리를 위한 Class
 */
public class DB2021Team03_MovieDao {

    /**
     * ActorDao 객체 생성자
     */
    private DB2021Team03_MovieDao(){}
    private static DB2021Team03_MovieDao instance = new DB2021Team03_MovieDao();

    /**
     * ActorDao 객체를 생성해주는 함수
     * @return DB2021Team03_ActorDao 객체
     */
    public static DB2021Team03_MovieDao getInstance() {
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
     * Movie Id로 해당 Movie 객체를 반환해주는 메소드
     *
     * @param id 영화의 pk
     * @return 영화를 성공적으로 찾을 시 해당 영화 객체 Movies 반환, 그 외엔 null을 반환합니다.
     */
    public Movies selectOne(int id) {
        String sql = "SELECT * FROM DB2021_MOVIE WHERE ID = ?";
        conn = DB2021Team03_DBConnection.getConnection();

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            Movies movie = new Movies();
            if (rs.next()) {
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

                conn.close();
                return movie;
            } else {
                conn.close();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    /**
     * Movie 평점 순으로 Top10을 선택하여 반환해주는 메소드
     *
     * @return 쿼리 성공 시 10개의 영화 객체가 담긴 Movies Vector 반환, 그 외엔 null을 반환합니다.
     */
    public Vector<Movies> selectRanking()
    {
        Vector<Movies> movies = new Vector<>();
        String sql = "SELECT id, title, rating FROM DB2021_MOVIE ORDER BY rating DESC, title LIMIT 10";
        conn = DB2021Team03_DBConnection.getConnection();

        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            int i=0;

            while (rs.next()) {
                Movies movie = new Movies();
                movie.setId(rs.getInt("ID"));
                movie.setTitle(rs.getString("TITLE"));
                movie.setRating(rs.getFloat("RATING"));
                movies.add(movie);
                i++;
            }

            conn.close();

            return movies;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

//    public Vector<Movies> selectKeyword(String keyword) {
//        Vector<Movies> movies = new Vector<>();
//        String sql = "SELECT *";
//        sql += " FROM MOVIE";
//        sql += " WHERE TITLE LIKE '%" + keyword + "%'";
//        conn = DB2021Team03_DBConnection.getConnection();
//    }
}