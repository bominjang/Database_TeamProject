package gui.user;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.Border;

import models.Combo;
import models.Keyword;

@SuppressWarnings("serial")
class CustomUI extends JFrame {
    JPanel backgroundPanel;

    public CustomUI() {}

    public CustomUI(JPanel backgroundPanel) {
        this.backgroundPanel = backgroundPanel;
    }

    protected void setPanel() {
        backgroundPanel.setLayout(null);
        backgroundPanel.setBackground(Color.WHITE);

        JPanel topBluePanel = new JPanel();
        topBluePanel.setBounds(0, 0, 800, 70);
        topBluePanel.setBackground(new Color(0, 70, 42));

        backgroundPanel.add(topBluePanel);

        JPanel topGrayPanel = new JPanel();
        topGrayPanel.setBounds(0, 70, 800, 50);
        topGrayPanel.setBackground(new Color(230, 236, 240));
        backgroundPanel.add(topGrayPanel);
    }

    protected JTextField setTextField(String name, String placeholder, int x, int y, int width, int height) {
        JTextField txt = new JTextField();

        if (placeholder == null) {
            txt.setText("Please input here");
        } else {
            txt.setText(placeholder);
        }

        Font tfFont = new Font("Arial", Font.PLAIN, 20);
        txt.setFont(tfFont);
        txt.setBackground(Color.white);
        txt.setForeground(Color.gray.brighter());

        txt.addFocusListener(new FocusListener() {
            public void focusLost(FocusEvent e) {
                JTextField tf = (JTextField)e.getComponent();
                if(tf.getText().equals("")) {
                    if (placeholder == null) {
                        tf.setForeground(Color.gray.brighter());
                        tf.setText("Please input here");
                    } else {
                        tf.setForeground(Color.gray.brighter());
                        tf.setText(placeholder);
                    }
                }
            }
            public void focusGained(FocusEvent e) {
                JTextField tf = (JTextField)e.getComponent();
                if (tf.getText().equals(placeholder) || tf.getText().equals("Please input here") || tf.getText().equals("")) {
                    tf.setText("");
                    tf.setForeground(Color.BLACK);
                }
            }
        });

        txt.setBounds(x, y, width, height);
        backgroundPanel.add(txt);
        txt.setName(name);

        return txt;
    }

    protected float setFloatField(String name, String placeholder, int x, int y, int width, int height) {
        JTextField txt = new JTextField();

        if (placeholder == null) {
            txt.setText("Please input here");
        } else {
            txt.setText(placeholder);
        }

        Font tfFont = new Font("Arial", Font.PLAIN, 20);
        txt.setFont(tfFont);
        txt.setBackground(Color.white);
        txt.setForeground(Color.gray.brighter());

        txt.addFocusListener(new FocusListener() {
            public void focusLost(FocusEvent e) {
                JTextField tf = (JTextField)e.getComponent();
                if(tf.getText().equals("")) {
                    if (placeholder == null) {
                        tf.setForeground(Color.gray.brighter());
                        tf.setText("Please input here");
                    } else {
                        tf.setForeground(Color.gray.brighter());
                        tf.setText(placeholder);
                    }
                }
            }
            public void focusGained(FocusEvent e) {
                JTextField tf = (JTextField)e.getComponent();
                if (tf.getText().equals(placeholder) || tf.getText().equals("Please input here") || tf.getText().equals("")) {
                    tf.setText("");
                    tf.setForeground(Color.BLACK);
                }
            }
        });

        txt.setBounds(x, y, width, height);
        backgroundPanel.add(txt);
        txt.setName(name);

        return Float.parseFloat(txt.getText());
    }

    protected JPasswordField setPasswordField(String name, String placeholder, int x, int y, int width, int height) {
        JPasswordField txt = new JPasswordField();

        if (placeholder == null) {
            txt.setText("Please input here");
        } else {
            txt.setText(placeholder);
        }

        Font tfFont = new Font("Arial", Font.PLAIN, 20);
        txt.setFont(tfFont);
        txt.setBackground(Color.white);
        txt.setForeground(Color.gray.brighter());

        txt.addFocusListener(new FocusListener() {
            public void focusLost(FocusEvent e) {
                JTextField tf = (JTextField)e.getComponent();
                if(tf.getText().equals("")) {
                    if (placeholder == null) {
                        tf.setForeground(Color.gray.brighter());
                        tf.setText("Please input here");
                    } else {
                        tf.setForeground(Color.gray.brighter());
                        tf.setText(placeholder);
                    }
                }
            }
            public void focusGained(FocusEvent e) {
                JTextField tf = (JTextField)e.getComponent();
                if (tf.getText().equals(placeholder) || tf.getText().equals("Please input here") || tf.getText().equals("")) {
                    tf.setText("");
                    tf.setForeground(Color.BLACK);
                }
            }
        });

        txt.setBounds(x, y, width, height);
        backgroundPanel.add(txt);
        txt.setName(name);

        return txt;
    }

    protected JButton setBtnGreen(String name, String text, int x, int y, int width, int height) {

        class RoundedButton extends JButton {
            public RoundedButton() {
                super();
                decorate();
            }

            protected void decorate() {
                setBorderPainted(false);
                setOpaque(false);
            }

            protected void paintComponent(Graphics g) {
                int width = getWidth();
                int height = getHeight();
                Graphics2D graphics = (Graphics2D) g;
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isArmed()) {
                    graphics.setColor(getBackground().darker());
                } else if (getModel().isRollover()) {
                    graphics.setColor(getBackground().brighter());
                } else {
                    graphics.setColor(getBackground());
                }
                graphics.fillRoundRect(0, 0, width, height, 15, 15);
                FontMetrics fontMetrics = graphics.getFontMetrics();
                Rectangle stringBounds = fontMetrics.getStringBounds(this.getText(), graphics).getBounds();
                int textX = (width - stringBounds.width) / 2;
                int textY = (height - stringBounds.height) / 2 + fontMetrics.getAscent();
                graphics.setColor(getForeground());
                graphics.setFont(getFont());
                graphics.drawString(getText(), textX, textY);
                super.paintComponent(g);
            }
        }

        RoundedButton btn = new RoundedButton();
        btn.setBackground(new Color(0, 70, 42));

        Font btnFont;
        if(text.equals("삭제하기"))
        {
            btnFont = new Font("맑은 고딕", Font.PLAIN, 14);

        }
        else{
            btnFont = new Font("맑은 고딕", Font.PLAIN, 20);
        }
        btn.setFont(btnFont);

        btn.setBackground(new Color(0, 70, 42));
        btn.setForeground(Color.WHITE);
        btn.setBounds(x, y, width, height);

        btn.setText(text);
        backgroundPanel.add(btn);
        btn.setName(name);

        return btn;
    }


    protected JButton setBtnGreen(String name, String text, int x, int y, String alignment, int width, int height) {
        class RoundedButton extends JButton {
            public RoundedButton() {
                super();
                decorate();
            }

            protected void decorate() {
                setBorderPainted(false);
                setOpaque(false);
            }

            protected void paintComponent(Graphics g) {
                int width = getWidth();
                int height = getHeight();
                Graphics2D graphics = (Graphics2D) g;
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isArmed()) {
                    graphics.setColor(getBackground().darker());
                } else if (getModel().isRollover()) {
                    graphics.setColor(getBackground().brighter());
                } else {
                    graphics.setColor(getBackground());
                }
                graphics.fillRoundRect(0, 0, width, height, 15, 15);
                FontMetrics fontMetrics = graphics.getFontMetrics();
                Rectangle stringBounds = fontMetrics.getStringBounds(this.getText(), graphics).getBounds();
                int textX = (width - stringBounds.width) / 2;
                int textY = (height - stringBounds.height) / 2 + fontMetrics.getAscent();
                graphics.setColor(getForeground());
                graphics.setFont(getFont());
                graphics.drawString(getText(), textX, textY);
                super.paintComponent(g);
            }
        }

        RoundedButton btn = new RoundedButton();
        btn.setBackground(new Color(0, 70, 42));

        Font btnFont;
        if(text.equals("삭제하기"))
        {
            btnFont = new Font("맑은 고딕", Font.PLAIN, 14);

        }
        else{
            btnFont = new Font("맑은 고딕", Font.PLAIN, 20);
        }
        btn.setFont(btnFont);

        btn.setBackground(new Color(0, 70, 42));
        btn.setForeground(Color.WHITE);
        btn.setHorizontalAlignment(setAlign(alignment));
        btn.setBounds(x, y, width, height);

        btn.setText(text);
        backgroundPanel.add(btn);
        btn.setName(name);

        return btn;
    }

    protected JButton setBtnWhite(String name, String text, int x, int y) {

        class RoundedBorder implements Border {
            int radius;

            RoundedBorder(int radius) {
                this.radius = radius;
            }

            public Insets getBorderInsets(Component c) {
                return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
            }

            public boolean isBorderOpaque() {
                return true;
            }

            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            }
        }

        JButton btn = new JButton();
        btn.setBorder(new RoundedBorder(15));
        Font btnFont = new Font("맑은 고딕", Font.PLAIN, 20);
        btn.setFont(btnFont);
        btn.setBackground(Color.WHITE);
        btn.setForeground(new Color(0, 70, 42));
        btn.setBounds(x, y, 350, 45);

        btn.setText(text);
        backgroundPanel.add(btn);
        btn.setName(name);

        return btn;
    }

    protected JButton setBtnMovie(String name, String time, String seatCnt, int x, int y) {
        JButton btn = new JButton("<html>" + time + "<br/>" + seatCnt + "</html>");

        btn.setBackground(new Color(230, 236, 240));
        btn.setForeground(new Color(114, 114, 114));
        btn.setBorderPainted(false);
        btn.setBounds(x, y, 90, 70);

        backgroundPanel.add(btn);
        btn.setName(name);

        return btn;
    }

    protected JButton setBtnSeat(String name, String seat, int x, int y) {
        JButton btn = new JButton(seat);

        Font btnFont = new Font("맑은 고딕", Font.BOLD, 14);
        btn.setFont(btnFont);
        btn.setBackground(new Color(230, 236, 240));
        btn.setForeground(new Color(114, 114, 114));
        btn.setBorderPainted(false);
        btn.setBounds(x, y, 53, 48);
        backgroundPanel.add(btn);
        btn.setName(name);

        return btn;
    }

    protected JButton setbtnBar(String name, String text, int y) {
        JButton btn = new JButton();

        Font btnFont = new Font("맑은 고딕", Font.BOLD, 14);
        btn.setFont(btnFont);
        btn.setBackground(new Color(230, 236, 240));
        btn.setForeground(new Color(114, 114, 114));
        btn.setBorderPainted(false);
        btn.setBounds(45, y, 334, 40);
        btn.setText(text);
        backgroundPanel.add(btn);
        btn.setName(name);

        return btn;
    }

    protected JButton  setBtnImg(String name, String text, int x, int y) {
        ImageIcon icon = new ImageIcon("img/icon5.png");
        Image originImg = icon.getImage();
        Image changedImg= originImg.getScaledInstance(170, 140, Image.SCALE_SMOOTH );
        ImageIcon Icon = new ImageIcon(changedImg);

        JButton btn = new JButton(text, Icon);

        Font btnFont = new Font("맑은 고딕", Font.PLAIN, 18);
        btn.setFont(btnFont);
        btn.setBackground(new Color(0, 70, 42));
        btn.setForeground(new Color(0, 70, 42));
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);

        btn.setBorderPainted(false);
        btn.setBounds(x, y, 170, 180);
        btn.setText(text);
        backgroundPanel.add(btn);
        btn.setName(name);

        return btn;
    }

    protected JTextArea setTextArea(String name, String placeholder, int x, int y, int width, int height, boolean isEditable) {
        JTextArea txt = new JTextArea();
        txt.setEditable(isEditable);

        //행 넘기기 기능(line wrapping)을 킨다.
        txt.setLineWrap(true);
        
        // 내용 추가될 때 마다 스크롤 내리지 않고 바로 보기
        txt.setCaretPosition(txt.getDocument().getLength());

        if (placeholder == null) {
            txt.setText("Please input here");
        } else {
            txt.setText(placeholder);
        }

        Font tfFont = new Font("Arial", Font.PLAIN, 20);
        txt.setFont(tfFont);
        txt.setBackground(Color.white);
        txt.setForeground(Color.gray.brighter());

        txt.addFocusListener(new FocusListener() {
            public void focusLost(FocusEvent e) {
                JTextArea ta = (JTextArea)e.getComponent();
                if(ta.getText().equals("")) {
                    if (placeholder == null) {
                        ta.setForeground(Color.gray.brighter());
                        ta.setText("Please input here");
                    } else {
                        ta.setForeground(Color.gray.brighter());
                        ta.setText(placeholder);
                    }
                }
            }
            public void focusGained(FocusEvent e) {
                JTextArea ta = (JTextArea)e.getComponent();
                if (ta.getText().equals(placeholder) || ta.getText().equals("Please input here") || ta.getText().equals("")) {
                    ta.setText("");
                    ta.setForeground(Color.BLACK);
                }
            }
        });

        Border border = BorderFactory.createLineBorder(Color.gray);
        txt.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        txt.setBounds(x, y, width, height);

        backgroundPanel.add(txt);

        txt.setName(name);

        return txt;
    }

    protected JLabel setLb(String name, String text, int x, int y, int width, int height, String alignment, int fontSize, String weight) {
        JLabel lb = new JLabel(text);
        Font lbFont = new Font("맑은 고딕", setWeight(weight), fontSize);
        lb.setFont(lbFont);
        lb.setForeground(new Color(114, 114, 114));
        lb.setHorizontalAlignment(setAlign(alignment));
        lb.setBounds(x, y, width, height);
        backgroundPanel.add(lb);
        lb.setName(name);

        return lb;
    }

    protected JLabel setLb(String name, String text, int x, int y, int width, int height, String alignment, int fontSize, String weight, JPanel panel) {
        JLabel lb = new JLabel(text);
        Font lbFont = new Font("맑은 고딕", setWeight(weight), fontSize);
        lb.setFont(lbFont);
        lb.setForeground(new Color(114, 114, 114));
        lb.setHorizontalAlignment(setAlign(alignment));
        lb.setBounds(x, y, width, height);
        lb.setName(name);

        return lb;
    }

    protected JLabel setLbBox(String name, String text, int x, int y) {
        JLabel lb = new JLabel(text);
        int age = Integer.parseInt(text);

        if(age == 99) {
            lb.setFont(new Font("맑은 고딕", Font.BOLD, 0));
            lb.setBackground(new Color(53, 121, 247));
        } else if(age >= 19) {
            lb.setFont(new Font("맑은 고딕", Font.BOLD, 15));
            lb.setBackground(Color.RED);
        } else if (age <= 0) {
            lb.setFont(new Font("맑은 고딕", Font.BOLD, 10));
            lb.setBackground(Color.BLUE);
            lb.setText("전체");
        } else {
            lb.setFont(new Font("맑은 고딕", Font.BOLD, 15));
            lb.setBackground(Color.GREEN);
        }
        lb.setOpaque(true);
        lb.setForeground(Color.WHITE);
        lb.setHorizontalAlignment(SwingConstants.CENTER);
        lb.setBounds(x, y, 27, 27);
        backgroundPanel.add(lb);
        lb.setName(name);

        return lb;
    }

    protected JLabel setLbBox(String name, String text, int x, int y, JPanel panel) {
        JLabel lb = new JLabel(text);
        int rating = Integer.parseInt(text);

        lb.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        lb.setBackground(new Color(0, 70, 42));

        lb.setOpaque(true);
        lb.setForeground(Color.WHITE);
        lb.setHorizontalAlignment(SwingConstants.CENTER);
        lb.setBounds(x, y, 27, 27);
        lb.setName(name);

        return lb;
    }

    protected JLabel setLbImg(String name, int iconNum, int x, int y) {
        JLabel lb = new JLabel();

        ImageIcon imgIc = new ImageIcon("img/icon"+iconNum+".png");
        Image img = imgIc.getImage();
        if(iconNum == 1 || iconNum == 2) {
            img = img.getScaledInstance(90, 90, Image.SCALE_SMOOTH);
        } else if(iconNum == 3) {
            img = img.getScaledInstance(100, 70, Image.SCALE_SMOOTH);
        } else if(iconNum == 4) {
            img = img.getScaledInstance(80, 56, Image.SCALE_SMOOTH);
        } else if(iconNum == 5) {
            img = img.getScaledInstance(60, 55, Image.SCALE_SMOOTH);
        }
        ImageIcon resizeImgIc = new ImageIcon(img);
        lb.setIcon(resizeImgIc);
        lb.setHorizontalAlignment(SwingConstants.CENTER);
        lb.setBounds(x, y, 100, 100);
        backgroundPanel.add(lb);
        lb.setName(name);

        return lb;
    }

    protected JCheckBox setCheckBox(String name, String text, int x, int y) {
        JCheckBox cb = new JCheckBox();
        cb.setBackground(Color.WHITE);
        cb.setIcon(new ImageIcon("img/checkbox.png"));
        cb.setSelectedIcon(new ImageIcon("img/selectedcheckbox.png"));
        cb.setBounds(x, y, 31, 31);
        backgroundPanel.add(cb);
        cb.setName(name);

        JLabel lb = new JLabel(text);
        Font lbFont = new Font("맑은 고딕", Font.PLAIN, 16);
        lb.setFont(lbFont);
        lb.setForeground(new Color(114, 114, 114));
        lb.setLocation(x+35, y-3);
        lb.setSize(300, 35);
        backgroundPanel.add(lb);

        return cb;
    }

    protected JComboBox<Keyword> setKeyword(String name, Vector<Keyword> combos, int x, int y, int width, int height){
        JComboBox<Keyword> keyword = new JComboBox<>();

        if(combos == null) {
            Keyword keywordNull = new Keyword("영화", "movie");
            keyword.addItem(keywordNull);
        } else {
            for (Keyword c : combos) {
                keyword.addItem(c);
            }
        }

        keyword.setBackground(Color.WHITE);
        keyword.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        keyword.setBounds(x, y, width, height);
        backgroundPanel.add(keyword);
        keyword.setName(name);

        return keyword;
    }


    protected JComboBox<Combo> setCombo(String name, Vector<Combo> combos, int x, int y, int width, int height){
        JComboBox<Combo> combo = new JComboBox<>();

        if(combos == null) {
            Combo comboNull = new Combo(0, "없음");
            combo.addItem(comboNull);
        } else {
            for (Combo c : combos) {
                combo.addItem(c);
            }
        }

        combo.setBackground(Color.WHITE);
        combo.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        combo.setBounds(x, y, width, height);
        backgroundPanel.add(combo);
        combo.setName(name);

        return combo;
    }

    protected JComboBox<String> setCombo(String name, String[] text, int x, int y, int width, int height){
        JComboBox<String> combo = new JComboBox<>(text);

        combo.setBackground(Color.WHITE);
        combo.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        combo.setBounds(x, y, width, height);
        backgroundPanel.add(combo);
        combo.setName(name);

        return combo;
    }

    private int setAlign(String alignment) {
        if(alignment.toUpperCase().equals("CENTER")) {
            return 0;
        } else if(alignment.toUpperCase().equals("LEFT")) {
            return 2;
        }  else if(alignment.toUpperCase().equals("RIGHT")) {
            return 4;
        } else {
            return 0;
        }
    }

    private int setWeight(String weight) {
        if(weight.toUpperCase().equals("BOLD")) {
            return 1;
        }  else if(weight.toUpperCase().equals("ITALIC")) {
            return 2;
        } else {
            return 0;
        }
    }

    protected JList<Combo> setList(String name, DefaultListModel<Combo> listModel, int x) {
        JList<Combo> list = new JList<Combo>(listModel);
        list.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        list.setForeground(new Color(114, 114, 114));

        JLabel lb = (JLabel) list.getCellRenderer();
        lb.setPreferredSize(new Dimension(200, 50));

        JScrollPane sp = new JScrollPane(list);
        sp.setBounds(x, 120, 420, 500);
        backgroundPanel.add(sp);
        list.setName(name);

        return list;
    }
}