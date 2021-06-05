package App;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {
        window.setTitle("Millionaires");
        Parent root = FXMLLoader.load(getClass().getResource("StartMenuStage.fxml"));
        window.getIcons().add(new Image(Main.class.getResourceAsStream("images\\icon.png")));
        window.setScene(new Scene(root));
        window.show();
        
    }

}
