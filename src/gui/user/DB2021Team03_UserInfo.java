package gui.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dao.DB2021Team03_DBConnection;
import models.Users;

@SuppressWarnings("serial")
public class DB2021Team03_UserInfo extends DB2021Team03_CustomUI {
    //유저 정보를 확인하기 위한 클래스
    private JFrame frame = new JFrame();
    private JPanel backgroundPanel;
    private JButton btnMain, btnReviews;
    private JLabel lbTitle, lbTitleId, lbId, lbTitleBirth, lbBirth, lbTitleTel, lbTel;

    private String nickname;

    //생성자 : 해당 유저의 nickname을 인자로 받음
    public DB2021Team03_UserInfo(String nickname) {
        this.nickname = nickname;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();

        setUserInfo(nickname);

        //메인화면으로 이동하는 버튼
        btnMain.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new DB2021Team03_Main(nickname);
                frame.dispose();
            }
        });
        //내가 쓴 리뷰들로 이동하는 버튼
        btnReviews.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new DB2021Team03_MyReviews(nickname);
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
        DB2021Team03_CustomUI custom = new DB2021Team03_CustomUI(backgroundPanel);
        custom.setPanel();

        lbTitle = custom.setLbImg("lbTitle", 0, 165, 150);

        lbTitleId = custom.setLb("lbTitleId", "닉네임", 35, 300, 100, 20, "left", 17, "bold");
        lbId = custom.setLb("lbId", "", 195, 300, 180, 20, "right", 17, "plain");

        lbTitleBirth = custom.setLb("lbTitleBirth", "생년월일", 35, 360, 100, 20, "left", 17, "bold");
        lbBirth = custom.setLb("lbText3", "", 195, 360, 180, 20, "right", 17, "plain");

        lbTitleTel = custom.setLb("lbTitleTel", "전화번호", 35, 420, 100, 20, "left", 17, "bold");
        lbTel = custom.setLb("lbBirth", "", 195, 420, 180, 20, "right", 17, "plain");

        btnReviews = custom.setBtnGreen("btnReviews","내가 쓴 리뷰 보기",35, 540, 350, 40);
        btnMain = custom.setBtnGreen("btnMain", "메인으로", 35, 605, 350 , 40);
    }

    //유저정보를 출력하는 메서드
    public Users setUserInfo(String nickname) {
        final String SQL = "SELECT BIRTH, PHONE FROM DB2021_User WHERE NICKNAME = ?";

        Connection conn;
        PreparedStatement pstmt;
        ResultSet rs;
        conn = DB2021Team03_DBConnection.getConnection();

        //nickname, 생일, 핸드폰 번호를 출력
        Users user = new Users();
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, nickname);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                user.setBirth(rs.getDate("BIRTH"));
                user.setPhone(rs.getString("PHONE"));
            }

            lbId.setText(nickname);
            SimpleDateFormat transFormat = new SimpleDateFormat("YYYY-MM-DD");
            lbBirth.setText(transFormat.format(user.getBirth()));
            lbTel.setText(formatTel(user.getPhone()));

            conn.close();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //전화번호를 파싱하는 메서드
    private String formatTel(String tel) {
        if(tel.length() == 10) {
            String tel1 = tel.substring(0, 3);
            String tel2 = tel.substring(3, 6);
            String tel3 = tel.substring(6);
            tel = tel1 + "-" + tel2 + "-" + tel3;
        } else if(tel.length() == 11) {
            String tel1 = tel.substring(0, 3);
            String tel2 = tel.substring(3, 7);
            String tel3 = tel.substring(7);
            tel = tel1 + "-" + tel2 + "-" + tel3;
        } else {
            return tel;
        }
        return tel;
    }

    /*
    private String formatDate(int date) {
        String formatDate = date + "";
        String date1 = formatDate.substring(0, 4);
        String date2 = formatDate.substring(4, 6);
        String date3 = formatDate.substring(6);
        formatDate = date1 + "-" + date2 + "-" + date3;
        return formatDate;
    }*/
}