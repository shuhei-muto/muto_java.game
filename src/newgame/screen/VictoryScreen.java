package newgame.screen;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.imageio.ImageIO;

import newgame.util.KeyManager;

import java.io.IOException;

public class VictoryScreen extends Screen {
	
	private Image background;
	private Image championship;
	private Image victory;
	private boolean victoryScreen = true;
	
	@Override
	public void init() throws IOException {
		//画像の読み込み
		background = ImageIO.read(getClass().getClassLoader().getResource("res/img/backimage/victory.jpg"));
		championship = ImageIO.read(getClass().getClassLoader().getResource("res/img/championship.png"));
		victory = ImageIO.read(getClass().getClassLoader().getResource("res/img/victory.png"));
	};
	
	public void update() {
		// KeyManagerインスタンスを取得
		KeyManager keyManager = KeyManager.getInstance();
        
		//エンターキーを押された時の画面遷移
		if (keyManager.isKeyPressed(KeyEvent.VK_ENTER)) {
			victoryScreen = !victoryScreen;
			if (!victoryScreen) {
				LoseScreen nextScreen = new LoseScreen();
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
		g.drawImage(background, 0, 0, 1000, 700, null);
		g.drawImage(championship, 230, 100, 500, 500, null);
		g.drawImage(victory, 300, 50, 400, 250, null);
	};
	
	public void dispose() {};
	
	public void cleanup() {};
	
	// 画像を取得するためのメソッド
	public Image getImage() {
		return background;
	}
}
