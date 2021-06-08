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


// Actor 테이블 관련 SQL처리를 위한 Class
public class DB2021Team03_ActorDao {
    
    private DB2021Team03_ActorDao() {}

    private static DB2021Team03_ActorDao instance = new DB2021Team03_ActorDao();

    public static DB2021Team03_ActorDao getInstance() {
        return instance;
    }

    private static Connection conn;
    private static PreparedStatement pstmt;
    private static ResultSet rs;

    // Actor의 name을 받아서 ID를 알려주는 메소드
    public int selectId(String name) {
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

    // Actor의 ID를 받아서 해당 Actor의 객체를 반환해주는 메소드
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

    // movie ID를 받아서 해당 movie에 출연한 모든 Actor 객체를 반환해주는 메소드
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


    // Actor의 ID를 받아서 해당 Actor가 출연한 모든 Movie 객체를 반환해주는 메소드
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

    // Actor의 ID를 받아서 해당 Actor가 어떤 영화로 어떠한 상들을 받았는지 그 쌍을 반환해주는 메소드
    public Map<String, Vector<String>> selectPrizes(int directorId) {
        Map<String, Vector<String>> MP = new HashMap<String, Vector<String>>();

        String sql = "SELECT prize, movie FROM DB2021_Actor_Prize WHERE actor = (SELECT name FROM DB2021_Actor WHERE ID = ?) ORDER BY movie";
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
