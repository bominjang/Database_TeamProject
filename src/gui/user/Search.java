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

        // 검색
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