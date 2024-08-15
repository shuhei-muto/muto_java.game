package newgame.screen;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import newgame.util.KeyManager;

public class NameScreen extends Screen{
	
	private Image background;
	private Image decisionButton;
	private Image moneyButton;
	public int price;
	
	@Override
	public void init() throws IOException {
		// 背景画像の読み込み
        background = ImageIO.read(getClass().getClassLoader().getResource("res/img/backimage/decide_name.jpg"));
        decisionButton = ImageIO.read(getClass().getClassLoader().getResource("res/img/button/decision_button.png"));
        moneyButton = ImageIO.read(getClass().getClassLoader().getResource("res/img/button/money.png"));
	}
	
	public void update() {};
	public void draw(Graphics g) {
		// 背景画像がなければ帰る
		if (background==null) return;
		
		//ここに描画するものを入れていく
		g.drawImage(background, 0, 0, 1000, 700, null);
		Image scaledDeButton = decisionButton.getScaledInstance(120, 60, Image.SCALE_SMOOTH);
		int centerX_dB = (1000 - scaledDeButton.getWidth(null)) / 2 ;
		g.drawImage(scaledDeButton, centerX_dB, 450, null);
		
		Image scaledmoneyButton = moneyButton.getScaledInstance(400, 80, Image.SCALE_SMOOTH);
		int centerX_mB = (1000 - scaledmoneyButton.getWidth(null)) / 2 ;
		g.drawImage(scaledmoneyButton, centerX_mB, 550, null);
		
	};
	public void dispose() {};
	
	public void cleanup() {};
	
	// 画像を取得するためのメソッド
    public Image getImage() {
        return background;
    }
}
