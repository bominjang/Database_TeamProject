package dao;

import models.Directors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Director 테이블 관련 SQL처리를 위한 Class
 */
public class DB2021Team03_DirectorDao {

    /**
     * DirectorDao 객체 생성자
     */
    private DB2021Team03_DirectorDao() { }

    private static DB2021Team03_DirectorDao instance = new DB2021Team03_DirectorDao();

    /**
     * DirectorDao 객체를 생성해주는 함수
     * @return DB2021Team03_DirectorDao 객체
     */
    public static DB2021Team03_DirectorDao getInstance() {
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
     * Director의 name을 받아서 ID를 알려주는 메소드
     *
     * @param name 어떤 감독의 ID를 찾을건지
     * @return ID를 성공적으로 찾을 시 해당 ID, 그 외엔 -1을 반환합니다.
     */
    public int selectId(String name) {
        String sql = "SELECT id FROM DB2021_Director WHERE NAME = ?";
        conn = DB2021Team03_DBConnection.getConnection();
        int id;

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                id = rs.getInt("ID");
                conn.close();
                return id;
            } else {
                conn.close();
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    /**
     * Director의 ID를 받아서 해당 Director 객체를 반환해주는 메소드
     *
     * @param id 감독 객체를 찾기 위해 필요한 pk
     * @return ID를 성공적으로 찾을 시 해당 Director 객체 반환, 그 외엔 null을 반환합니다.
     */
    public Directors selectOne(int id) {
        String sql = "SELECT * FROM DB2021_Director WHERE ID = ?";
        conn = DB2021Team03_DBConnection.getConnection();

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            Directors director = new Directors();
            if (rs.next()) {
                director.setId(rs.getInt("ID"));
                director.setName(rs.getString("name"));
                director.setBirth(rs.getDate("birth"));
                director.setCountry(rs.getString("country"));

                conn.close();
                return director;
            } else {
                conn.close();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    /**
     * Director의 ID를 받아서 해당 Director가 제작한 모든 영화 객체를 반환해주는 메소드
     *
     * @param directorId 찾고자하는 감독의 pk
     * @return 해당 감독이 제작한 영화들을 성공적으로 찾을 시 Vector<String> 반환, 그 외엔 null을 반환합니다.
     */
    public Vector<String> selectMovies(int directorId) {
        //영화들을 저장하는 Vector 선언 + 객체 생성.
        Vector<String> movies = new Vector<String>();
        //directorId값을 갖는 director가 제작한 영화제목과 개봉일자를 불러오는 쿼리문.
        String sql = "SELECT title, opening_date FROM DB2021_Movie Where director = (SELECT name FROM DB2021_Director WHERE ID = ?)";
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
        conn = DB2021Team03_DBConnection.getConnection();

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, directorId);
            rs = pstmt.executeQuery();

            //해당 director가 제작한 영화들의 제목, 개봉일을 movies에 저장한다.
            while (rs.next()) {
                String title = rs.getString("title");
                Date date = rs.getDate("opening_date");
                String birth = transFormat.format(date).split("-")[0];
                movies.add(String.format("%s(%s)", title, birth));
            }

            conn.close();

            //director가 제작한 영화들의 제목, 개봉일이 담긴 정보를 return
            return movies;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    /**
     * Director의 ID를 받아서 해당 Director가 어떤 영화로 어떠한 상들을 받았는지 그 쌍을 반환해주는 메소드
     *
     * @param directorId 감독의 pk
     * @return 해당 감독이 어떤 영화를 제작해서 상을 받았는지 모든 쌍을 성공적으로 찾을 시 ap<String, Vector<String>> 반환, 그 외엔 null을 반환합니다
     */
    public Map<String, Vector<String>> selectPrizes(int directorId) {
        Map<String, Vector<String>> MP = new HashMap<String, Vector<String>>();

        String sql = "SELECT prize, movie FROM DB2021_Director_Prize WHERE director = (SELECT name FROM DB2021_Director WHERE ID = ?) ORDER BY movie";
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
        conn = DB2021Team03_DBConnection.getConnection();

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, directorId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String movie = rs.getString("movie");
                String prize = rs.getString("prize");

                if(MP.containsKey(movie)){
                    Vector<String> prizes = MP.get(movie);
                    prizes.add(prize);
                    MP.replace(movie, prizes);
                } else{
                    Vector<String> prizes = new Vector<>();
                    prizes.add(prize);
                    MP.put(movie, prizes);
                }
            }

            conn.close();

            return MP;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
