package gui.user;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.*;

import dao.DB2021Team03_ActorDao;
import dao.DB2021Team03_DirectorDao;
import dao.DB2021Team03_MovieDao;
import models.Actors;
import models.Movies;

@SuppressWarnings("serial")
public class DB2021Team03_SearchToDetail extends DB2021Team03_CustomUI {
    //영화 검색에서 영화를 선택하면 나오는 화면
    private JFrame frame = new JFrame();

    private JPanel container = new JPanel();
    private JPanel backgroundPanel;
    private JLabel lbIcon, lbTitle, lbTitleMovie, lbTitleCountry, lbTitlePlot, lbTItleRating, lbTitleGenreAge, lbTitleRunOpenTime, lbTitleDirector, lbTitleActor;
    private JLabel lbMovie, lbRating, lbGenreAge, lbCountry, lbRunOpenTime, lbDirector, lbActors[];
    private JTextArea taPlot;

    private JButton btnMain, btnBack, btnMovieReviews;

    private String nickname;
    private Vector<Actors> actors;
    private JPanel panel;

    // 생성자 : 로그인 유지를 위한 nickname과 영화 상세페이지를 위한 movieId를 인자로 받음.
    public DB2021Team03_SearchToDetail(String nickname, int MovieId) {
        this.nickname = nickname;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 출연 배우
        DB2021Team03_ActorDao aDao = DB2021Team03_ActorDao.getInstance();
        actors = aDao.selectAll(MovieId);
        lbActors = new JLabel[actors.size()];

        init();

        JPanel in_panel = new JPanel();
        in_panel.setLayout(null);
        in_panel.setBackground(Color.BLACK);

        DB2021Team03_MovieDao mDao = DB2021Team03_MovieDao.getInstance();
        Movies movie = mDao.selectOne(MovieId);

        DB2021Team03_DirectorDao dDao = DB2021Team03_DirectorDao.getInstance();


        // 라벨에 값들 set하기
        // 제목
        lbMovie.setText(movie.getTitle());

        // 평점
        lbRating.setText(Float.toString(movie.getRating()));

        // 장르/연령제한
        lbGenreAge.setText(movie.getGenre() + "/" + Integer.toString(movie.getAge()) + "세이상관람가능");

        // 나라
        lbCountry.setText(movie.getCountry());

        // 상연시간/개봉일
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        lbRunOpenTime.setText(Integer.toString(movie.getRunningTime()) + "분/" + dateFormat.format(movie.getOpening_date()));

        // 감독
        lbDirector.setText(movie.getDirector());

        // 줄거리
        taPlot.setText(movie.getPlot());

        in_panel.setPreferredSize(new Dimension(600, 200));

        JScrollPane sp1 = new JScrollPane(taPlot);
        sp1.setBounds(100, 520, 600, 200);
        panel.add(sp1);

        JScrollPane sp2 = new JScrollPane();
        sp2.setViewportView(panel);
        sp2.setBounds(0, 100, 800, 1000);
        backgroundPanel.add(sp2);

        //영화 review보기
        btnMovieReviews.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DB2021Team03_MovieReviews(nickname, MovieId);
                frame.dispose();
            }
        });

        btnMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DB2021Team03_Main(nickname);
                frame.dispose();
            }
        });

        //감독상세페이지 이동
        lbDirector.addMouseListener(new MouseListener() {
            public void mouseReleased(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseClicked(MouseEvent e) {
                JLabel lb = (JLabel) e.getSource();
                int result = -1;

                result = dDao.selectId(lb.getText());
                if (result != -1) {
                    new DB2021Team03_Director(nickname, result);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "감독 정보가 없습니다.");
                }

            }
        });

        //배우 상세페이지 이동
        //배우 상세페이지 이동
        for(JLabel lbActor:lbActors) {
            lbActor.addMouseListener(new MouseListener() {
                public void mouseReleased(MouseEvent e) {
                }

                public void mousePressed(MouseEvent e) {
                }

                public void mouseExited(MouseEvent e) {
                }

                public void mouseEntered(MouseEvent e) {
                }

                public void mouseClicked(MouseEvent e) {
                    JLabel lb = (JLabel) e.getSource();
                    int result = -1;

                    result = aDao.selectId(lb.getText());
                    if (result != -1) {
                        new DB2021Team03_Actor(nickname, result);
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "배우 정보가 없습니다.");
                    }

                }
            });
        }

        // 이전 페이지로 돌아가도록
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnCd = JOptionPane.showConfirmDialog(frame, "이전 페이지로 돌아가시겠습니까?", "경고", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (returnCd == JOptionPane.YES_OPTION) {
                    new DB2021Team03_Search(nickname);
                    frame.dispose();
                }
            }
        });

        frame.add(container);
        frame.setSize(800, 900);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    //영화 상세 화면 구성을 위한 GUI 코드
    private void init() {
        backgroundPanel = new JPanel();
        frame.setContentPane(backgroundPanel);
        frame.setTitle("DB2021Team03-영화 정보 프로그램");
        DB2021Team03_CustomUI custom = new DB2021Team03_CustomUI(backgroundPanel);
        custom.setPanel();

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);


        lbIcon = custom.setLbImg("lbIcon", 0, 350, 130);
        lbMovie = custom.setLb("lbTitle", "영화제목", 350, 150, 100, 185, "center", 20, "bold");

        panel.add(lbIcon);
        panel.add(lbMovie);

        lbTItleRating = custom.setLb("lbTitleMovie", "평점", 230, 270, 150, 20, "right", 17, "bold");
        lbRating = custom.setLb("lbMovie", "0.0", 425, 270, 200, 20, "left", 17, "plain");

        panel.add(lbTItleRating);
        panel.add(lbRating);

        lbTitleGenreAge = custom.setLb("lbTitleDate", "장르/연령제한", 230, 330, 150, 20, "left", 17, "bold");
        lbGenreAge = custom.setLb("lbDate", "장르와 연령제한입니다.", 425, 330, 200, 20, "left", 17, "plain");

        panel.add(lbTitleGenreAge);
        panel.add(lbGenreAge);

        lbTitleCountry = custom.setLb("lbTitleDate", "나라", 230, 360, 150, 20, "left", 17, "bold");
        lbCountry = custom.setLb("lbDate", "나라입니다.", 425, 360, 200, 20, "left", 17, "plain");

        panel.add(lbTitleCountry);
        panel.add(lbCountry);

        lbTitleRunOpenTime = custom.setLb("lbTitleRating", "상영시간/개봉일", 230, 390, 150, 20, "left", 17, "bold");
        lbRunOpenTime = custom.setLb("lbRating", "상연시간과 개봉일입니다.", 425, 390, 200, 20, "left", 17, "plain");

        panel.add(lbTitleRunOpenTime);
        panel.add(lbRunOpenTime);

        lbTitleDirector = custom.setLb("lbTitleDetail", "감독", 230, 420, 150, 20, "left", 17, "bold");
        lbDirector = custom.setLb("lbDetail", "영화를 맡은 감독입니다.", 425, 420, 200, 20, "left", 17, "plain");

        panel.add(lbTitleDirector);
        panel.add(lbDirector);

        lbTitleActor = custom.setLb("lbTitleDetail", "출연배우", 230, 450, 150, 20, "left", 17, "bold");
        panel.add(lbTitleActor);

        if(actors == null) {
            custom.setLb("lbActor", "없음", 319, 360, 200, 20, "left", 17, "plain");
        }
        else{
            for (int i = 0; i < actors.size(); i++) {
                Actors actor = actors.get(i);
                String name = actor.getName();

                lbActors[i] = custom.setLb("lbActor", name, 425 + (50 * i), 450, 60, 20, "left", 17, "plain");
                lbActors[i].setText(name);
                panel.add(lbActors[i]);
            }
        }

        lbTitlePlot = custom.setLb("lbTitlePlot", "줄거리", 330, 490, 150, 20, "center", 17, "bold");
        taPlot = custom.setTextArea("lbPlot", "줄거리주루룩", 100, 520, 600, 120, false);

        panel.add(lbTitlePlot);

        btnMovieReviews = custom.setBtnGreen("btnReviews", "영화 리뷰 보기", 220, 780, 350, 40);
        btnBack = custom.setBtnWhite("btnBack", "이전으로", 220, 840);
        btnMain = custom.setBtnGreen("btnMain", "메인으로", 220, 900, 350, 40);


        panel.setPreferredSize(new Dimension(800, 1500));

        panel.add(btnMovieReviews);
        panel.add(btnMain);
        panel.add(btnBack);


    }
}