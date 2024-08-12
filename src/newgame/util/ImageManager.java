package newgame.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageManager {
    private static ImageManager instance;
    private Image background;
    private Image administrator;
	private BufferedImage combinedImage; // 合成された画像を保持するためのフィールド

    // プライベートコンストラクタ
    private ImageManager() {
    }

    // インスタンスを取得するためのメソッド
    public static synchronized ImageManager getInstance(){
        if (instance == null) {
            instance = new ImageManager();
        }
        return instance;
    }

    // 初期化処理を行うメソッド
    public void init() throws IOException {
        // 画像の読み込み
        background = ImageIO.read(getClass().getClassLoader().getResource("res/img/backimage/start.jpg"));
        administrator = ImageIO.read(getClass().getClassLoader().getResource("res/img/character/administrator.png"));
        if (background == null) {
            throw new IOException("Failed to load image.");
        }
        
     	// 背景画像・キャラクター画像を特定のサイズにスケーリング（例: 幅1000px, 高さ600px）
        Image scaledBackground = background.getScaledInstance(1000, 700, Image.SCALE_SMOOTH);
        Image scaledAdministrator = administrator.getScaledInstance(400,400, Image.SCALE_SMOOTH);
        
        // 背景画像のサイズに合わせたBufferedImageの作成
        BufferedImage bufferedBackground = new BufferedImage(
        scaledBackground.getWidth(null),
        scaledBackground.getHeight(null),
        BufferedImage.TYPE_INT_ARGB
        );
    	
    	// グラフィックスコンテキストの取得
        Graphics2D g2d = bufferedBackground.createGraphics();
        
        // 背景画像を描画
        g2d.drawImage(scaledBackground, 0, 0, null);
        
        // キャラクター画像をセンターに描画
        int centerX = (bufferedBackground.getWidth() - scaledAdministrator.getWidth(null)) / 2;
        int centerY = (bufferedBackground.getHeight() - scaledAdministrator.getHeight(null)) / 2;
        g2d.drawImage(scaledAdministrator, centerX, centerY, null);
        
        // グラフィックスコンテキストを解放
        g2d.dispose();

        // 合成された画像をフィールドに保持
        combinedImage = bufferedBackground;
    }

    // 画像を取得するためのメソッド
    public Image getImage() {
        return combinedImage;
    }
}
