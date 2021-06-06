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

public class ActorDao {

    private ActorDao() {}

    private static ActorDao instance = new ActorDao();

    public static ActorDao getInstance() {
        return instance;
    }

    private static Connection conn;
    private static PreparedStatement pstmt;
    private static ResultSet rs;

    public Actors selectOne(int id) {
        String sql = "SELECT * FROM DB2021_Actor WHERE ID = ?";
        conn = DBConnection.getConnection();

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            Actors actor = new Actors();
            if (rs.next()) {
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

    public String selectAll(int movieId) {
        String movies = "";
//        String sql = "SELECT actor FROM DB2021_Actor_Movie Where movie in (SELECT title FROM DB2021_Movie where ID = ? and title = ?)";
        String sql = "SELECT actor FROM DB2021_Actor_Movie as am left outer join DB2021_Movie as m on am.movie = m.title where m.ID = ?";
        conn = DBConnection.getConnection();

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, movieId);
//            pstmt.setString(2, title);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String actor_name = rs.getString("actor");
                movies += actor_name +" ";
            }

            conn.close();

            return movies;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Vector<String> selectMovies(String name) {
        Vector<String> movies = new Vector<String>();
        String sql = "SELECT title, opening_date FROM DB2021_Actor_Movie as am left outer join DB2021_Movie as m on am.movie = m.title WHERE am.actor = ?";
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
        conn = DBConnection.getConnection();

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

    public Map<String, Vector<String>> selectPrizes(int directorId) {
        Map<String, Vector<String>> MP = new HashMap<String, Vector<String>>();

        String sql = "SELECT prize, movie FROM DB2021_Actor_Prize WHERE actor = (SELECT name FROM DB2021_Actor WHERE ID = ?) ORDER BY movie";
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
        conn = DBConnection.getConnection();

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
