package newgame.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

public class KeyManager implements KeyListener {
    private static KeyManager instance;
    private Set<Integer> pressedKeys;

    // プライベートコンストラクタ
    private KeyManager() {
        pressedKeys = new HashSet<>();
    }

    // インスタンスを取得するためのメソッド
    public static synchronized KeyManager getInstance() {
        if (instance == null) {
            instance = new KeyManager();
        }
        return instance;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        pressedKeys.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // 必要に応じて実装
    }

    // 特定のキーが押されているかどうかを確認するメソッド
    public boolean isKeyPressed(int keyCode) {
        return pressedKeys.contains(keyCode);
    }
}
