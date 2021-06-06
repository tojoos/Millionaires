package App;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import java.net.URISyntaxException;

public class SoundEffectsClass {
    private MediaPlayer mediaPlayer;
    private boolean isMuted = false;

    public void playIntroMusic() throws URISyntaxException {
        mediaPlayer = new MediaPlayer(new Media(Main.class.getResource("\\sounds\\main theme.mp3").toURI().toString()));
        mediaPlayer.setOnReady(() -> mediaPlayer.play());
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
    }

    public void playQuizMusic(int questionNumber) throws URISyntaxException {
            if (questionNumber <= 5) {
                mediaPlayer = new MediaPlayer(new Media(Main.class.getResource("\\sounds\\500_5000_music.mp3").toURI().toString()));
            } else if (questionNumber <= 10) {
                mediaPlayer = new MediaPlayer(new Media(Main.class.getResource("\\sounds\\7500_50000_music.mp3").toURI().toString()));
            } else if (questionNumber <= 11) {
                mediaPlayer = new MediaPlayer(new Media(Main.class.getResource("\\sounds\\75000_music.mp3").toURI().toString()));
            } else if (questionNumber <= 13) {
                mediaPlayer = new MediaPlayer(new Media(Main.class.getResource("\\sounds\\150000_250000_music.mp3").toURI().toString()));
            } else if (questionNumber <= 14) {
                mediaPlayer = new MediaPlayer(new Media(Main.class.getResource("\\sounds\\500000_music.mp3").toURI().toString()));
            } else {
                mediaPlayer = new MediaPlayer(new Media(Main.class.getResource("\\sounds\\1000000_music.mp3").toURI().toString()));
            }

            mediaPlayer.setOnReady(() -> mediaPlayer.play());
            mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));

            if(isMuted)
                muteMediaPlayer();
    }

    public void playQuestionIntroSound() throws URISyntaxException {
            mediaPlayer = new MediaPlayer(new Media(Main.class.getResource("\\sounds\\lets play.mp3").toURI().toString()));
            mediaPlayer.setOnReady(() -> mediaPlayer.play());
            mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.stop());

            if(isMuted)
                muteMediaPlayer();
    }

    public void playAnswerSound() throws URISyntaxException {
            mediaPlayer = new MediaPlayer(new Media(Main.class.getResource("\\sounds\\answer sound.mp3").toURI().toString()));
            mediaPlayer.setOnReady(() -> mediaPlayer.play());
            mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.stop());

            if(isMuted)
                muteMediaPlayer();
    }

    public void playCorrectAnswerSound() throws URISyntaxException {

            mediaPlayer = new MediaPlayer(new Media(Main.class.getResource("\\sounds\\correct answer.mp3").toURI().toString()));
            mediaPlayer.setOnReady(() -> mediaPlayer.play());
            mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.stop());

            if(isMuted)
                muteMediaPlayer();
    }

    public void playWrongAnswerSound() throws URISyntaxException {
        mediaPlayer = new MediaPlayer(new Media(Main.class.getResource("\\sounds\\wrong answer.mp3").toURI().toString()));
            mediaPlayer.setOnReady(() -> mediaPlayer.play());
            mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.stop());

            if(isMuted)
                muteMediaPlayer();
    }

    public void lifebouySound() throws URISyntaxException {
        mediaPlayer = new MediaPlayer(new Media(Main.class.getResource("\\sounds\\lifebouy sound.mp3").toURI().toString()));
        mediaPlayer.setOnReady(() -> mediaPlayer.play());
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.stop());

        if(isMuted)
            muteMediaPlayer();
    }

    public void mainPrizeWonSound() throws URISyntaxException {
        mediaPlayer = new MediaPlayer(new Media(Main.class.getResource("\\sounds\\main prize won.mp3").toURI().toString()));
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
