package newgame.screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import newgame.DatabaseConnection;
import newgame.DatabaseConnection.GachaResult;
import newgame.bgm.Bgm;
import newgame.util.EquipmentManager;
import newgame.util.GlobalState;
import newgame.util.ItemRarity;
import newgame.util.KeyManager;
import newgame.util.Status;
import newgame.util.WeaponType;

public class GachaScreen extends Screen {
	
	private Image background;
	private Image gacha_return;
	private Image warrior;
	private Image first_evo;
	private Image second_evo;
	private Image arrow;
	private boolean gachaScreen = true;
	private boolean return_screen = false;
	public String itemName;
	public int hp;
	public int attack;
	public int defense;
	public int agility;
	public int luck;
	public String gacha;//引くガチャ番号
	public GachaResult gachaResult;//ガチャ結果
	public String gachaRank;//引くガチャの名前
	public ItemRarity itemRarity;//アイテムのレアリティ(R,SR,SSR,UR)
	public WeaponType weaponType;//アイテムの種類
	public Status status;
	public Status getWeaponStatus;
	public Status weaponStatusNow;
	public Status now_status;
	public int evo;
	public int old_evo;
	public int XevoX;
	Bgm bgm;
	
	@Override
	public void init() throws IOException {
		// 背景画像の読み込み
        background = ImageIO.read(getClass().getClassLoader().getResource("res/img/backimage/gacha_after.jpg"));
        gacha_return = ImageIO.read(getClass().getClassLoader().getResource("res/img/button/gacha_back.png"));
        warrior = ImageIO.read(getClass().getClassLoader().getResource("res/img/character/warrior.gif"));
        first_evo = ImageIO.read(getClass().getClassLoader().getResource("res/img/character/first_evolution.gif"));
        second_evo = ImageIO.read(getClass().getClassLoader().getResource("res/img/character/second_evolution.gif"));
        arrow = ImageIO.read(getClass().getClassLoader().getResource("res/img/arrow.png"));
        gacha();//ガチャを引く
        
        bgm = new Bgm();	//Bgmインスタンスを作成
        try {
        	bgm.gacha();	//BGMを再生
        } catch(Exception e) {
        	System.out.println("例外が発生しました。");
            System.out.println(e);
        }
    	
	}
	
	public void update() {
		// KeyManagerインスタンスを取得
        KeyManager keyManager = KeyManager.getInstance();
        
        //エンターキーを押された時の画面遷移
        if (keyManager.isKeyPressed(KeyEvent.VK_ENTER)) {
        	gachaScreen = !gachaScreen;
        	if (!gachaScreen && return_screen) {
                GachaMainScreen nextScreen = new GachaMainScreen();
                
                statusSet();//入手したアイテムをステータスに反映させる
                // EquipmentManager のインスタンスを作成
            	// グローバル状態から EquipmentManager を取得
                EquipmentManager manager = GlobalState.equipmentManager;
            	manager.equipItem(weaponType, getWeaponStatus);
                ScreenManager.getInstance().setScreen(nextScreen);
                bgm.stop();	//クラスフィールドbgmでstopを呼び出す
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
        String text =  gachaRank + "ガチャを引いた！";
        int textCenter = stringCenterX(g, text);
        g.drawString(text, 500 - textCenter, 200);
        
        String resultTitle = "レア度" + itemRarity +  "の" + itemName +"(" +weaponType + ")" + "が出た！";
        int resultTitleCenter = stringCenterX(g, resultTitle);
        g.drawString(resultTitle, 500 - resultTitleCenter, 230);
        
        String statusText = "" + getWeaponStatus;//入手アイテムの内容
        int statusTextCenter = stringCenterX(g, statusText);
        
        g.drawString(statusText, 500 - statusTextCenter, 260);		//ガチャの内容を取得して表示
        //ステータス
        //mapからnullになっている
        g.drawString("【ステータス】", 210, 320);
        g.drawString("ＨＰ　：" + ((status.getHp()      + hp)      * XevoX) + hikizan(((status.getHp()      + hp)      * XevoX), (status.getHp()      + weaponStatusNow.getHp())      ), 210, 350);	
        g.drawString("攻撃力：" + ((status.getAttack()  + attack)  * XevoX) + hikizan(((status.getAttack()  + attack)  * XevoX), (status.getAttack()  + weaponStatusNow.getAttack())  ), 210, 380);	
        g.drawString("防御力：" + ((status.getDefense() + defense) * XevoX) + hikizan(((status.getDefense() + defense) * XevoX), (status.getDefense() + weaponStatusNow.getDefense()) ), 210, 410);	
        g.drawString("回避　：" + ((status.getAgility() + agility) * XevoX) + hikizan(((status.getAgility() + agility) * XevoX), (status.getAgility() + weaponStatusNow.getAgility()) ), 210, 440);	
        g.drawString("運　　：" + ((status.getLuck()    + luck)    * XevoX) + hikizan(((status.getLuck()    + luck)    * XevoX), (status.getLuck()    + weaponStatusNow.getLuck())    ), 210, 470);	
        
        g.drawString("【アイテム】", 450, 320);
        g.drawString("回復薬×" + potionCC() + potionPlus(), 450, 350);	//×の後にガチャの内容を取得して個数を表示したい
        
        if(weaponType.equals(WeaponType.進化アイテム)) {
        	if(evo == 1) {
        		g.drawImage(warrior,   450, 480, 50, 50, null);
        		g.drawImage(arrow,     500, 430, 100, 100, null);
        		g.drawImage(first_evo, 600, 330, 200, 200, null);//int x, int y, int width, int height
        	} else if (evo == 2) {
        		g.drawImage(first_evo,  450, 480, 50, 50, null);
        		g.drawImage(arrow,      500, 430, 100, 100, null);
        		g.drawImage(second_evo, 600, 330, 200, 200, null);
        	} else {
        		System.out.println("これ以上進化しないよ");
        	}
        }
        
        
        //2秒経ってから「ガチャへ戻る」ボタンを表示
        Timer timer = new Timer(1000, e -> {
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
	/* init内で使用される。
	 * アイテムガチャの結果をまとめている
	 */
	public void gacha() {
		//ガチャ番号を取得、gacha()の引数はStringにしている
        gacha = System.getProperty("gachaNo");
        gachaResult = DatabaseConnection.gacha(gacha);
        //ガチャ結果をそれぞれの変数に代入
        itemName = gachaResult.getItemName();
    	hp = gachaResult.getHp();
    	attack = gachaResult.getAttack();
    	defense = gachaResult.getDefense();
    	agility = gachaResult.getAgility();
    	luck = gachaResult.getLuck(); 
    	itemRarity = ItemRarity.fromId(Integer.parseInt(gachaResult.getRarity()));
    	weaponType = WeaponType.fromId(Integer.parseInt(gachaResult.getType()));
    	if(gacha.equals("0")) {
    		gachaRank = "ブロンズ";
    	} else if (gacha.equals("1")) {
    		gachaRank = "シルバー";
    	} else {
    		gachaRank = "ゴールド";
    	}
    	
    	// グローバル状態から 現在の Status を取得
        status = GlobalState.currentStatus;
        now_status = status;
        evo = status.getEvolutionCount();
        XevoX = 1;
        old_evo = status.getEvolutionCount();
        //進化アイテム入手時
        if(weaponType.equals(WeaponType.進化アイテム)) {
        	if (evo == 1) {
        		XevoX =  2;
        	} else if (evo == 2) {
        		XevoX =  3;
        	} else {
        		System.out.println("最終進化しているよ");
        	}
        }
        // ガチャを引いて装備
        getWeaponStatus = new Status(hp, attack, defense, agility, luck);
        getWeaponStatus.setItemName(itemName);
        
        // グローバル状態から EquipmentManager を取得
        EquipmentManager manager = GlobalState.equipmentManager;
        // 現在装備されているアイテムを表示
        
        //マップから装備中のものを取得する
        weaponStatusNow = manager.getEquippedItem(weaponType);
        if (weaponStatusNow == null) {
            // 初めて装備する場合、デフォルトのステータスを設定
            weaponStatusNow = new Status(0, 0, 0, 0, 0); // デフォルトのステータス
        }
	}
	

	/*
	 * @param a 入手した装備
	 * @param b 現在装備中のアイテム
	 * @return String 計算結果
	 */
	public static String hikizan(int a, int b) {
        // aとbの引き算を実行
        int result = a - b;
        String xxx = "";

        // 結果をString型に変換し、正の場合には"+"を追加
        if (result > 0) {
            return "(+" + result + ")";
        } else if (result == 0) {
        	return xxx;
        } else {
            return "(" + Integer.toString(result) + ")";
        }
    }
	/*
	 * ガチャで引いたアイテムを装備し、ステータスを反映させる
	 * (現在のステータス - 装備中のアイテム + 入手した装備)
	 */
	public void statusSet() {
		if (weaponType.equals(WeaponType.ステ上げアイテム)) {
			//永続ステ上げの場合加算していく
			status.setHp(status.getHp()+ hp);
            status.setAttack(status.getAttack() + attack);
            status.setDefense(status.getDefense() + defense);
            status.setAgility(status.getAgility() + agility);
            status.setLuck(status.getLuck() + luck);
            status.setStatusUpCount(status.getStatusUPCount()+1);
            
        }  else if (weaponType.equals(WeaponType.回復アイテム)) {
        	status.setHp(status.getHp() - weaponStatusNow.getHp() + hp);//HP加算していく？
        	status.setPotionCount(status.getPotionCount() + 1);
        } else if (weaponType.equals(WeaponType.進化アイテム)) {
        	//ここでステータスを倍々にしていくと表示の際も倍になりおかしくなる
        	//進化１回目
        	System.out.println("進化したね");
            if(status.getEvolutionCount() == 1) {
            	System.out.println(XevoX + "倍になる");
            	status.setHp(status.getHp() * XevoX);
                status.setAttack(status.getAttack() * XevoX);
                status.setDefense(status.getDefense() * XevoX);
                status.setAgility(status.getAgility() * XevoX);
                status.setLuck(status.getLuck() * XevoX);
            	status.setevolutionCount(status.getEvolutionCount() * 2);
            } else if(status.getEvolutionCount() == 2) {
            	//進化２回目
            	System.out.println(evo + "=evo ２回目の進化。");
            	status.setHp(status.getHp() * XevoX);
                status.setAttack(status.getAttack() * XevoX);
                status.setDefense(status.getDefense() * XevoX);
                status.setAgility(status.getAgility() * XevoX);
                status.setLuck(status.getLuck() * XevoX);
            	status.setevolutionCount(status.getEvolutionCount() + 1);
            } else {
            	System.out.println("最終進化してるね");
            }
           
        } else {
        	//そのほかの装備
        	status.setHp(status.getHp() - weaponStatusNow.getHp() + hp);
            status.setAttack(status.getAttack() - weaponStatusNow.getAttack() + attack);
            status.setDefense(status.getDefense() - weaponStatusNow.getDefense() + defense);
            status.setAgility(status.getAgility() - weaponStatusNow.getAgility() + agility);
            status.setLuck(status.getLuck() - weaponStatusNow.getLuck() + luck);
        }
        
    }
	
	public String potionCC() {
		int potion = status.getPotionCount();
		if(weaponType.equals(WeaponType.回復アイテム)) {
			potion = potion+1;
		}
		return Integer.toString(potion) ;
	}
	public String potionPlus() {
		String xxx = "";
		if (weaponType.equals(WeaponType.回復アイテム)) {
            return "(+1)";
        } 
		return xxx;
	}
	
}