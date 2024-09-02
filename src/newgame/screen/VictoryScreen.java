package newgame.screen;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.imageio.ImageIO;
import newgame.bgm.Bgm;
import newgame.util.KeyManager;
import java.io.IOException;

public class VictoryScreen extends Screen {
	
	private Image background;
	private Image championship;
	private Image victory;
	private boolean victoryScreen = true;
	private Bgm bgm;
	
	@Override
	public void init() throws IOException {
		//画像の読み込み
		background = ImageIO.read(getClass().getClassLoader().getResource("res/img/backimage/victory.jpg"));
		championship = ImageIO.read(getClass().getClassLoader().getResource("res/img/championship.png"));
		victory = ImageIO.read(getClass().getClassLoader().getResource("res/img/victory.png"));
		bgm = new Bgm();	//Bgmインスタンスを作成
        try {
        	bgm.victory();	//BGMを再生
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
			victoryScreen = !victoryScreen;
			if (!victoryScreen) {
				GameStartScreen nextScreen = new GameStartScreen();
//				try {
//					nextScreen.init(); // 次の画面の初期化
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
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
