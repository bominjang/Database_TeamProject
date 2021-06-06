package models;

import java.sql.Date;

public class Directors {
    private int id;
    private String name;
    private String country;
    private Date birth;

    public Directors() {}

    public Directors(int id, String name, String country, Date birth) {
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
