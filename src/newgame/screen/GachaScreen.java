package newgame.screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.swing.Timer;

import javax.imageio.ImageIO;
import newgame.util.KeyManager;

public class GachaScreen extends Screen {
	
	private Image background;
	private Image gacha_return;
	private Image warrior;
	private Image first_evo;
	private Image second_evo;
	private boolean gachaScreen = true;
	private boolean return_screen = false;
	public int hp;
	public int attack;
	public int defense;
	public int agility;
	public int luck;
	
	@Override
	public void init() throws IOException {
		// 背景画像の読み込み
        background = ImageIO.read(getClass().getClassLoader().getResource("res/img/backimage/gacha_after.jpg"));
        gacha_return = ImageIO.read(getClass().getClassLoader().getResource("res/img/button/gacha_back.png"));
        warrior = ImageIO.read(getClass().getClassLoader().getResource("res/img/character/warrior.gif"));
        first_evo = ImageIO.read(getClass().getClassLoader().getResource("res/img/character/first_evolution.gif"));
        second_evo = ImageIO.read(getClass().getClassLoader().getResource("res/img/character/second_evolution.gif"));
	}
	
	public void update() {
		// KeyManagerインスタンスを取得
        KeyManager keyManager = KeyManager.getInstance();
        
        //エンターキーを押された時の画面遷移
        if (keyManager.isKeyPressed(KeyEvent.VK_ENTER)) {
        	gachaScreen = !gachaScreen;
        	if (!gachaScreen && return_screen) {
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
		g.drawImage(background, 0, 0, 1000, 700, null);	//int x, int y, int width, int height
		g.setColor(Color.WHITE);
        g.fillRect(200, 150, 600, 400);	//int x, int y, int width, int height
        g.setColor(Color.BLACK);
        g.setFont(new Font("Serif", Font.PLAIN, 24));
        String text = "ガチャを引いた！";
        g.drawString(text, 400, 200);
        
        g.drawString("レア度Aの武器が出た！", 360, 230);
        g.drawString("攻撃力+10", 400, 260);		//ガチャの内容を取得して表示
        //ステータス
        g.drawString("【ステータス】", 210, 320);
        g.drawString("HP：", 210, 350);		//ガチャの内容を取得して表示
        g.drawString("攻撃力：", 210, 380);	//ガチャの内容を取得して表示
        g.drawString("防御力：", 210, 410);	//ガチャの内容を取得して表示
        g.drawString("回避：", 210, 440);		//ガチャの内容を取得して表示
        g.drawString("運：", 210, 470);		//ガチャの内容を取得して表示
        
        g.drawString("アイテム", 450, 320);
        g.drawString("回復薬×", 450, 350);	//×の後にガチャの内容を取得して個数を表示したい
        
        //2秒経ってから「ガチャへ戻る」ボタンを表示
        Timer timer = new Timer(2000, e -> {
        	return_screen = true;
        });
        timer.setRepeats(false);
        timer.start();
        
        if(return_screen) {
        	g.drawImage(gacha_return, 600, 570, 200, 60, null);
        }
	};
	
	public void dispose() {};
	
	public void cleanup() {};
	
	// 画像を取得するためのメソッド
    public Image getImage() {
        return background;
    }
}
