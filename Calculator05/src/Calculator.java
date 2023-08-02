import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
public class Calculator {
    public static void main(String[] args) {
        new Calculator();
    }
    private JFrame jf;
    private JPanel jp1, jp2;
    private JTextField jtf1, jtf2;
    private JButton[] jbs;
    private boolean fistZero = true;//去除初始0
    private boolean flag = true;//计算过程
    private boolean fist = true;//第一次不按数字直接输入符号时处理
    private boolean fistEqual = true;//第一次按等号时记忆数字
    private boolean point = false;//控制小数点
    private boolean errorZero = false;//是否有除0错误
    private char symbol = 'n';//符号
    private BigDecimal ans = new BigDecimal("0");//当前值记录
    private BigDecimal history = new BigDecimal("0");//历史值记录
    private BigDecimal tDEcimal = new BigDecimal("0");
    private StringBuffer str1 = new StringBuffer("0");
    private StringBuffer str2 = new StringBuffer("0");

    public Calculator() {
        jf = new JFrame("计算器");
        ((JPanel) jf.getContentPane()).setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        jp1 = new JPanel();
        setText();
        setButton();
        setFunction();
        init();
        setJF();
        addKeyFunction();
    }
    //键盘监听
    private void addKeyFunction() {
        jf.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keycode = e.getKeyCode();
                if (keycode == KeyEvent.VK_ESCAPE||keycode == KeyEvent.VK_DELETE)
                    jbs[1].doClick();
                else if (keycode == KeyEvent.VK_BACK_SPACE)
                    jbs[2].doClick();
                else if (keycode == KeyEvent.VK_DIVIDE)
                    jbs[3].doClick();
                else if (keycode == KeyEvent.VK_NUMPAD7||keycode == KeyEvent.VK_7)
                    jbs[4].doClick();
                else if (keycode == KeyEvent.VK_NUMPAD8||keycode == KeyEvent.VK_8)
                    jbs[5].doClick();
                else if (keycode == KeyEvent.VK_NUMPAD9||keycode == KeyEvent.VK_9)
                    jbs[6].doClick();
                else if (keycode == KeyEvent.VK_MULTIPLY)
                    jbs[7].doClick();
                else if (keycode == KeyEvent.VK_NUMPAD4||keycode == KeyEvent.VK_4)
                    jbs[8].doClick();
                else if (keycode == KeyEvent.VK_NUMPAD5||keycode == KeyEvent.VK_5)
                    jbs[9].doClick();
                else if (keycode == KeyEvent.VK_NUMPAD6||keycode == KeyEvent.VK_6)
                    jbs[10].doClick();
                else if (keycode == KeyEvent.VK_SUBTRACT)
                    jbs[11].doClick();
                else if (keycode == KeyEvent.VK_NUMPAD1||keycode == KeyEvent.VK_1)
                    jbs[12].doClick();
                else if (keycode == KeyEvent.VK_NUMPAD2||keycode == KeyEvent.VK_2)
                    jbs[13].doClick();
                else if (keycode == KeyEvent.VK_NUMPAD3||keycode == KeyEvent.VK_3)
                    jbs[14].doClick();
                else if (keycode == KeyEvent.VK_ADD)
                    jbs[15].doClick();
                else if (keycode == KeyEvent.VK_BACK_QUOTE||keycode == KeyEvent.VK_MINUS)
                    jbs[16].doClick();
                else if (keycode == KeyEvent.VK_NUMPAD0||keycode == KeyEvent.VK_0)
                    jbs[17].doClick();
                else if (keycode == KeyEvent.VK_DECIMAL)
                    jbs[18].doClick();
                else if (keycode == KeyEvent.VK_ENTER || keycode == KeyEvent.VK_EQUALS)
                    jbs[19].doClick();
            }
        });
        //鼠标监听
        jf.setFocusable(true);
        jtf1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                jf.requestFocus();
            }
        });
        jtf2.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                jf.requestFocus();
            }
        });
    }
    private void setFunction() {
        for (int i = 0; i < jbs.length; i++) {
            final int j = i;
            jbs[i].addActionListener(e -> {
                String str = e.getActionCommand();
                char c = str.charAt(0);
                if (Character.isDigit(c)) {//数字键
                    if (point)//有小数点
                        str1.append(c);
                    else {
                        if (fistZero) {//清除0
                            if (c == '0') {//输入是0返回
                                if (symbol != 'n') {
                                    str1 = new StringBuffer("0");
                                    ans = BigDecimal.ZERO;
                                    jtf1.setText(str1.toString());
                                }
                                return;
                            }
                            fistZero = false;//输入显示的字符串
                            str1 = new StringBuffer(str);//替换0
                        } else
                            str1.append(c);
                    }
                    ans = new BigDecimal(str1.toString());
                    flag = true;
                } else {
                    fistZero = true;
                    switch (j) {
                        case 0:
                            //CE
                            str1 = new StringBuffer("0");
                            ans = new BigDecimal("0");
                            fist = true;
                            fistZero = true;
                            point = false;
                            break;
                        case 1:
                            //C 初始化
                            calculatorInit();
                            break;
                        case 2:
                            //退格
                            if (str1.length() > 0)
                                str1.replace(str1.length() - 1, str1.length(), "");
                            if (str1.length() == 0) {
                                str1.append(0);
                                flag = false;
                            }
                            ans = ans.divide(BigDecimal.TEN, 0, RoundingMode.DOWN);
                            break;
                        case 3:
                            //÷
                            operate('÷');
                            break;
                        case 7:
                            //×
                            operate('×');
                            break;
                        case 11:
                            //-
                            operate('-');
                            break;
                        case 15:
                            //+
                            operate('+');
                            break;
                        case 16:
                            //±
                            if (symbol == 'n') {
                                ans = BigDecimal.ZERO.subtract(ans);
                                str1 = new StringBuffer(ans.toString());
                            } else {
                                history = BigDecimal.ZERO.subtract(history);
                                str1 = new StringBuffer(history.toString());
                            }
                            break;
                        case 18:
                            //.
                            if (!point) {
                                if (symbol == 'n')
                                    str1.append(".");
                                else
                                    str1 = new StringBuffer("0.");
                                point = true;
                            }
                            break;
                        case 19:
                            //=
                            if (symbol == 'n')
                                break;
                            tDEcimal = ans;
                            if (!fistEqual)
                                str2.append(symbol);
                            str2.append(tDEcimal.toString());
                            arithmetic();
                            str1 = new StringBuffer(ans.toString());
                            flag = false;
                            fistEqual = false;
                            history = ans;
                            ans = tDEcimal;
                            break;
                    }
                    if (j == 3 || j == 7 || j == 11 || j == 15) {//按过一次加减乘除
                        fist = false;
                        point = false;
                    }
                    if (j == 19)
                        point = false;
                    if (j != 19)//不连续按等号
                        fistEqual = true;
                }
                if (errorZero) { //除数为0时的处理
                    calculatorInit();
                    jtf1.setText("错误");
                    errorZero = false;
                } else {
                    jtf1.setText(str1.toString());
                    jtf2.setText(str2.toString());
                }
                jf.requestFocus();
            });
        }
    }
    private void calculatorInit() {
        symbol = 'n';
        str1 = new StringBuffer("0");
        str2 = new StringBuffer("0");
        ans = new BigDecimal("0");
        history = new BigDecimal("0");
        tDEcimal = new BigDecimal("0");
        fist = true;
        fistZero = true;
        flag = true;
        fistEqual = true;
        point = false;
    }
    private void operate(char x) {
        if (!fistEqual)
            str2.append(x);
        else {
            if (fist) {//第一次
                str2.replace(0, 1, ans.toString());
                str2.append(x);
            } else if (flag) {//计算后加入到历史记录
                str2.append(str1);
                str2.append(x);
                arithmetic();
                str1 = new StringBuffer(ans.toString());
            } else//已经有符号替换符号
                str2.replace(str2.length() - 1, str2.length(), Character.toString(x));
            history = ans;
        }
        flag = false;
        symbol = x;
    }
    private void arithmetic() {
        switch (symbol) {
            case '÷':
                if (ans.compareTo(BigDecimal.ZERO) == 0) {
                    errorZero = true;
                    break;
                }
                ans = history.divide(ans, 10, RoundingMode.HALF_UP).stripTrailingZeros();
                break;
            case '×':
                ans = history.multiply(ans);
                break;
            case '-':
                ans = history.subtract(ans);
                break;
            case '+':
                ans = history.add(ans);
                break;
        }
    }
    //输入框属性
    private void setText() {
        jtf1 = new JTextField(18);
        jtf1.setHorizontalAlignment(JTextField.RIGHT);//右对齐
        jtf1.setOpaque(false);//文本框透明
        jtf1.setBorder(null);//去除边框
        jtf1.setPreferredSize(new Dimension(480, 51));
        Font f1 = new Font("微软雅黑", Font.BOLD, 45);
        jtf1.setFont(f1);
        jtf2 = new JTextField(30);
        jtf2.setHorizontalAlignment(JTextField.RIGHT);//右对齐
        jtf2.setOpaque(false);//文本框透明
        jtf2.setBorder(null);//去除边框
        jtf2.setPreferredSize(new Dimension(480, 20));
        Font f2 = new Font("微软雅黑", Font.PLAIN, 18);
        jtf2.setFont(f2);
    }
    //按钮属性
    private void setButton() {
        jbs = new JButton[20];
        Font f = new Font("微软雅黑", Font.BOLD, 20);
        Color white = new Color(255, 255, 255);//鼠标未触碰时按钮颜色
        Color in = new Color(178, 178, 178);//鼠标触碰时按钮颜色
        jbs[0] = new JButton("CE");
        jbs[1] = new JButton("C");
        jbs[2] = new JButton("<");
        jbs[3] = new JButton("÷");
        for (int i = 0; i < 4; i++) {
            //设置格式
            jbs[i].setBackground(white);
            jbs[i].setFont(f);
            jbs[i].setFocusPainted(false);//去掉去掉按钮文字周围的焦点框
            final int j = i;
            jbs[i].addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    jbs[j].setBackground(in);
                }
                public void mouseExited(MouseEvent e) {
                    jbs[j].setBackground(white);
                }
            });
        }
        String s = "789×456-123+±0.=";
        for (int i = 0; i < s.length(); i++) {
            int t = i + 4;
            jbs[t] = new JButton(s.substring(i, i + 1));
            jbs[t].setFont(f);//设置格式
            jbs[t].setFocusPainted(false);//去掉去掉按钮文字周围的焦点框
            final int j = t;
            if (i % 4 == 3 || i == 12 || i == 14) {
                jbs[t].setBackground(white);
                jbs[j].addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        jbs[j].setBackground(in);
                    }
                    public void mouseExited(MouseEvent e) {
                        jbs[j].setBackground(white);
                    }
                });
            } else {
                jbs[t].setBackground(white);
                jbs[j].addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        jbs[j].setBackground(in);
                    }
                    public void mouseExited(MouseEvent e) {
                        jbs[j].setBackground(white);
                    }
                });
            }
        }
        for (int i = 0; i < jbs.length; ++i) {
            jbs[i].setBorderPainted(false);
            jbs[i].setPreferredSize(new Dimension(95, 60));
        }
    }
    private void init() {
        Color c = new Color(255, 255, 255);
        jp1.setLayout(new BorderLayout());
        jp1.add(jtf2, BorderLayout.NORTH);
        jp1.add(jtf1, BorderLayout.SOUTH);
        jp1.setBackground(c);
        jtf1.setEditable(false);
        jtf1.setText("0");
        jtf2.setEditable(false);
        jtf2.setText("0");
        jp2 = new JPanel();
        jp2.setLayout(new GridLayout(5, 4, 2, 2));
        jp2.setBackground(c);
        for (int i = 0; i < jbs.length; i++) {
            jp2.add(jbs[i]);
        }
        jf.add(jp1, BorderLayout.NORTH);
        jf.add(jp2, BorderLayout.CENTER);
    }
    //窗口属性
    public void setJF() {
        jf.setVisible(true);
        jf.getContentPane().setBackground(new Color(255, 255, 255));
        jf.setSize(300, 400);
        jf.setLocation(20,20);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
