package newgame.screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import newgame.util.KeyManager;

import javax.swing.*;
import newgame.bgm.Bgm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import newgame.util.Status;
import newgame.util.GlobalState;

public class BattleScreen extends Screen {
	
	//画像のローカル変数
	private Image background;
	private Image dragon;
	private Image warrior;
	private Image w_first_evolution;
	private Image w_second_evolution;
	private Icon d_normal_attack;
	private Icon flame;
	private Icon w_normal_attack;
	private Icon w_special_attack;
	private Icon gifImage;
	
	//タイマーによる表示非表示に使用する変数
	StringBuilder text1 = new StringBuilder();
	private boolean battle = false;
	private boolean BattleScreen = true;
	
	//キャラステータスの変数(あとで置き換える)
	int w_hp;
	int w_attack;
	int w_defense;
	int w_agility;
	int w_luck;
	
	int d_hp = 200;
	int d_attack = 50;
	int d_defense = 30;
//	int d_agility = 50;
//	int d_luck = 50;
	int d_damage;
	int w_damage;
	int count = 0; //コマンドの連続押下防止用
	private int textCount = 0;
	private String message;
	StringBuilder text = new StringBuilder();
	int step = 0;
	
	int choice_y = 530;
	private int currentSele = 0;
	private Status status;
	
	//BGM
	private Bgm bgm;
	private Bgm wind;
//	private BattleBgm bgm;
	
	
	@Override
	public void init() throws IOException {
		// 背景画像とその他の画像読み込み
		background = ImageIO.read(getClass().getClassLoader().getResource("res/img/backimage/battle.jpg"));
		dragon = ImageIO.read(getClass().getClassLoader().getResource("res/img/character/dragon.gif"));
		warrior = ImageIO.read(getClass().getClassLoader().getResource("res/img/character/warrior.gif"));
		w_first_evolution = ImageIO.read(getClass().getClassLoader().getResource("res/img/character/first_evolution.gif"));
		w_second_evolution = ImageIO.read(getClass().getClassLoader().getResource("res/img/character/second_evolution.gif"));
		
		String d_attackPath = "src/res/img/effect/D_Normal_attack.gif";
		d_normal_attack = new ImageIcon(d_attackPath);
		String flamePath = "src/res/img/effect/flame.gif";
		flame = new ImageIcon(flamePath);
		String w_normal_attackPath = "src/res/img/effect/W_Normal_attack.gif";
		w_normal_attack = new ImageIcon(w_normal_attackPath);
		String w_special_attackPath = "src/res/img/effect/Special_attack.gif";
		w_special_attack = new ImageIcon(w_special_attackPath);
		
		status = GlobalState.currentStatus;
		w_hp = status.getHp();
		w_attack = status.getAttack();
		w_defense = status.getDefense();
		w_agility = status.getAgility();
		w_luck = status.getLuck();
		
		wind = new Bgm();
        bgm = new Bgm();
		
//		bgm = new BattleBgm();
        
        try {
        	wind.wind();
        	// wind の再生後に bgm を再生するための Timer 設定
            Timer bgmTimer = new Timer(5000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        bgm.battlescreen();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
            bgmTimer.setRepeats(false); // 一度だけ実行
        	bgmTimer.start();
        } catch(Exception e) {
        	System.out.println("例外が発生しました。");
        	System.out.println(e);
        }
        
//        if (!wind.isPlaying()) {
//        	try {
//				bgm.battlescreen();
//			} catch(Exception e) {
//				System.out.println(e);
//			}
//        }
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
				wind.stop();
				bgm.stop();
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
				wind.stop();
				bgm.stop();
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
		Timer display_delay = new Timer(3000, e -> {
			battle = true;
		});
		display_delay.start();

		
		//elseは風の音が止んだ後に表示するもの
		if (wind.isPlaying()) {
			g.drawString("(風の音)", 110, 530);
			g.drawString(text.toString(), 110, 560);
//			System.out.println(text);
			if (step == 0) {
				message = "不気味な静寂が辺りを包む……";
				if (textCount < message.length()) {
					text.append(message.charAt(textCount));
					textCount++;
				} else {
					step++;
					textCount = 0;
				}
			}
		} else {
			g.setColor(Color.WHITE);
			g.fillRect(750, 500, 150, 100);		//コマンド選択枠の表示
			g.drawImage(dragon, 150, 50, 250, 400, null);
			g.drawImage(warrior, 650, 250, 150, 150, null);
			g.setColor(Color.RED);
			g.fillRect(200, 430, d_hp, 10);	//ドラゴンのHPゲージ
			g.fillRect(680, 430, w_hp, 10);	//戦士のHPゲージ
			
			g.setColor(Color.BLACK);
			g.drawString("▶", 760, choice_y);
			g.drawString("攻撃", 790, 530);
			g.drawString("回復薬", 790, 560);
			g.drawString("ドラゴンが現れた！", 110, 530);
		}
		
		KeyManager keyManager = KeyManager.getInstance();
		//エンターキーを押された時の画面遷移
		if (keyManager.isKeyPressed(KeyEvent.VK_ENTER)) {
			if (count == 0) {
				if(choice_y == 530) {
					battle();
					// GIFアイコンを描画
			        if (d_normal_attack != null) {
			            // GIFアイコンを描画する位置を指定 (例: x = 100, y = 100)
			        	d_normal_attack.paintIcon(null, g, 0, 0);
			        }
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
		
	}
	
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
		
		status = GlobalState.currentStatus;
		w_hp = status.getHp();
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
	
	public void textSequetially() {
		
	}
	
	
	public void dispose() {};
	
	public void cleanup() {};
	
	// 画像を取得するためのメソッド
	public Image getImage() {
		return background;
    }
}
