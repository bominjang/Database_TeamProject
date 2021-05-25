package models;

import java.sql.Date;

public class Users {

    private int id;
    private String nickname;
    private String password;
    private String name;
    private Date birth;
    private String phone;
    private String join_time;
    private String privacyFg;
    private String adminFg;
    private String delFg;
    private String delete_time;

    public Users() {}

    public Users(int id, String nickname, String password, String name, Date birth, String phone,
                 String join_time, String privacyFg, String adminFg, String delFg, String delete_time) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.phone = phone;
        this.join_time = join_time;
        this.privacyFg = privacyFg;
        this.adminFg = adminFg;
        this.delFg = delFg;
        this.delete_time = delete_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getJoin_time() {
        return join_time;
    }

    public void setJoin_time(String join_time) {
        this.join_time = join_time;
    }

    public String getPrivacyFg() {
        return privacyFg;
    }

    public void setPrivacyFg(String privacyFg) {
        this.privacyFg = privacyFg;
    }

    public String getAdminFg() {
        return adminFg;
    }

    public void setAdminFg(String adminFg) {
        this.adminFg = adminFg;
    }

    public String getDelFg() {
        return delFg;
    }

    public void setDelFg(String delFg) {
        this.delFg = delFg;
    }

    public String getDelete_time() {
        return delete_time;
    }

    public void setDelete_time(String delete_time) {
        this.delete_time = delete_time;
    }
}
