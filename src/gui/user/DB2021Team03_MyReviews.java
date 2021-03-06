package gui.user;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.*;

import dao.DB2021Team03_DBConnection;
import dao.DB2021Team03_ReviewDao;
import models.Reviews;

/**
 * 내가 작성한 리뷰들을 확인할 수 있는 클래스
 */
@SuppressWarnings("serial")
public class DB2021Team03_MyReviews extends DB2021Team03_CustomUI {

    private JFrame frame = new JFrame();
    private JPanel backgroundPanel;
    private JLabel lbTitle,lbBox[], lb, lbReview[], lbTitleRating[], lbTitleMovie[], lbCreateTime[];
    private JTextField txtdetail, txtrating;
    private JButton btnReview, btnBack, btnDelete[], btnUpdate[];

    private final String SQL = "SELECT * FROM DB2021_Review WHERE NICKNAME=?";
    private ArrayList<Reviews> rvs = new ArrayList<Reviews>();

    private String nickname;
    private int[] reviewId;

    /**
     * 해당 유저가 쓴 리뷰 목록 화면 생성자
     *
     * @param nickname 로그인 유지를 위한 사용자 nickname
     */
    public DB2021Team03_MyReviews(String nickname) {
        this.nickname = nickname;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        init();
        //메인으로 이동하는 버
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
     * 나의 리뷰 모아보기 화면 구성을 위한 GUI 및 데이터 셋팅
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
            pstmt.setString(1, nickname);
            rs = pstmt.executeQuery();

            // 유저가 쓴 리뷰 저장
            while (rs.next()) {
                Reviews rv = new Reviews();
                rv.setMovie(rs.getString("MOVIE"));
                rv.setCreate_time(rs.getString("CREATE_TIME"));
                rv.setRating(rs.getFloat("RATING"));
                rv.setDetail(rs.getString("DETAIL"));
                rv.setId(rs.getInt("ID"));
                rvs.add(rv);
            }

            conn.close();

            int i = 0;
            lbBox = new JLabel[rvs.size()];
            lbTitleMovie = new JLabel[rvs.size()];
            lbCreateTime = new JLabel[rvs.size()];
            lbTitleRating = new JLabel[rvs.size()];
            lbReview = new JLabel[rvs.size()];
            btnDelete = new JButton[rvs.size()];
            btnUpdate = new JButton[rvs.size()];
            reviewId = new int[rvs.size()];

            JPanel panel = new JPanel();
            panel.setLayout(null);
            panel.setBackground(Color.WHITE);

            //리뷰 출력
            for (Reviews r : rvs) {
                int moveY = 50 * i;
                i++;
                lbBox[i-1] = custom.setLbBox("lbBox"+i, i  + "", 0, 20 + moveY, panel);
                //lb = custom.setLb("lbTitleMovie", r.getMovie(), 20, 20 + moveY, 400, 20, "left", 14, "plain", panel);
                lbTitleMovie[i-1] = custom.setLb("lbTitleMovie"+i, r.getMovie(), 30, 20 + moveY, 80, 20, "left", 14, "plain", panel);
                lbCreateTime[i-1]= custom.setLb("lbCreateTime"+i, r.getCreate_time(), 30, 20 + moveY, 400, 20, "right", 14, "plain", panel);
                lbTitleRating[i-1]= custom.setLb("lbTitleRating"+i, Float.toString(r.getRating()), 30, 20 + moveY, 400, 20, "center", 14, "plain", panel);
                btnDelete[i-1]=custom.setBtnGreen(r.getId()+"", "삭제하기", 450, 20+moveY, "right", 60, 27);
                btnUpdate[i-1] = custom.setBtnGreen(r.getId()+"", "수정하기",510, 20+moveY, "right", 60, 27);

                lbReview[i-1]= custom.setLb("btnReview", r.getDetail(), 30, 35 + moveY, 400, 25,"left", 14, "plain", panel);

                panel.add(lbBox[i-1]);
                panel.add(lbTitleMovie[i-1]);
                panel.add(lbCreateTime[i-1]);
                panel.add(lbTitleRating[i-1]);
                panel.add(btnDelete[i-1]);
                panel.add(btnUpdate[i-1]);
                panel.add(lbReview[i-1]);

                DB2021Team03_ReviewDao reviewDao = DB2021Team03_ReviewDao.getInstance();

                //리뷰를 삭제하는 기능
                btnDelete[i - 1].addMouseListener(new MouseListener() {
                    public void mouseReleased(MouseEvent e) {}
                    public void mousePressed(MouseEvent e) {}
                    public void mouseExited(MouseEvent e) {}
                    public void mouseEntered(MouseEvent e) {}
                    public void mouseClicked(MouseEvent e) {
                        JButton btn = (JButton) e.getSource();
                        int result = 0;
                        int id=-1;
                        String mv = "";

                        for (Reviews rv : rvs) {
                            if (btn.getName().equals(rv.getId() + "")) {
                                System.out.println(rv.getId());
                                id = rv.getId();
                                mv = rv.getMovie();
                            }
                        }

                        result = reviewDao.delete(id, mv);
                        if (result == -1) {
                            JOptionPane.showMessageDialog(frame, "ER21:데이터를 삭제할 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                        } else if (result== 0) {
                            JOptionPane.showMessageDialog(frame, "ER22:데이터를 삭제할 수 없습니다.\n존재하지 않는 데이터일 수 있습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(frame, "데이터 삭제가 완료되었습니다.", "완료", JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                        }
                        new DB2021Team03_MyReviews(nickname);
                        frame.dispose();
                    }
                });

                //리뷰를 수정하는 기능
                btnUpdate[i - 1].addMouseListener(new MouseListener() {
                    public void mouseReleased(MouseEvent e) {}
                    public void mousePressed(MouseEvent e) {}
                    public void mouseExited(MouseEvent e) {}
                    public void mouseEntered(MouseEvent e) {}
                    public void mouseClicked(MouseEvent e) {
                        JButton btn = (JButton) e.getSource();

                        int result = 0;
                        int id=-1;
                        String mv = "";

                        for (Reviews rv : rvs) {
                            if (btn.getName().equals(rv.getId()+"")) {
                                System.out.println(rv.getId());
                                id = rv.getId();
                                mv = rv.getMovie();
                            }
                        }
                        new DB2021Team03_Update(nickname, id, mv);
                        frame.dispose();
                    }
                });
            }
            panel.setPreferredSize(new Dimension(1000, 20+ 100*i));

            JScrollPane sp = new JScrollPane();
            sp.setViewportView(panel);
            sp.setBounds(0, 200, 800, 400);
            backgroundPanel.add(sp);

        } catch (Exception e) {
            e.printStackTrace();
        }

        lbTitle = custom.setLb("lbTitle", "나의 리뷰", 100, 85, 220, 185, "center", 20, "bold");
        btnBack = custom.setBtnWhite("btnBack", "메인으로", 35, 655);

    }

}