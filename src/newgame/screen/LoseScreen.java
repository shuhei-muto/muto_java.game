package newgame.screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;

public class LoseScreen extends Screen {
	private Image background;
	
	@Override
	public void init() throws IOException {
		//画像の読み込み
		background = ImageIO.read(getClass().getClassLoader().getResource("res/img/lose.png"));
	};
	
	public void update() {};
	
	@Override
	public void draw(Graphics g) {
		// 背景画像がなければ帰る
		if (background == null) return;
		
		//ここに描画するものを入れていく
		g.drawImage(background, 0, 0, 1000, 700, null);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(400, 500, 200, 100);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Serif", Font.PLAIN, 20));
		g.drawString("REPLAY", 460, 555);
	};
	
	public void dispose() {};
	
	public void cleanup() {};
	
	// 画像を取得するためのメソッド
	public Image getImage() {
		return background;
	}
}
