package dao;

import models.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;


public class DB2021Team03_UserDao {
    private DB2021Team03_UserDao() {
    }

    // Dao: [자신의타입] [변수명]
    private static DB2021Team03_UserDao instance = new DB2021Team03_UserDao();

    public static DB2021Team03_UserDao getInstance(){
        return instance;
    }

    private static Connection conn;
    private static PreparedStatement pstmt;
    private static ResultSet rs;
    
    // 유저 1명 정보 가져오기
    public Users selectOne(String nickname){
        String sql = "SELECT * FROM DB2021_User WHERE nickname = ";

        conn = DB2021Team03_DBConnection.getConnection();

        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nickname);
            rs = pstmt.executeQuery();

            Users user = new Users();

            if(rs.next()) {
                user.setId(rs.getInt("ID"));
                user.setNickname(rs.getString("nickname"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setBirth(rs.getDate("birth"));
                user.setPhone(rs.getString("phone"));
                user.setJoin_time(rs.getString("join_time"));
                user.setPrivacyFg(rs.getString("privacy_fg"));
                user.setAdminFg(rs.getString("admin_fg"));
                user.setDelFg(rs.getString("delete_fg"));
                user.setDelete_time(rs.getString("delete_time"));

                conn.close();
                return user;
            } else{
                conn.close();
                return null;
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }
    
    // 유저 여러명 정보 가져오기
    public Vector<Users> selectAll(String keyword) {
        Vector<Users> users = new Vector<>();
        String sql = "SELECT * FROM DB2021_User WHERE nickname LIKE '%" + keyword + "%'";

        conn = DB2021Team03_DBConnection.getConnection();

        try{
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while(rs.next()) {
                Users user = new Users();
                user.setId(rs.getInt("ID"));
                user.setNickname(rs.getString("nickname"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setBirth(rs.getDate("birth"));
                user.setPhone(rs.getString("phone"));
                user.setJoin_time(rs.getString("join_time"));
                user.setPrivacyFg(rs.getString("privacy_fg"));
                user.setDelFg(rs.getString("delete_fg"));
                user.setDelete_time(rs.getString("delete_time"));
                users.add(user);
            }
            conn.close();
            return users;
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    // 비밀번호 초기화
    public int updatePassword(String nickname) {
        // 핸드폰 번호로 비밀번호 초기화
        String sql = "UPDATE DB2021_User SET PASSWORD = PHONE WHERE nickname = ?";

        conn = DB2021Team03_DBConnection.getConnection();

        try {
            int returnCnt = 0; // 실행한 row 개수
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nickname);
            returnCnt = pstmt.executeUpdate();

            conn.close();
            return returnCnt;

        } catch(Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    // 유저 탈퇴 정보 update
    public int updateDel(String nickname) {
        String sql = "UPDATE DB2021_User SET delete_fg = 'Y', delete_time = NOW() WHERE nickname = ?";

        conn = DB2021Team03_DBConnection.getConnection();

        try {
            int returnCnt = 0; // 실행한 row 개수
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nickname);

            returnCnt = pstmt.executeUpdate();

            conn.close();
            return returnCnt;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public Users selectBirth(String nickname) {
        String sql = "SELECT birth FROM DB2021_User WHERE nickname = ?";

        conn = DB2021Team03_DBConnection.getConnection();

        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nickname);
            rs = pstmt.executeQuery();

            Users user = new Users();
            if(rs.next()) {
                user.setBirth(rs.getDate("birth"));

                conn.close();
                return user;
            } else {
                conn.close();
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
