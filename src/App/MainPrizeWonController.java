package App;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.sound.midi.Transmitter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPrizeWonController implements Initializable {
    private Stage quizStage;
    private SoundEffectsClass soundEffectsClass;

    @FXML
    private Button playAgainButton, goToMainMenuButton, submitScoreButton;

    @FXML
    private ImageView volumeIconOn, volumeIconOff;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        soundEffectsClass = new SoundEffectsClass();
        try {
            soundEffectsClass.mainPrizeWonSound();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void transferQuizStage(Stage quizStage) {
        this.quizStage = quizStage;
    }

    @FXML
    private void volumeIconOffClicked() {
        soundEffectsClass.unmuteMediaPlayer();
        volumeIconOff.disableProperty().setValue(true);
        volumeIconOff.visibleProperty().setValue(false);

        volumeIconOn.disableProperty().setValue(false);
        volumeIconOn.visibleProperty().setValue(true);
    }

    @FXML
    private void volumeIconOnClicked() {
        soundEffectsClass.muteMediaPlayer();
        volumeIconOn.disableProperty().setValue(true);
        volumeIconOn.visibleProperty().setValue(false);

        volumeIconOff.disableProperty().setValue(false);
        volumeIconOff.visibleProperty().setValue(true);
    }

    @FXML
    private void playAgainButtonClicked() throws IOException {
        soundEffectsClass.stopMediaPlayer();
        playAgainButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("QuizStage.fxml"));
        Parent root = loader.load();
        if(quizStage != null) {
            quizStage.setScene(new Scene(root));
            quizStage.show();
        }
    }

    @FXML
    private void goBackToMenuButtonClicked() throws IOException {
        soundEffectsClass.stopMediaPlayer();
        goToMainMenuButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StartMenuStage.fxml"));
        Parent root = loader.load();
        if(quizStage != null) {
            quizStage.setScene(new Scene(root));
            quizStage.show();
        }
    }

    /* --- cosmetic --- */

    private final InnerShadow innerShadowButton = new InnerShadow((BlurType.THREE_PASS_BOX), Color.web("ffa700"),15,0,0,0);

    @FXML
    private void submitScoreButtonMouseExited() {
        submitScoreButton.setEffect(null);
    }

    @FXML
    private void submitScoreButtonMouseEntered() {
        submitScoreButton.setEffect(innerShadowButton);
    }

    @FXML
    private void goToMainMenuButtonMouseExited() {
        goToMainMenuButton.setEffect(null);
    }

    @FXML
    private void goToMainMenuButtonMouseEntered() {
        goToMainMenuButton.setEffect(innerShadowButton);
    }

    @FXML
    private void playAgainButtonMouseExited() {
        playAgainButton.setEffect(null);
    }

    @FXML
    private void playAgainButtonMouseEntered() {
        playAgainButton.setEffect(innerShadowButton);
    }

}
