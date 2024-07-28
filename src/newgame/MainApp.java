package newgame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MainApp extends JPanel implements ActionListener {
    private Timer timer;
    private int x;

    /***********************************************
     * コンストラクタ
     ***********************************************/
    public MainApp() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        timer = new Timer(1000 / 60, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Color.WHITE);
        g2.fillOval(x, 250, 50, 50);
        g2.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        x += 2;
        if (x > getWidth()) {
            x = -50;
        }
        repaint();
    }

    /***********************************************
     * エントリーポイント
     ***********************************************/
    public static void main(String[] args) {
    	// フレーム作成
        JFrame frame = new JFrame("Simple Double Buffering");
        // 自身の作成
        MainApp panel = new MainApp();
        // 閉じるボタンを押したら閉じる設定
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // パネル設定
        frame.add(panel);
        // frameサイズをpanelに合わせる
        frame.pack();
        // ディスプレイの中央へウィンドウを移動
        frame.setLocationRelativeTo(null);
        // ウィンドウの描画設定
        frame.setVisible(true);
    }
}
