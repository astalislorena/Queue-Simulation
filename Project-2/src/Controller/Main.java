package Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../View/view.fxml"));
        primaryStage.setTitle("Queue simulator");
        primaryStage.setScene(new Scene(root, 1100, 900));
        primaryStage.getIcons().removeAll();
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
