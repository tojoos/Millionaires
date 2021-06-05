package App;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class QuizController implements Initializable {

    private List<String> questions = new ArrayList<>();
    private List<String> answers = new ArrayList<>();
    private List<String> correctAnswers = new ArrayList<>();
    private int iterator = 0;
    private int points = 0;
    private String selectedAnswer;
    private static final int MAX_POINTS = 15;
    private boolean confirmationInProgress = false;
    private boolean answerSelected = false;
    private boolean fiftyFiftyUsed = false;
    private boolean phoneCallUsed = false;
    private boolean votingUsed = false;
    private static final double PHONE_CALL_ACCURACY = 0.75;
    private static final double VOTING_ACCURACY = 0.90;
    private SoundEffectsClass soundEffectsClass;

    private final String formatSelectedLabelString =
            "-fx-background-color: #f49e0a;" +
            "-fx-background-radius: 10;" +
            "-fx-border-color:  linear-gradient(#bdbcbc,#676565);" +
            "-fx-border-radius: 10;" +
            "-fx-text-fill: black;";

    private final String formatToDefaultLabelString =
            "-fx-background-color:  linear-gradient(#001299, #4d61ff);" +
            "-fx-background-radius: 10;" +
            "-fx-border-color:  linear-gradient(#bdbcbc,#676565);" +
            "-fx-border-radius: 10;" +
            "-fx-text-fill: white;";

    private final String correctAnswerStyleSheet =
            "-fx-background-color: #00b300;" +
            "-fx-border-color:  linear-gradient(#bdbcbc,#676565);" +
            "-fx-border-radius: 10;" +
            "-fx-background-radius: 10;";

    private final String prizeLabelStyleSheet =
            "-fx-background-color: #f49e0a;" +
            "-fx-background-radius: 10;" +
            "-fx-border-color:  linear-gradient(#bdbcbc,#676565);" +
            "-fx-border-radius: 10;" +
            "-fx-text-fill: black;";

    @FXML
    private Label answerLabelA = new Label(), answerLabelB = new Label(), answerLabelC = new Label(), answerLabelD = new Label(), questionLabel = new Label();

    @FXML
    private Label AwordLabel = new Label(), BwordLabel = new Label(), CwordLabel = new Label(), DwordLabel = new Label();

    @FXML
    private Ellipse fiftyFiftyLabel, phoneLabel, votingLabel;
    
    @FXML
    private VBox prizeListVBox;

    @FXML
    private Button confirmButton;

    @FXML
    private ImageView fiftyFiftyCross, phoneCross, voteCross, volumeIconOn, volumeIconOff;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        prepareQuestions();
        prepareNewQuizQuestion();

        soundEffectsClass = new SoundEffectsClass();
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3.5));
        pauseTransition.playFromStart();
        soundEffectsClass.playQuestionIntroSound();
        pauseTransition.setOnFinished(e -> {
            if(!confirmationInProgress)
                soundEffectsClass.playQuizMusic(iterator);
        });
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
    private void onConfirmButtonClick() {
        if(answerSelected) {
            if (!confirmationInProgress) {
                confirmationInProgress = true;

                soundEffectsClass.stopMediaPlayer();

                PauseTransition pt1 = new PauseTransition(Duration.seconds(4));
                soundEffectsClass.playAnswerSound();
                pt1.playFromStart();

                pt1.setOnFinished(e -> {
                    soundEffectsClass.stopMediaPlayer();
                    highlightCorrectAnswer();
                    if (checkAnswer()) {
                        PauseTransition pt3 = new PauseTransition(Duration.seconds(4));
                        soundEffectsClass.playCorrectAnswerSound();
                        pt3.setOnFinished(ev1 -> {
                            soundEffectsClass.stopMediaPlayer();

                            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3.5));
                            pauseTransition.playFromStart();
                            soundEffectsClass.playQuestionIntroSound();

                            prepareNewQuizQuestion();
                            confirmationInProgress = false;
                            answerSelected = false;
                            points++;

                            pauseTransition.setOnFinished(ev2 -> {
                                if(!confirmationInProgress)
                                    soundEffectsClass.playQuizMusic(iterator);
                            });
                        });
                        pt3.playFromStart();
                    } else {
                            PauseTransition pt3 = new PauseTransition(Duration.seconds(4));
                            soundEffectsClass.playWrongAnswerSound();
                            pt3.setOnFinished(ev1 -> {
                                soundEffectsClass.stopMediaPlayer();

                                try {
                                    showEndGameStage();
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }
                            });
                            pt3.playFromStart();

                    }
                });
            }
        }
    }

    private void showEndGameStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EndGameStage.fxml"));
        Parent root = loader.load();
        Stage endGameStage = new Stage();
        EndGameController endGameController = loader.getController();
        endGameController.transferQuizStage((Stage) answerLabelA.getScene().getWindow());
        endGameController.setScore(calculateScore());
        endGameStage.initModality(Modality.APPLICATION_MODAL);
        endGameStage.getIcons().add(new Image(Main.class.getResourceAsStream("images\\icon.png")));
        endGameStage.setTitle("Game over");
        endGameStage.setScene(new Scene(root));
        endGameStage.show();
    }

    private void setEverythingVisible() {
        answerLabelA.setVisible(true);
        AwordLabel.setVisible(true);

        answerLabelB.setVisible(true);
        BwordLabel.setVisible(true);

        answerLabelC.setVisible(true);
        CwordLabel.setVisible(true);

        answerLabelD.setVisible(true);
        DwordLabel.setVisible(true);
    }

    @FXML
    private void fiftyFiftyLabelClick() {
        if(!confirmationInProgress) {
            if (!fiftyFiftyUsed) {
                SoundEffectsClass soundEffectsClass = new SoundEffectsClass();
                soundEffectsClass.lifebouySound();
                
                fiftyFiftyUsed = true;
                fiftyFiftyCross.setOpacity(1.0);
                Random rand = new Random();
                Set<Integer> twoRandomWrongAnswers = new LinkedHashSet<>();
                while (twoRandomWrongAnswers.size() < 2) {
                    int randomNumber = rand.nextInt(4);
                    if (randomNumber == 0) {
                        if (!correctAnswers.get(iterator - 1).equals(answerLabelA.getText())) {
                            twoRandomWrongAnswers.add(randomNumber);
                            answerLabelA.setVisible(false);
                            AwordLabel.setVisible(false);
                        }
                    } else if (randomNumber == 1) {
                        if (!correctAnswers.get(iterator - 1).equals(answerLabelB.getText())) {
                            twoRandomWrongAnswers.add(randomNumber);
                            answerLabelB.setVisible(false);
                            BwordLabel.setVisible(false);
                        }
                    } else if (randomNumber == 2) {
                        if (!correctAnswers.get(iterator - 1).equals(answerLabelC.getText())) {
                            twoRandomWrongAnswers.add(randomNumber);
                            answerLabelC.setVisible(false);
                            CwordLabel.setVisible(false);
                        }
                    } else {
                        if (!correctAnswers.get(iterator - 1).equals(answerLabelD.getText())) {
                            twoRandomWrongAnswers.add(randomNumber);
                            answerLabelD.setVisible(false);
                            DwordLabel.setVisible(false);
                        }
                    }
                }
            }
        }
    }

    @FXML
    private Pane phoneCallPane = new Pane();

    @FXML
    private Label phoneCallLabel = new Label();

    @FXML
    private Pane votePane = new Pane();

    @FXML
    private BarChart<String, Number> voteChart;

    @FXML
    private void phoneLabelClick() {
        if (!confirmationInProgress) {
            if (!phoneCallUsed) {
                SoundEffectsClass soundEffectsClass = new SoundEffectsClass();
                soundEffectsClass.lifebouySound();

                phoneCallUsed = true;
                phoneCross.setOpacity(1.0);
                phoneCallPane.setVisible(true);
                String callAnswer = "I think it will be answer: ";
                Random rand = new Random();
                if (rand.nextDouble() < PHONE_CALL_ACCURACY) { //case where phone call is accurate
                    callAnswer += correctAnswers.get(iterator - 1);
                } else {                       //case when it's not accurate answer (random answer)
                    callAnswer += answers.get(4 * (iterator - 1) + (rand.nextInt(4)));
                }
                callAnswer += ", but i am not entirely sure.";
                if (phoneCallLabel != null)
                    phoneCallLabel.setText(callAnswer);
            }
        }
    }

    @FXML
    private void closePhoneCall() {
        phoneCallPane.setVisible(false);
    }

    @FXML
    private void votingLabelClick() {
        if (!confirmationInProgress) {
            if (!votingUsed) {
                SoundEffectsClass soundEffectsClass = new SoundEffectsClass();
                soundEffectsClass.lifebouySound();

                votingUsed = true;
                voteCross.setOpacity(1.0);
                votePane.setVisible(true);

                int AChartValue = 0, BChartValue = 0, CChartValue = 0, DChartValue = 0;

                Random rand = new Random();
                Set<Integer> randomSet = new LinkedHashSet<>();
                while (randomSet.size() < 4) {
                    randomSet.add(rand.nextInt(4));
                }
                if (rand.nextDouble() < VOTING_ACCURACY) {   //case voting is accurate (good answer is dominating)
                    if (findCorrectAnswerLabel().getId().contains("A")) {   //good answer is dominating
                        AChartValue = rand.nextInt(25) + 50;
                    } else if (findCorrectAnswerLabel().getId().contains("B")) {
                        BChartValue = rand.nextInt(25) + 50;
                    } else if (findCorrectAnswerLabel().getId().contains("C")) {
                        CChartValue = rand.nextInt(25) + 50;
                    } else {
                        DChartValue = rand.nextInt(25) + 50;
                    }
                    for (int numb : randomSet) {
                        if (numb == 0 && !answerLabelA.getText().equals(findCorrectAnswerLabel().getText())) {
                            AChartValue = rand.nextInt(100 - AChartValue - BChartValue - CChartValue - DChartValue);
                        } else if (numb == 1 && !answerLabelB.getText().equals(findCorrectAnswerLabel().getText())) {
                            BChartValue = rand.nextInt(100 - AChartValue - BChartValue - CChartValue - DChartValue);
                        } else if (numb == 2 && !answerLabelC.getText().equals(findCorrectAnswerLabel().getText())) {
                            CChartValue = rand.nextInt(100 - AChartValue - BChartValue - CChartValue - DChartValue);
                        } else if (numb == 3 && !answerLabelD.getText().equals(findCorrectAnswerLabel().getText())) {
                            DChartValue = rand.nextInt(100 - AChartValue - BChartValue - CChartValue - DChartValue);
                        }
                    }
                } else {      //this part of the code is responsible for random chart (not exceeding 100%)
                    for (int numb : randomSet) {
                        if (numb == 0) {
                            AChartValue = rand.nextInt(100 - AChartValue - BChartValue - CChartValue - DChartValue);
                        } else if (numb == 1) {
                            BChartValue = rand.nextInt(100 - AChartValue - BChartValue - CChartValue - DChartValue);
                        } else if (numb == 2) {
                            CChartValue = rand.nextInt(100 - AChartValue - BChartValue - CChartValue - DChartValue);
                        } else {
                            DChartValue = rand.nextInt(100 - AChartValue - BChartValue - CChartValue - DChartValue);
                        }
                    }
                }

                XYChart.Series<String, Number> votingSeries = new XYChart.Series<>();
                votingSeries.getData().add(new XYChart.Data<>("A", AChartValue));
                votingSeries.getData().add(new XYChart.Data<>("B", BChartValue));
                votingSeries.getData().add(new XYChart.Data<>("C", CChartValue));
                votingSeries.getData().add(new XYChart.Data<>("D", DChartValue));
                voteChart.getData().add(votingSeries);
            }
        }
    }

    @FXML
    private void closeVote() {
        votePane.setVisible(false);
    }

    private Label findCorrectAnswerLabel() {
        if (correctAnswers.get(iterator-1).equals(answerLabelA.getText())) {
            return answerLabelA;
        } else if (correctAnswers.get(iterator-1).equals(answerLabelB.getText())) {
            return answerLabelB;
        } else if (correctAnswers.get(iterator-1).equals(answerLabelC.getText())) {
            return answerLabelC;
        } else  {
            return answerLabelD;
        }
    }

    private String calculateScore(){
        String score;
        if(points < 5) {
            score = "0 $";
        } else if(points < 10) {
            score = "5.000 $";
        } else if(points < 15) {
            score = "50.000 $";
        } else {
            score = "1.000.000 $";
        }
        return score;
    }

    private void prepareNewQuizQuestion() {
        setEveryAnswerLabelToDefault();
        setEverythingVisible();
        if(phoneCallPane.isVisible())
            phoneCallPane.setVisible(false);

        if(votePane.isVisible())
            votePane.setVisible(false);

        if(iterator>0) {
            prizeListVBox.getChildren().get(prizeListVBox.getChildren().size()-iterator).setStyle(null);
        }
        if(prizeListVBox!=null) {
            prizeListVBox.getChildren().get(prizeListVBox.getChildren().size() - 1 - iterator).setStyle(prizeLabelStyleSheet);
        }
        if(iterator != MAX_POINTS) {

            /* This part of code provides a random answers location */
            Random randNumb = new Random();
            Set<Integer> randomDistinctNumbs = new LinkedHashSet<>();
            while(randomDistinctNumbs.size()<4) {
                randomDistinctNumbs.add(randNumb.nextInt(4));
            }
            Iterator<Integer> iter = randomDistinctNumbs.iterator();

            if (questionLabel != null) {
                questionLabel.setText(questions.get(iterator));
            }
            if (answerLabelA != null) {
                answerLabelA.setText(answers.get(iterator * 4 + iter.next()));
            }
            if (answerLabelB != null) {
                answerLabelB.setText(answers.get(iterator * 4 + iter.next()));
            }
            if (answerLabelC != null) {
                answerLabelC.setText(answers.get(iterator * 4 + iter.next()));
            }
            if (answerLabelD != null) {
                answerLabelD.setText(answers.get(iterator * 4 + iter.next()));
            }
        }
        iterator++;
    }

    private void highlightCorrectAnswer() {
        if(correctAnswers.get(iterator-1).equals(answerLabelA.getText())) {
            answerLabelA.setStyle(correctAnswerStyleSheet);
        } else if(correctAnswers.get(iterator-1).equals(answerLabelB.getText())) {
            answerLabelB.setStyle(correctAnswerStyleSheet);
        } else if(correctAnswers.get(iterator-1).equals(answerLabelC.getText())) {
            answerLabelC.setStyle(correctAnswerStyleSheet);
        } else {
            answerLabelD.setStyle(correctAnswerStyleSheet);
        }
    }

    private void setEveryAnswerLabelToDefault() {
        answerLabelA.setStyle(formatToDefaultLabelString);
        answerLabelB.setStyle(formatToDefaultLabelString);
        answerLabelC.setStyle(formatToDefaultLabelString);
        answerLabelD.setStyle(formatToDefaultLabelString);

        AwordLabel.setStyle("-fx-text-fill: #f49e0a;");
        BwordLabel.setStyle("-fx-text-fill: #f49e0a;");
        CwordLabel.setStyle("-fx-text-fill: #f49e0a;");
        DwordLabel.setStyle("-fx-text-fill: #f49e0a;");
    }

    private boolean checkAnswer() {
        return selectedAnswer.equals(correctAnswers.get(iterator-1));
    }

    private void prepareQuestions() {
        try {
            File script = new File("C:\\Users\\joos\\IdeaProjects\\MillionairesFXApp\\src\\App\\scripts\\script.txt");
            if(script.canRead()) {
                BufferedReader br = new BufferedReader(new FileReader(script));
                int iterator = 0;
                String line;
                while ((line = br.readLine()) != null) {
                    if (iterator % 5 == 0)
                        questions.add(line);
                    else if (iterator % 5 >= 1) {
                        if (iterator % 5 == 1)
                            correctAnswers.add(line);
                        answers.add(line);
                    }
                    iterator++;
                }
                br.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void answerChoiceLabelA() {
        if(!confirmationInProgress) {
            answerSelected = true;
            setEveryAnswerLabelToDefault();
            answerLabelA.setStyle(formatSelectedLabelString);
            AwordLabel.setStyle("-fx-text-fill: white;");
            selectedAnswer = answerLabelA.getText();
        }
    }

    @FXML
    private void answerChoiceLabelB() {
        if(!confirmationInProgress) {
            answerSelected = true;
            setEveryAnswerLabelToDefault();
            answerLabelB.setStyle(formatSelectedLabelString);
            BwordLabel.setStyle("-fx-text-fill: white;");
            selectedAnswer = answerLabelB.getText();
        }
    }

    @FXML
    private void answerChoiceLabelC() {
        if(!confirmationInProgress) {
            answerSelected = true;
            setEveryAnswerLabelToDefault();
            answerLabelC.setStyle(formatSelectedLabelString);
            CwordLabel.setStyle("-fx-text-fill: white;");
            selectedAnswer = answerLabelC.getText();
        }
    }

    @FXML
    private void answerChoiceLabelD() {
        if(!confirmationInProgress) {
            answerSelected = true;
            setEveryAnswerLabelToDefault();
            answerLabelD.setStyle(formatSelectedLabelString);
            DwordLabel.setStyle("-fx-text-fill: white;");
            selectedAnswer = answerLabelD.getText();
        }
    }

    /* --- cosmetic --- */

    private final InnerShadow innerShadowButton = new InnerShadow((BlurType.THREE_PASS_BOX), Color.web("ffa700"),15,0,0,0);

    @FXML
    private void ALabelMouseExited() {
        answerLabelA.setEffect(null);
    }

    @FXML
    private void ALabelMouseEntered() {
        answerLabelA.setEffect(innerShadowButton);
    }

    @FXML
    private void CLabelMouseExited() {
        answerLabelC.setEffect(null);
    }

    @FXML
    private void CLabelMouseEntered() {
        answerLabelC.setEffect(innerShadowButton);
    }

    @FXML
    private void BLabelMouseExited() {
        answerLabelB.setEffect(null);
    }

    @FXML
    private void BLabelMouseEntered() {
        answerLabelB.setEffect(innerShadowButton);
    }

    @FXML
    private void DLabelMouseExited() {
        answerLabelD.setEffect(null);
    }

    @FXML
    private void DLabelMouseEntered() {
        answerLabelD.setEffect(innerShadowButton);
    }

    @FXML
    private void confirmButtonMouseExited() {
        confirmButton.setEffect(null);
    }

    @FXML
    private void confirmButtonMouseEntered() {
        confirmButton.setEffect(innerShadowButton);
    }

    @FXML
    private void fiftyFiftyLabelMouseExited() {
        fiftyFiftyLabel.setEffect(null);
    }

    @FXML
    private void fiftyFiftyLabelMouseEntered() {
        fiftyFiftyLabel.setEffect(innerShadowButton);
    }

    @FXML
    private void phoneLabelMouseExited() {
        phoneLabel.setEffect(null);
    }

    @FXML
    private void phoneLabelMouseEntered() {
        phoneLabel.setEffect(innerShadowButton);
    }

    @FXML
    private void votingLabelMouseExited() {
        votingLabel.setEffect(null);
    }

    @FXML
    private void votingLabelMouseEntered() {
        votingLabel.setEffect(innerShadowButton);
    }
}
