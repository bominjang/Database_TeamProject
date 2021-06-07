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

import dao.DBConnection;
import dao.ReviewDao;
import models.Reviews;

@SuppressWarnings("serial")
public class MyReviews extends CustomUI {

    private JFrame frame = new JFrame();
    private JPanel backgroundPanel;
    private JLabel lbTitle,lbBox[], lb, lbReview[], lbTitleRating[], lbTitleMovie[], lbCreateTime[];
    private JTextField txtdetail, txtrating;
    private JButton btnReview, btnBack, btnDelete[], btnUpdate[];

    private final String SQL = "SELECT * FROM DB2021_Review WHERE NICKNAME=?";
    private ArrayList<Reviews> rvs = new ArrayList<Reviews>();

    private String nickname;
    private int[] reviewId;

    public MyReviews(String nickname) {
        this.nickname = nickname;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        init();

        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Main(nickname);
                frame.dispose();
            }
        });
        frame.setSize(426, 779);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void init() {
        backgroundPanel = new JPanel();
        frame.setContentPane(backgroundPanel);
        frame.setTitle("DB2021Team03-영화 정보 프로그램");
        CustomUI custom = new CustomUI(backgroundPanel);
        custom.setPanel();

        Connection conn;
        PreparedStatement pstmt;
        ResultSet rs;

        conn = DBConnection.getConnection();
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, nickname);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Reviews rv = new Reviews();
                rv.setMovie(rs.getString("MOVIE")); // 영화제목
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

                ReviewDao reviewDao = ReviewDao.getInstance();

                btnDelete[i - 1].addMouseListener(new MouseListener() {
                    public void mouseReleased(MouseEvent e) {}
                    public void mousePressed(MouseEvent e) {}
                    public void mouseExited(MouseEvent e) {}
                    public void mouseEntered(MouseEvent e) {}
                    public void mouseClicked(MouseEvent e) {
                        JButton btn = (JButton) e.getSource();
                        int result = 0;
                        int id=-1;

                        for (Reviews rv : rvs) {
                            if (btn.getName().equals(rv.getId() + "")) {
                                System.out.println(rv.getId());
                                id = rv.getId();
                            }
                        }


                    
                        result = reviewDao.delete(id);
                        if (result == -1) {
                            JOptionPane.showMessageDialog(frame, "ER21:데이터를 삭제할 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                        } else if (result== 0) {
                            JOptionPane.showMessageDialog(frame, "ER22:데이터를 삭제할 수 없습니다.\n존재하지 않는 데이터일 수 있습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(frame, "데이터 삭제가 완료되었습니다.", "완료", JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                        }
                        new MyReviews(nickname);
                        frame.dispose();
                    }
                });

                btnUpdate[i - 1].addMouseListener(new MouseListener() {
                    public void mouseReleased(MouseEvent e) {}
                    public void mousePressed(MouseEvent e) {}
                    public void mouseExited(MouseEvent e) {}
                    public void mouseEntered(MouseEvent e) {}
                    public void mouseClicked(MouseEvent e) {
                        JButton btn = (JButton) e.getSource();

                        int result = 0;
                        int id=-1;

                        for (Reviews rv : rvs) {
                            if (btn.getName().equals(rv.getId()+"")) {
                                System.out.println(rv.getId());
                                id = rv.getId();
                            }
                        }
                        new Update(nickname, id);
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