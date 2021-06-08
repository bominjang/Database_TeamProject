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

import dao.DB2021Team03_DBConnection;
import dao.DB2021Team03_MovieDao;
import models.Movies;

/**
 * 영화의 순위를 확인할 수 있는 클래스
 */
@SuppressWarnings("serial")
public class DB2021Team03_Ranking extends DB2021Team03_CustomUI {

    private JFrame frame = new JFrame();
    private JPanel backgroundPanel;
    private JLabel lbBox[], lbMovieName[], lbRating[], lbTitle, lbLine;
    private JButton btnBack;
    private static Connection conn;
    private int addnum;
    private int movieId;

    private String nickname;
    private DB2021Team03_MovieDao mDao;
    private Vector<Movies> rMovies;

    /**
     * TOP10 영화 목록 화면 생성자
     *
     * @param nickname 로그인 유지를 위한 사용자 nickname
     */
    public DB2021Team03_Ranking(String nickname) {
        this.nickname = nickname;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //MovieDao 객체를 이용해 영화 순위를 담은 객체 가져옴.
        mDao = DB2021Team03_MovieDao.getInstance();
        rMovies = mDao.selectRanking();

        lbBox = new JLabel[rMovies.size()];
        lbMovieName = new JLabel[rMovies.size()];
        lbRating = new JLabel[rMovies.size()];

        init();

        //이전화면으로
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new DB2021Team03_Main(nickname);
                frame.dispose();
            }
        });

        frame.setSize(426, 779);
        frame.setResizable(false);
        frame.setVisible(true);
    }


    /**
     * 순위 화면 구성을 위한 GUI
     */
    private void init() {
        conn = DB2021Team03_DBConnection.getConnection();
        try {
            backgroundPanel = new JPanel();
            frame.setContentPane(backgroundPanel);
            frame.setTitle("DB2021Team03-영화 정보 프로그램");

            DB2021Team03_CustomUI custom = new DB2021Team03_CustomUI(backgroundPanel);
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

                //영화 이름을 클릭하면, 영화 상세화면로 이동하도록 함.
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
                        new DB2021Team03_Movie(nickname, movieId);
                        frame.dispose();

                    }
                });
            }
            panel.setPreferredSize(new Dimension(400, 20 + 55 * addnum));

            // 스크롤 추가
            JScrollPane sp = new JScrollPane();
            sp.setViewportView(panel);
            sp.setBounds(0, 120, 422, 500);
            backgroundPanel.add(sp);

            lbTitle = custom.setLb("lbTitle", "영화 순위", 100, 85, 220, 185, "center", 20, "bold");
            btnBack = custom.setBtnWhite("btnBack", "메인으로", 35, 650);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}