package App;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class StartMenuController {

    @FXML
    Button newGameButton, rankingButton, loadScriptButton, helpButton;

    @FXML
    private void onNewGameButton() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("QuizStage.fxml"));
        Parent root = loader.load();
        Stage quizStage = (Stage) newGameButton.getScene().getWindow();
        quizStage.setScene(new Scene(root));
        quizStage.show();
    }

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

}
