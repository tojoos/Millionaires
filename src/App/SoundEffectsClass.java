package App;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import java.net.MalformedURLException;
import java.nio.file.Paths;

public class SoundEffectsClass {
    private MediaPlayer mediaPlayer;

    public void playIntroMusic() throws MalformedURLException {
        mediaPlayer = new MediaPlayer(new Media(Paths.get("C:\\Users\\joos\\IdeaProjects\\MillionairesFXApp\\src\\App\\sounds\\main theme.mp3").toUri().toString()));
        mediaPlayer.setOnReady(() -> mediaPlayer.play());
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
    }

    public void stopMediaPlayer() {
        mediaPlayer.stop();
    }

    public void muteMediaPlayer() {
        mediaPlayer.setMute(true);
    }

    public void unmuteMediaPlayer() {
        mediaPlayer.setMute(false);
    }


}
