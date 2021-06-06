package gui.user;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.*;

import dao.*;
import models.Combo;
import models.Keyword;
import models.Movies;

@SuppressWarnings("serial")
public class SearchResult extends CustomUI {

    private JFrame frame = new JFrame();

    private JPanel container = new JPanel();
    private JPanel backgroundPanel;
    private JLabel lbIcon, lbTitle, lbTitleMovie,lbTitleCountry, lbTitlePlot, lbTItleRating, lbTitleGenreAge, lbTitleRunOpenTime, lbTitleDirector, lbTitleActor;
    private JLabel lbMovieName[], lbRating[], lbGenreAge, lbCountry, lbRunOpenTime, lbDirector, lbActor;
    private JTextArea taPlot;
    private JTextField searchbar;
    private JComboBox<Keyword> comboKeyword;
    private JButton btnMain, btnBack, btnSearch;
    private JLabel lbBox[];
    private Vector<Movies> rMovies;
    private SearchDao sDao;

    private String nickname;
    private int addnum;

    public SearchResult(String nickname, String keyword, String text) {
        this.nickname = nickname;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        sDao = SearchDao.getInstance();
        rMovies = sDao.search(keyword, text);

        lbBox = new JLabel[rMovies.size()];
        lbMovieName = new JLabel[rMovies.size()];
        lbRating = new JLabel[rMovies.size()];

        init();

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
                    new Search(nickname);
                    frame.dispose();
                }
            }
        });

        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Keyword movie = (Keyword) comboKeyword.getSelectedItem();

                String key = movie.getKey();
                String text = searchbar.getText();

                SearchDao sDao = SearchDao.getInstance();
                boolean check = sDao.dataExist(key, text);

                if(check) {
                    new SearchResult(nickname, key, text);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "등록되어 있지 않은 정보입니다.", "오류", JOptionPane.ERROR_MESSAGE);
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

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        btnBack = custom.setBtnWhite("btnBack", "이전으로", 30, 30);
        btnMain = custom.setBtnGreen("btnMain", "메인으로", 410, 30, 350, 40);

        panel.add(btnMain);
        panel.add(btnBack);

        lbTitle = custom.setLb("lbTitle", "영화 검색", 310, 100, 180, 50, "center", 35, "bold");
        panel.add(lbTitle);


        SearchDao sDao = SearchDao.getInstance();
        Vector<Keyword> comboKeywords = sDao.setCombo("search");
        comboKeyword = custom.setKeyword("combo", comboKeywords, 35, 200, 150, 40);

        panel.add(comboKeyword);

        searchbar = custom.setTextField("searchbar", "검색어를 입력하세요", 200, 200, 430, 40);
        panel.add(searchbar);

        btnSearch = custom.setBtnGreen("btnMain", "검색", 645, 200, 100, 40);
        panel.add(btnSearch);

        lbTitle = custom.setLb("lbTitle", "검색 결과", 310, 300, 180, 50, "center", 35, "bold");
        panel.add(lbTitle);

        for (int j = 0; j < rMovies.size(); j++) {
            int moveY = 55;
            addnum++;
            lbBox[j] = custom.setLbBox("lbBox" + j, j + 1 + "", 150, 400 + (moveY * j), panel);
            lbMovieName[j] = custom.setLb(rMovies.get(j).getTitle(), rMovies.get(j).getTitle(), 200, 402 + (moveY * j), 300, 20, "left", 20, "plain", panel);
            lbRating[j] = custom.setLb("lbTime" + j, "평점: " + rMovies.get(j).getRating() + "", 350, 402 + (moveY * j), 300, 20, "right", 20, "plain", panel);

            panel.add(lbBox[j]);
            panel.add(lbMovieName[j]);
            panel.add(lbRating[j]);

            lbMovieName[j].addMouseListener(new MouseListener() {
                public void mouseReleased(MouseEvent e) {
                }

                public void mousePressed(MouseEvent e) {
                }

                public void mouseExited(MouseEvent e) {
                }

                public void mouseEntered(MouseEvent e) {
                }

                public void mouseClicked(MouseEvent e) {
                    int movieId = 0;
                    String movieTitle = e.getSource().toString();
                    for (int i = 0; i < lbMovieName.length; i++) {
                        if (movieTitle.contains(rMovies.get(i).getTitle())) {
                            movieId = rMovies.get(i).getId();
                        }
                    }
                    new Movie(nickname, movieId);
                    frame.dispose();
                }
            });
        }
        panel.setPreferredSize(new Dimension(800, 900 + 55 * addnum));

        JScrollPane sp = new JScrollPane();
        sp.setViewportView(panel);
        sp.setBounds(0, 100, 800, 900);
        backgroundPanel.add(sp);
    }
}