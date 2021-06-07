package dao;

import models.Reviews;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Review 테이블 관련 SQL처리를 위한 Class
public class DB2021Team03_ReviewDao {

    private DB2021Team03_ReviewDao() {
    }

    private static DB2021Team03_ReviewDao instance = new DB2021Team03_ReviewDao();

    public static DB2021Team03_ReviewDao getInstance() {
        return instance;
    }

    private static Connection conn;
    private static PreparedStatement pstmt;
    private static ResultSet rs;
    private String sql, sql2, sql3;

    // 사용자가 리뷰를 작성하면 Review table에 리뷰를 insert하는 메소드
    public int insert(String movie, String nickname, float rating, String detail) {
        //DB2021_Review 테이블에 사용자가 입력한 리뷰를 insert하는 쿼리문.
        sql = "INSERT INTO DB2021_Review(movie, nickname, create_time, rating, detail)";
        sql += " VALUES(?, ?, now(), ?, ?)";

        //사용자가 리뷰를 남긴 영화에 대한 평균 평점을 계산하여 select하는 쿼리문.
        sql2 = "SELECT AVG(rating) as average FROM DB2021_Review WHERE movie = ?";

        //DB2021_Movie 테이블에서 사용자가 평점를 남긴 영화에 대한 rating 값을 update하는 쿼리문.
        sql3 = "UPDATE DB2021_Movie set rating = ? WHERE title = ?";

        conn = DB2021Team03_DBConnection.getConnection();
        try {

            //Transaction으로 review 삽입, 평균 평점 계산, 평점 업데이트를 구현함.
            conn.setAutoCommit(false);

            // 리뷰 insert
            int returnCnt = 0;
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, movie);
            pstmt.setString(2, nickname);
            pstmt.setFloat(3, rating);
            pstmt.setString(4, detail);
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
            try {
                conn.rollback();
            } catch (SQLException se) {
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

    // 사용자의 nickname을 받아 해당 사용자가 작성한 모든 리뷰 객체를 반환하는 메소드
    public Reviews reviewDetail(String nickname) {
        sql = "SELECT * FROM DB2021_Review WHERE nickname = ? ORDER BY create_time DESC limit 1";

        conn = DB2021Team03_DBConnection.getConnection();
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

    // Review ID를 해당 리뷰 객체를 반환해주는 메소드
    public Reviews reviewDetail(int reviewId) {
        sql = "SELECT * FROM DB2021_Review WHERE id = ?";

        conn = DB2021Team03_DBConnection.getConnection();

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
    public int update(int reviewId, String movie, float rating, String detail) {
        //사용자가 업데이트한 review의 평점과 내용을 업데이트하는 쿼리문.
        sql = "UPDATE DB2021_Review SET RATING = ?, DETAIL = ?, CREATE_TIME = now() WHERE ID = ?";

        //업데이트 영화리뷰에 대한 평균 평점을 다시 계산하여 select하는 쿼리문.
        sql2 = "SELECT AVG(rating) as average FROM DB2021_Review WHERE movie = ?";

        //DB2021_Movie 테이블에서 사용자가 업데이트한 리뷰의 영화에 대한 rating 값을 update하는 쿼리문.
        sql3 = "UPDATE DB2021_Movie set rating = ? WHERE title = ?";

        conn = DB2021Team03_DBConnection.getConnection();

        try {
            //Transaction으로 review 업데이트, 평균 평점 다시 계산, 평점 업데이트를 구현함.
            conn.setAutoCommit(false);

            // 리뷰 update
            int returnCnt = 0;

            pstmt = conn.prepareStatement(sql);
            pstmt.setFloat(1, rating);
            pstmt.setString(2, detail);
            pstmt.setInt(3, reviewId);

            returnCnt = pstmt.executeUpdate();

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
            try {
                conn.rollback();
            } catch (SQLException se) {
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


    //delete
    public int delete(int reviewId, String movie) {
        //DB2021_Review 테이블에 선택된 리뷰를 delete하 쿼리문.
        sql = "DELETE FROM DB2021_Review WHERE ID = ?";

        //삭제된 영화리뷰에 대한 평균 평점을 다시 계산하여 select하는 쿼리문.
        sql2 = "SELECT AVG(rating) as average FROM DB2021_Review WHERE movie = ?";

        //DB2021_Movie 테이블에서 사용자가 삭제한 리뷰의 영화에 대한 rating 값을 update하는 쿼리문.
        sql3 = "UPDATE DB2021_Movie set rating = ? WHERE title = ?";

        conn = DB2021Team03_DBConnection.getConnection();
        try {
            //Transaction으로 review 삭제, 평균 평점 다시 계산, 평점 업데이트를 구현함.
            conn.setAutoCommit(false);

            // 리뷰 delete
            int returnCnt = 0;

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reviewId);
            returnCnt = pstmt.executeUpdate();

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
            try {
                conn.rollback();
            } catch (SQLException se) {
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
}
