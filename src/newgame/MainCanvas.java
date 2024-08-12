package newgame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import newgame.util.GameObjectManager;

public class MainCanvas extends Canvas implements Runnable {
	    private boolean running = false;
	    private Thread thread;

	    /*****************************
	     * コンストラクタ
	     *****************************/
	    public MainCanvas() {
	        setPreferredSize(new Dimension(1000, 700));
	    }
	    
	    /*****************************
	     * スレッドの起動・停止
	     *****************************/
	    public synchronized void start() {
	        running = true;
	        thread = new Thread(this);
	        thread.start();
	    }

	    public synchronized void stop() {
	        running = false;
	        try {
	            thread.join();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    /*****************************
	     * スレッド処理 (fps管理しながら、update draw)
	     *****************************/
	    @Override
	    public void run() {
	        
	        long lastTime = System.nanoTime();
	        double amountOfTicks = 30.0;
	        double ns = 1000000000 / amountOfTicks;
	        double delta = 0;
	        while (running) {
	            long now = System.nanoTime();
	            delta += (now - lastTime) / ns;
	            lastTime = now;
	            while (delta >= 1) {
	                update();
	                delta--;
	            }
	            draw();
	        }
	        stop();
	    }

	    
	    /*****************************
	     * メイン更新処理
	     *****************************/
	    private void update() {
	    	// メイン更新処理
	    	GameObjectManager.getInstance().updateAll();
	    	// 更新が終わったら、削除対象になったものの掃除
	    	GameObjectManager.getInstance().cleanupAll();
	    }
	    /*****************************
	     * メイン描画処理
	     *****************************/
	    private void draw() {
	    	
	    	// トリプルバッファ要処理
	        BufferStrategy bs = getBufferStrategy();
	        if (bs == null) {
	            createBufferStrategy(3);
	            return;
	        }
	        // バッファから描画領域取得
	        Graphics g = bs.getDrawGraphics();
	        
	        // まずは真っ白にする(表示のリセット)
	        g.setColor(Color.WHITE);
	        g.fillRect(0, 0, getWidth(), getHeight());
	        
//	        g.setColor(Color.BLACK);
//	        g.fillOval(x, 250, 50, 50);
//	        
//	        g.setColor(Color.BLACK);
//	        g.drawString("FPS: aaaaaaaaaaaaa", 10, 20);
	        
	    	GameObjectManager.getInstance().drawAll(g);
	        
	        // 使い終わったのでリソース破棄
	        g.dispose();
	        // その後、描画実行
	        bs.show();
	    }
	}
