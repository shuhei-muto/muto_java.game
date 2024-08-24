package newgame.screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import newgame.util.KeyManager;
import javax.swing.Timer;
import javax.swing.*;

public class BattleScreen extends Screen {
	
	private Image background;
	private Image dragon;
	private Image warrior;
	private Image w_first_evolution;
	private Image w_second_evolution;
	private Image d_normal_attack;
	private Image flame;
	private Image w_normal_attack;
	private Image w_special_attack;
	private Image gifImage;
	StringBuilder text1 = new StringBuilder();
	private boolean text = true;
	private boolean before_battle = false;
	
	private boolean BattleScreen = true;
	
	@Override
	public void init() throws IOException {
		// 背景画像とその他の画像読み込み
		background = ImageIO.read(getClass().getClassLoader().getResource("res/img/backimage/battle.jpg"));
		dragon = ImageIO.read(getClass().getClassLoader().getResource("res/img/character/dragon.gif"));
		warrior = ImageIO.read(getClass().getClassLoader().getResource("res/img/character/warrior.gif"));
		w_first_evolution = ImageIO.read(getClass().getClassLoader().getResource("res/img/character/first_evolution.gif"));
		w_second_evolution = ImageIO.read(getClass().getClassLoader().getResource("res/img/character/second_evolution.gif"));
		d_normal_attack = ImageIO.read(getClass().getClassLoader().getResource("res/img/effect/D_Normal_attack.gif"));
		flame = ImageIO.read(getClass().getClassLoader().getResource("res/img/effect/flame.gif"));
		w_normal_attack = ImageIO.read(getClass().getClassLoader().getResource("res/img/effect/W_Normal_attack.gif"));
		w_special_attack = ImageIO.read(getClass().getClassLoader().getResource("res/img/effect/Special_attack.gif"));
        String gifPath = "res/img/effect/W_Normal_attack.gif";
        ImageIcon gifIcon = new ImageIcon(gifPath);
        gifImage = gifIcon.getImage();
	}
	
	public void update() {
		// KeyManagerインスタンスを取得
		KeyManager keyManager = KeyManager.getInstance();
		        
		//エンターキーを押された時の画面遷移
		if (keyManager.isKeyPressed(KeyEvent.VK_ENTER)) {
			BattleScreen = !BattleScreen;
			if (!BattleScreen) {
				VictoryScreen nextScreen = new VictoryScreen();
				try {
					nextScreen.init(); // 次の画面の初期化
				} catch (IOException e) {
					e.printStackTrace();
				}
				ScreenManager.getInstance().setScreen(nextScreen);
			}
		}
	};
	
	@Override
	public void draw(Graphics g) {
		// 背景画像がなければ帰る
		if (background == null) return;
		
		//ここに描画するものを入れていく
		g.drawImage( background, 0, 0, 1000, 700, null);	//int x, int y, int width, int height
		g.setColor(Color.WHITE);
		g.fillRect(100, 500, 600, 150);		//テキストエリア、コマンド等の文章が入る
		
		
		//コマンド選択エリア白黒
//		g.setColor(Color.WHITE);
//		g.fillRect(749, 499, 152, 102);		//コマンド選択枠の表示
//		g.setColor(Color.BLACK);
//		g.fillRect(750, 500, 150, 100);		//コマンド選択枠の表示
		
		//表示するテキスト
		g.setColor(Color.BLACK);
        g.setFont(new Font("Serif", Font.PLAIN, 24));
		
		//タイマー処理
		Timer timer = new Timer(2000, e -> {
			text = false;
			before_battle = true;
		});
		timer.start();
		
		if(text) {
			g.drawString("(風の音)", 110, 530);
		} else if(before_battle) {
			g.setColor(Color.WHITE);
			g.fillRect(750, 500, 150, 100);		//コマンド選択枠の表示
			g.drawImage(dragon, 200, 100, 200, 300, null);
			g.drawImage(warrior, 650, 250, 150, 150, null);
			g.setColor(Color.RED);
			g.fillRect(200, 430, 200, 10);	//ドラゴンのHPゲージ
			g.fillRect(680, 430, 100, 10);	//戦士のHPゲージ
		}
		
		
	}
	
	public void dispose() {};
	
	public void cleanup() {};
	
	// 画像を取得するためのメソッド
	public Image getImage() {
		return background;
    }
}

