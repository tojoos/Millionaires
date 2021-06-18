package App;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;

public class EndGameController {
    private Stage quizStage;
    private int points;
    private String score;
    private String currentScript;
    private boolean submitButtonClicked = false;

    @FXML
    private Label scoreOfGameLabel;

    public void setScore(String score) {
        if(scoreOfGameLabel != null) {
            scoreOfGameLabel.setText(score);
            this.score = score;
        }
    }

    public void transferQuizStage(Stage quizStage) {
        this.quizStage = quizStage;
    }

    public void transferPoints(int points) {
        this.points = points;
    }

    public void transferScript(String currentScript) {
        this.currentScript = currentScript;
    }

    @FXML
    private void playAgainButtonClicked() throws IOException {
        scoreOfGameLabel.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("QuizStage.fxml"));
        Parent root = loader.load();
        if(quizStage != null) {
            quizStage.setScene(new Scene(root));
            quizStage.show();
        }
    }

    @FXML
    private void goBackToMenuButtonClicked() throws IOException {
        scoreOfGameLabel.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StartMenuStage.fxml"));
        Parent root = loader.load();
        if(quizStage != null) {
            quizStage.setScene(new Scene(root));
            quizStage.show();
        }
    }

    @FXML
    private Button submitScoreButton, goToMainMenuButton, playAgainButton;

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
                stringDataRecord += "\n" + score + "~" + (points-1) + "~" + dateString + "~" + submitScoreTextField.getText() + "~" + currentScript;
                RankingFileManagerClass.addDataToRankingFile(stringDataRecord);
                submitButtonClicked = true;
                submitScoreTextField.setEffect(null);
            }
        } else {
                submittedLabel.visibleProperty().setValue(true);
        }
    }

    private final InnerShadow innerShadowButton = new InnerShadow((BlurType.THREE_PASS_BOX), Color.web("ffa700"),15,0,0,0);

    /* ----- cosmetic --- */

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
