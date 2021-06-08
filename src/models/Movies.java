package models;

import java.sql.Date;

/**
 * Movie(영화) 모델 클래스
 */
public class Movies {
    /**
     * DB2021_Movie 테이블의 pk인 ID
     *
     * @see #getId()
     * @see #setId(int)
     */
    private int id;
    /**
     * 영화 제목
     *
     * @see #getTitle()
     * @see #setTitle(String)
     */
    private String title;
    /**
     * 영화 장르
     *
     * @see #getGenre()
     * @see #setGenre(String)
     */
    private String genre;
    /**
     * 어느 나라 영화인지
     *
     * @see #getCountry() 
     * @see #setCountry(String) 
     */
    private String country;
    /**
     * 상영 시간
     *
     * @see #getRunningTime() 
     * @see #setRunningTime(int)  
     */
    private int runningTime;
    /**
     * 개봉일
     *
     * @see #getOpening_date() 
     * @see #setOpening_date(Date) 
     */
    private Date opening_date;
    /**
     * 영화를 제작한 감독
     *
     * @see #getDirector() 
     * @see #setDirector(String) 
     */
    private String director;
    /**
     * 영화 줄거리
     *
     * @see #getPlot() 
     * @see #setPlot(String) 
     */
    private String plot;
    /**
     * 평점(사용자 리뷰에서 평균을 낸 점수)
     *
     * @see #getRating() 
     * @see #setRating(float)  
     */
    private float rating;
    /**
     * 관람 가능 연령(age 이상만 연령 가능)
     *
     * @see #getAge() 
     * @see #setAge(int)
     */
    private int age;

    public Movies() {}

    /**
     * Directors 객체 생성자
     *
     * @param id ID
     * @param title 영화 제목
     * @param genre 영화 장르
     * @param country 어느나라 영화인지
     * @param runningTime 상영 시간
     * @param opening_date 개봉일
     * @param director 영화 감독
     * @param plot 영화 감독
     * @param rating 영화 평점
     * @param age 관람 가능 연령;
     */
    public Movies(int id, String title, String genre, String country, int runningTime,
                  Date opening_date, String director, String plot, float rating, int age) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.country = country;
        this.runningTime = runningTime;
        this.opening_date = opening_date;
        this.director = director;
        this.plot = plot;
        this.rating = rating;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(int runningTime) {
        this.runningTime = runningTime;
    }

    public Date getOpening_date() {
        return opening_date;
    }

    public void setOpening_date(Date opening_date) {
        this.opening_date = opening_date;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}