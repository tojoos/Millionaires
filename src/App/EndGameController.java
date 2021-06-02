package App;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class EndGameController {
    Stage quizStage;

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
