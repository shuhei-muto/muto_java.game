package newgame.screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.imageio.ImageIO;
import java.io.IOException;
import newgame.util.KeyManager;

public class GachaMainScreen extends Screen {
	private Image background;
	private Image moneyButton;
	private Image gold;
	private Image silver;
	private Image bronze;
	private Image gold_gacha;
	private Image silver_gacha;
	private Image bronze_gacha;
	private Image battle_button;
	private boolean gachaMainScreen = true;
	 
	@Override
	public void init() throws IOException {
		// 背景画像とその他の画像読み込み
        background = ImageIO.read(getClass().getClassLoader().getResource("res/img/backimage/gacha.jpg"));
        moneyButton = ImageIO.read(getClass().getClassLoader().getResource("res/img/button/money.png"));
        gold = ImageIO.read(getClass().getClassLoader().getResource("res/img/gacha/gold.png"));
        silver = ImageIO.read(getClass().getClassLoader().getResource("res/img/gacha/silver.png"));
        bronze = ImageIO.read(getClass().getClassLoader().getResource("res/img/gacha/bronze.png"));
        gold_gacha = ImageIO.read(getClass().getClassLoader().getResource("res/img/button/gold_gacha.png"));
        silver_gacha = ImageIO.read(getClass().getClassLoader().getResource("res/img/button/silver_gacha.png"));
        bronze_gacha = ImageIO.read(getClass().getClassLoader().getResource("res/img/button/bronze_gacha.png"));
        battle_button = ImageIO.read(getClass().getClassLoader().getResource("res/img/button/battle_button.png"));
	}
	 
	public void update() {
		// KeyManagerインスタンスを取得
        KeyManager keyManager = KeyManager.getInstance();
        
        //エンターキーを押された時の画面遷移
        if (keyManager.isKeyPressed(KeyEvent.VK_ENTER)) {
        	gachaMainScreen = !gachaMainScreen;
        	if (!gachaMainScreen) {
                GachaScreen nextScreen = new GachaScreen();
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
		g.drawImage(background, 0, 0, 1000, 700, null);	//int x, int y, int width, int height
		g.drawImage(moneyButton, 650, 30, 300, 50, null);
		g.drawImage(battle_button, 440, 600, 120, 60, null);
		//ガチャ金銀銅
		g.drawImage(gold, 670, 150, 280, 280, null);
		g.drawImage(silver, 360, 150, 280, 280, null);
		g.drawImage(bronze, 50, 150, 280, 280, null);
		//ガチャボタン
		g.drawImage(gold_gacha, 670, 450, 280, 100, null);
		g.drawImage(silver_gacha, 360, 450, 280, 100, null);
		g.drawImage(bronze_gacha, 50, 450, 280, 100, null);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Serif", Font.PLAIN, 20));
		String money = "所持金：";
		g.drawString(money, 720, 60);
	}
	 
	public void dispose() {};
		
	public void cleanup() {};
	
	// 画像を取得するためのメソッド
	public Image getImage() {
		return background;
    }
}
