package App;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private List<String> questions = new ArrayList<>();
    private List<String> answers = new ArrayList<>();
    private List<String> correctAnswers = new ArrayList<>();
    private int iterator = 0;
    private int currentPoints = 0;
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

    @FXML
    private Label answerLabelA, answerLabelB, answerLabelC, answerLabelD, questionLabel;

    @FXML
    private Label AwordLabel, BwordLabel, CwordLabel, DwordLabel;


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
        if(iterator != MAX_POINTS) {
            if (questionLabel != null) {
                questionLabel.setText(questions.get(iterator));
            }
            if (answerLabelA != null) {
                answerLabelA.setText(answers.get(iterator * 4));
            }
            if (answerLabelB != null) {
                answerLabelB.setText(answers.get(iterator * 4 + 1));
            }
            if (answerLabelC != null) {
                answerLabelC.setText(answers.get(iterator * 4 + 2));
            }
            if (answerLabelD != null) {
                answerLabelD.setText(answers.get(iterator * 4 + 3));
            }

            iterator++;
        }
    }

    @FXML
    private void onConfirmButtonClick() throws InterruptedException {

        Thread.sleep(1000);
        highlightCorrectAnswer();
        System.out.println(correctAnswers.get(iterator-1));
        Thread.sleep(2000);
        System.out.println("po odczekaniu");

        if(checkAnswer()) {
            currentPoints++;
            prepareNewQuizQuestion();
        } else {
            System.out.println("game over");
        }
    }

    private String correctAnswerStyleSheet = "-fx-background-color: #00e600;" +
            "-fx-border-color:  linear-gradient(#bdbcbc,#676565);" +
            "-fx-border-radius: 10;" +
            "-fz-background-radius: 10;";

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
