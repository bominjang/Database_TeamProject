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

    public Main(String nickname) {
        this.nickname = nickname;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();

        btnMovie.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Ranking(nickname);
                frame.dispose();
            }
        });

        btnTheater.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Search(nickname);
                frame.dispose();
            }
        });

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
        frame.setTitle("DB2021Team03-영화 정보 프로그램");

        CustomUI custom = new CustomUI(backgroundPanel);
        custom.setPanel();

        btnMovie = custom.setBtnImg("btnMovie", "영화순위 보기", 33, 240);
        btnTheater = custom.setBtnImg("btnTheater", "검색하기", 212, 240);
        btnReview = custom.setBtnImg("btnReview", "리뷰 작성", 33, 400);
        btnInfo = custom.setBtnImg("btnInfo", "마이페이지", 212, 400);
        btnLogout = custom.setBtnWhite("btnLogout", "로그아웃", 35, 650);
    }
}