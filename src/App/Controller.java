package App;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {

    private List<String> questions = new ArrayList<>();
    private List<String> answers = new ArrayList<>();
    private List<String> correctAnswers = new ArrayList<>();
    private int iterator = 0;
    private String selectedAnswer;
    private static final int MAX_POINTS = 15;

    private String formatSelectedLabelString =
            "-fx-background-color: #f49e0a;" +
            "-fx-background-radius: 10;" +
            "-fx-border-color:  linear-gradient(#bdbcbc,#676565);" +
            "-fx-border-radius: 10;" +
            "-fx-text-fill: black;";

    private String formatToDefaultLabelString =
            "-fx-background-color:  linear-gradient(#001299, #4d61ff);" +
            "-fx-background-radius: 10;" +
            "-fx-border-color:  linear-gradient(#bdbcbc,#676565);" +
            "-fx-border-radius: 10;" +
            "-fx-text-fill: white;";

    private String correctAnswerStyleSheet =
            "-fx-background-color: #00b300;" +
            "-fx-border-color:  linear-gradient(#bdbcbc,#676565);" +
            "-fx-border-radius: 10;" +
            "-fx-background-radius: 10;";

    private String prizeLabelStyleSheet =
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
    private Label prizeLabel1000000, prizeLabel500000, prizeLabel250000, prizeLabel150000,
                  prizeLabel75000, prizeLabel50000, prizeLabel25000, prizeLabel15000,
                  prizeLabel10000, prizeLabel7500, prizeLabel5000, prizeLabel3000,
                  prizeLabel2000, prizeLabel1000, prizeLabel500;

    private List<Label> prizeLabelList;

    @FXML
    private VBox prizeListVBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        prepareQuestions();
        prepareNewQuizQuestion();



    }

    @FXML
    private void onNewGameButton(javafx.event.ActionEvent event) throws Exception {
        Parent blah = FXMLLoader.load(getClass().getResource("quizWindow.fxml"));
        Scene scene = new Scene(blah);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(scene);
        appStage.show();

    }

    private void prepareNewQuizQuestion() {
        setEveryAnswerLabelToDefault();
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
                questionLabel.setText(questions.get(iterator * 4));
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

    @FXML
    private void onConfirmButtonClick() {
        PauseTransition pt1 = new PauseTransition(Duration.seconds(3));
        pt1.setOnFinished(e -> highlightCorrectAnswer());
        pt1.playFromStart();
        if(checkAnswer()) {
            System.out.println("congratulations!");
            PauseTransition pt2 = new PauseTransition(Duration.seconds(7));
            pt2.setOnFinished(e -> {
                prepareNewQuizQuestion();
            });
            pt2.playFromStart();

        } else {
            System.out.println("game over");
        }
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

    @FXML
    private void answerChoiceLabelA() {
        setEveryAnswerLabelToDefault();
        answerLabelA.setStyle(formatSelectedLabelString);

        AwordLabel.setStyle("-fx-text-fill: white;");
        selectedAnswer = answerLabelA.getText();
    }

    @FXML
    private void answerChoiceLabelB() {
        setEveryAnswerLabelToDefault();
        answerLabelB.setStyle(formatSelectedLabelString);

        BwordLabel.setStyle("-fx-text-fill: white;");
        selectedAnswer = answerLabelB.getText();
    }

    @FXML
    private void answerChoiceLabelC() {
        setEveryAnswerLabelToDefault();
        answerLabelC.setStyle(formatSelectedLabelString);

        CwordLabel.setStyle("-fx-text-fill: white;");
        selectedAnswer = answerLabelC.getText();
    }

    @FXML
    private void answerChoiceLabelD() {
        setEveryAnswerLabelToDefault();
        answerLabelD.setStyle(formatSelectedLabelString);

        DwordLabel.setStyle("-fx-text-fill: white;");
        selectedAnswer = answerLabelD.getText();
    }

    private boolean checkAnswer() {
        return selectedAnswer.equals(correctAnswers.get(iterator-1));
    }

    private void prepareQuestions() {
        try {
            File script = new File("C:\\Users\\joos\\IdeaProjects\\MillionairesFXApp\\src\\App\\script.txt");
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
}
