package newgame.object;

import java.awt.Graphics;
import java.awt.Image;

import newgame.util.ImageManager;

public class TextObject extends GameObject {
	
	int x = 0;

    @Override
    public void update() {
        // Characterの更新処理
    	++x;
    }

    @Override
    public void draw(Graphics g) {
        // Characterの描画処理
//        g.setColor(Color.BLACK);
//        g.fillOval(x, 250, 50, 50);
        
    	Image image = ImageManager.getInstance().getImage();
    	int imageWidth = image.getWidth(null);
    	int imageHeight = image.getHeight(null);
    	g.drawImage(image, 0, 0, imageWidth*2,imageHeight*2,  null);
        
    }
    
    /********************************
     * 削除時の掃除処理
     ********************************/
    public void cleanup() {};
    
}