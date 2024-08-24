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
import newgame.util.ItemRarity;
import newgame.util.KeyManager;
import newgame.util.WeaponType;

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
	public String gacha;//引くガチャ番号
	public GachaResult gachaResult;//ガチャ結果
	public String gachaRank;//引くガチャの名前
	public ItemRarity itemRarity;//アイテムのレアリティ(R,SR,SSR,UR)
	public WeaponType weaponType;//アイテムの種類
	@Override
	public void init() throws IOException {
		// 背景画像の読み込み
        background = ImageIO.read(getClass().getClassLoader().getResource("res/img/backimage/gacha_after.jpg"));
        gacha_return = ImageIO.read(getClass().getClassLoader().getResource("res/img/button/gacha_back.png"));
        warrior = ImageIO.read(getClass().getClassLoader().getResource("res/img/character/warrior.gif"));
        first_evo = ImageIO.read(getClass().getClassLoader().getResource("res/img/character/first_evolution.gif"));
        second_evo = ImageIO.read(getClass().getClassLoader().getResource("res/img/character/second_evolution.gif"));
        //ガチャ番号を取得、gacha()の引数はStringにしている
        gacha = System.getProperty("gachaNo");
        gachaResult = DatabaseConnection.gacha(gacha);
        //ガチャ結果をそれぞれの変数に代入
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
        String text =  gachaRank + "ガチャを引いた！";
        int textCenter = stringCenterX(g, text);
        g.drawString(text, 500 - textCenter, 200);
        
        String resultTitle = "レア度" + itemRarity +  "の" + weaponType + "が出た！";
        int resultTitleCenter = stringCenterX(g, resultTitle);
        
        g.drawString(resultTitle, 500 - resultTitleCenter, 230);
        
        g.drawString("レア度Aの武器が出た！", 360, 230);
        g.drawString("攻撃力+10", 450, 260);		//ガチャの内容を取得して表示
        g.drawString("攻撃力+10", 400, 260);		//ガチャの内容を取得して表示

        //ステータス
        g.drawString("【ステータス】", 210, 320);
        g.drawString("ＨＰ　：　+　" + hp, 210, 350);		//ガチャの内容を取得して表示
        g.drawString("攻撃力：　+　" + attack, 210, 380);	//ガチャの内容を取得して表示
        g.drawString("防御力：　+　" + defense, 210, 410);	//ガチャの内容を取得して表示
        g.drawString("回避　：　+　" + agility, 210, 440);		//ガチャの内容を取得して表示
        g.drawString("運　　：　+　" + luck, 210, 470);		//ガチャの内容を取得して表示
        
        g.drawString("【アイテム】", 450, 320);
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
}
