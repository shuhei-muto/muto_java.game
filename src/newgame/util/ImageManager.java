package newgame.util;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageManager {
    private static ImageManager instance;
    private Image image;

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
        image = ImageIO.read(getClass().getClassLoader().getResource("res/img/yuusya_game.png"));
        if (image == null) {
            throw new IOException("Failed to load image.");
        }
    }

    // 画像を取得するためのメソッド
    public Image getImage() {
        return image;
    }
}
