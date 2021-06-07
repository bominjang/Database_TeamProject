package dao;

import gui.user.Director;
import models.Actors;
import models.Directors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;

public class DirectorDao {
    private DirectorDao() {
    }

    private static DirectorDao instance = new DirectorDao();

    public static DirectorDao getInstance() {
        return instance;
    }

    private static Connection conn;
    private static PreparedStatement pstmt;
    private static ResultSet rs;

    public int selectId(String name) {
        String sql = "SELECT id FROM DB2021_Director WHERE NAME = ?";
        conn = DBConnection.getConnection();
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

    public Directors selectOne(int id) {
        String sql = "SELECT * FROM DB2021_Director WHERE ID = ?";
        conn = DBConnection.getConnection();

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

    public Vector<String> selectMovies(int directorId) {
        Vector<String> movies = new Vector<String>();
        String sql = "SELECT title, opening_date FROM DB2021_Movie Where director = (SELECT name FROM DB2021_Director WHERE ID = ?)";
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
        conn = DBConnection.getConnection();

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, directorId);
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

        String sql = "SELECT prize, movie FROM DB2021_Director_Prize WHERE director = (SELECT name FROM DB2021_Director WHERE ID = ?) ORDER BY movie";
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
