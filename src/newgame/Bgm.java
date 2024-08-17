package newgame;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class Bgm extends Application {
	@Override
    public void start(Stage primaryStage) {
        String mp3FilePath = "/Applications/Eclipse_2023-03.app/Contents/workspace/newgame/src/res/bgm/gamestart.mp3";
        Media media = new Media(mp3FilePath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }
	
	public static void main(String[] args) {
		launch();
	}
}
