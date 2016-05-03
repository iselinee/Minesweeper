package fxMinesweeper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.Timer;


public class MenuController implements EventHandler<MouseEvent>{
    Minefield minefield;          // minefield is the instance, ie... instance.function
    Boolean endGame = false;
    Boolean success = false;
    // time
    Timer timer;
    double timeAtStart;           // time since start in milli seconds
    double timeElapsed = 0;
    boolean started;            // has game started?
    int cellSize =30;
    public String difficultyLevel ;      // set to Normal?

    // included in scene
    @FXML    ChoiceBox<String> difficultyBox = new ChoiceBox<>();
    @FXML    TextField textFieldTimer = new TextField();
    @FXML    Label labelTime = new Label();
    @FXML    TextField textFieldMines = new TextField();
    @FXML    Label labelMines = new Label();
    @FXML    Button pause = new Button();
    @FXML    Button start = new Button();
    @FXML    Pane pane = new Pane();
    VBox vbox = new VBox(1);
    HBox hbox = new HBox();
    //hbox.getChildren().add(this.difficultyBox, textFieldTimer);

    @Override
    public void handle(MouseEvent event) {

    }

    public void initialize() {
        System.out.println("Begin Setup");
        minefield = new Minefield();
        difficultyBox();
        setTextFieldsandLabels();
        System.out.println("End Setup");
        startGame();
    }

    public void startGame() {
        System.out.println("begin 'startGame' ");

        pane.getChildren().clear();
        minefield.makeMinefield();
        Pane pane = new Pane();
        getDifficultyLevel(difficultyBox);
        minefield.addMines();

        addMinefieldButtons();
        timer = new Timer();
        started = false;
        setTextFieldsandLabels();
        System.out.println("end game 'startGame' ");

        /* TODO
        minesLeft from makeBoard();
        numCellsUncovered ?
        timeElapsed setValue(0) and update;
        endGame boolean setValue(false);
        Won ? if cells uncovered == total cells - mines NOT if mine exploded
        */
    StartButton();
    }

    //@Override
    public void handle(ActionEvent event) {
        if (event.getSource()==difficultyBox){
            System.out.println("Difficulty changed: ");
        }
        if (event.getSource()==pause){
            System.out.println("Pause Button: ");
        }
        if (event.getSource()==start){
            System.out.println("Reset Button: ");
        }
    }


    void difficultyBox() {                                      // listener
        difficultyBox.setItems(FXCollections.observableArrayList("Easy", "Normal", "Hard"));
        difficultyBox.setValue("Normal");
        difficultyBox.setPrefWidth(90);
        difficultyBox.setTooltip(new Tooltip("Select the difficulty"));

        difficultyBox.getSelectionModel()
                .selectedItemProperty()
                .addListener((ObservableValue<? extends String> observable,
                              String oldValue, String newValue) -> setDifficulty(newValue));
    }
    private void getDifficultyLevel(ChoiceBox<String> difficultyBox){
        difficultyLevel = difficultyBox.getValue();
        System.out.println(" Difficulty set to"+ difficultyLevel);

    }

    void setDifficulty(String difficultyLevel){
        if(difficultyLevel != null) {
            if (difficultyLevel.equals("Easy")) {
                minefield.minefieldWidth = minefield.gridEasyWidth;
                minefield.minefieldHeight = minefield.gridEasyHeight;
                minefield.numMinesLeft = minefield.gridEasyNumMines;
                //minefield.changeDifficultyToEasy();
            }
            if (difficultyLevel.equals( "Normal")) {
                minefield.minefieldWidth = minefield.gridNormalWidth;
                minefield.minefieldHeight = minefield.gridNormalHeight;
                minefield.numMinesLeft = minefield.gridNormalNumMines;
                //minefield.changeDifficultyToNormal();
            }
            if (difficultyLevel.equals( "Hard")) {
                minefield.minefieldWidth = minefield.gridHardWidth;
                minefield.minefieldHeight = minefield.gridHardHeight;
                minefield.numMinesLeft = minefield.gridHardNumMines;
                //minefield.changeDifficultyToHard();
            }
            System.out.println(" Difficulty set to "+ difficultyLevel);

        }
    }

    void setTextFieldsandLabels(){
        textFieldTimer = new TextField();
        textFieldTimer.setText(" This is my new text. method = setTextFieldandLabels() ");
        labelTime = new Label();
        labelTime.setText("Time");
        textFieldMines = new TextField();
        textFieldMines.setText("This is my new text.");
        labelMines = new Label();
        labelMines.setText("MinesLeft");
    }


    int numMinesLeft() {
        return minefield.numMinesLeft;
    }

    void StartButton() {
        Button start = new Button();
        start.setText("Say 'Hello World'");
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World! - Start Button");
            }
        });
        startOver();   // timer START
        // public void handle() { handle(); }
        // if (mouse.button == clicked )
    }


    // http://www.programcreek.com/java-api-examples/index.php?api=javafx.scene.control.ButtonBuilder
    // http://docs.oracle.com/javafx/2/layout/size_align.htm
    public void addMinefieldButtons() {
        for (int x = 0; x < minefield.minefieldWidth; x++) {
            for (int y = 0; y < minefield.minefieldHeight; y++) {
                minefield.minefield[x][y].button = new Button();
                minefield.minefield[x][y].button.setLayoutX(cellSize * minefield.minefieldWidth);
                minefield.minefield[x][y].button.setLayoutY(cellSize * minefield.minefieldHeight);
                minefield.minefield[x][y].button.setPrefSize(cellSize,cellSize);
                minefield.minefield[x][y].button.setStyle("-fx-background-insets: 0,1,2");
                minefield.minefield[x][y].button.setStyle("-fx-padding: 8 8 8 8;");
                pane.getChildren().add(minefield.minefield[x][y].button);
                int i = x;
                int j = y;
                minefield.minefield[x][y].button.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.SECONDARY) {   // mouse could have >2 buttons
                        minefield.mark(minefield.minefield[i][j]);
                    }
                    if (event.getButton() == MouseButton.PRIMARY){      // using only 2
                        minefield.expose(minefield.minefieldWidth, minefield.minefieldHeight);
                    }
                    cellClicked();
                    updateTimer();
                });
            }
        }
    }

    public void cellClicked() {
        if (!started) {
            startOver();
            started = true;
        }
        System.out.println("Mines Left: "+ minefield.getNumMinesLeft());
        if (minefield.exploded) {
            endGame = true;
            success = false;
            pause();
        }
        if (minefield.numMinesLeft == 0) {
            endGame = true;
            success = true;
            pause();
        }
    }

    public void startOver() {
        timeAtStart = System.currentTimeMillis();
    }
    private void updateTimer() {
        if (!(minefield.exploded || (minefield.numMinesLeft == 0)))

            labelTime.setText("Time:  "+ textFieldTimer);
    }

    Integer timeCounter() {
        double timePassed = (System.currentTimeMillis() - timeAtStart)/1000.0;
        return (int) timePassed;        // set start = current clock time, start - current  = time elapsed
    }

    private void pause() {              // set pause button 1) if pressed 2) if mine triggered
        Button pause = new Button();
        for (int x = 0; x < minefield.minefieldWidth; x++) {
            for (int y = 0; y < minefield.minefieldHeight; y++) {
                Button button = minefield.minefield[x][y].button;
                if (minefield.exploded || minefield.minefield[x][y].mined)  //mine exploded
                    // Change this to only happen on specified button, all others expose or SHOW
                    button.setText("!");                                    // show remaining mines
                button.setOnMouseClicked(event -> {                         // no click
                    if (event.getButton() == MouseButton.SECONDARY) {
                        //do nothing
                    }
                    button.setDisable(true);  // should disable buttons
                });
            }
        }
    }

}