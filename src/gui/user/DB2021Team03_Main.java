package gui.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 메뉴들이 있는 메인화면 클래스
 */
@SuppressWarnings("serial")
public class DB2021Team03_Main extends DB2021Team03_CustomUI {

    private JFrame frame = new JFrame();
    private JPanel backgroundPanel;
    private JButton btnMovie, btnSearch, btnReview, btnInfo, btnLogout;

    private String nickname;

    /**
     * 메인화면 생성자
     *
     * @param nickname 로그인 유지를 위한 사용자 nickname
     */
    public DB2021Team03_Main(String nickname) {
        this.nickname = nickname;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();

        //영화 순위로 이동하는 버튼
        btnMovie.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new DB2021Team03_Ranking(nickname);
                frame.dispose();
            }
        });
        //영화검색으로 이동하는 버튼
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new DB2021Team03_Search(nickname);
                frame.dispose();
            }
        });

        //Review 작성으로 이동하는 버튼
        btnReview.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new DB2021Team03_Review(nickname);
                frame.dispose();
            }
        });

        //User 정보 화면으로 이동하는 버튼
        btnInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new DB2021Team03_UserInfo(nickname);
                frame.dispose();
            }
        });

        //로그아웃 버튼
        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new DB2021Team03_Login();
                frame.dispose();
            }
        });

        frame.setSize(426, 779);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    /**
     * 메인 화면 구성을 위한 GUI
     */
    private void init() {
        backgroundPanel = new JPanel();
        frame.setContentPane(backgroundPanel);
        frame.setTitle("DB2021Team03-영화 정보 프로그램");

        DB2021Team03_CustomUI custom = new DB2021Team03_CustomUI(backgroundPanel);
        custom.setPanel();

        btnMovie = custom.setBtnImg("btnMovie", "영화순위 보기", 33, 240);
        btnSearch = custom.setBtnImg("btnTheater", "검색하기", 212, 240);
        btnReview = custom.setBtnImg("btnReview", "리뷰 작성", 33, 400);
        btnInfo = custom.setBtnImg("btnInfo", "마이화면", 212, 400);
        btnLogout = custom.setBtnWhite("btnLogout", "로그아웃", 35, 650);
    }
}