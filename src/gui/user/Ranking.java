package gui.user;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import dao.DBConnection;
import dao.MovieDao;
import models.Movies;

@SuppressWarnings("serial")
public class Ranking extends CustomUI {

    private JFrame frame = new JFrame();
    private JPanel backgroundPanel;
    private JLabel lbBox[], lbMovieName[], lbRating[], lbTitle, lbLine;
    private JButton btnBack;
    private static Connection conn;
    private int addnum;
    private int movieId;

    private String nickname;
    private MovieDao mDao;
    private Vector<Movies> rMovies;

    public Ranking(String nickname) {
        this.nickname = nickname;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mDao = MovieDao.getInstance();
        rMovies = mDao.selectRanking();

        lbBox = new JLabel[rMovies.size()];
        lbMovieName = new JLabel[rMovies.size()];
        lbRating = new JLabel[rMovies.size()];

        init();

        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Ranking(nickname);
                frame.dispose();
            }
        });

        frame.setSize(426, 779);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void init() {
        conn = DBConnection.getConnection();
        try {
            backgroundPanel = new JPanel();
            frame.setContentPane(backgroundPanel);
            frame.setTitle("영화 예매 프로그램 ver1.0");

            CustomUI custom = new CustomUI(backgroundPanel);
            custom.setPanel();


            JPanel panel = new JPanel();
            panel.setLayout(null);
            panel.setBackground(Color.WHITE);

            for (int j = 0; j < rMovies.size(); j++) {
                int moveY = 55;
                addnum++;
                lbBox[j] = custom.setLbBox("lbBox" + j, j + 1 + "", 35, 20 + (moveY * j), panel);
                lbMovieName[j] = custom.setLb("lbMovieName" + j, rMovies.get(j).getTitle(), 75, 22 + (moveY * j), 300, 20, "left", 14, "plain", panel);
                lbRating[j] = custom.setLb("lbTime" + j, "평점: " + rMovies.get(j).getRating() + "", 80, 22 + (moveY * j), 300, 20, "right", 13, "plain", panel);

                panel.add(lbBox[j]);
                panel.add(lbMovieName[j]);
                panel.add(lbRating[j]);

                lbMovieName[j].addMouseListener(new MouseListener() {
                    public void mouseReleased(MouseEvent e) {
                    }

                    public void mousePressed(MouseEvent e) {
                    }

                    public void mouseExited(MouseEvent e) {
                    }

                    public void mouseEntered(MouseEvent e) {
                    }

                    public void mouseClicked(MouseEvent e) {
                        JLabel label = (JLabel) e.getSource();
                        for (Movies rMovie : rMovies) {
                            if (label.getText().equals(rMovie.getTitle())) {
                                movieId = rMovie.getId();
                            }
                        }
                        new Movie(nickname, movieId);
                        frame.dispose();

                    }
                });
            }
            panel.setPreferredSize(new Dimension(400, 20 + 55 * addnum));

            JScrollPane sp = new JScrollPane();
            sp.setViewportView(panel);
            sp.setBounds(0, 120, 422, 500);
            backgroundPanel.add(sp);

            lbTitle = custom.setLb("lbTitle", "영화 순위", 100, 85, 220, 185, "center", 20, "bold");
            btnBack = custom.setBtnWhite("btnBack", "이전으로", 35, 650);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}