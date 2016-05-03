package fxMinesweeper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("fxMinesweeper.fxml"));

        primaryStage.setTitle("Minesweeper");

        primaryStage.setFullScreen(false);
        //Group root = new Group();

        Scene scene = new Scene(root, Color.BEIGE);

        primaryStage.setScene(scene);


        //primaryStage.setVisible(true);
        primaryStage.setMaxWidth(700);
        primaryStage.setMaxHeight(800);
        primaryStage.setMinHeight(800);
        primaryStage.setMinWidth(700);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
