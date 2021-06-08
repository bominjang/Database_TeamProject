package models;

import java.sql.Date;

/**
 * User(유저, 사용자) 모델 클래스
 *
 * @author 김서영
 *
 */
public class Users {

    /**
     * DB2021_User 테이블의 pk인 ID
     *
     * @see #getId()
     * @see #setId(int)
     */
    private int id;

    /**
     * 사용자가 쓸 계정 닉네임
     *
     * @see #getNickname() 
     * @see #setNickname(String) 
     */
    private String nickname;

    /**
     * 계정 비밀번호
     *
     * @see #getPassword() 
     * @see #setPassword(String) 
     */
    private String password;

    /**
     * 사용자 이름
     *
     * @see #getName() 
     * @see #setName(String)
     */
    private String name;

    /**
     * 사용자의 생년월일
     *
     * @see #getBirth() 
     * @see #setBirth(Date) 
     */
    private Date birth;

    /**
     * 사용자의 핸드폰 번호
     *
     * @see #getPhone() 
     * @see #setPhone(String) 
     */
    private String phone;

    /**
     * 사용자가 가입한 날짜, 시간
     *
     * @see #getJoin_time() 
     * @see #setJoin_time(String) 
     */
    private String join_time;

    /**
     * 개인정보제공 동의 여부
     *
     * @see #getPrivacyFg() 
     * @see #setPrivacyFg(String) 
     */
    private String privacyFg;

    /**
     * 관리자 여부
     *
     * @see #getAdminFg() 
     * @see #setAdminFg(String) 
     */
    private String adminFg;

    /**
     * 탈퇴한 사용자 여부
     *
     * @see #getDelFg() 
     * @see #setDelFg(String) 
     */
    private String delFg;

    /**
     * 탈퇴한 시간
     *
     * @see #getDelete_time() 
     * @see #setDelete_time(String) 
     */
    private String delete_time;

    public Users() {}

    /**
     * Users 객체 생성자
     *
     * @param id ID
     * @param nickname 닉네임
     * @param password 비밀번호
     * @param name 이름
     * @param birth 생년월일
     * @param phone 핸드폰 번호
     * @param join_time 가입한 날짜
     * @param privacyFg 개인정보제공 동의
     * @param adminFg 관리자 여부
     * @param delFg 탈퇴 여부
     * @param delete_time 탈퇴한 날짜
     */
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
