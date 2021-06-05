package dao;

import models.Reviews;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReviewDao {

    private ReviewDao() {
    }

    private static ReviewDao instance = new ReviewDao();

    public static ReviewDao getInstance() {
        return instance;
    }

    private static Connection conn;
    private static PreparedStatement pstmt;
    private static ResultSet rs;
    private String sql;


    public int insert(String movie, String nickname, float rating, String detail) {
        sql = "INSERT INTO DB2021_Review(movie, nickname, create_time, rating, detail)";
        sql += " VALUES(?, ?, now(), ?, ?)";

        conn = DBConnection.getConnection();
        try {
            int returnCnt = 0;
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, movie);
            pstmt.setString(2, nickname);
            pstmt.setFloat(3, rating);
            pstmt.setString(4, detail);
            returnCnt = pstmt.executeUpdate();

            conn.close();
            return returnCnt;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public Reviews selectRecent(String nickname) {
        sql = "SELECT * FROM DB2021_Review WHERE nickname = ? ORDER BY create_time DESC limit 1";

        conn = DBConnection.getConnection();
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nickname);
            rs = pstmt.executeQuery();

            Reviews review = new Reviews();
            if (rs.next()) {
                review.setNickname(rs.getString("nickname"));
                review.setMovie(rs.getString("movie"));
                review.setCreate_time(rs.getString("create_time"));
                review.setRating(rs.getFloat("rating"));
                review.setDetail(rs.getString("detail"));

                conn.close();
                return review;
            } else {
                conn.close();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
