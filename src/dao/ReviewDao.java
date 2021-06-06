package dao;

import models.Reviews;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    private String sql, sql2, sql3;


    public int insert(String movie, String nickname, float rating, String detail) {
        sql = "INSERT INTO DB2021_Review(movie, nickname, create_time, rating, detail)";
        sql += " VALUES(?, ?, now(), ?, ?)";

        // id면 좋겠지만
        sql2 = "SELECT AVG(rating) as average FROM DB2021_Review WHERE movie = ?";
        sql3 = "UPDATE DB2021_Movie set rating = ? WHERE title = ?";

        conn = DBConnection.getConnection();
        try {

            conn.setAutoCommit(false);

            // 리뷰 insert
            int returnCnt = 0;
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, movie);
            System.out.println(movie);
            pstmt.setString(2, nickname);
            System.out.println(nickname);
            pstmt.setFloat(3, rating);
            System.out.println(rating);
            pstmt.setString(4, detail);
            System.out.println(detail);
            returnCnt = pstmt.executeUpdate();

            // 트랜잭션-영화 평점 같이 올리기
            // 영화 평점 다시 계산
            pstmt = conn.prepareStatement(sql2);
            pstmt.setString(1, movie);
            rs = pstmt.executeQuery();
            float update_rating;
            if (rs.next()) {
                update_rating = rs.getFloat("average");
            } else {
                throw new Exception();
            }
            
            // 새 평점으로 업데이트
            pstmt = conn.prepareStatement(sql3);
            pstmt.setFloat(1, update_rating);
            pstmt.setString(2, movie);
            returnCnt = pstmt.executeUpdate();
            conn.commit();

            conn.setAutoCommit(true);
            conn.close();
            return returnCnt;

        } catch (Exception e) {
            e.printStackTrace();
            try{
                conn.rollback();
            } catch(SQLException se) {
                System.out.println(se.getMessage());
            }
        }

        try {
            conn.close();
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
                review.setId(rs.getInt("ID"));
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

    public Reviews reviewDetail(int reviewId)
    {
        sql = "SELECT * FROM DB2021_Review WHERE id = ?";

        conn = DBConnection.getConnection();

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reviewId);
            rs = pstmt.executeQuery();

            Reviews review = new Reviews();
            if (rs.next()) {
                review.setId(rs.getInt("ID"));
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


    //update

    //delete
}
