package newgame.screen;

import java.awt.Graphics;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class ScreenManager {
	private static ScreenManager instance;
    private Screen currentScreen;
    private List<Screen> currentScreens;
	
	// プライベートコンストラクタ
	private ScreenManager() {
	}
	
	// インスタンスを取得するためのメソッド
    public static synchronized ScreenManager getInstance(){
        if (instance == null) {
            instance = new ScreenManager();
        }
        return instance;
    }
	
    //表示する画面を設定するメソッド
    public void setScreen(Screen screen) {
        if (currentScreen != null) {
            currentScreen.dispose();  // 現在の画面を解放
        }
        currentScreen = screen;
        try {
        	currentScreen.init();  // 新しい画面を初期化
        } catch (IOException e) {
        	e.printStackTrace();
        }
    }
    
    //画面遷移するメソッド
    public void update() {
        if (currentScreen != null) {
            currentScreen.update();
        }
    }
    
    //画面の描画をするメソッド
    public void draw(Graphics g) {
        if (currentScreen != null) {
            currentScreen.draw(g);
        }
    }
	
    //すべてのGameObjectのcleanupメソッドを呼び出すメソッド
    public void cleanupAll() {
        Iterator<Screen> iterator = currentScreens.iterator();
        while (iterator.hasNext()) {
            Screen screen = iterator.next();
            if (screen.isToBeDeleted()) {
            	screen.cleanup();
                iterator.remove();
            }
        }
    }
}
