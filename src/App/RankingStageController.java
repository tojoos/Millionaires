package App;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class RankingStageController implements Initializable {

    private Stage mainMenuStage;
    private SoundEffectsClass soundEffectsClass;

    @FXML
    private TableView<RankingRecord> rankingTableView;

    @FXML
    private TableColumn<RankingRecord, String> winningColumn;

    @FXML
    private TableColumn<RankingRecord, Integer> pointsColumn;

    @FXML
    private TableColumn<RankingRecord, String> dateColumn;

    @FXML
    private TableColumn<RankingRecord, String> nameColumn;

    @FXML
    Button goToMainMenuButton;

    @FXML
    ImageView volumeIconOn, volumeIconOff;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadRankingTable();
    }

    private void loadRankingTable() {
        if(winningColumn!=null) {
            winningColumn.setCellValueFactory(new PropertyValueFactory<>("winning"));
            pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            
            rankingTableView.setItems(RankingFileManagerClass.getObservableListOfRecords());
        }
    }

    public void transferQuizStage(Stage quizStage) {
        this.mainMenuStage = quizStage;
    }

    public void transferMediaPlayer(SoundEffectsClass soundEffectsClass) {
        this.soundEffectsClass = soundEffectsClass;
        if(this.soundEffectsClass.isMuted()) {
            volumeIconOnClicked();
        }
    }

    @FXML
    private void goBackToMainMenuButtonClicked() throws IOException {
        soundEffectsClass.stopMediaPlayer();
        goToMainMenuButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StartMenuStage.fxml"));
        Parent root = loader.load();
        if(mainMenuStage != null) {
            mainMenuStage.setScene(new Scene(root));
            mainMenuStage.show();
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

    private final InnerShadow innerShadowButton = new InnerShadow((BlurType.THREE_PASS_BOX), Color.web("ffa700"),15,0,0,0);

    @FXML
    private void goToMainMenuButtonMouseExited() {
        goToMainMenuButton.setEffect(null);
    }

    @FXML
    private void goToMainMenuButtonMouseEntered() {
        goToMainMenuButton.setEffect(innerShadowButton);
    }
}
