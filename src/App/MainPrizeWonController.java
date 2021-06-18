package App;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class MainPrizeWonController implements Initializable {
    private Stage quizStage;
    private SoundEffectsClass soundEffectsClass;
    private boolean submitButtonClicked = false;
    private String currentScript;

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

    public void transferScript(String currentScript) {
        this.currentScript = currentScript;
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

    @FXML
    private TextField submitScoreTextField;

    @FXML
    private Label submittedLabel;

    @FXML
    private void onSubmitScoreButtonClicked() {
        if(!submitButtonClicked) {
            if (submitScoreTextField.getText().equals("")) {
                InnerShadow innerShadow = new InnerShadow(10,0.5,0.5,Color.web("#ff0000"));
                innerShadow.setChoke(0.3);
                innerShadow.setWidth(21.0);
                innerShadow.setHeight(21.0);
                submitScoreTextField.setEffect(innerShadow);
            } else {
                String dateString = LocalDateTime.now().toString();
                dateString = dateString.substring(0, 10);
                String stringDataRecord = "";
                stringDataRecord += "\n" + "1.000.000 $" + "~" + 15 + "~" + dateString + "~" + submitScoreTextField.getText();
                RankingFileManagerClass.addDataToRankingFile(stringDataRecord);
                submitButtonClicked = true;
                submitScoreTextField.setEffect(null);
            }
        } else {
            submittedLabel.visibleProperty().setValue(true);
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
