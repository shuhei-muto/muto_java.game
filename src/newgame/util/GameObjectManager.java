package newgame.util;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import newgame.object.GameObject;

public class GameObjectManager {
    private static GameObjectManager instance;
    private List<GameObject> gameObjects;

    // プライベートコンストラクタ
    private GameObjectManager() {
        gameObjects = new ArrayList<>();
    }

    // インスタンスを取得するためのメソッド
    public static synchronized GameObjectManager getInstance() {
        if (instance == null) {
            instance = new GameObjectManager();
        }
        return instance;
    }

    // GameObjectを追加するメソッド
    public void add(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    // すべてのGameObjectのupdateメソッドを呼び出すメソッド
    public void updateAll() {
        for (GameObject gameObject : gameObjects) {
            gameObject.update();
        }
    }
    // すべてのGameObjectのupdateメソッドを呼び出すメソッド
    public void drawAll(Graphics g) {
        for (GameObject gameObject : gameObjects) {
            gameObject.draw(g);
        }
    }
    
    
    // すべてのGameObjectのupdateメソッドを呼び出すメソッド
    public void cleanupAll() {
        Iterator<GameObject> iterator = gameObjects.iterator();
        while (iterator.hasNext()) {
            GameObject gameObject = iterator.next();
            if (gameObject.isToBeDeleted()) {
            	gameObject.cleanup();
                iterator.remove();
            }
        }
    }
}
