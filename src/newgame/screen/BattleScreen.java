package newgame.screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import newgame.util.KeyManager;

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
	
	private boolean startScreen = true;
	
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
        
	}
	
	public void update() {};
	
	@Override
	public void draw(Graphics g) {
		// 背景画像がなければ帰る
		if (background == null) return;
		
		//ここに描画するものを入れていく
		g.drawImage( background, 0, 0, 1000, 700, null);	//int x, int y, int width, int height
		
	}
	
	public void dispose() {};
	
	public void cleanup() {};
	
	// 画像を取得するためのメソッド
    public Image getImage() {
        return background;
    }
}

