package dao;

import models.Actors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class ActorDao {
    private ActorDao() {
    }

    private static ActorDao instance = new ActorDao();

    public static ActorDao getInstance() {
        return instance;
    }

    private static Connection conn;
    private static PreparedStatement pstmt;
    private static ResultSet rs;

    public Actors selectOne(int id) {
        String sql = "SELECT * FROM MOVIE WHERE ID = ?";
        conn = DBConnection.getConnection();

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            Actors movie = new Actors();
            if (rs.next()) {
//                movie.setTitle(rs.getString("title"));
//                movie.setGenre(rs.getString("genre"));
//                movie.setCountry(rs.getString("country"));
//                movie.setRunningTime(rs.getInt("running_time"));
//                movie.setOpening_date(rs.getDate("opening_date"));
//                movie.setDirector(rs.getString("director"));
//                movie.setPlot(rs.getString("plot"));
//                movie.setRating(rs.getFloat("rating"));
//                movie.setAge(rs.getInt("age"));

                conn.close();
                return movie;
            } else {
                conn.close();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String selectAll(int movieId, String title) {
        String movies = "";
        String sql = "SELECT actor FROM DB2021_Actor_Movie Where movie in (SELECT title FROM DB2021_Movie where ID = ? and title = ?)";
        conn = DBConnection.getConnection();

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, movieId);
            pstmt.setString(2, title);
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
