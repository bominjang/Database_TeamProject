package dao;

import models.Actors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
//                actor.setTitle(rs.getString("title"));
//                actor.setGenre(rs.getString("genre"));
//                actor.setCountry(rs.getString("country"));
//                actor.setRunningTime(rs.getInt("running_time"));
//                actor.setOpening_date(rs.getDate("opening_date"));
//                actor.setDirector(rs.getString("director"));
//                actor.setPlot(rs.getString("plot"));
//                actor.setRating(rs.getFloat("rating"));
//                actor.setAge(rs.getInt("age"));

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
}
