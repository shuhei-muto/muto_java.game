package newgame.screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.imageio.ImageIO;

import newgame.bgm.Bgm;
import newgame.util.KeyManager;

import java.io.IOException;

public class LoseScreen extends Screen {
	private Image background;
	private boolean loseScreen = true;
	private Bgm bgm;
	
	@Override
	public void init() throws IOException {
		//画像の読み込み
		background = ImageIO.read(getClass().getClassLoader().getResource("res/img/lose.png"));
		
		bgm = new Bgm();	//Bgmインスタンスを作成
        try {
        	bgm.lose();		//BGMを再生
        } catch(Exception e) {
        	System.out.println("例外が発生しました。");
            System.out.println(e);
        }
	};
	
	public void update() {
		// KeyManagerインスタンスを取得
		KeyManager keyManager = KeyManager.getInstance();
        
		//エンターキーを押された時の画面遷移
		if (keyManager.isKeyPressed(KeyEvent.VK_ENTER)) {
			loseScreen = !loseScreen;
			if (!loseScreen) {
				GameStartScreen nextScreen = new GameStartScreen();
				try {
					nextScreen.init(); // 次の画面の初期化
				} catch (IOException e) {
					e.printStackTrace();
				}
				ScreenManager.getInstance().setScreen(nextScreen);
				bgm.stop();	//クラスフィールドbgmでstopを呼び出す
			}
		}
	};
	
	@Override
	public void draw(Graphics g) {
		// 背景画像がなければ帰る
		if (background == null) return;
		
		//ここに描画するものを入れていく
		g.drawImage(background, 0, 0, 1000, 700, null);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(400, 500, 200, 100);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Serif", Font.PLAIN, 20));
		g.drawString("REPLAY", 460, 555);
	};
	
	public void dispose() {};
	
	public void cleanup() {};
	
	// 画像を取得するためのメソッド
	public Image getImage() {
		return background;
	}
}
