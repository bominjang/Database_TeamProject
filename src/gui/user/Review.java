package gui.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.NumberFormat;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import dao.ComboDao;
//import dao.MovieDao;
//import dao.ReserveDao;
import dao.ReviewDao;
import models.Combo;
import models.Movies;
//import util.Utils;

@SuppressWarnings("serial")
public class Review extends CustomUI {

    private JFrame frame = new JFrame();
    private JPanel backgroundPanel;
    private JLabel lbTitleNickname, lbTitleUser, lbTitleRating, lbReview, lbTitlePrice, lbPrice, lbTitleMovie, lbTitleResult, lbResult, lbTitleCard, lbTitlePassword;
    private JComboBox<Combo> comboMovie;
    private JTextField txtCard1;
    private String detail;
    private float rating;
    private JPasswordField txtCard2, txtCard3, txtPassword;
    private JButton btnReview, btnBack;

    private String nickname, reserveDate, reserveTime, beforePage;
    private int movieId, placeId, theaterId;

    public Review(String nickname) {
        this.nickname = nickname;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();

        // 결제 금액(instance에 저장된 값을 보여줄 때 프로세스)
//        MovieDao mDao = MovieDao.getInstance();
//        Movies movie = mDao.selectPrice(movieId);
//
//        lbPrice.setText(NumberFormat.getInstance().format(movie.getPrice()) + "원 x " + reserveCnt + "인");
//        lbResult.setText(NumberFormat.getInstance().format(movie.getPrice() * reserveCnt)+"원");

        // 할인 항목(dropbox로 저장된 값을 보여줄 때)
        comboMovie.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Combo selectedComboItem = (Combo) comboMovie.getSelectedItem();
                System.out.println("a: "+selectedComboItem);
                String splitComboItem[] = selectedComboItem.toString().split("\\(");
                System.out.println("b: "+splitComboItem);
                String discountContent = splitComboItem[1].replace(")", "");
                String discountUnit = discountContent.substring(discountContent.length()-1, discountContent.length());
                int discountVal = Integer.parseInt(discountContent.replace(discountUnit, ""));
                String priceText = lbPrice.getText().replace(",", "").replace("원", "").replace(" x ", "").replace("인", "");
                priceText = priceText.substring(0, priceText.length()-1);
                int price = Integer.parseInt(priceText);

//                if(discountUnit.equals("원")) {
//                    price = (price - discountVal) * reserveCnt;
//                } else if(discountUnit.equals("%")) {
//                    double discountRate = Double.parseDouble(discountVal+"") / 100;
//                    double discountPrice = price * (1-discountRate);
//                    price = (int)discountPrice * reserveCnt;
//                } else {
//                    price = price * reserveCnt;
//                }
                lbResult.setText(NumberFormat.getInstance().format(price)+"원");
            }
        });
//
//        // 카드 번호
//        Utils util = new Utils();
//        util.restrictNumber(txtCard1, 4);
//        util.restrictNumber(txtCard2, 4);
//        util.restrictNumber(txtCard3, 4);
//        util.restrictNumber(txtCard4, 4);
//
        // 등록하기
        btnReview.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnCd = JOptionPane.showConfirmDialog(frame, "등록하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if(returnCd == JOptionPane.YES_OPTION) {
//                    int finalPrice = Integer.parseInt(lbResult.getText().replace(",", "").replace("원", ""));
                    Combo movie = (Combo) comboMovie.getSelectedItem();
                    int movieId = movie.getKey();

                    // 카드번호 검사
//                    int check = 0;
//                    check = checkField();

//                    if(check == 0) {
//                        String cardNo = txtCard1.getText() + "" + String.valueOf(txtCard2.getPassword()) + "" + String.valueOf(txtCard3.getPassword()) + "" + txtCard4.getText();

//                        ReserveDao rDao = ReserveDao.getInstance();
//                        int returnCnt = rDao.insert(nickname, movieId, placeId, theaterId, reserveDate, reserveTime, reserveCnt, seat, finalPrice, discountId, cardNo);
                    ReviewDao rDao = ReviewDao.getInstance();

                    int returnCnt = rDao.insert(nickname, movieId, rating, detail);

                    if(returnCnt == 1) {
                        new Result(nickname);
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frame, "등록에 실패하였습니다. 다시 시도해 주세요.", "오류", JOptionPane.ERROR_MESSAGE);
                    }
//                    }
                }
            }
        });
        
        // 이전으로
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnCd = JOptionPane.showConfirmDialog(frame, "메인 페이지로 돌아가시겠습니까?", "경고", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if(returnCd == JOptionPane.YES_OPTION) {
                    new Main(nickname);
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
        frame.setTitle("영화 예매 프로그램 ver1.0");

        CustomUI custom = new CustomUI(backgroundPanel);
        custom.setPanel();

        // 회원 아이디
        lbTitleNickname = custom.setLb("lbTitleNickname", "회원 이름", 35, 170, 100, 20, "left", 17, "bold");
        lbTitleUser = custom.setLb("lbTitleUser", nickname, 200, 170, 180, 20, "right", 17, "plain");

//        lbTitlePrice = custom.setLb("lbTitlePrice", "결제금액", 35, 200, 100, 20, "left", 17, "bold");
//        lbPrice = custom.setLb("lbPrice", "14,000원", 200, 200, 180, 20, "right", 17, "plain");

        // 영화 선택
        lbTitleMovie = custom.setLb("lbTitleMovie", "영화선택", 35, 240, 180, 20, "left", 17, "bold");

        ComboDao cDao = ComboDao.getInstance();
        Vector<Combo> comboMovies = cDao.setCombo("movie");
        comboMovie = custom.setCombo("combo", comboMovies, 235, 240, 150, 25);

        // 별점 선택
        lbTitleRating = custom.setLb("lbTitleRating", "평점입력", 35, 310, 100, 20, "left", 17, "bold");
        rating = custom.setFloatField("txtRating", "0.0", 200, 310, 180, 20);

        // 리뷰 작성
        lbReview = custom.setLb("lbTitleCard", "리뷰작성", 35, 380, 100, 20, "left", 17, "bold");
        detail = custom.setTextField("txtRating", "후기를 남겨주세요", 200, 380, 180, 150).getText();

//        lbTitleResult = custom.setLb("lbTitleResult", "최종금액", 35, 340, 100, 20, "left", 17, "bold");
//        lbResult = custom.setLb("lbText3", "7,000원", 200, 340, 180, 20, "right", 17, "plain");
//
//        lbTitleCard = custom.setLb("lbTitleCard", "카드번호", 35, 410, 100, 20, "left", 17, "bold");
//        txtCard1 = custom.setTextField("txtCard1", "****", 170, 408, 50, 25);
//        txtCard2 = custom.setPasswordField("txtCard2", "****", 225, 408, 50, 25);
//        txtCard3 = custom.setPasswordField("txtCard3", "****", 280, 408, 50, 25);
//        txtCard4 = custom.setTextField("txtCard4", "****", 335, 408, 50, 25);
//
//        lbTitlePassword = custom.setLb("lbTitlePassword", "비밀번호", 35, 510, 100, 20, "left", 17, "bold");
//        txtPassword = custom.setPasswordField("txtPassword", "", 200, 508, 180, 25);

        btnReview = custom.setBtnBlue("btnReview", "등록하기", 600);
        btnBack = custom.setBtnWhite("btnBack", "이전으로", 655);
    }

//    private int checkField() {
//        int check = 0;
//        int len1 = txtCard1.getText().length();
//        int len2 = String.valueOf(txtCard2.getPassword()).length();
//        int len3 = String.valueOf(txtCard3.getPassword()).length();
//        int len4 = txtCard4.getText().length();
//        int len5 = String.valueOf(txtPassword.getPassword()).length();
//
//        if(!(len1 == 4)) {
//            JOptionPane.showMessageDialog(frame, "카드입력란 1번의 자리수가 맞지 않습니다.", "오류", JOptionPane.ERROR_MESSAGE);
//            check = 1;
//        } else if(!(len2 == 4)) {
//            JOptionPane.showMessageDialog(frame, "카드입력란 2번의 자리수가 맞지 않습니다.", "오류", JOptionPane.ERROR_MESSAGE);
//            check = 1;
//        } else if(!(len3 == 4)) {
//            JOptionPane.showMessageDialog(frame, "카드입력란 3번의 자리수가 맞지 않습니다.", "오류", JOptionPane.ERROR_MESSAGE);
//            check = 1;
//        } else if(!(len4 == 4)) {
//            JOptionPane.showMessageDialog(frame, "카드입력란 4번의 자리수가 맞지 않습니다.", "오류", JOptionPane.ERROR_MESSAGE);
//            check = 1;
//        if(len5 == 0) {
//            JOptionPane.showMessageDialog(frame, "카드 비밀번호를 입력해주세요.", "오류", JOptionPane.ERROR_MESSAGE);
//            check = 1;
//        }
//        return check;
//    }
}