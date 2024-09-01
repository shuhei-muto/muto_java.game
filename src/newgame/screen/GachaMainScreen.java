package newgame.screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import newgame.util.EquipmentManager;
import newgame.util.GlobalState;
import newgame.util.KeyManager;
import newgame.util.Status;
import newgame.util.WeaponType;
import newgame.bgm.Bgm;

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
	private BufferedImage[] images;
	private int currentIndex = 0; // `▼`マークの現在の位置
	private boolean gachaMainScreen = true;
	int markerX = 180;	//▲マーク用のx座標初期値
	int markerY = 560;	//▲マーク用のy座標初期値
	private long lastMoveTime = 0;	//最後に更新した時間
	private long moveDelay = 300;	//遅延させる時間
	public String name;	//ガチャで取得した名前を入れる
	public int money;	//ガチャで取得した所持金を入れる
	private boolean enough = false;
	public Status status;
	public String statusText;
	public String weaponText;
	public Status enum0;
	public Status enum1;
	public Status enum2;
	public Status enum3;
	public Status enum4;
	public Status enum5;
	public Status enum6;
	public Status enum7;
	private Bgm bgm;
//	private int bgmCount;
	 
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
        images = new BufferedImage[4];
    	images[0] = (BufferedImage) bronze_gacha;
    	images[1] = (BufferedImage) silver_gacha;
    	images[2] = (BufferedImage) gold_gacha;
    	images[3] = (BufferedImage) battle_button;
    	bgm = new Bgm();	//Bgmインスタンスを作成
//    	bgmCount = 0;
    	try {
    		bgm.gacha();	//BGMを再生
    	} catch(Exception e) {
    		System.out.println("例外が発生しました。");
            System.out.println(e);
    	}
    	
    	name = System.getProperty("name");
    	//PropatyにセットするときはString型のため型変換をする
    	money = Integer.parseInt(System.getProperty("money"));
    	
    	// グローバル状態から Status を取得
        status = GlobalState.currentStatus;
        // グローバル状態から EquipmentManager を取得
        EquipmentManager manager = GlobalState.equipmentManager;
        //manager.printEquippedItems(); 装備中のアイテムをコンソールに表示
        int Rank = status.getEvolitionCount();
        String RankText ;
        if(Rank == 1) {
        	RankText = "見習い";
        } else if (Rank == 2) {
        	RankText = "上級冒険者";
        } else if (Rank == 3) {
        	RankText = "達人";
        } else {
        	RankText = "null";
        }
        
        
        
        //表示用のステータステキストの編集
        statusText = "【" + name +  "】\n" +
	                "ＨＰ　：" + status.getHp() + "\n" +
	                "攻撃力：" + status.getAttack() + "\n" +
	                "防御力：" + status.getDefense() + "\n" +
	                "回避　：" + status.getAgility() + "\n" +
	                "運　　：" + status.getLuck();
        enum0 = Efirst(manager.getEquippedItem(WeaponType.武器)); 
        enum1 = Efirst(manager.getEquippedItem(WeaponType.鎧));
        enum2 = Efirst(manager.getEquippedItem(WeaponType.盾));
        enum3 = Efirst(manager.getEquippedItem(WeaponType.ブーツ));
        enum4 = Efirst(manager.getEquippedItem(WeaponType.アクセサリー));
        enum5 = Efirst(manager.getEquippedItem(WeaponType.ステ上げアイテム));
        enum6 = Efirst(manager.getEquippedItem(WeaponType.回復アイテム));
        
        weaponText = "【装備】\n" +
                "武器　：" +  enum0.getItemName() + "\n" +
                "鎧　　：" + enum1.getItemName() + "\n" +
                "盾　　：" + enum2.getItemName() + "\n" +
                "ブーツ：" + enum3.getItemName() + "\n" +
                "アクセ：" + enum4.getItemName() + "\n" +
                "ステ上：" + status.getStatusUPCount() + "回" + "\n" +
                "回復　：" + status.getPotionCount() + "個" + "\n" +
                "ランク：" + RankText;
    	
	}

	public void update() {
		// KeyManagerインスタンスを取得
        KeyManager keyManager = KeyManager.getInstance();
        long currentTime = System.currentTimeMillis();	//現在の時間を取得
        
        //キーが押された際に一定の遅延を持たせる
        if(currentTime - lastMoveTime > moveDelay) {
        	//左矢印を押したら▲が左に移動(反時計周り)
	        if (keyManager.isKeyPressed(KeyEvent.VK_LEFT)) {
	        	if(currentIndex > 0) {
	        		currentIndex--;	// 左矢印キーで左に移動
	        	} else if(currentIndex == 0) {
	        		currentIndex = 3;
	        	}
	        	lastMoveTime = currentTime;	//最後の移動時間を更新
	        }
	        //右矢印を押したら▲が右に移動(時計回り)
	        if (keyManager.isKeyPressed(KeyEvent.VK_RIGHT)) {
	        	if(currentIndex < images.length - 1) {
	        		currentIndex++;	// 右矢印キーで右に移動
	        	} else if(currentIndex == 3) {
	        		currentIndex = 0;
	        	}
	        	lastMoveTime = currentTime;	//最後の移動時間を更新
	        }
        }
        
        //エンターキーを押された時の画面遷移
        if (keyManager.isKeyPressed(KeyEvent.VK_ENTER)) {
        	gachaMainScreen = !gachaMainScreen;
        	if(currentIndex < 3) {	//ガチャボタン選択時はガチャ画面へ
        		if (!gachaMainScreen) {
        			if(!handleSelection()) {//ガチャを回すお金がなかった時
        				System.out.println("お金が足りないよ");
        				enough = true;
        				Timer timer = new Timer(1000, e -> {
        					enough = false;
        		        });
        		        timer.setRepeats(false); // タイマーは一度だけ動作させる
        		        timer.start();
        		        
        				return;
        			};//選択された画像に対する処理
        			GachaScreen nextScreen = new GachaScreen();
        			System.setProperty("gachaNo", String.valueOf(currentIndex));
        			//setPropertyはString型を引数にするためint型から変換をする
                    System.setProperty("money", String.valueOf(money));
        			try {
        				nextScreen.init(); // 次表示する画面の初期化
        			} catch (IOException e) {
        				e.printStackTrace();
        			}
        			ScreenManager.getInstance().setScreen(nextScreen);
        		}
        	} else {	//バトル画面選択時はバトル画面へ
        		if(!gachaMainScreen) {
        			BattleScreen nextnextScreen = new BattleScreen();
//        			try {
//        				nextnextScreen.init();	//次表示する画面の初期化
//        			} catch (IOException e) {
//        				e.printStackTrace();
//        			}
        			ScreenManager.getInstance().setScreen(nextnextScreen);
        			bgm.stop();	//クラスフィールドbgmでstopを呼び出す
//        			bgmCount = 0;
        			System.out.println("バトル画面へ");
        		}
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
		g.drawImage(battle_button, 440, 590, 120, 60, null);
		//ガチャ金銀銅
		g.drawImage(gold, 670, 150, 280, 280, null);
		g.drawImage(silver, 360, 150, 280, 280, null);
		g.drawImage(bronze, 50, 150, 280, 280, null);
		//ガチャボタン
		g.drawImage(gold_gacha, 670, 450, 280, 100, null);
		g.drawImage(silver_gacha, 360, 450, 280, 100, null);
		g.drawImage(bronze_gacha, 50, 450, 280, 100, null);
		System.out.println("1");
		
		//**********ここに所持金を入れる**********************
		String moneyText = "所持金：$" + money;
		//*************************************************
		g.setColor(new Color(128, 128, 128, 128));	//名前が入る四角の部分
		FontMetrics fm = g.getFontMetrics();	
		// 文字列の幅を取得
		int statusWidth = fm.stringWidth("【" + name + "】");
		g.fillRect(5, 10, 85 + statusWidth, 150);	
		g.fillRect(85 + statusWidth, 10, 250, 225);	
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Serif", Font.PLAIN, 20));
		
		// 各行の描画位置を調整
        int x = 10;
        int y = 30;
        int lineHeight = g.getFontMetrics().getHeight(); // 行間の高さ

        // ステータステキストを行ごとに分割
        String[] lines = statusText.split("\n");
        String[] lines2 = weaponText.split("\n");

        // 各行を描画
        for (String line : lines) {
            g.drawString(line, x, y);
            y += lineHeight; // 次の行の位置に移動
        }
        
        int x2 = 85 + statusWidth;
        int y2 = 30;
        for (String line2 : lines2) {
            g.drawString(line2, x2, y2);
            y2 += lineHeight; // 次の行の位置に移動
        }
		
        g.setColor(Color.BLACK);
		g.setFont(new Font("Serif", Font.PLAIN, 20));
		g.drawString(moneyText, 720, 60);
        int spacing = 30; // 画像間のスペース
        
        // `▼`マークを描画
        if(currentIndex == 3) {		//バトルボタン選択時の座標
        	markerX = 490;
        	markerY = 670;
        } else {					//ガチャボタン選択時の座標
        	markerX = 180 + (currentIndex * (280 + spacing));
        	markerY = 560;
        }
        g.setFont(new Font("Serif", Font.BOLD, 24));
        g.drawString("▲", markerX, markerY);
        
        if (enough) {
        	g.setFont(new Font("Serif", Font.PLAIN, 24));
    		g.setColor(Color.RED);
        	g.drawString("所持金足りない", 420, 300);
        } 
        
        
	}
	 
	public void dispose() {};
	
	public void cleanup() {};
	
	// 選択された画像に対する処理
    private boolean handleSelection() {
    	boolean result = true;
        System.out.println("選択された画像: " + currentIndex);
        // ここで選択された画像に対する処理を行う
        if(currentIndex == 0) {
        	System.out.println("$10");
        	if (money >= 10) {
        		money = money - 10;
        		System.out.println("ガチャを引いたよ残りの所持金は：" + money);
        	} else {
        		result = false;
        	}
        	//************ここに(所持金 - $10)の計算書く****************
        	
        } else if (currentIndex == 1) {
        	System.out.println("$50");
        	if (money >= 50) {
        		money = money - 50;
        		System.out.println("ガチャを引いたよ残りの所持金は：" + money);
        	} else {
        		result = false;
        	}
        	//************ここに(所持金 - $50)の計算書く****************
        	
        } else if(currentIndex == 2) {
        	System.out.println("$100");
        	if (money >= 100) {
        		money = money - 100;
        		System.out.println("ガチャを引いたよ残りの所持金は：" + money);
        	} else {
        		result = false;
        	}
        	//************ここに(所持金 - $100)の計算書く****************
        	
        } else {
        	System.out.println("バトルへ");
        }
        
        return result;
    }
	
	// 画像を取得するためのメソッド
	public Image getImage() {
		return background;
    }
	
	
	/* 文字列の中心の長さを返します
	 *  @param g Graphicsオブジェクト
	 *  @param s 対象の文字列
	 *  @return center 中心までの長さ
	 */
	public int stringCenterX(Graphics g, String s) {
		int center;
		// GraphicsオブジェクトからFontMetricsを取得
		FontMetrics fm = g.getFontMetrics();	
		// 文字列の幅を取得
		int stringWidth = fm.stringWidth(s);
		// 中心位置を計算
		center = stringWidth / 2;
		return center;
	}
	
	public Status Efirst (Status em) {
		if (em == null) {
            // 初めて装備する場合、デフォルトのステータスを設定
        	em = new Status(0, 0, 0, 0, 0); // デフォルトのステータス
        }
		
		return em;
		
	}
}
