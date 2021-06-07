package gui.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dao.DB2021Team03_DBConnection;

@SuppressWarnings("serial")
public class DB2021Team03_Join extends DB2021Team03_CustomUI {
    // 회원가입을 위한 클래스

    private JFrame frame = new JFrame();
    private JPanel backgroundPanel;
    private JTextField txtUserId, txtNickName, txtPassword, txtName, txtcheck, txtbirth, txtphone;
    private JButton btnJoinComplete, btnPrev;
    private JCheckBox cbAgree;

    private static Connection conn;
    private static PreparedStatement pstmt;

    private static final String SQL = "INSERT INTO DB2021_User (ID, NICKNAME, PASSWORD, NAME, BIRTH, PHONE, JOIN_TIME, PRIVACY_FG, ADMIN_FG, DELETE_FG) VALUES (0, ?, ?, ?, ?, ?, now(), ?,'N','N')";
    private static final String SQL2 = "SELECT * FROM DB2021_User WHERE NICKNAME = ?";

    public DB2021Team03_Join() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();

        // 회원가입 버튼을 누르면 실행되는 검증들
        btnJoinComplete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 아이디 길이 검증
//                String userid = txtUserId.getText();
//                if (userid.length() < 8) {
//                    JOptionPane.showMessageDialog(null, "아이디는 8글자 이상 입력해주세요");
//                    txtUserId.setText("");
//                }

                //닉네임 길이 검증
                String nickname = txtNickName.getText();
                if (nickname.length() > 20) {
                    JOptionPane.showMessageDialog(null, "닉네임은 20글자 이하로 입력해주세요");
                    txtNickName.setText("");
                }

                // 비밀번호 길이 검증
                String password = txtPassword.getText();
                if (password.length() < 8) {
                    JOptionPane.showMessageDialog(null, "비밀번호는 8글자 이상 입력해주세요");
                    txtPassword.setText("");
                }

                // 비밀번호 확인 검증
                String passwordCheck = txtcheck.getText();
                if (!(password.equals(passwordCheck))) {
                    JOptionPane.showMessageDialog(null, "비밀번호가 동일하지 않습니다.");
                    txtPassword.setText("");
                    txtcheck.setText("");
                }

                // 이름 길이 검증
                String name = txtName.getText();
                if (name.length() >= 20) {
                    JOptionPane.showMessageDialog(null, "이름은 20글자 이하로 입력해주세요");
                    txtName.setText("");
                }
                
                // 생년월일 검증
                String regExp = "^(19[0-9][0-9]|20\\d{2})-(0[0-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])+$";
                String birth = txtbirth.getText();
                if (birth.length() != 10) {
                    JOptionPane.showMessageDialog(null, "생년월일은 10글자로 입력해주세요 ex)1999-01-01");

                    txtbirth.setText("");
                } else if (!(birth.matches(regExp))) {
                    JOptionPane.showMessageDialog(null, "생년월일은 1990-01-01형식으로만 입력할 수 있습니다");
                    txtbirth.setText("");
                }

                // 전화번호 검증
                String regExp2 = "^[0-9]+$";
                String phone = txtphone.getText();
                if (!(phone.length() == 10 || phone.length() == 11)) {
                    JOptionPane.showMessageDialog(null, "전화번호는 10-11자리만 가능합니다");
                    txtphone.setText("");
                } else if (!(phone.matches(regExp2))) {
                    JOptionPane.showMessageDialog(null, "전화번호는 숫자만 입력할 수 있습니다");
                    txtphone.setText("");
                }
                
                // 개인정보 동의 검증
                String privacyFg = "N";
                if (cbAgree.isSelected() == true) {
                    // 동의 시 Y
                    privacyFg = "Y";
                } else {
                    // 비동의 시 팝업
                    JOptionPane.showMessageDialog(null, "개인정보 수집 및 이용 약관에 동의해주세요");
                }


                // User 객체로 만들어서 User table에 insert
                conn = DB2021Team03_DBConnection.getConnection();

                // 아이디 중복 검증
                int checkId = 0;
                try {
                    pstmt = conn.prepareStatement(SQL2);
                    pstmt.setString(1, nickname);
                    checkId = pstmt.executeUpdate();
                    conn.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }

                if(checkId == 1) {
                    JOptionPane.showMessageDialog(null, "이미 사용중인 아이디 입니다.\n다른 아이디를 이용해주세요.");
                }
                //nickname.length() <= 20 &&
                // 비밀번호 검증
                if (password.length() >= 8 && password.equals(passwordCheck)
                        && birth.length() == 10 && (phone.length() == 10 || phone.length() == 11)
                        && cbAgree.isSelected() == true && birth.matches(regExp) && phone.matches(regExp2)
                        && checkId == 0) {

                    conn = DB2021Team03_DBConnection.getConnection();
                    int returnCnt = 0;
                    try {
                        pstmt = conn.prepareStatement(SQL);
                        pstmt.setString(1, nickname);
                        pstmt.setString(2, password);
                        pstmt.setString(3, name);
                        pstmt.setString(4, birth);
                        pstmt.setString(5, phone);
                        pstmt.setString(6, privacyFg);

                        returnCnt = pstmt.executeUpdate();
                        conn.close();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    
                    if(returnCnt == 1) {
                        // 성공적으로 회원가입이 됐을 때
                        JOptionPane.showMessageDialog(null, "회원가입 완료");
                        new DB2021Team03_Login();
                        frame.dispose();
                    } else {
                        // 실패 시 팝업
                        JOptionPane.showMessageDialog(null, "회원가입 실패, 다시 시도해 주세요.");
                    }
                }
            }
        });

        // 개인정보 수집 동의 팝업 Listener
        cbAgree.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String consent = "[필수] 개인정보 수집 및 이용 동의\r\n" + "\r\n" + "아래의 목적으로 개인정보를 수집 및 이용합니다\r\n" + "\r\n"
                        + "1. 목적 : 개인 식별, 프로그램 기능 사용\r\n" + "2. 항목 : 아이디, 휴대폰 번호, 생년월일\r\n"
                        + "3. 보유 기간 : 회원 탈퇴 후 3개월까지 보유\r\n" + "4. 개인정보의 수집 및 이용에 대한 동의를 거부할 수 있으나, 기능 사용이 제한됩니다.";
                int result = JOptionPane.showConfirmDialog(null, consent, "Confirm", JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    cbAgree.setSelected(true);
                } else if (result == JOptionPane.NO_OPTION) {
                    cbAgree.setSelected(false);
                }
            }
        });

        // 이전으로 가기 버튼 기능
        btnPrev.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new DB2021Team03_Login();
                frame.dispose();
            }
        });

        // frame에 마우스 Listener 추가
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

    // 회원가입 화면 구성을 위한 GUI 코드
    private void init() {
        backgroundPanel = new JPanel();
        frame.setContentPane(backgroundPanel);
        frame.setTitle("DB2021Team03-영화 정보 프로그램");

        DB2021Team03_CustomUI custom = new DB2021Team03_CustomUI(backgroundPanel);
        custom.setPanel();

        //txtUserId = custom.setTextField("txtUserId", "ID", 35, 160, 350, 40);
        txtNickName = custom.setTextField("txtNickName","NICKNAME", 35,160,350, 40);
        txtPassword = custom.setPasswordField("txtPassword", "PASSWORD", 35, 200, 350, 40);
        txtcheck = custom.setPasswordField("txtcheck", "PASSWORD", 35, 240, 350, 40);
        txtName = custom.setTextField("txtName", "NAME",35,280,280, 40);
        txtbirth = custom.setTextField("txtbirth", "BIRTH", 35, 320, 350, 40);
        txtphone = custom.setTextField("txtphone", "PHONE", 35, 360, 350, 40);
        cbAgree = custom.setCheckBox("cbAgree", " ARE YOU AGREE?", 35, 400);

        btnJoinComplete = custom.setBtnGreen("btnBlue", "회원가입완료", 35, 550, 350, 40);
        btnPrev = custom.setBtnWhite("btnWhite", "이전으로", 35, 605);
    }
}