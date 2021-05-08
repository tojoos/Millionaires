package App;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {

    private Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("mainMenuWindow.fxml"));
        window.setTitle("Millionaires");
        window.getIcons().add(new Image(Main.class.getResourceAsStream("images\\icon.png")));
        window.setScene(new Scene(root));
        window.show();
    }

}
