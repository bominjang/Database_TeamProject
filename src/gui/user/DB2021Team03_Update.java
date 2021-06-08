package gui.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import dao.DB2021Team03_ReviewDao;
import models.Reviews;


/**
 * 리뷰 수정을 위한 클래스
 */
@SuppressWarnings("serial")
public class DB2021Team03_Update extends DB2021Team03_CustomUI {

    private JFrame frame = new JFrame();
    private JPanel backgroundPanel;
    private JLabel lbIcon, lbTitle;
    private JLabel lbTitleMovie, lbMovie, lbTitleDate, lbDate, lbTitleRating, lbTitleDetail;
    private JButton btnBack, btnUpdate;
    private JTextField lbRating;
    private JTextArea lbDetail;

    private String nickname;
    private int reviewId;

    /**
     * 리뷰 업데이트 화면 생성자
     *
     * @param nickname 로그인 유지를 위한 사용자 nickname
     * @param reviewId 리뷰 pk
     * @param mv 영화 제목
     */
    public DB2021Team03_Update(String nickname, int reviewId, String mv) {
        this.nickname = nickname;
        this.reviewId = reviewId;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();

        //ReviewDao 객체를 이용하여 reviewId에 해당하는 리뷰 내용을 불러온다.
        DB2021Team03_ReviewDao rDao = DB2021Team03_ReviewDao.getInstance();
        Reviews review = rDao.reviewDetail(reviewId);
        System.out.println(review.getMovie());
        lbMovie.setText(review.getMovie());

        lbDate.setText(review.getCreate_time());
        lbRating.setText(Float.toString(review.getRating()));
        lbDetail.setText(review.getDetail());

        //이전으로 이동하는 버튼
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new DB2021Team03_MyReviews(nickname);
                frame.dispose();
            }
        });
        //수정을 완료하는 버튼
        btnUpdate.addActionListener(new ActionListener() {
            int result=0;
            public void actionPerformed(ActionEvent e) {
                //사용자가 수정한 rating과, 리뷰내용을 인자로 하여, rDao 객체의 update메서드를 호출.
                result = rDao.update(reviewId, mv, Float.parseFloat(lbRating.getText()),lbDetail.getText());
                new DB2021Team03_Result(nickname, reviewId);
                frame.dispose();

                if (result == -1) {
                    JOptionPane.showMessageDialog(frame, "ER5:데이터를 수정할 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                } else if (result == 0) {
                    JOptionPane.showMessageDialog(frame, "ER6:데이터를 수정할 수 없습니다.\n이미 존재하는 데이터일 수 있습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "데이터 수정이 완료되었습니다.", "완료", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                }
            }
        });

        frame.setSize(426, 779);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    /**
     * 리뷰 수정 화면 구성을 위한 GUI 코드
     */
    private void init() {
        backgroundPanel = new JPanel();
        frame.setContentPane(backgroundPanel);
        frame.setTitle("DB2021Team03-영화 정보 프로그램");

        DB2021Team03_CustomUI custom = new DB2021Team03_CustomUI(backgroundPanel);
        custom.setPanel();

        lbIcon = custom.setLbImg("lbIcon", 0, 160, 130);
        lbTitle = custom.setLb("lbTitle", "리뷰 수정", 100, 150, 220, 185, "center", 20, "bold");

        lbTitleMovie = custom.setLb("lbTitleMovie", "영화제목", 35, 310, 100, 20, "left", 17, "bold");
        lbMovie = custom.setLb("lbMovie", "영화를 입력하세요", 195, 310, 180, 20, "right", 17, "plain");

        lbTitleDate = custom.setLb("lbTitleDate", "리뷰날짜", 35, 360, 100, 20, "left", 17, "bold");
        lbDate = custom.setLb("lbDate", "00-00-00 00:00:00", 195, 360, 180, 20, "right", 17, "plain");

        lbTitleRating = custom.setLb("lbTitleRating", "평점", 35, 410, 100, 20, "left", 17, "bold");
        lbRating = custom.setTextField("lbRating", "0.0", 195, 410, 180, 20);

        lbTitleDetail = custom.setLb("lbTitleDetail", "후기", 35, 460, 100, 20, "left", 17, "bold");
        lbDetail = custom.setTextArea("lbDetail", "", 195, 460, 180, 100, true);

        btnUpdate = custom.setBtnGreen(reviewId+"", "수정 완료",35, 600, 350, 40);
        btnBack = custom.setBtnGreen("btnMain", "취소하기", 35, 655, 350, 40);
    }
}