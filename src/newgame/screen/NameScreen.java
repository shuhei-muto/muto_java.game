package newgame.screen;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import newgame.util.KeyManager;

public class NameScreen extends Screen {
	
	private Image background;
	private Image decisionButton;
	private Image moneyButton;
	public int money;	//ガチャで回したものを入れる
	public String name;	//ガチャで回したものを入れる
	private boolean nameScreen = true;
	private boolean text = true;
	private boolean button = false;
	
	@Override
	public void init() throws IOException {
		// 背景とその他画像の読み込み
        background = ImageIO.read(getClass().getClassLoader().getResource("res/img/backimage/decide_name.jpg"));
        decisionButton = ImageIO.read(getClass().getClassLoader().getResource("res/img/button/decision_button.png"));
        moneyButton = ImageIO.read(getClass().getClassLoader().getResource("res/img/button/money.png"));
	}
	
	public void update() {
		// KeyManagerインスタンスを取得
        KeyManager keyManager = KeyManager.getInstance();
        
        //エンターキーを押された時の画面遷移
        if (keyManager.isKeyPressed(KeyEvent.VK_ENTER)) {
        	nameScreen = !nameScreen;
        	if (!nameScreen) {
                GachaMainScreen nextScreen = new GachaMainScreen();
                try {
                    nextScreen.init(); // 次の画面の初期化
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ScreenManager.getInstance().setScreen(nextScreen);
            }
        }
	};
	
	public void draw(Graphics g) {
		// 背景画像がなければ帰る
		if (background == null) return;
		
		//ここに描画するものを入れていく
		g.drawImage(background, 0, 0, 1000, 700, null);		//int x, int y, int width, int height
		Image scaledmoneyButton = moneyButton.getScaledInstance(400, 80, Image.SCALE_SMOOTH);
		int centerX_mB = (1000 - scaledmoneyButton.getWidth(null)) / 2 ;
		g.drawImage(scaledmoneyButton, centerX_mB, 550, null);
		Image scaledDeButton = decisionButton.getScaledInstance(120, 60, Image.SCALE_SMOOTH);
		int centerX_dB = (1000 - scaledDeButton.getWidth(null)) / 2 ;
		g.setColor(Color.BLACK);
		g.setFont(new Font("Serif", Font.PLAIN, 24));
		
		
		name = "勇者";	//ガチャで回した後に変数nameに名前が入る
		String moneyText = "所持金：" + money;	//ガチャ回した後に変数moneyに金額が入る
		
		g.drawString(name, 475, 360);
		g.drawString(moneyText, 400, 595);
		g.setFont(new Font("Serif", Font.PLAIN, 24));
		g.setColor(Color.WHITE);	//名前が入る四角の部分
		g.fillRect(200, 300, 600, 100);		//int x, int y, int width, int height
		
		
		// タイマーを使って3秒後にLoodingの文字を消す＆決定ボタンを表示
        Timer timer = new Timer(3000, e -> {
            text = false;
            button = true;
        });
        timer.setRepeats(false); // タイマーは一度だけ動作させる
        timer.start();
		
        if (text && !button) {
        	g.drawString("Loading......", 450, 200);
        } else if (!text && button) {
        	g.drawImage(scaledDeButton, centerX_dB, 450, null);
    		g.drawString("Enter", 475, 525);
        }
		
	};
	
	public void dispose() {};
	
	public void cleanup() {};
	
	// 画像を取得するためのメソッド
    public Image getImage() {
        return background;
    }
}
