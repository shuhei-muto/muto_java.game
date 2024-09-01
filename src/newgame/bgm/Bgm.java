package newgame.bgm;

import java.io.File;
import javafx.scene.media.AudioClip;

public class Bgm {
	private AudioClip c;
	
	//音楽ファイルを再生する共通メソッド
	private void playAudio(String filePath) {
		if (c != null && c.isPlaying()) {
			c.stop();
		}
		c = new AudioClip(new File(filePath).toURI().toString());
		c.play();
	}
	
	//BGMを止めるためのメソッド
	public void stop() {
		if (c == null) {
			System.out.println("AudioClip is null. Cannot stop playback.");
			return;
		}
		c.stop();
	}
	
	//BGMが再生されているか
	public boolean isPlaying() {
		return c.isPlaying();
	}
	
	//ゲームスタート画面用のBGM
	public void gamestart() throws Exception {
		String mp3FilePath = "src/res/bgm/gamestart.mp3";
		playAudio(mp3FilePath);
	}
	
	//名前決める画面用のBGM
	public void namescreen() throws Exception {
		String mp3FilePath = "src/res/bgm/namescreen.mp3";
		playAudio(mp3FilePath);
	}
	
	//ガチャ画面用のBGM
	public void gacha() throws Exception {
		String mp3FilePath = "src/res/bgm/gacha.mp3";
		playAudio(mp3FilePath);
	}
	
	//バトル前のBGM(風)
	public void wind() throws Exception {
		String mp3FilePath = "src/res/bgm/wind.mp3";
		playAudio(mp3FilePath);
	}
	
	//バトル画面用のBGM
	public void battlescreen() throws Exception {
		String mp3FilePath = "src/res/bgm/battle.mp3";
		playAudio(mp3FilePath);
	}
	
	//勝利画面用のBGM
	public void victory() throws Exception {
		String mp3FilePath = "src/res/bgm/victory.mp3";
		playAudio(mp3FilePath);
	}
	
	//敗北画面用のBGM
	public void lose() throws Exception {
		String mp3FilePath = "src/res/bgm/lose.mp3";
		playAudio(mp3FilePath);
	}
	
	
	
	
	
}
