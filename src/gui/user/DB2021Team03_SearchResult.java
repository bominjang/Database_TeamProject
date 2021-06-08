package gui.user;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.*;

import dao.*;
import models.Keyword;
import models.Movies;

@SuppressWarnings("serial")
public class DB2021Team03_SearchResult extends DB2021Team03_CustomUI {
    //검색 결과 화면을 위한 클래스
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
    private DB2021Team03_SearchDao sDao;
    private int movieId;

    private String nickname;
    private int addnum;

    //생성자 : 로그인 유지를 위한 nickname과 검색을 위한 keyword, text를 인자로 입력받는다.
    public DB2021Team03_SearchResult(String nickname, String keyword, String text) {
        this.nickname = nickname;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //SearchDao 객체를 이용해 keyword, text 검색.
        sDao = DB2021Team03_SearchDao.getInstance();
        rMovies = sDao.search(keyword, text);

        lbBox = new JLabel[rMovies.size()];
        lbMovieName = new JLabel[rMovies.size()];
        lbRating = new JLabel[rMovies.size()];

        init();

        // 메인 페이지로 돌아가는 버튼
        btnMain.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnCd = JOptionPane.showConfirmDialog(frame, "메인 페이지로 돌아가시겠습니까?", "경고", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if(returnCd == JOptionPane.YES_OPTION) {
                    new DB2021Team03_Main(nickname);
                    frame.dispose();
                }
            }
        });

        // 이전 페이지로 돌아가는 버튼
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnCd = JOptionPane.showConfirmDialog(frame, "이전 페이지로 돌아가시겠습니까?", "경고", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if(returnCd == JOptionPane.YES_OPTION) {
                    new DB2021Team03_Search(nickname);
                    frame.dispose();
                }
            }
        });

        //검색 버튼
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //사용자가 선택한 키워드를 key 변수에 저장
                Keyword movie = (Keyword) comboKeyword.getSelectedItem();
                String key = movie.getKey();
                //사용자가 입력한 검색어를 text 변수에 저장
                String text = searchbar.getText();

                //getInstance객체를 이용하여 해당 검색어가 db에 존재하는지 확인
                DB2021Team03_SearchDao sDao = DB2021Team03_SearchDao.getInstance();
                boolean check = sDao.dataExist(key, text);

                //존재한다면 결과페이지 호출,존재하지 않는다면 경고문 팝업
                if(check) {
                    new DB2021Team03_SearchResult(nickname, key, text);
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

    //검색 결과 화면 구성을 위한 GUI 코드
    private void init() {
        backgroundPanel = new JPanel();
        frame.setContentPane(backgroundPanel);
        frame.setTitle("DB2021Team03-영화 정보 프로그램");

        DB2021Team03_CustomUI custom = new DB2021Team03_CustomUI(backgroundPanel);
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

        // 영화 검색을 위한 키워드 콤보박스 셋팅
        DB2021Team03_SearchDao sDao = DB2021Team03_SearchDao.getInstance();
        Vector<Keyword> comboKeywords = sDao.setCombo();
        comboKeyword = custom.setKeyword("combo", comboKeywords, 35, 200, 150, 40);

        panel.add(comboKeyword);

        searchbar = custom.setTextField("searchbar", "검색어를 입력하세요", 200, 200, 430, 40);
        panel.add(searchbar);

        btnSearch = custom.setBtnGreen("btnMain", "검색", 645, 200, 100, 40);
        panel.add(btnSearch);

        lbTitle = custom.setLb("lbTitle", "검색 결과", 310, 300, 180, 50, "center", 35, "bold");
        panel.add(lbTitle);

        //검색한 영화들을 화면에 출력
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

                //영화 제목을 클릭하면 영화 상세페이지로 이동.
                public void mouseClicked(MouseEvent e) {
                    JLabel label = (JLabel) e.getSource();
                    for (Movies rMovie : rMovies) {
                        if (label.getText().equals(rMovie.getTitle())) {
                            movieId = rMovie.getId();
                        }
                    }

                    new DB2021Team03_SearchToDetail(nickname, movieId);
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