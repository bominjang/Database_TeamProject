package gui.user;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.*;

import dao.*;
import models.Keyword;

@SuppressWarnings("serial")
public class Search extends CustomUI {

    private JFrame frame = new JFrame();

    private JPanel container = new JPanel();
    private JPanel backgroundPanel;
    private JLabel lbTitle;
    private JTextField searchbar;

    private JComboBox<Keyword> comboKeyword;

    private JButton btnMain, btnBack, btnSearch;

    private String nickname;

    public Search(String nickname) {
        this.nickname = nickname;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();

//        MovieDao mDao = MovieDao.getInstance();
//        Movies movie = mDao.selectOne(MovieId);
//
//        // 라벨에 값들 set하기
//        // 제목
//        lbMovie.setText(movie.getTitle());
//
//        // 평점
//        lbRating.setText(Float.toString(movie.getRating()));
//
//        // 장르/연령제한
//        lbGenreAge.setText(movie.getGenre()+"/"+Integer.toString(movie.getAge())+"세이상관람가능");
//
//        // 나라
//        lbCountry.setText(movie.getCountry());
//
//        // 상연시간/개봉일
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        lbRunOpenTime.setText(Integer.toString(movie.getRunningTime())+"분/"+dateFormat.format(movie.getOpening_date()));
//
//        // 감독
//        lbDirector.setText(movie.getDirector());
//
//        // 출연 배우
//        ActorDao aDao = ActorDao.getInstance();
//        String actors = aDao.selectAll(MovieId);
//        lbActor.setText(actors);
//
//        // 줄거리
//        taPlot.setText(movie.getPlot());
//
//        // 영화 선택
//        comboMovie.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                Combo selectedComboItem = (Combo) comboMovie.getSelectedItem();
//                System.out.println("a: "+selectedComboItem);
//                String movie = selectedComboItem.toString();
//                lbResult.setText(movie);
//            }
//        });
//
//        // 등록하기
//        btnReview.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                int returnCd = JOptionPane.showConfirmDialog(frame, "등록하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
//                if(returnCd == JOptionPane.YES_OPTION) {
//                    Combo movie = (Combo) comboMovie.getSelectedItem();
//
//                    String movieTitle = movie.getValue();
//                    String rating = txtrating.getText();
//                    String detail = txtdetail.getText();
//
//                    ReviewDao rDao = ReviewDao.getInstance();
//
//                    int returnCnt = rDao.insert(movieTitle, nickname, Float.parseFloat(rating), detail);
//
//                    if(returnCnt == 1) {
//                        new Result(nickname);
//                        frame.dispose();
//                    } else {
//                        JOptionPane.showMessageDialog(frame, "등록에 실패하였습니다. 다시 시도해 주세요.", "오류", JOptionPane.ERROR_MESSAGE);
//                    }
//                }
//            }
//        });

        // 메인 페이지로 돌아가도록
        btnMain.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnCd = JOptionPane.showConfirmDialog(frame, "메인 페이지로 돌아가시겠습니까?", "경고", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if(returnCd == JOptionPane.YES_OPTION) {
                    new Main(nickname);
                    frame.dispose();
                }
            }
        });

        // 이전 페이지로 돌아가도록
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnCd = JOptionPane.showConfirmDialog(frame, "이전 페이지로 돌아가시겠습니까?", "경고", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if(returnCd == JOptionPane.YES_OPTION) {
                    new Main(nickname);
                    frame.dispose();
                }
            }
        });

        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                new SearchResult(nickname, keyword, text);
                new SearchResult(nickname, "movie", "hi");
                frame.dispose();
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

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        btnBack = custom.setBtnWhite("btnBack", "이전으로", 30, 30);
        btnMain = custom.setBtnGreen("btnMain", "메인으로", 410, 30, 350, 40);

        panel.add(btnMain);
        panel.add(btnBack);

        lbTitle = custom.setLb("lbTitle", "영화 검색", 310, 200, 180, 50, "center", 35, "bold");
        panel.add(lbTitle);

        SearchDao sDao = SearchDao.getInstance();
        Vector<Keyword> comboKeywords = sDao.setCombo("search");
        comboKeyword = custom.setKeyword("combo", comboKeywords, 35, 300, 150, 40);

        panel.add(comboKeyword);

        searchbar = custom.setTextField("searchbar", "검색어를 입력하세요", 200, 300, 430, 40);
        panel.add(searchbar);

        btnSearch = custom.setBtnGreen("btnMain", "검색", 645, 300, 100, 40);
        panel.add(btnSearch);

        panel.setPreferredSize(new Dimension(800, 900));

        JScrollPane sp = new JScrollPane();
        sp.setViewportView(panel);
        sp.setBounds(0, 100, 800, 900);
        backgroundPanel.add(sp);
    }
}