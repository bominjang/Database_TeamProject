package dao;

import models.Reviews;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Review 테이블 관련 SQL처리를 위한 Class
 */
public class DB2021Team03_ReviewDao {

    /**
     * ReviewDao 객체 생성자
     */
    private DB2021Team03_ReviewDao() { }

    private static DB2021Team03_ReviewDao instance = new DB2021Team03_ReviewDao();

    /**
     * ReviewDao 객체를 생성해주는 함수
     * @return DB2021Team03_ReviewDao 객체
     */
    public static DB2021Team03_ReviewDao getInstance() {
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
     * Query 필드
     */
    private String sql, sql2, sql3;


    /**
     * 사용자가 리뷰를 작성하면 Review table에 리뷰를 insert하는 메소드
     *
     * @param movie 영화
     * @param nickname 후기를 작성한 유저의 닉네임
     * @param rating 유저가 매긴 별점
     * @param detail 유저가 쓴 후기
     * @return 성공시 DB2021_Review 테이블에 해당 리뷰 insert, 그 외엔 null을 반환합니다.
     */
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

            conn.close();
            return returnCnt;

        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
                conn.setAutoCommit(true);
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


    /**
     * 사용자의 nickname을 받아 해당 사용자가 작성한 최근 리뷰 객체를 반환하는 메소드
     *
     * @param nickname 사용자 닉네임
     * @return 해당 사용자가 작성한 최근 리뷰 객체 Reviews 반환, 그 외엔 null을 반환합니다.
     */
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

    /**
     * Review ID에 해당하는 리뷰 객체를 반환해주는 메소드
     *
     * @param reviewId 리뷰 pk
     * @return 성공적으로 찾을 시 해당 리뷰 객체 Reviews 반환, 그 외엔 null을 반환합니다.
     */
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


    /**
     * 사용자의 Review를 수정할 수 있는 메소드
     *
     * @param reviewId 리뷰 pk
     * @param movie 영화
     * @param rating 수정한 별점
     * @param detail 수정한 후기
     * @return 성공 시 수정한 리뷰 객체 Reviews 반환, 그 외엔 null을 반환합니다.
     */
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

            conn.close();
            return returnCnt;

        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
                conn.setAutoCommit(true);
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


    /**
     * 사용자의 Review를 삭제할 수 있는 메소드
     *
     * @param reviewId 리뷰 pk
     * @param movie 영화
     * @return 해당 리뷰 객체 삭제 후 성공 시 1 반환, 그 외엔 -1을 반환합니다.
     */
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

            conn.close();
            return returnCnt;

        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
                conn.setAutoCommit(true);
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
