package newgame.screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.imageio.ImageIO;

import newgame.bgm.Bgm;
import newgame.util.KeyManager;


public class GameStartScreen extends Screen {
	
	private Image background;
	private Image administrator;
	private boolean startScreen = true;
	private int count = 0;
	private int step = 0;
	private String message1;
	private String message2;
	private String message3;
	int y = 430;
	boolean loop = true;
	StringBuilder text1 = new StringBuilder();
	StringBuilder text2 = new StringBuilder();
	StringBuilder text3 = new StringBuilder();
	private Bgm bgm;
	private boolean return_screen = false;
	
	@Override
	public void init() throws IOException {
		// 背景画像とその他の画像読み込み
		background = ImageIO.read(getClass().getClassLoader().getResource("res/img/backimage/start.jpg"));
		administrator = ImageIO.read(getClass().getClassLoader().getResource("res/img/character/administrator.png"));
		
		bgm = new Bgm();	//Bgmインスタンスを作成
        try {
        	bgm.gamestart();	//BGMを再生
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
        	startScreen = !startScreen;
        	if (!startScreen && return_screen) {
        		NameScreen nextScreen = new NameScreen();
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
		g.drawImage(background, 0, 0, 1000, 700, null);	//int x, int y, int width, int height
    	Image scaledAdministrator = administrator.getScaledInstance(400, 400, Image.SCALE_SMOOTH);
    	int centerX = (1000 - scaledAdministrator.getWidth(null)) / 2 ;
    	g.drawImage(scaledAdministrator, centerX, 150, null);
    	
    	// GIFアイコンを描画
//        if (gifIcon != null) {
//            // GIFアイコンを描画する位置を指定 (例: x = 100, y = 100)
//            gifIcon.paintIcon(null, g, 100, 100);  // `null` の代わりに `JComponent`を指定しても良いです。
//        }
    	
    	
    	
    	g.setColor(Color.WHITE);
    	int x = (1000 - 700) / 2;
        g.fillRect(x, 400, 700, 200);	//int x, int y, int width, int height
        g.setColor(Color.BLACK);
        g.setFont(new Font("Serif", Font.PLAIN, 24));
        String fixedText = "管理人：";
        g.drawString(fixedText, x+10, 430);
        g.drawString(text1.toString(), x+105, 430);
        g.drawString(text2.toString(), x+105, 460);
        g.drawString(text3.toString(), x+105, 490);
        
        if (step == 0) {
        	message1 = "あのモンスターを倒しに行くのか！？";
        	if (count < message1.length()) {
        		text1.append(message1.charAt(count));
        		count++;
        	} else {
        		step++;
        		count = 0;
        	}
        } else if(step == 1) {
        	message2 = "せめて装備だけは備えておけ！";
        	if (count < message2.length()) {
        		text2.append(message2.charAt(count));
        		count++;
        	} else {
        		step++;
        		count = 0;
        	}
        } else if(step == 2) {
        	message3 = "その前におまえさんの名前はなんじゃったかな？ ▼";
        	if (count < message3.length()) {
        		text3.append(message3.charAt(count));
        		count++;
        	} else {
        		step++;
        		count = 0;
        		return_screen = true;
        	}
        } else {
        	return;
        }
	};

	public void dispose() {};
	
	public void cleanup() {};
	
	// 画像を取得するためのメソッド
    public Image getImage() {
        return background;
    }
}
