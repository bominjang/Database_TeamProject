package gui.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Vector;

import javax.swing.*;

import dao.DB2021Team03_ActorDao;
import models.Actors;

/**
 * 배우에 대한 상세 정보를 볼 수 있는 클래스
 */
@SuppressWarnings("serial")
public class DB2021Team03_Actor extends DB2021Team03_CustomUI {

    private JFrame frame = new JFrame();
    private JPanel backgroundPanel;
    private JLabel lbIcon, lbName, lbTitleCountry, lbTitleBirth, lbTitleMovies, lbTitlePrize;
    private JLabel lbCountry, lbBirth, lbMovies[], lbPrizes[];
    private JButton btnMain, btnRanking;

    private Vector<String> actorMovies;
    private Map<String, Vector<String>> actorPrizes;

    private String nickname;

    /**
     * 해당 Actor의 상세 정보 화면 생성자
     *
     * @param nickname 로그인 유지를 위한 사용자 nickname
     * @param actorId 배우 pk
     */
    public DB2021Team03_Actor(String nickname, int actorId) {
        this.nickname = nickname;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ActorDao 객체를 이용해 actorId로 해당 actor의 객체를 가져온다.
        DB2021Team03_ActorDao aDao = DB2021Team03_ActorDao.getInstance();
        Actors actor = aDao.selectOne(actorId);

        // 배우가 출연한 영화 리스트 set
        actorMovies = aDao.selectMovies(actor.getName());
        lbMovies = new JLabel[actorMovies.size()];

        // 배우의 수상 이력 리스트 set
        actorPrizes = aDao.selectPrizes(actorId);

        // GUI 호출
        init();

        // 배우의 이름 set
        lbName.setText(actor.getName());

        // 배우의 출신국가 set
        lbCountry.setText(actor.getCountry());

        // 배우의 생년월일 set
        SimpleDateFormat transFormat = new SimpleDateFormat("YYYY-MM-DD");
        String birth;
        if(actor.getBirth() == null){
            birth="unknown";
        } else {
            birth=transFormat.format(actor.getBirth());
        }
        lbBirth.setText(birth);


        // 영화 목록(순위)으로 이동하기 버튼
        btnRanking.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnCd = JOptionPane.showConfirmDialog(frame, "영화 목록 화면로 돌아가시겠습니까?", "경고", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (returnCd == JOptionPane.YES_OPTION) {
                    new DB2021Team03_Ranking(nickname);
                    frame.dispose();
                }
            }
        });

        // 메인으로 이동하기 버튼
        btnMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DB2021Team03_Main(nickname);
                frame.dispose();
            }
        });

        frame.setSize(600, 900);
        frame.setResizable(false);
        frame.setVisible(true);

        frame.setSize(600, 900);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    /**
     * 배우 상세 화면 구성을 위한 GUI 코드
     */
    private void init() {
        backgroundPanel = new JPanel();
        frame.setContentPane(backgroundPanel);
        frame.setTitle("DB2021Team03-영화 정보 프로그램");
        DB2021Team03_CustomUI custom = new DB2021Team03_CustomUI(backgroundPanel);
        custom.setPanel();

        lbIcon = custom.setLbImg("lbIcon", 0, 247, 130);
        lbName = custom.setLb("lbName", "배우이름", 0, 150, 600, 185, "center", 20, "bold");

        lbTitleCountry = custom.setLb("lbTitleCountry", "출신국가", 150, 300, 150, 20, "left", 17, "bold");
        lbCountry = custom.setLb("lbCountry", "출신 국가", 319, 300, 200, 20, "left", 17, "plain");

        lbTitleBirth = custom.setLb("lbTitleBirth", "생년월일", 150, 330, 150, 20, "left", 17, "bold");
        lbBirth = custom.setLb("lbBirth", "00-00-00", 319, 330, 200, 20, "left", 17, "plain");

        lbTitleMovies = custom.setLb("lbTitleMovies", "필모그라피", 150, 360, 200, 20, "left", 17, "bold");

        // 배우가 출연한 영화들을 가져온다
        if(actorMovies == null) {
            custom.setLb("lbMovies", "없음", 319, 360, 200, 20, "left", 17, "plain");
        }
        else{
            for (int i = 0; i < actorMovies.size(); i++) {
                String movie = actorMovies.get(i);

                lbMovies[i] = custom.setLb("lbMovies", "영화(연도)", 319, 360 + (20 * i), 200, 20, "left", 17, "plain");
                lbMovies[i].setText(movie);
            }
        }

        // 배우의 수상 이력들을 가져온다
        lbTitlePrize = custom.setLb("lbTitlePrize", "수상이력", 150, 450, 200, 20, "left", 17, "bold");
        if(actorPrizes == null) {
            custom.setLb("lbPrizes", "없음", 319, 450, 200, 20, "left", 17, "plain");
        }
        else {
            int i = 0;
            for (String mapkey : actorPrizes.keySet()){
                custom.setLb("lbMovies", mapkey, 319, 450 + (20 * i), 200, 20, "left", 17, "plain");

                Vector<String> prizes = actorPrizes.get(mapkey);
                for(String prize : prizes) { //for문을 통한 전체출력
                    ++i;
                    custom.setLb("lbMovies", "- "+prize, 319, 450 + (20 * i), 200, 20, "left", 17, "plain");
                }
                ++i;
            }
        }

        btnRanking = custom.setBtnWhite("btnBack", "영화 목록으로", 120, 680);
        btnMain = custom.setBtnGreen("btnMain", "메인으로", 120, 730, 350, 40);
    }
}