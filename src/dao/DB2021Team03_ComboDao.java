package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import models.Combo;

/**
 * 콤보박스 구현을 위한 Class
 */
public class DB2021Team03_ComboDao {

    /**
     * ComboDao 객체 생성자
     */
    private DB2021Team03_ComboDao(){}

    private static DB2021Team03_ComboDao instance = new DB2021Team03_ComboDao();

    /**
     * ComboDao 객체를 생성해주는 함수
     * @return DB2021_Team03_ComboDao 객체
     */
    public static DB2021Team03_ComboDao getInstance() {
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
     * Review 작성 시 어떤 영화에 대한 리뷰를 작성할건지 선택하는 콤보박스에 쓰이는 메소드
     *
     * @param comboContent "movie" 고정
     * @return Movie 테이블에 있는 모든 영화 title로 만든 Combo Vector를 반환한다. 그 외엔 null을 반환합니다.
     */
    public Vector<Combo> setCombo(String comboContent) {
        Vector<Combo> combos = new Vector<>();
        String sql;

        if(comboContent.equals("movie")) {
            sql = "SELECT ID AS 'KEY', TITLE AS 'VALUE' FROM DB2021_MOVIE";
        } else {
            sql = "";
        }

        conn = DB2021Team03_DBConnection.getConnection();
        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while(rs.next()){
                Combo combo = new Combo();
                combo.setKey(rs.getInt("KEY"));
                combo.setValue(rs.getString("VALUE"));
                combos.add(combo);
            }

            if(rs.getRow() != 0) {
                conn.close();
                return null;
            } else {
                conn.close();
                return combos;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}