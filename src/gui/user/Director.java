package gui.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Vector;

import javax.swing.*;

import dao.DirectorDao;
import models.Directors;

@SuppressWarnings("serial")
public class Director extends CustomUI {

    private JFrame frame = new JFrame();
    private JPanel backgroundPanel;
    private JLabel lbIcon, lbName, lbTitleCountry, lbTitleBirth, lbTitleMovies, lbTitlePrize;
    private JLabel lbCountry, lbBirth, lbMovies[], lbPrizes[];
    private JButton btnMain, btnBack;
    private Vector<String> direcMovies;
    private Map<String, Vector<String>> direcPrizes;

    private String nickname;

    public Director(String nickname, int directorId) {
        this.nickname = nickname;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DirectorDao dDao = DirectorDao.getInstance();
        Directors director = dDao.selectOne(directorId);

        direcMovies = dDao.selectMovies(directorId);
        lbMovies = new JLabel[direcMovies.size()];

        direcPrizes = dDao.selectPrizes(directorId);

        init();

        lbName.setText(director.getName());

        lbCountry.setText(director.getCountry());

        SimpleDateFormat transFormat = new SimpleDateFormat("YYYY-MM-DD");
        String birth;
        if(director.getBirth() == null){
            birth="unknown";
        } else {
            birth=transFormat.format(director.getBirth());
        }
        lbBirth.setText(birth);


        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnCd = JOptionPane.showConfirmDialog(frame, "메인 페이지로 돌아가시겠습니까?", "경고", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if(returnCd == JOptionPane.YES_OPTION) {
                    new Main(nickname);
                    frame.dispose();
                }
            }
        });

        btnMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Main(nickname);
                frame.dispose();
            }
        });

        frame.setSize(600, 900);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void init() {
        backgroundPanel = new JPanel();
        frame.setContentPane(backgroundPanel);
        frame.setTitle("영화 예매 프로그램 ver1.0");

        CustomUI custom = new CustomUI(backgroundPanel);
        custom.setPanel();

        lbIcon = custom.setLbImg("lbIcon", 4, 232, 130);
        lbName = custom.setLb("lbName", "감독이름", 232, 150, 100, 185, "center", 20, "bold");

        lbTitleCountry = custom.setLb("lbTitleCountry", "출신국가", 150, 300, 150, 20, "left", 17, "bold");
        lbCountry = custom.setLb("lbCountry", "출신 국가", 319, 300, 200, 20, "left", 17, "plain");

        lbTitleBirth = custom.setLb("lbTitleBirth", "생년월일", 150, 330, 150, 20, "left", 17, "bold");
        lbBirth = custom.setLb("lbBirth", "00-00-00", 319, 330, 200, 20, "left", 17, "plain");

        lbTitleMovies = custom.setLb("lbTitleMovies", "필모그라피", 150, 360, 200, 20, "left", 17, "bold");

        if(direcMovies == null) {
            custom.setLb("lbMovies", "없음", 319, 360, 200, 20, "left", 17, "plain");
        }
        else{
            for (int i = 0; i < direcMovies.size(); i++) {
                String movie = direcMovies.get(i);

                lbMovies[i] = custom.setLb("lbMovies", "영화(연도)", 319, 360 + (20 * i), 200, 20, "left", 17, "plain");
                lbMovies[i].setText(movie);
            }
        }

//        taMovies = custom.setTextArea("lbMovies", "0.0", 425, 360, 200, 60, false);

//        lbTitlePrize = custom.setLb("lbTitleDetail", "상", 230, 420, 150, 20, "left", 17, "bold");
//        lbPrize = custom.setLb("lbDetail", "감독이 받은 상", 425, 420, 200, 20, "left", 17, "plain");

        lbTitlePrize = custom.setLb("lbTitlePrize", "수상이력", 150, 450, 200, 20, "left", 17, "bold");
        if(direcPrizes == null) {
            custom.setLb("lbPrizes", "없음", 319, 450, 200, 20, "left", 17, "plain");
        }
        else {
            int i = 0;
            for (String mapkey : direcPrizes.keySet()){
                custom.setLb("lbMovies", mapkey, 319, 450 + (20 * i), 200, 20, "left", 17, "plain");

                Vector<String> prizes = direcPrizes.get(mapkey);
                for(String prize : prizes) { //for문을 통한 전체출력
                    ++i;
                    custom.setLb("lbMovies", "- "+prize, 319, 450 + (20 * i), 200, 20, "left", 17, "plain");
                }
                ++i;
            }
        }
//
//        lbTitlePlot = custom.setLb("lbTitlePlot", "줄거리", 330, 490, 150, 20, "center", 17, "bold");
//        taPlot = custom.setTextArea("lbPlot", "줄거리주루룩", 100, 520, 600, 120, false);

        btnMain = custom.setBtnGreen("btnMain", "메인으로", 120, 680, 350, 40);
        btnBack = custom.setBtnWhite("btnBack", "이전으로", 120, 730);
    }
}