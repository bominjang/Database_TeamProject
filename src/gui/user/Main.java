package gui.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Main extends CustomUI {

    private JFrame frame = new JFrame();
    private JPanel backgroundPanel;
    private JButton btnMovie, btnTheater, btnReview, btnInfo, btnLogout;

    private String nickname;
    private int userId;

    public Main(String nickname) {
        this.nickname = nickname;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();

        btnMovie.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // 영화 상세-테스트용(원래는 nickname, MovieID)
//                new Movie(nickname,6);
                // 감독 상세-테스트용(nickname, DirectorID)
//                new Director(nickname, 14);
                // 배우 상세-테스트용(nickname, ActorID)
                new Actor(nickname, 26);
                frame.dispose();
            }
        });

//        btnTheater.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                new SelectTheater1(nickname);
//                frame.dispose();
//            }
//        });

        btnReview.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Review(nickname);
                frame.dispose();
            }
        });

        btnInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new UserInfo(nickname);
                frame.dispose();
            }
        });

        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Login();
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
        frame.setTitle("영화 예매 프로그램 ver1.0");

        CustomUI custom = new CustomUI(backgroundPanel);
        custom.setPanel();

        btnMovie = custom.setBtnImg("btnMovie", "(테스트)둘러보기", 33, 240);
        btnTheater = custom.setBtnImg("btnTheater", "검색하기", 212, 240);
        btnReview = custom.setBtnImg("btnReview", "리뷰 작성", 33, 400);
        btnInfo = custom.setBtnImg("btnInfo", "마이페이지", 212, 400);
        btnLogout = custom.setBtnWhite("btnLogout", "로그아웃", 35, 650);
    }
}