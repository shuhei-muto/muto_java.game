package newgame;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import java.io.File;

import java.net.MalformedURLException;
import javafx.scene.media.AudioClip;

public class Bgm extends Application {
	@Override
    public void start(Stage primaryStage) {
//        String mp3FilePath = "/Applications/Eclipse_2023-03.app/Contents/workspace/newgame/src/res/bgm/soundEffects_slash.mp3";
//        Media media = new Media(new File(mp3FilePath).toURI().toString());
//        MediaPlayer mediaPlayer = new MediaPlayer(media);
//        mediaPlayer.play();
    }
	
	public static void main(String[] args) throws Exception {
		String mp3FilePath = "/Applications/Eclipse_2023-03.app/Contents/workspace/newgame/src/res/bgm/soundEffects_slash.mp3";
		AudioClip c = new AudioClip(new File(mp3FilePath).toURI().toString());
		c.play();
//		Thread.sleep(6000);
//		launch();
	}
}
