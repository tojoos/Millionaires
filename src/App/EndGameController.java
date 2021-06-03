package App;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class EndGameController {
    private Stage quizStage;

    @FXML
    private Label scoreOfGameLabel;

    public void setScore(String score) {
        if(scoreOfGameLabel!=null) {
            scoreOfGameLabel.setText(score);
        }
    }

    public void transferQuizStage(Stage quizStage) {
        this.quizStage = quizStage;
    }

    @FXML
    private void playAgainButtonClicked() throws IOException {
        scoreOfGameLabel.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("QuizStage.fxml"));
        Parent root = loader.load();
        if(quizStage!=null) {
            quizStage.setScene(new Scene(root));
            quizStage.show();
        }
    }

    @FXML
    private void goBackToMenuButtonClicked() throws IOException {
        scoreOfGameLabel.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StartMenuStage.fxml"));
        Parent root = loader.load();
        if(quizStage!=null) {
            quizStage.setScene(new Scene(root));
            quizStage.show();
        }
    }

    @FXML
    private Button submitScoreButton, goToMainMenuButton, playAgainButton;

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
