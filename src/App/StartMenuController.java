package App;

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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartMenuController implements Initializable {

    private SoundEffectsClass soundEffectsClass;

    @FXML
    Button newGameButton, rankingButton, loadScriptButton, helpButton, exitButton;

    @FXML
    ImageView volumeIconOn, volumeIconOff;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            soundEffectsClass = new SoundEffectsClass();
        try {
            soundEffectsClass.playIntroMusic();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    private void onNewGameButton() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("QuizStage.fxml"));
        Parent root = loader.load();
        Stage quizStage = (Stage) newGameButton.getScene().getWindow();
        quizStage.setScene(new Scene(root));
        quizStage.show();
        soundEffectsClass.stopMediaPlayer();
    }

    @FXML
    private void onExitButtonClicked() {
        System.exit(0);
    }

    @FXML
    private void onRankingButtonClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RankingStage.fxml"));
        Parent root = loader.load();
        RankingStageController rankingStageController = loader.getController();
        rankingStageController.transferQuizStage((Stage) newGameButton.getScene().getWindow());
        rankingStageController.transferMediaPlayer(soundEffectsClass);
        Stage rankingStage = (Stage) newGameButton.getScene().getWindow();
        rankingStage.setScene(new Scene(root));
        rankingStage.show();
    }

    /* --- cosmetic --- */

    private final InnerShadow innerShadowButton = new InnerShadow((BlurType.THREE_PASS_BOX), Color.web("ffa700"),15,0,0,0);

    @FXML
    private void onrankingButtonMouseEntered() {
        rankingButton.setEffect(innerShadowButton);
    }

    @FXML
    private void onrankingButtonMouseExited() {
        rankingButton.setEffect(null);
    }

    @FXML
    private void loadScriptButtonMouseEntered() {
        loadScriptButton.setEffect(innerShadowButton);
    }

    @FXML
    private void loadScriptButtonMouseExited() {
        loadScriptButton.setEffect(null);
    }

    @FXML
    private void onhelpButtonMouseEntered() {
        helpButton.setEffect(innerShadowButton);
    }

    @FXML
    private void onhelpButtonMouseExited() {
        helpButton.setEffect(null);
    }

    @FXML
    private void onNewGameButtonMouseEntered() {
        newGameButton.setEffect(innerShadowButton);
    }

    @FXML
    private void onNewGameButtonMouseExited() {
        newGameButton.setEffect(null);
    }

    @FXML
    private void onExitButtonMouseEntered() {
        exitButton.setEffect(innerShadowButton);
    }

    @FXML
    private void onExitButtonMouseExited() {
        exitButton.setEffect(null);
    }
}
