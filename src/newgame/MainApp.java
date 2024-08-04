package newgame;

import java.awt.Image;
import java.io.IOException;

import javax.swing.JFrame;

import newgame.object.ImageObject;
import newgame.util.GameObjectManager;
import newgame.util.ImageManager;
import newgame.util.KeyManager;

public class MainApp {

	    /**********************************
	     * エントリーポイント
	     **********************************/
	    public static void main(String[] args) {
	    	
	    	
	    	// 初期化 - 読み込み処理
	    	try {
	    		ImageManager.getInstance().init();
	    	} catch (IOException e) {
	    	    e.printStackTrace();
	    	}
	    	
	    	
	    	// フレーム生成
	        JFrame frame = new JFrame("Simple Game");
	        

	        // キャンバス生成
	        MainCanvas canvas = new MainCanvas();
	        frame.add(canvas);
	        frame.pack();
	        // 閉じるボタン対応
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        // 画面サイズ変更を不可能にする
	        frame.setResizable(false);
	        
	        // 画面中央へ配置
	        frame.setLocationRelativeTo(null);
	        frame.setVisible(true);
	        // ループスタート
	        canvas.start();
	        
	        
	        
	        // テストゲームオブジェクト表示
	        ImageObject test = new ImageObject();
	        Image image = ImageManager.getInstance().getImage();
	        test.setImage(image);
	        GameObjectManager.getInstance().add(test);
	        
	        
	        // キー入力設定
	        KeyManager keyManager = KeyManager.getInstance();
	        canvas.addKeyListener(keyManager);
	        canvas.requestFocusInWindow();
	        System.out.println(canvas.isFocusOwner());
	        
	        
	    }
	}
