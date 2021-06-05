package App;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import java.net.MalformedURLException;
import java.nio.file.Paths;

public class SoundEffectsClass {
    private MediaPlayer mediaPlayer;
    private boolean isMuted = false;

    public void playIntroMusic() throws MalformedURLException {
        mediaPlayer = new MediaPlayer(new Media(Paths.get("C:\\Users\\joos\\IdeaProjects\\MillionairesFXApp\\src\\App\\sounds\\main theme.mp3").toUri().toString()));
        mediaPlayer.setOnReady(() -> mediaPlayer.play());
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
    }

    public void playQuizMusic(int questionNumber) {
            if (questionNumber <= 5) {
                mediaPlayer = new MediaPlayer(new Media(Paths.get("C:\\Users\\joos\\IdeaProjects\\MillionairesFXApp\\src\\App\\sounds\\500_5000_music.mp3").toUri().toString()));
            } else if (questionNumber <= 10) {
                mediaPlayer = new MediaPlayer(new Media(Paths.get("C:\\Users\\joos\\IdeaProjects\\MillionairesFXApp\\src\\App\\sounds\\7500_50000_music.mp3").toUri().toString()));
            } else if (questionNumber <= 11) {
                mediaPlayer = new MediaPlayer(new Media(Paths.get("C:\\Users\\joos\\IdeaProjects\\MillionairesFXApp\\src\\App\\sounds\\75000_music.mp3").toUri().toString()));
            } else if (questionNumber <= 13) {
                mediaPlayer = new MediaPlayer(new Media(Paths.get("C:\\Users\\joos\\IdeaProjects\\MillionairesFXApp\\src\\App\\sounds\\150000_250000_music.mp3").toUri().toString()));
            } else if (questionNumber <= 14) {
                mediaPlayer = new MediaPlayer(new Media(Paths.get("C:\\Users\\joos\\IdeaProjects\\MillionairesFXApp\\src\\App\\sounds\\500000_music.mp3").toUri().toString()));
            } else {
                mediaPlayer = new MediaPlayer(new Media(Paths.get("C:\\Users\\joos\\IdeaProjects\\MillionairesFXApp\\src\\App\\sounds\\1000000_music.mp3").toUri().toString()));
            }

            mediaPlayer.setOnReady(() -> mediaPlayer.play());
            mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));

            if(isMuted)
                muteMediaPlayer();
    }

    public void playQuestionIntroSound() {
            mediaPlayer = new MediaPlayer(new Media(Paths.get("C:\\Users\\joos\\IdeaProjects\\MillionairesFXApp\\src\\App\\sounds\\lets play.mp3").toUri().toString()));
            mediaPlayer.setOnReady(() -> mediaPlayer.play());
            mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));

            if(isMuted)
                muteMediaPlayer();
    }

    public void playAnswerSound() {
            mediaPlayer = new MediaPlayer(new Media(Paths.get("C:\\Users\\joos\\IdeaProjects\\MillionairesFXApp\\src\\App\\sounds\\answer sound.mp3").toUri().toString()));
            mediaPlayer.setOnReady(() -> mediaPlayer.play());
            mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));

            if(isMuted)
                muteMediaPlayer();
    }

    public void playCorrectAnswerSound() {
            mediaPlayer = new MediaPlayer(new Media(Paths.get("C:\\Users\\joos\\IdeaProjects\\MillionairesFXApp\\src\\App\\sounds\\correct answer.mp3").toUri().toString()));
            mediaPlayer.setOnReady(() -> mediaPlayer.play());
            mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));

            if(isMuted)
                muteMediaPlayer();
    }

    public void playWrongAnswerSound() {
            mediaPlayer = new MediaPlayer(new Media(Paths.get("C:\\Users\\joos\\IdeaProjects\\MillionairesFXApp\\src\\App\\sounds\\wrong answer.mp3").toUri().toString()));
            mediaPlayer.setOnReady(() -> mediaPlayer.play());
            mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));

            if(isMuted)
                muteMediaPlayer();
    }

    public void lifebouySound() {
        mediaPlayer = new MediaPlayer(new Media(Paths.get("C:\\Users\\joos\\IdeaProjects\\MillionairesFXApp\\src\\App\\sounds\\lifebouy sound.mp3").toUri().toString()));
        mediaPlayer.setOnReady(() -> mediaPlayer.play());
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.stop());

        if(isMuted)
            muteMediaPlayer();
    }

    public void stopMediaPlayer() {
        mediaPlayer.stop();
    }

    public void muteMediaPlayer() {
        this.isMuted = true;
        mediaPlayer.setMute(true);
    }

    public void unmuteMediaPlayer() {
        this.isMuted = false;
        mediaPlayer.setMute(false);
    }
}
