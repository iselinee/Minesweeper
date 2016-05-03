package fxMinesweeper;

public class Minefield {

    // Minefield dimensions
    int gridEasyWidth = 5;
    int gridEasyHeight = 5;
    int gridNormalHeight = 10;
    int gridNormalWidth = 10;
    int gridHardWidth = 16;
    int gridHardHeight = 20;

    int minefieldWidth  = 10;           // starts at normal , default values
    int minefieldHeight = 10;           // should change when choicebox changes

    // grid amount of mines
    int gridEasyNumMines = 5;
    int gridNormalNumMines = 50;
    int gridHardNumMines = 190;

    int numMinesatStart;
    int numMinesLeft   ;            // number mines left = number mines at start
    int cellsUncovered = 0;             // == 0 at start
    boolean exploded;
    Cell[][] minefield;
    int totalCells=0;
    int exposedCells=0;


    public Minefield(){
        numMinesLeft = 0;
        numMinesatStart = 0;
        cellsUncovered =0;
        exploded = false;
    }

    public void makeMinefield(){
        minefield = new Cell[minefieldWidth][minefieldHeight];
        System.out.println("begin 'makeMineField' ");
        for (int i = 0; i < minefieldWidth; i++) {
            for (int j = 0; j < minefieldHeight ; j++ ){
                Cell Cell = new Cell();             // make Cell
                minefield[i][j] = Cell;            // newCellArray[x][y] is one cell
                minefield[i][j].mined = false;
                minefield[i][j].flagged = false;
                minefield[i][j].button =null;
                minefield[i][j].x = i;
                minefield[i][j].y = j;
                totalCells++;
                System.out.println("Cell X = "+ minefield[i][j].x +": Y = "+ minefield[i][j].y
                        +": Mined = "+ minefield[i][j].mined +": flagged : "+ minefield[i][j].flagged);
            }
        }
    }

    public int isExposed(int column, int row){
        if (minefield[column][row].exposed){
            return 1;
        }
        return 0;
    }

    public Cell[][] getMinefield() { return minefield; }
    public int unexposedMines(){ return numMinesatStart - numMinesLeft; }   // REQ'd
    public int unexposedCount(){ return totalCells - exposedCells -numMinesLeft; }  // REQ'd

    public int expose(int x, int y){   //REQ'd
        Cell Cell = minefield[x][y];
        if (minefield[x][y].mined ){
            exploded = true;
            Cell.button.setText("!");
            return -1;              // will return -1 if mine exploded
        }
        if (minefield[x][y].exposed == false){
            Cell.button.setText("");
            exposedCells++;
            return neighborsMined(x,y); // will return 0-8

        }
        return 0;
    }

    public int neighborsMined(int x, int y){
        int countToShow =0;
        int newXL;              // looking at +/- 1 row
        int newYL;              // looking +/- 1 column
        int newXH;
        int newYH;
        if ( x-1 <minefieldWidth){   // EDGE case compares
            newXL = x;              // wall to left
            }
        else { newXL = x-1; }
        if (x+1 >minefieldWidth){
            newXH = x;              // wall to right
        }
        else { newXH = x+1; }
        if (y-1 <minefieldHeight){
            newYL = y;              // wall below
        }
        else { newYL = y-1; }
        if (y+1 >minefieldHeight){
            newYH = y;              // wall above
        }
        else { newYH = y+1; }
        for (int i=newXL; i <= newXH ; i++   ){
            for ( int j=newYL ; j<= newYH ; j++){
                if ((i == x) && (j == y)){ continue; }               // skip self
                if (minefield[i][j].mined == true){ countToShow++; } // add to # mines nearby
                if (minefield[i][j].mined == false){ expose(i,j);  }
            }
        }
        cellsUncovered++;
        return countToShow;
    }

    // set flag or unflag
    boolean mark(Cell cell) {       // REG'd
        if (cell.flagged != true) {
            cell.flagged = true;
            numMinesLeft--;}
        else { cell.flagged = false; }
        return cell.flagged;
    }

// random() method returns a random number between 0.0 and 0.999. then multiply it by 50,
// so upper limit becomes 0.0 to 49.95, when you add 1, it becomes 1.0 to 50.95, Truncates to 50
    void addMines(){
        for (int i = 0; i < numMinesLeft; ++i){
            int randomX = (int )(Math.random() * minefieldWidth );
            int randomY = (int )(Math.random() * minefieldHeight );
            if (minefield[randomX][randomY].mined == true ){ i--; }  // try again
            if (minefield[randomX][randomY].mined == false) {        // set mine
                minefield[randomX][randomY].mined = true;   }
        }
    }

    public int getNumMinesLeft(){ return numMinesLeft; } // total cells at start = difficulty level

}







