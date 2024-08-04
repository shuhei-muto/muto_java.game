package newgame.object;

import java.awt.Graphics;

public abstract class GameObject {
    protected int x = 0;
    protected int y = 0;
    protected int height = 0;
    protected int width = 0;
    protected boolean toBeDeleted = false;

    /********************************
     * 更新処理
     ********************************/
    public abstract void update();

    /********************************
     * 描画処理
     ********************************/
    public abstract void draw(Graphics g);
    
    
    /********************************
     * 削除時の掃除処理
     ********************************/
    public abstract void cleanup();
    
    
    
    /********************************
     * isDeleteのゲッター
     ********************************/
    public boolean isToBeDeleted() {
        return toBeDeleted;
    }

    /********************************
     * isDeleteのセッター
     ********************************/
    public void setToBeDeleted(boolean toBeDeleted) {
        this.toBeDeleted = toBeDeleted;
    }
}
