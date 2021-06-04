package models;

import java.sql.Date;

public class Movies {
    private int id;
    private String title;
    private String genre;
    private String country;
    private int runningTime;
    private Date opening_date;
    private String director;
    private String plot;
    private float rating;
    private int age;

    public Movies() {}

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