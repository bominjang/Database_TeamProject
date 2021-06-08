package models;

import java.sql.Date;

/**
 * Review(리뷰) 모델 클래스
 *
 * @author 김서영
 *
 */
public class Reviews {
    /**
     * DB2021_Review 테이블의 pk인 ID
     *
     * @see #getId()
     * @see #setId(int)
     */
    private int id;
    /**
     * 리뷰할 영화
     *
     * @see #getMovie() 
     * @see #setMovie(String) 
     */
    private String movie;
    /**
     * 리뷰하는 유저의 닉네임
     *
     * @see #getNickname() 
     * @see #setNickname(String)
     */
    private String nickname;
    /**
     * 리뷰작성 연도, 월, 일, 시간
     *
     * @see #getCreate_time() 
     * @see #setCreate_time(String) 
     */
    private String create_time;
    /**
     * 사용자가 매긴 별점
     *
     * @see #getRating()
     * @see #setRating(float)
     */
    private float rating;
    /**
     * 사용자가 작성한 후기
     *
     * @see #getDetail() 
     * @see #setDetail(String)
     */
    private String detail;

    public Reviews() {}

    /**
     * Directors 객체 생성자
     *
     * @param id ID
     * @param movie 리뷰할 영화
     * @param nickname 리뷰한 사용자
     * @param create_time 리뷰 작성 날짜
     * @param detail 리뷰 내용
     * @param rating 사용자가 남긴 영화의 별점
     */
    public Reviews(int id, String movie, String nickname, String create_time,
                   float rating, String detail) {
        this.id = id;
        this.movie = movie;
        this.nickname = nickname;
        this.create_time = create_time;
        this.rating = rating;
        this.detail = detail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
