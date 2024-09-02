package newgame;

import javax.swing.JFrame;
import newgame.screen.ScreenManager;
import newgame.screen.GameStartScreen;
import newgame.util.KeyManager;

//import newgame.screen.Screen;
//import newgame.screen.BattleScreen;
//import newgame.screen.ScreenChangeListener;
//import newgame.bgm.BattleBgm;
//import javafx.stage.Stage;
//import javafx.application.Application;

public class MainApp {

	
	    /**********************************
	     * エントリーポイント
	     **********************************/
	    public static void main(String[] args) {
	    	
	    	
	    	//スタート画面のインスタンスを取得
	    	GameStartScreen gss = new GameStartScreen();
	    	
	    	// 初期化 - 読み込み処理
    		ScreenManager.getInstance().setScreen(gss);
	    	
    		
	    	// ScreenManagerにリスナーを追加
//	        MainApp app = new MainApp();
//	        ScreenManager.getInstance().addScreenChangeListener(app);
    		
	    	
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
	        
	        
//	        Screen currentScreen = ScreenManager.getInstance().getCurrentScreen();
//	        if (currentScreen instanceof BattleScreen) {
//	        	System.out.println("今バトル画面が表示されている");
//	        } else {
//	        	System.out.println("ここで実行されている");
//	        }
	        
	        // テストゲームオブジェクト表示
//	        ImageObject test = new ImageObject();
//	        Image image = ImageManager.getInstance().getImage();
//	        test.setImage(image);
//	        GameObjectManager.getInstance().add(test);
	        
	        
	        // キー入力設定
	        KeyManager keyManager = KeyManager.getInstance();
	        canvas.addKeyListener(keyManager);
	        canvas.requestFocusInWindow();
//	        System.out.println(canvas.isFocusOwner());
	        
	    }
	    
	    /*****************************
	     * バトル画面Bgm　MediaPlayer用
	     *****************************/
//	    @Override
//	    public void onScreenChange(Screen newScreen) {
//	        // BattleScreenが現在表示されているかどうかを判定
//	        if (newScreen instanceof BattleScreen) {
//	            BattleBgm.startApplication(null);
//	        }
//	    }
	}
