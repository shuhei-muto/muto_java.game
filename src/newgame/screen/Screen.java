package newgame.screen;

import java.io.IOException;
import java.awt.Graphics;


public abstract class Screen {
	
	protected boolean toBeDeleted = false;
	
	public abstract void init() throws IOException;
	public abstract void update();
	public abstract void draw(Graphics g);
	public abstract void dispose();
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
