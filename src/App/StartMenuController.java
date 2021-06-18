package App;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.ResourceBundle;

public class StartMenuController implements Initializable {

    private SoundEffectsClass soundEffectsClass;
    private ObservableList<File> scripts;
    private File selectedScriptFile;
    private boolean isScriptValid;

    @FXML
    Button newGameButton, rankingButton, loadScriptButton, exitButton;

    @FXML
    ImageView volumeIconOn, volumeIconOff;

    @FXML
    ChoiceBox<String> scriptDisplayChoiceBox;

    @FXML
    Label isScriptValidLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            soundEffectsClass = new SoundEffectsClass();
        try {
            soundEffectsClass.playIntroMusic();
        } catch (Exception e) {
            e.printStackTrace();
        }

        loadScripts();
        createScriptChoiceBox();
    }

    private void createScriptChoiceBox() {
        // zrobic do tego jeszcze przycisk wczytujacy nowy skrytp
        ObservableList<String> scriptNames = FXCollections.observableArrayList();
        for(File f : scripts) {
            scriptNames.add(f.toString().substring(f.toString().lastIndexOf("\\")+1));
        }
        //dodac w rankingu rodzaj quizu

        scriptDisplayChoiceBox.getItems().addAll(scriptNames);
        scriptDisplayChoiceBox.getSelectionModel().select(0);

        selectedScriptFile = new File("src\\App\\scripts\\" + scriptDisplayChoiceBox.getSelectionModel().getSelectedItem());
        if(isScriptValidLabel != null) {
            if (isScriptValid = isScriptValid(selectedScriptFile)) {
                isScriptValidLabel.setText("Valid");
                isScriptValidLabel.setStyle("-fx-text-fill: green");
            } else {
                isScriptValidLabel.setText("Invalid");
                isScriptValidLabel.setStyle("-fx-text-fill: red");
            }
        }

        scriptDisplayChoiceBox.getSelectionModel().selectedItemProperty()
                .addListener((v, oldV, newV) -> {
                    selectedScriptFile = new File("src\\App\\scripts\\" + scriptDisplayChoiceBox.getSelectionModel().getSelectedItem());
                    if(isScriptValidLabel != null) {
                        if (isScriptValid = isScriptValid(selectedScriptFile)) {
                            isScriptValidLabel.setText("Valid");
                            isScriptValidLabel.setStyle("-fx-text-fill: green");
                        } else {
                            isScriptValidLabel.setText("Invalid");
                            isScriptValidLabel.setStyle("-fx-text-fill: red");
                        }
                    }
                });

    }

    private boolean isScriptValid(File script) {
        try {
            if (script.canRead()) {
                BufferedReader br = new BufferedReader(new FileReader(script));
                int iterator = 0;
                while (br.readLine() != null) {
                    iterator++;
                    }
                br.close();
                if(iterator >= 75) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            }
        return false;
    }

    private void loadScripts() {
        scripts = FXCollections.observableArrayList();
        try {
            File rankingDir = new File("src\\App\\scripts");

            if (rankingDir.exists()) {
                File[] scriptFiles = rankingDir.listFiles((dir, name) -> name.endsWith(".txt"));
                if(scriptFiles != null)
                    scripts.addAll(Arrays.asList(scriptFiles));
            } else {
                Files.createDirectory(rankingDir.toPath());
            }

            } catch (IOException e) {
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
        if(isScriptValid) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("QuizStage.fxml"));
            Parent root = loader.load();
            QuizController quizController = loader.getController();
            quizController.transferScriptFile(selectedScriptFile);
            quizController.initializeQuizQuestions();
            Stage quizStage = (Stage) newGameButton.getScene().getWindow();
            quizStage.setScene(new Scene(root));
            quizStage.show();
            soundEffectsClass.stopMediaPlayer();
        } else {
            DropShadow shadow = new DropShadow(10,Color.web("#ff0000"));
            scriptDisplayChoiceBox.setEffect(shadow);
            PauseTransition pt = new PauseTransition(Duration.seconds(2));
            pt.playFromStart();
            pt.setOnFinished(e -> scriptDisplayChoiceBox.setEffect(null));
        }
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
