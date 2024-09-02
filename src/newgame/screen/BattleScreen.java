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
	
//	private boolean battle = false;
	private boolean BattleScreen = true;
	
	//キャラステータスの変数
	int w_hp;
	int w_attack;
	int w_defense;
	int w_agility;
	int w_luck;
	
	int d_hp = 200;
	int d_attack = 50;
	int d_defense = 30;
	int d_damage;
	int w_damage;
	int count = 0; //コマンドの連続押下防止用
	
	//テキスト表示のローカル変数
	private int textCount = 0;
	private String message1;
	private String message2;
	private String message3;
	String doragon_damage;
	String warrior_damage;
	String d_damageMessage;
	String w_damageMessage;
	String critical;
	String avoid;
	StringBuilder text1 = new StringBuilder();		//不気味な静寂が辺りを包む……
	StringBuilder text2 = new StringBuilder();		//ドラゴンが現れた
	StringBuilder text3 = new StringBuilder();		//ドラゴンを倒せ
	StringBuilder w_damageText = new StringBuilder();
	StringBuilder d_damageText = new StringBuilder();
	StringBuilder criticalText = new StringBuilder();
	StringBuilder avoidText = new StringBuilder();
	int step1 = 0;
	int step2 = 0;
	int step3 = 0;
	int textStep = 0;
	int y;
	
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
            Timer bgmTimer = new Timer(6500, new ActionListener() {
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
				ScreenManager.getInstance().setScreen(nextScreen);	// 次の画面の初期化及びセット
				wind.stop();
				bgm.stop();
			}
		} else if (w_hp <= 0) {
			BattleScreen = !BattleScreen;
			if (!BattleScreen) {
				LoseScreen nextScreen = new LoseScreen();
				ScreenManager.getInstance().setScreen(nextScreen);	// 次の画面の初期化及びセット
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
		
		try {
	        Thread.sleep(50); // 50ミリ秒スリープ
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
		
		//elseは風の音が止んだ後に表示するもの
		if (wind.isPlaying()) {
			g.drawString("(風の音)", 110, 530);
			g.drawString(text1.toString(), 110, 560);
//			System.out.println(text);
			if (step1 == 0) {
				message1 = "不気味な静寂が辺りを包む……";
				if (textCount < message1.length()) {
					text1.append(message1.charAt(textCount));
					textCount++;
					System.out.println(text1);
				} else {
					step1++;
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
			
			if(d_damageMessage == null) {
				//バトル前テキスト表示*************************
				g.drawString(text2.toString(), 110, 530);
				if (step2 == 0) {
					message2 = "ドラゴンが現れた！";
					if (textCount < message2.length()) {
						text2.append(message2.charAt(textCount));
						textCount++;
						System.out.println(text2);
					} else {
						step2++;
						textCount = 0;
					}
				}
				
				g.drawString(text3.toString(), 110, 560);
				if (step2 == 1) {
					message3 = "ドラゴンを倒せ！！！！";
					if (textCount < message3.length()) {
						text3.append(message3.charAt(textCount));
						textCount++;
						System.out.println(text3);
					} else {
						step2++;
						textCount = 0;
					}
				}
				//******************************************
				
			} else {
				//****************攻撃テキスト******************
				g.drawString(criticalText.toString(), 110, 530);
				if(critical != null) {		//クリティカルヒットしたらクリティカルテキスト表示
					if (textCount < critical.length()) {
						criticalText.append(critical.charAt(textCount));
						textCount++;
						System.out.println(criticalText);
					} else {
						textStep++;
						textCount = 0;
					}
					
				} else if(critical != null && textStep == 1) {
					g.drawString(d_damageText.toString(), 110, 560);
					if(critical != null && textStep == 1) {
						if (textCount < d_damageMessage.length()) {
							d_damageText.append(d_damageMessage.charAt(textCount));
							textCount++;
							System.out.println(d_damageText);
						} else {
							textStep++;
							textCount = 0;
						}
					}
				} else {	//クリティカルヒットしなければ通常のテキストを表示
					g.drawString(d_damageText.toString(), 110, 530);
					if(textStep == 0) {
						if (textCount < d_damageMessage.length()) {
							d_damageText.append(d_damageMessage.charAt(textCount));
							textCount++;
							System.out.println(d_damageText);
						} else {
							textStep++;
							textCount = 0;
						}
					}
				}
				
				

				
				
				
				
				//****************ダメージ受けたテキスト******************
//				g.drawString(w_damageText.toString(), 110, 590);
//				if(textStep == 2) {
//					if (textCount < w_damageMessage.length()) {
//						w_damageText.append(w_damageMessage.charAt(textCount));
//						textCount++;
//						System.out.println(w_damageText);
//					} else {
//						textStep++;
//						textCount = 0;
//					}
//				}
			}
			
		}
		
		KeyManager keyManager = KeyManager.getInstance();
		//エンターキーを押された時の画面遷移
		if (keyManager.isKeyPressed(KeyEvent.VK_ENTER)) {
			if (count == 0) {
				if(choice_y == 530) {
					battle();		//攻撃した時のメソッド実行
					// GIFアイコンを描画
			        if (d_normal_attack != null) {
			            // GIFアイコンを描画する位置を指定 (例: x = 100, y = 100)
			        	d_normal_attack.paintIcon(null, g, 0, 0);
			        }
					count++;
				} else if(currentSele == 1) {
					recovery();		//回復薬を使った時のメソッド実行
					count++;
				}
			} else {
				Timer t = new Timer(700, e -> {
					count = 0;
				});
				t.setRepeats(false);
				t.start();
			}
		}
		
	}
	
	//コマンド「攻撃」を選択時の処理
	public void battle() {
		textStep = 0;
		//クリティカルの確率計算
		int l = new java.util.Random().nextInt(100);
		int luck = 100 - w_luck;
		//回避の確率計算
		int a = new java.util.Random().nextInt(100);
		int agility = 100 - w_agility;
		
		//ドラゴンのダメージ処理*****************
		if (w_attack > d_defense) {
			d_damage = w_attack - d_defense;
		} else {
			d_damage = 1;
		}
		
		//クリティカルヒットした時の処理
		if (luck <= l) {
			d_damage = d_damage * 2;
			critical = "攻撃がクリティカルヒットした！！";
			System.out.println("クリティカルヒット");
		} else {
			critical = null;
		}
		d_hp -= d_damage;
		doragon_damage = String.valueOf(d_damage);
		d_damageMessage = "ドラゴンに" + doragon_damage + "ダメージ与えた！";
		System.out.println(d_damageMessage);
		//*************************************
		
		
		//戦士のダメージ処理**********************
		Timer damage_delay = new Timer(2000, e -> {		//戦士のダメージ反映を遅延する処理
			if (d_attack > w_defense) {
				w_damage = d_attack - w_defense;
			} else {
				w_damage = 1;
			}
			
			//攻撃を回避した時の処理
			if (agility <= a) {
				w_damage = 0;
				avoid = "ドラゴンの攻撃を避けた";
				System.out.println("ドラゴンの攻撃を避けた");
			} else {
				avoid = null;
				return;
			}
			w_hp -= w_damage;
			warrior_damage = String.valueOf(w_damage);
			w_damageMessage = "戦士に" + warrior_damage + "のダメージ";
			System.out.println(w_damageMessage);
			//*******************************
		});
		damage_delay.setRepeats(false);
		damage_delay.start();
	};
	
	
	//コマンド「回復薬」を選択時の処理
	public void recovery() {
		textStep = 0;
		//回避の確率計算
		int a = new java.util.Random().nextInt(100);
		int agility = 100 - w_agility;
		
		status = GlobalState.currentStatus;
		w_hp = status.getHp();
		System.out.println("戦士は回復薬を使った！　HP全回復！！");
		
		//戦士のダメージ処理
		Timer damage_delay = new Timer(2000, e -> {		//戦士のダメージ反映を遅延する処理
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
	
	
	public void dispose() {};
	
	public void cleanup() {};
	
	// 画像を取得するためのメソッド
	public Image getImage() {
		return background;
    }
}
