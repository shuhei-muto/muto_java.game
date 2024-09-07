//package newgame.bgm;
//
//import javafx.application.Application;
//import javafx.application.Platform;
//import javafx.scene.input.KeyCode;
//import javafx.scene.input.KeyEvent;
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;
//import javafx.stage.Stage;
//
//import java.awt.Graphics;
//import java.awt.image.BufferStrategy;
//import java.io.File;
//
//import newgame.screen.BattleScreen;
//import newgame.screen.ScreenManager;
//
//import javafx.scene.layout.StackPane;
//import javafx.scene.Scene;
//
//public class BattleBgm extends Application {
//	@Override
//	public void start(Stage primaryStage) {
//		String windUrl1 = "src/res/bgm/wind.mp3";
//        String battleUrl2 = "src/res/bgm/battle.mp3";
//        String slashUrl3 = "src/res/bgm/soundEffects_slash.mp3";
//        
//        // Mediaオブジェクトを作成
//        Media wind = new Media(new File(windUrl1).toURI().toString());
//        Media battle = new Media(new File(battleUrl2).toURI().toString());
//        Media slash = new Media(new File(slashUrl3).toURI().toString());
//        
//        // MediaPlayerオブジェクトを作成
//        MediaPlayer windBgm = new MediaPlayer(wind);
//        MediaPlayer battleBgm = new MediaPlayer(battle);
//        MediaPlayer slashBgm = new MediaPlayer(slash);
//        
//        //風の音が止まったら戦闘BGMを再生する
//        windBgm.setOnEndOfMedia(() -> battleBgm.play());
//        
//        // シーンとステージの設定
//        StackPane root = new StackPane();
//        Scene scene = new Scene(root, 300, 200);
//
//        // キーイベントリスナーを追加
//        scene.setOnKeyPressed(event -> handleKeyPress(event, slashBgm));
//        
//        primaryStage.setTitle("Media Player Example");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//        primaryStage.setAlwaysOnTop(false);
//        
//        //最初のBGMを再生
//        windBgm.play();
//        
//        
//        // 音楽再生中に実行する別の処理
//        new Thread(() -> {
//            while (true) {
//                // 他の処理（例: UI更新、データ処理など）
//                System.out.println("音楽再生中...");
//                
//                try {
//                    Thread.sleep(1000);
//                    return;
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//	}
//	
//	private void handleKeyPress(KeyEvent event, MediaPlayer mediaPlayer) {
//		//エンターキーが押されたら効果音を再生する
//		if (event.getCode() == KeyCode.ENTER) {
//			mediaPlayer.stop();		//stopを入れることでエンターキーが押されるたびに再生される
//			mediaPlayer.play();
//		}
//	}
//	
//	public static void startApplication(String[] args) {
//		launch(args);
//		
//	}
//}
