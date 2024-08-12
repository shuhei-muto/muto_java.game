package newgame.object;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import newgame.util.KeyManager;

public class ImageObject extends GameObject {
	
	Image image = null;
	float scaleWidth=1.0f;
	float scaleHeight=1.0f;

	
    @Override
    public void update() {
//        // Characterの更新処理
//    	x += 1;
//    	y += 1;
    	
    	
        // KeyManagerインスタンスを取得
        KeyManager keyManager = KeyManager.getInstance();

        // 左矢印キーが押された場合
        if (keyManager.isKeyPressed(KeyEvent.VK_LEFT)) {
            x -= 10; // 左に移動
        }
        
        // 右矢印キーが押された場合
        if (keyManager.isKeyPressed(KeyEvent.VK_RIGHT)) {
            x += 10; // 右に移動
        }

        // 上矢印キーが押された場合
        if (keyManager.isKeyPressed(KeyEvent.VK_UP)) {
            y -= 10; // 上に移動
        }

        // 下矢印キーが押された場合
        if (keyManager.isKeyPressed(KeyEvent.VK_DOWN)) {
            y += 10; // 下に移動
        }
    }

    @Override
    public void draw(Graphics g) {        
    	// 画像がなければ帰る
    	if (image==null) return;
    	// イメージの描画
    	
    	
//    	int scalingFactorWidth = (int)(width*scaleWidth);
//    	int scalingFactorHeight = (int)(height*scaleHeight);
    	
    	g.drawImage( image, 
    			0, 
    			0, 
    			(int)(width*scaleWidth), 
    			(int)(height*scaleHeight),  
    			null);
        
    }
    
    /********************************
     * 削除時の掃除処理
     ********************************/
    public void cleanup() {};
    
    
    // イメージのセット
    public void setImage(Image i) {
    	image = i;
    	width = i.getWidth(null);
    	height = i.getHeight(null);
    }
}