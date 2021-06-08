package models;

import java.sql.Date;

/**
 * Actor(배우) 모델 클래스
 *
 * @author 김서영
 *
 */
public class Actors {
    /**
     * DB2021_Actor 테이블의 pk인 ID
     *
     * @see #getId()
     * @see #setId(int) 
     */
    private int id;
    /**
     * 배우의 이름
     *
     * @see #getName()
     * @see #setName(String)
     */
    private String name;
    /**
     * 배우의 출생국가
     *
     * @see #getCountry()
     * @see #setCountry(String)
     */
    private String country;
    /**
     * 배우의 생년월일(YYYY-MM-DD)
     *
     * @see #getBirth()
     * @see #setBirth(Date)
     */
    private Date birth;

    public Actors() {}

    /**
     * Actors 객체 생성자
     *
     * @param id ID
     * @param name 이름
     * @param birth 생년월일;
     * @param country 출생국가;
     */
    public Actors(int id, String name, String country, Date birth) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.birth = birth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }
}
