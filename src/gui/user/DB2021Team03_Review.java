package gui.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.*;

import dao.DB2021Team03_ComboDao;
import dao.DB2021Team03_ReviewDao;
import models.Combo;

@SuppressWarnings("serial")
public class DB2021Team03_Review extends DB2021Team03_CustomUI {

    private JFrame frame = new JFrame();
    private JPanel backgroundPanel;
    private JLabel lbResult, lbTitleNickname, lbTitleUser, lbTitleRating, lbReview, lbTitleMovie;
    private JComboBox<Combo> comboMovie;
    private JTextField txtrating;
    private JTextArea txtdetail;
    private JButton btnReview, btnBack;

    private String nickname;

    public DB2021Team03_Review(String nickname) {
        this.nickname = nickname;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();
        
        // 영화 선택
        comboMovie.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Combo selectedComboItem = (Combo) comboMovie.getSelectedItem();
                String movie = selectedComboItem.toString();
                lbResult.setText(movie);
            }
        });

        // 등록하기
        btnReview.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnCd = JOptionPane.showConfirmDialog(frame, "등록하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if(returnCd == JOptionPane.YES_OPTION) {
                    Combo movie = (Combo) comboMovie.getSelectedItem();

                    //사용자에게서 입력받은 값을 변수에 저장
                    String movieTitle = movie.getValue();
                    String rating = txtrating.getText();
                    String detail = txtdetail.getText();

                    DB2021Team03_ReviewDao rDao = DB2021Team03_ReviewDao.getInstance();
                    //rDao 객체의 insert메서드 호출
                    int returnCnt = rDao.insert(movieTitle, nickname, Float.parseFloat(rating), detail);

                    if(returnCnt == 1) {
                        new DB2021Team03_Result(nickname);
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frame, "등록에 실패하였습니다. 다시 시도해 주세요.", "오류", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        
        // 이전으로
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnCd = JOptionPane.showConfirmDialog(frame, "메인 페이지로 돌아가시겠습니까?", "경고", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if(returnCd == JOptionPane.YES_OPTION) {
                    new DB2021Team03_Main(nickname);
                    frame.dispose();
                }
            }
        });

        frame.addMouseListener(new MouseListener() {
            public void mouseReleased(MouseEvent e) {}
            public void mousePressed(MouseEvent e) {
                frame.requestFocus();
            }
            public void mouseExited(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseClicked(MouseEvent e) {}
        });

        frame.setSize(426, 779);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void init() {
        backgroundPanel = new JPanel();
        frame.setContentPane(backgroundPanel);
        frame.setTitle("DB2021Team03-영화 정보 프로그램");

        DB2021Team03_CustomUI custom = new DB2021Team03_CustomUI(backgroundPanel);
        custom.setPanel();

        // 회원 아이디
        lbTitleNickname = custom.setLb("lbTitleNickname", "회원 이름", 35, 170, 100, 20, "left", 17, "bold");
        lbTitleUser = custom.setLb("lbTitleUser", nickname, 200, 170, 180, 20, "right", 17, "plain");

        // 영화 선택
        lbTitleMovie = custom.setLb("lbTitleMovie", "영화선택", 35, 240, 180, 20, "left", 17, "bold");

        DB2021Team03_ComboDao cDao = DB2021Team03_ComboDao.getInstance();
        Vector<Combo> comboMovies = cDao.setCombo("movie");
        comboMovie = custom.setCombo("combo", comboMovies, 235, 240, 150, 25);

        // 별점 선택
        lbTitleRating = custom.setLb("lbTitleRating", "평점입력", 35, 310, 100, 20, "left", 17, "bold");
        txtrating = custom.setTextField("txtRating", "0.0", 200, 310, 180, 20);

        // 리뷰 작성
        lbReview = custom.setLb("lbTitleCard", "리뷰작성", 35, 380, 100, 20, "left", 17, "bold");
        txtdetail = custom.setTextArea("txtRating", "후기를 남겨주세요", 200, 380, 180, 150, true);

        btnReview = custom.setBtnGreen("btnReview", "등록하기", 35, 600, 350, 40);
        btnBack = custom.setBtnWhite("btnBack", "이전으로", 35, 655);
    }
}