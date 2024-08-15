package newgame.screen;

import java.awt.Graphics;
import java.awt.Image;

public class ScreenObject {
	Image image = null;
	
	public void draw(Graphics g) {        
    	// 画像がなければ帰る
    	if (image==null) return;
    	// イメージの描画    	
    	g.drawImage( image, 0, 0, null);
    }
    
	
    /********************************
     * 削除時の掃除処理
     ********************************/
    public void cleanup() {};
    
    // イメージのセット
    public void setImage(Image i) {
    	image = i;
    }
}