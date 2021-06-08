package gui.user;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.*;

import dao.DB2021Team03_DBConnection;
import models.Reviews;

/**
 * 특정 영화에 등록된 리뷰들을 볼 수 있는 클래스
 */
@SuppressWarnings("serial")
public class DB2021Team03_MovieReviews extends DB2021Team03_CustomUI {

    private JFrame frame = new JFrame();
    private JPanel backgroundPanel;
    private JLabel lbTitle,lbNickName[], lbCreateTime[],lbRating[], lbReview[];
    private JButton btnBack;

    //movie의 review들을 모두 선택.
    private final String SQL = "SELECT * FROM DB2021_Review WHERE Movie = (SELECT title FROM DB2021_MOVIE AS M WHERE M.ID = ?)";
    private ArrayList<Reviews> rvs = new ArrayList<Reviews>();

    private String nickname;
    private int movieId;

    /**
     * 해당 영화의 리뷰 목록 화면 생성자
     *
     * @param nickname 로그인 유지를 위한 사용자 nickname
     * @param MovieId 영화 pk
     */
    public DB2021Team03_MovieReviews(String nickname, int MovieId) {
        this.nickname = nickname;
        this.movieId = MovieId;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();

        //이전으로 이동
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
     * 해당 영화의 리뷰 화면 구성을 위한 GUI 및 데이터 셋팅
     */
    private void init() {
        backgroundPanel = new JPanel();
        frame.setContentPane(backgroundPanel);
        frame.setTitle("DB2021Team03-영화 정보 프로그램");

        DB2021Team03_CustomUI custom = new DB2021Team03_CustomUI(backgroundPanel);
        custom.setPanel();

        Connection conn;
        PreparedStatement pstmt;
        ResultSet rs;

        conn = DB2021Team03_DBConnection.getConnection();
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, movieId);
            rs = pstmt.executeQuery();

            //리뷰들을 저장함
            while (rs.next()) {
                Reviews rv = new Reviews();
                rv.setNickname(rs.getString("NICKNAME"));
                rv.setCreate_time(rs.getString("CREATE_TIME"));
                rv.setRating(rs.getFloat("RATING"));
                rv.setDetail(rs.getString("DETAIL"));
                rvs.add(rv);
            }

            conn.close();

            int i = 0;
            lbNickName = new JLabel[rvs.size()];
            lbCreateTime = new JLabel[rvs.size()];
            lbRating = new JLabel[rvs.size()];
            lbReview = new JLabel[rvs.size()];

            JPanel panel = new JPanel();
            panel.setLayout(null);
            panel.setBackground(Color.WHITE);

            //리뷰 출력
            for (Reviews r : rvs) {
                int moveY = 50 * i;
                i++;
                lbNickName[i-1] = custom.setLb("lbNickName", r.getNickname(), 20, 20 + moveY, 400, 20, "left", 15, "plain", panel);
                lbCreateTime[i-1]= custom.setLb("lbCreateTime", r.getCreate_time(), 20, 20 + moveY, 400, 20, "right", 15, "plain", panel);
                lbRating[i-1]= custom.setLb("lbTitleRating", Float.toString(r.getRating()), 20, 20 + moveY, 400, 20, "center", 15, "plain", panel);

                lbReview[i-1]= custom.setLb("lbReview", r.getDetail(), 20, 30 + moveY, 300, 40, "left", 15, "plain", panel);

                panel.add(lbNickName[i-1]);
                panel.add(lbCreateTime[i-1]);
                panel.add(lbRating[i-1]);
                panel.add(lbReview[i-1]);
            }
            panel.setPreferredSize(new Dimension(450, 20+ 100*i));

            JScrollPane sp = new JScrollPane();
            sp.setViewportView(panel);
            sp.setBounds(0, 200, 422, 400);
            backgroundPanel.add(sp);

        } catch (Exception e) {
            e.printStackTrace();
        }

        lbTitle = custom.setLb("lbTitle", "리뷰", 100, 85, 220, 185, "center", 20, "bold");
        btnBack = custom.setBtnWhite("btnBack", "이전으로", 35, 655);
    }

}