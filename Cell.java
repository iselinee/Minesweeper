package fxMinesweeper;
import javafx.scene.control.Button;

public class Cell {
    int x =0;
    int y =0;
    Button button = new Button();
    boolean flagged = false;
    boolean mined = false;
    boolean exposed = false;
    int nearMines = 0;

    public Cell() {
        x =0;
        y =0;
        button = null;
        flagged = false;
        mined = false;
        exposed = false;
        nearMines = 0;
    }

}
