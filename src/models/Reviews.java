package models;

import java.sql.Date;

public class Reviews {
    private int id;
    private String movie;
    private String nickname;
    private Date create_time;
    private float rating;
    private String detail;

    public Reviews() {}

    public Reviews(int id, String movie, String nickname, Date create_time,
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

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
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
