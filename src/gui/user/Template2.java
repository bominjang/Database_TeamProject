<<<<<<< HEAD
=======
package gui.user;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.*;

public class Template2 extends CustomUI {

    private JFrame frame = new JFrame();
    private JPanel container = new JPanel();
    private JPanel backgroundPanel;
    private JLabel lbIcon, lbTitle, lbTitleMovie,lbTitleCountry, lbTitlePlot, lbTItleRating, lbTitleGenreAge, lbTitleRunOpenTime, lbTitleDirector, lbTitleActor;
    private JLabel lbMovie, lbRating, lbGenreAge, lbCountry, lbRunOpenTime, lbDirector, lbActor;
    private JTextArea taPlot;

    private JButton btnMain, btnBack;

    private String nickname;

    public Template2(String nickname, int DirectorId) {
        this.nickname = nickname;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();

        // 메인으로 돌아가도록
        btnMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Main(nickname);
                frame.dispose();
            }
        });

        // 이전 페이지로 돌아가도록(수정)
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnCd = JOptionPane.showConfirmDialog(frame, "메인 페이지로 돌아가시겠습니까?", "경고", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if(returnCd == JOptionPane.YES_OPTION) {
                    new Main(nickname);
                    frame.dispose();
                }
            }
        });

        frame.add(container);
        frame.setSize(800, 900);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void init() {
        backgroundPanel = new JPanel();
        frame.setContentPane(backgroundPanel);
        frame.setTitle("영화 예매 프로그램 ver1.0");

        CustomUI custom = new CustomUI(backgroundPanel);
        custom.setPanel();

        lbIcon = custom.setLbImg("lbIcon", 4, 350, 130);
        lbActor = custom.setLb("lbTitle", "배우 이름", 350, 150, 100, 185, "center", 20, "bold");

        lbTItleRating = custom.setLb("lbTitleMovie", "평점", 230, 270, 150, 20, "right", 17, "bold");
        lbRating = custom.setLb("lbMovie", "0.0", 425, 270, 200, 20, "left", 17, "plain");

        lbTitleGenreAge = custom.setLb("lbTitleDate", "장르/연령제한", 230, 330, 150, 20, "left", 17, "bold");
        lbGenreAge = custom.setLb("lbDate", "장르와 연령제한입니다.", 425, 330, 200, 20, "left", 17, "plain");

        lbTitleCountry = custom.setLb("lbTitleDate", "나라", 230, 360, 150, 20, "left", 17, "bold");
        lbCountry = custom.setLb("lbDate", "나라입니다.", 425, 360, 200, 20, "left", 17, "plain");

        lbTitleRunOpenTime = custom.setLb("lbTitleRating", "상영시간/개봉일", 230, 390, 150, 20, "left", 17, "bold");
        lbRunOpenTime = custom.setLb("lbRating", "상연시간과 개봉일입니다.", 425, 390, 200, 20, "left", 17, "plain");

        lbTitleDirector = custom.setLb("lbTitleDetail", "감독", 230, 420, 150, 20, "left", 17, "bold");
        lbDirector = custom.setLb("lbDetail", "영화를 맡은 감독입니다.", 425, 420, 200, 20, "left", 17, "plain");

        lbTitleActor = custom.setLb("lbTitleDetail", "출연배우", 230, 450, 150, 20, "left", 17, "bold");
        lbActor = custom.setLb("lbDetail", "출연배우들 입니다.", 425, 450, 200, 20, "left", 17, "plain");

        lbTitlePlot = custom.setLb("lbTitlePlot", "줄거리", 330, 490, 150, 20, "center", 17, "bold");
        taPlot = custom.setTextArea("lbPlot", "줄거리주루룩", 100, 520, 600, 120, false);

        btnMain = custom.setBtnGreen("btnMain", "메인으로", 220, 680);
        btnBack = custom.setBtnWhite("btnBack", "이전으로", 220, 730);
    }
}
>>>>>>> f1196d4c8e72486828409a5c9b5338eed26eee6d
