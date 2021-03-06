package gui.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dao.DB2021Team03_ReviewDao;
import models.Reviews;


/**
 * 리뷰를 작성, 수정하고 난 후 결과를 보여주기 위한 클래스
 */
@SuppressWarnings("serial")
public class DB2021Team03_Result extends DB2021Team03_CustomUI {

    private JFrame frame = new JFrame();
    private JPanel backgroundPanel;
    private JLabel lbIcon, lbTitle;
    private JLabel lbTitleMovie, lbMovie, lbTitleDate, lbDate, lbTitleRating, lbRating, lbTitleDetail, lbDetail;
    private JButton btnMain,btnReviews;

    private String nickname;
    private int reviewId;

    /**
     * 검색 결과 화면 생성자
     *
     * @param nickname 로그인 유지를 위한 사용자 nickname
     */
    public DB2021Team03_Result(String nickname) {
        this.nickname = nickname;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();

        //ReviewDao 객체를 통해 해당 user가 작성한 모든 review를 불러옴,
        DB2021Team03_ReviewDao rDao = DB2021Team03_ReviewDao.getInstance();
        Reviews review = rDao.reviewDetail(nickname);
        System.out.println(review.getMovie());

        lbMovie.setText(review.getMovie());

        lbDate.setText(review.getCreate_time());
        lbRating.setText(Float.toString(review.getRating()));
        lbDetail.setText(review.getDetail());

        //메인화면으로 이동하는 버튼
        btnMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DB2021Team03_Main(nickname);
                frame.dispose();
            }
        });
        //내가 쓴 리뷰로 이동하는 버튼
        btnReviews.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new DB2021Team03_MyReviews(nickname);
                frame.dispose();
            }
        });

        frame.setSize(426, 779);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    /**
     * 리뷰를 수정하고난 후 결과를 보여주는 생성자
     *
     * @param nickname 로그인 유지를 위한 사용자 nickname
     * @param reviewId 리뷰 pk
     */
    public DB2021Team03_Result(String nickname, int reviewId) {
        this.nickname = nickname;
        this.reviewId = reviewId;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();

        //리뷰를 수정하고나서의 결과를 보여주는 기능
        DB2021Team03_ReviewDao rDao = DB2021Team03_ReviewDao.getInstance();
        Reviews review = rDao.reviewDetail(reviewId);
        System.out.println(review.getMovie());
        lbMovie.setText(review.getMovie());

        lbDate.setText(review.getCreate_time());
        lbRating.setText(Float.toString(review.getRating()));
        lbDetail.setText(review.getDetail());

        btnMain.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new DB2021Team03_Main(nickname);
                frame.dispose();
            }
        });

        //내가 쓴 리뷰들로 이동
        btnReviews.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new DB2021Team03_MyReviews(nickname);
                frame.dispose();
            }
        });

        frame.setSize(426, 779);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    /**
     * 리뷰 등록 결과 상세화면을 위한 GUI코드
     */
    private void init() {
        backgroundPanel = new JPanel();
        frame.setContentPane(backgroundPanel);
        frame.setTitle("DB2021Team03-영화 정보 프로그램");
        DB2021Team03_CustomUI custom = new DB2021Team03_CustomUI(backgroundPanel);
        custom.setPanel();

        lbIcon = custom.setLbImg("lbIcon", 0, 160, 130);
        lbTitle = custom.setLb("lbTitle", "리뷰가 등록되었습니다", 100, 150, 220, 185, "center", 20, "bold");

        lbTitleMovie = custom.setLb("lbTitleMovie", "영화제목", 35, 310, 100, 20, "left", 17, "bold");
        lbMovie = custom.setLb("lbMovie", "영화를 입력하세요", 195, 310, 180, 20, "right", 17, "plain");

        lbTitleDate = custom.setLb("lbTitleDate", "리뷰날짜", 35, 360, 100, 20, "left", 17, "bold");
        lbDate = custom.setLb("lbDate", "00-00-00 00:00:00", 195, 360, 180, 20, "right", 17, "plain");

        lbTitleRating = custom.setLb("lbTitleRating", "평점", 35, 410, 100, 20, "left", 17, "bold");
        lbRating = custom.setLb("lbRating", "0.0", 195, 410, 180, 20, "right", 17, "plain");

        lbTitleDetail = custom.setLb("lbTitleDetail", "후기", 35, 460, 100, 20, "left", 17, "bold");
        lbDetail = custom.setLb("lbDetail", "후기를 입력하세요.", 195, 460, 180, 20, "right", 17, "plain");

        btnReviews = custom.setBtnGreen("btnReviews", "내가 쓴 리뷰들", 35, 600, 350, 40);
        btnMain = custom.setBtnGreen("btnMain", "메인으로", 35, 655, 350, 40);
    }
}