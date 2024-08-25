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
	
	//画像のローカル変数
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
	
	//タイマーによる表示非表示に使用する変数
	StringBuilder text1 = new StringBuilder();
	private boolean wind = true;
	private boolean battle = false;
	private boolean BattleScreen = true;
	
	//キャラステータスの変数(あとで置き換える)
	int w_hp = 100;
	int w_attack = 50;
	int w_defense = 30;
	int w_agility = 50;
	int w_luck = 50;
	
	int d_hp = 200;
	int d_attack = 50;
	int d_defense = 30;
//	int d_agility = 50;
//	int d_luck = 50;
	int d_damage;
	int w_damage;
	int count = 0;
	
	int choice_y = 530;
	private int currentSele = 0;
	
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
		
		if (keyManager.isKeyPressed(KeyEvent.VK_UP)) {
			if (currentSele == 1) {
				choice_y = 530;
				currentSele--;
			}
		}
		
		if (keyManager.isKeyPressed(KeyEvent.VK_DOWN)) {
			if (currentSele == 0) {
				choice_y = 560;
				currentSele++;
			}
		}
		        
		//エンターキーを押された時の画面遷移
		if (keyManager.isKeyPressed(KeyEvent.VK_ENTER)) {
			if (count == 0) {
				if(currentSele == 0) {
					battle();
					count++;
				} else if(currentSele == 1) {
					recovery();
					count++;
				}
			} else {
				Timer t = new Timer(600, e -> {
					count = 0;
				});
				t.setRepeats(false);
				t.start();
			}
		}
		
		if(d_hp <= 0) {
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
		} else if (w_hp <= 0) {
			BattleScreen = !BattleScreen;
			if (!BattleScreen) {
				LoseScreen nextScreen = new LoseScreen();
				try {
					nextScreen.init();
				} catch(IOException e) {
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
		Timer display_delay = new Timer(2000, e -> {
			wind = false;
			battle = true;
		});
		display_delay.start();
		
		//elseは風の音が止んだ後に表示するもの
		if(wind) {
			g.drawString("(風の音)", 110, 530);
		} else if(battle) {
			g.setColor(Color.WHITE);
			g.fillRect(750, 500, 150, 100);		//コマンド選択枠の表示
			g.drawImage(dragon, 200, 100, 200, 300, null);
			g.drawImage(warrior, 650, 250, 150, 150, null);
			g.setColor(Color.RED);
			g.fillRect(200, 430, d_hp, 10);	//ドラゴンのHPゲージ
			g.fillRect(680, 430, w_hp, 10);	//戦士のHPゲージ
			
			g.setColor(Color.BLACK);
			g.drawString("▶", 760, choice_y);
			g.drawString("攻撃", 790, 530);
			g.drawString("回復薬", 790, 560);
		}
	}
	
	public void dispose() {};
	
	public void cleanup() {};
	
	//コマンド「攻撃」を選択時の処理
	public void battle() {
		//クリティカルの確率計算
		int l = new java.util.Random().nextInt(100);
		int luck = 100 - w_luck;
		
		//回避の確率計算
		int a = new java.util.Random().nextInt(100);
		int agility = 100 - w_agility;
		
		if (w_attack > d_defense) {
			d_damage = w_attack - d_defense;
		} else {
			d_damage = 1;
		}
		
		//クリティカルヒットした時の処理
		if (luck <= l) {
			d_damage = d_damage * 2;
			System.out.println("クリティカルヒット");
		}
		d_hp -= d_damage;
		System.out.println("ドラゴンに" + d_damage + "のダメージ");
		
		//戦士のダメージ反映を遅延する処理
		Timer damage_delay = new Timer(2000, e -> {
			if (d_attack > w_defense) {
				w_damage = d_attack - w_defense;
			} else {
				w_damage = 1;
			}
			
			//攻撃を回避した時の処理
			if (agility <= a) {
				w_damage = 0;
				System.out.println("ドラゴンの攻撃を避けた");
			}
			w_hp -= w_damage;
			System.out.println("戦士に" + w_damage + "のダメージ");
		});
		damage_delay.setRepeats(false);
		damage_delay.start();
	};
	
	//コマンド「回復薬」を選択時の処理
	public void recovery() {
		//回避の確率計算
		int a = new java.util.Random().nextInt(100);
		int agility = 100 - w_agility;
				
		w_hp = 100;
		System.out.println("戦士は回復薬を使った！　HP全回復！！");
		
		//戦士のダメージ反映を遅延する処理
		Timer damage_delay = new Timer(2000, e -> {
			if (d_attack > w_defense) {
				w_damage = d_attack - w_defense;
			} else {
				w_damage = 1;
			}
			
			//攻撃を回避した時の処理
			if (agility <= a) {
				w_damage = 0;
				System.out.println("ドラゴンの攻撃を避けた");
			}
			w_hp -= w_damage;
			System.out.println("戦士に" + w_damage + "のダメージ");
		});
		damage_delay.setRepeats(false);
		damage_delay.start();
	}
	
	// 画像を取得するためのメソッド
	public Image getImage() {
		return background;
    }
}
