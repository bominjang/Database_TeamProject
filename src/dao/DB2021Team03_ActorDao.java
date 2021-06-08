package dao;

import models.Actors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;


/**
 * Actor 테이블 관련 SQL처리를 위한 Class
 */
public class DB2021Team03_ActorDao {

    /**
     * ActorDao 객체 생성자
     */
    private DB2021Team03_ActorDao() {}


    private static DB2021Team03_ActorDao instance = new DB2021Team03_ActorDao();

    /**
     * ActorDao 객체를 생성해주는 함수
     * @return DB2021Team03_ActorDao 객체
     */
    public static DB2021Team03_ActorDao getInstance() {
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
     * Actor의 name을 받아서 ID를 알려주는 메소드
     *
     * @param name 어떤 배우의 ID를 찾을건지
     * @return ID를 성공적으로 찾을 시 해당 ID, 그 외엔 -1을 반환합니다.
     */
    public int selectId(String name) {
        // Actor의 name을 받아서 ID를 알려주는 메소드
        String sql = "SELECT id FROM DB2021_Actor WHERE NAME = ?";
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
     * Actor의 ID를 받아서 해당 Actor의 객체를 반환해주는 메소드
     *
     * @param id 배우 객체를 찾기 위해 필요한 pk
     * @return ID를 성공적으로 찾을 시 해당 Actor 객체 반환, 그 외엔 null을 반환합니다.
     */
    public Actors selectOne(int id) {
        String sql = "SELECT * FROM DB2021_Actor WHERE ID = ?";
        conn = DB2021Team03_DBConnection.getConnection();

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            Actors actor = new Actors();
            if (rs.next()) {
                actor.setId(rs.getInt("ID"));
                actor.setName(rs.getString("name"));
                actor.setBirth(rs.getDate("birth"));
                actor.setCountry(rs.getString("country"));

                conn.close();
                return actor;
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
     * movie ID를 받아서 해당 movie에 출연한 모든 Actor 객체를 반환해주는 메소드
     *
     * @param movieId 찾고자하는 영화의 pk
     * @return 해당 영화에 출연하는 배우들을 성공적으로 찾을 시 Actors Vector 반환, 그 외엔 null을 반환합니다.
     */
    public Vector<Actors> selectAll(int movieId) {
        Vector<Actors> actors = new Vector<Actors>();

        //Movie.java에서 사용하는 쿼리문이다. 해당 영화에 출연한 배우를 가져오기 위해 left outer join을 사용하였음.
        String sql = "SELECT * FROM DB2021_Actor WHERE name in";
        sql += " (SELECT actor FROM DB2021_Actor_Movie as am left outer join DB2021_Movie as m on am.movie = m.title where m.ID = ?)";
        conn = DB2021Team03_DBConnection.getConnection();

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, movieId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                //출연한 배우들을 actors 변수에 담음.
                Actors actor = new Actors();
                actor.setId(rs.getInt("id"));
                actor.setName(rs.getString("name"));
                actor.setCountry(rs.getString("country"));
                actor.setBirth(rs.getDate("birth"));
                actors.add(actor);
            }

            conn.close();

            return actors;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Actor의 Name을 받아서 해당 Actor가 출연한 모든 Movie 객체를 반환해주는 메소드
     *
     * @param name 배우의 이름
     * @return 해당 배우가 출연한 모든 영화들을 성공적으로 찾을 시 String Vector반환, 그 외엔 null을 반환합니다.
     */
    public Vector<String> selectMovies(String name) {
        Vector<String> movies = new Vector<String>();
        String sql = "SELECT title, opening_date FROM DB2021_Actor_Movie as am left outer join DB2021_Movie as m on am.movie = m.title WHERE am.actor = ?";
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
        conn = DB2021Team03_DBConnection.getConnection();

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String title = rs.getString("title");
                Date date = rs.getDate("opening_date");
                String birth = transFormat.format(date).split("-")[0];
                movies.add(String.format("%s(%s)", title, birth));
            }

            conn.close();

            return movies;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     *  Actor의 ID를 받아서 해당 Actor가 어떤 영화로 어떠한 상들을 받았는지 그 쌍을 반환해주는 메소드
     *
     * @param actorId 배우의 pk
     * @return 해당 배우가 어떤 영화에 출연해서 상을 받았는지 모든 쌍을 성공적으로 찾을 시 Map(String, String Vector) 반환, 그 외엔 null을 반환합니다.
     */
    public Map<String, Vector<String>> selectPrizes(int actorId) {
        Map<String, Vector<String>> MP = new HashMap<String, Vector<String>>();

        String sql = "SELECT prize, movie FROM DB2021_Actor_Prize WHERE actor = (SELECT name FROM DB2021_Actor WHERE ID = ?) ORDER BY movie";
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
        conn = DB2021Team03_DBConnection.getConnection();

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, actorId);
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
