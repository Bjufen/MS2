//Yusuf Shehadeh, 7395116
package testing;

/*
 * Siehe Hinweise auf dem Aufgabenblatt.
 */

import logic.Board;
import logic.Component;
import logic.Field;
import logic.SubBoard;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Testing {

    private final Field[][] board;
    private Board pBoard;

    private final int[] SCHLECHTE_AUFGABENSTELLUNG_WEIL = {1, 2, 3, 4, 5, 6};


    public Testing(Field[][] initBoard) {
        this.board = initBoard;
        pBoard = new Board(initBoard, SCHLECHTE_AUFGABENSTELLUNG_WEIL);

    }

    public Testing(int aRows, int aColumns) {
        pBoard = new Board(SCHLECHTE_AUFGABENSTELLUNG_WEIL.length, aRows, aColumns);
        this.board = pBoard.getBoard();
    }


    public boolean isStartklar() {
        if (!isDifferent())
            return false;
        if (!allColorsPresent())
            return false;
        return cornersDifferent();
    }

    /*
     * Was wenn es nur noch zwei Farben aufm Brett, aber die nicht alle zu s1 oder s2 gehören?
     *
     * */
    public boolean isEndConfig() {
        return pBoard.getAllColorsBoard().length <= 2 && (this.getpBoard().getS1().getSize() + this.getpBoard().getS2().getSize()) == (board.length * board[0].length);
    }


    public int testStrategy01() {
        SubBoard temp = new SubBoard(board, SCHLECHTE_AUFGABENSTELLUNG_WEIL);
        return temp.strategies(1, getpBoard().getS2());
    }

    public int testStrategy02() {
        SubBoard temp = new SubBoard(board, SCHLECHTE_AUFGABENSTELLUNG_WEIL);
        return temp.strategies(2, getpBoard().getS2());
    }

    public int testStrategy03() {
        SubBoard temp = new SubBoard(board, SCHLECHTE_AUFGABENSTELLUNG_WEIL);
        return temp.strategies(3, getpBoard().getS1());
    }

    public boolean toBoard(Field[][] anotherBoard, int moves) {
        if (this.getBoard().length != anotherBoard.length)
            return false;
        if (this.getBoard()[0].length != anotherBoard[0].length)
            return false;
        Board anotherField = new Board(anotherBoard);
        /*for (int i = 0; i < anotherBoard.length; i++) {
            for (int j = 0; j < anotherBoard[0].length; j++) {
                if (!(anotherField.getS1().getComponent().contains(getBoard()[i][j]) || anotherField.getS2().getComponent().contains(getBoard()[i][j]))) {
                    if (anotherBoard[i][j].getColor() != getBoard()[i][j].getColor())
                        return false;
                }
            }
        }*/
        return recursiveStep(getpBoard(), anotherField, moves, true);
    }

    public boolean recursiveStep(Board ourBoard, Board anotherBoard, int moves, boolean s1Turn) {
        boolean isIdentical = recursiveHelper(ourBoard, anotherBoard);
       /* System.out.println("-------------------------------------------------------------");
        System.out.println("Moves left: " + moves + "\nourBoard:\n" + ourBoard + "\nanotherBoard:\n" + anotherBoard);
        System.out.println("Isidentical: " + isIdentical);*/
        if ((moves == 0 || (isIdentical)))
            return isIdentical;

       /* int ourBoardS1Size = ourBoard.getS1().getSize();
        int ourBoardS2Size = ourBoard.getS2().getSize();
        int anotherBoardS1Size = anotherBoard.getS1().getSize();
        int anotherBoardS2Size = anotherBoard.getS2().getSize();

        if ((ourBoardS1Size > anotherBoardS1Size) || (ourBoardS2Size > anotherBoardS2Size)) return false;*/
        Board copyBoard;
        for (int i = 1; i < 7; i++) {
            Board copyBoard2;
            if ((i == ourBoard.getS1().getColor()) || (i == ourBoard.getS2().getColor()))
                continue;
            copyBoard = new Board(ourBoard.getCopyOfBoard());
            if(s1Turn) {
                copyBoard2 = new Board(copyBoard.makeTurnToBoard(copyBoard.getS1(),i));
                /*System.out.println("CopyBoard:\n" + copyBoard);
                System.out.println("CopyBoard2:\n" + copyBoard2);*/
            }
            else{
                copyBoard2 = new Board(copyBoard.makeTurnToBoard(copyBoard.getS2(),i));
            }
            if (recursiveStep(copyBoard2, anotherBoard, moves - 1, !s1Turn))
                return true;
        }
        return false;
    }

    private boolean recursiveHelper(Board ourBoard, Board anotherBoard) {
        Field[][] ourField = ourBoard.getBoard();
        Field[][] anotherField = anotherBoard.getBoard();
        for (int i = 0; i < ourField.length; i++) {
            for (int j = 0; j < ourField[0].length; j++) {
                if (ourField[i][j].getColor() != anotherField[i][j].getColor())
                    return false;
            }
        }
        return true;
    }

    public int minMoves(int row, int col) {
        Field[][] testingBoard = pBoard.getCopyOfBoard();
        SubBoard temp = new SubBoard(testingBoard, SCHLECHTE_AUFGABENSTELLUNG_WEIL);
        Component needle = temp.getBoard()[row][col].getComponent();
        int currentColor = temp.getS1().getColor();
        int moves = 0;
        while (temp.getComponents().contains(needle)) {
            if (currentColor == getColors().length)
                currentColor = 1;
            else currentColor++;
            temp.makeTurnSinglePlayer(temp.getS1(), currentColor);
            moves++;
        }
        System.out.println(temp);
        return moves;
    }

    public int minMovess(int row, int col){
        int[] minMoves = new int[SCHLECHTE_AUFGABENSTELLUNG_WEIL.length + 1];
        SubBoard originalBoard = new SubBoard(pBoard.getCopyOfBoard(), SCHLECHTE_AUFGABENSTELLUNG_WEIL);
        int currentColour = originalBoard.getS1().getColor();
        minMoves[0] = Integer.MAX_VALUE;
        minMoves[currentColour] = Integer.MAX_VALUE;
        for (int i = 0; i < minMoves.length; i++){
            if(i == currentColour)
                continue;
            int moves = 0;
            int currentColor = i;
            Field[][] testingBoard = pBoard.getCopyOfBoard();
            SubBoard temp = new SubBoard(testingBoard, SCHLECHTE_AUFGABENSTELLUNG_WEIL);
            Component needle = temp.getBoard()[row][col].getComponent();
            while (temp.getComponents().contains(needle)) {
                if (currentColor == getColors().length)
                    currentColor = 1;
                else currentColor++;
                temp.makeTurnSinglePlayer(temp.getS1(), currentColor);
                moves++;
            }
            minMoves[i] = moves;
        }
        int minValue = minMoves[1];
        for (int i = 2; i < minMoves.length; i++){
            if (minMoves[i] < minValue)
                minValue = minMoves[i];
        }
        return minValue;
    }


    public int minMovesFull() {
        SubBoard temp = new SubBoard(pBoard.getCopyOfBoard(), SCHLECHTE_AUFGABENSTELLUNG_WEIL);
        int currentColor = temp.getS1().getColor();
        int moves = 0;
        while (temp.getS1().getSize() != (board.length * board[0].length)) {
            if (currentColor == getColors().length)
                currentColor = 1;
            else currentColor++;
            temp.makeTurnSinglePlayer(temp.getS1(), currentColor);
            moves++;
        }
       // Testing test = new Testing(temp.getBoard());
        return moves;
    }


    public int minMovesFulll(){
        int[] minMoves = new int[SCHLECHTE_AUFGABENSTELLUNG_WEIL.length + 1];
        SubBoard originalBoard = new SubBoard(pBoard.getCopyOfBoard(), SCHLECHTE_AUFGABENSTELLUNG_WEIL);
        int currentColour = originalBoard.getS1().getColor();
        minMoves[0] = Integer.MAX_VALUE;
        minMoves[currentColour] = Integer.MAX_VALUE;
        for (int i = 0; i < minMoves.length; i++){
            if(i == currentColour)
                continue;;
            int moves = 0;
            int currentColor = i;
            SubBoard temp = new SubBoard(originalBoard.getCopyOfBoard(), SCHLECHTE_AUFGABENSTELLUNG_WEIL);
            while (temp.getS1().getSize() != (board.length * board[0].length)) {
                if (currentColor == getColors().length)
                    currentColor = 1;
                else currentColor++;
                temp.makeTurnSinglePlayer(temp.getS1(), currentColor);
                moves++;
            }
            minMoves[i] = moves;
        }
        int minValue = minMoves[1];
        for (int i = 2; i < minMoves.length; i++){
            if (minMoves[i] < minValue)
                minValue = minMoves[i];
        }
        return minValue;
    }

    //Returns true if every cell of every cell's neighbour is a different color
    private boolean isDifferent() {
        Field[][] board = getBoard();
        int rows = board.length;
        int columns = board[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int currentColor = board[i][j].getColor();
                if (i > 0 && board[i - 1][j].getColor() == currentColor)
                    return false;
                if (i < rows - 1 && board[i + 1][j].getColor() == currentColor)
                    return false;
                if (j > 0 && board[i][j - 1].getColor() == currentColor)
                    return false;
                if (j < columns - 1 && board[i][j + 1].getColor() == currentColor)
                    return false;
            }
        }

        return true;
    }

    /*
        getAllColorsBoard returns a list of unique int variables
        possibilities:
            boardColors not as long as getColors -> false
            a color out of the spectrum is in boardColors -> false

        if the for loop has completed without returning false, it means that there are as many colors on the board as are in the game.
        Return true.
    */
    private boolean allColorsPresent() {
        int[] boardColors = pBoard.getAllColorsBoard();
        if (this.getColors().length != boardColors.length)
            return false;
        for (int boardColor : boardColors) {
            if (boardColor > getColors().length || boardColor < 1)
                return false;
        }
        return true;
    }

    private boolean cornersDifferent() {
        int s1 = getBoard()[getBoard().length - 1][0].getColor();
        int s2 = getBoard()[0][getBoard()[0].length - 1].getColor();
        return s1 != s2;
    }

    private int getUniqueColours(Set<Field> playerComponents, int row, int col) {
        Set<Integer> uniqueColors = new HashSet<>();
        for (Field field : playerComponents) {
            if (!hasSameComp(getBoard()[field.getRow()][field.getCol()], getBoard()[row][col]))
                continue;
            uniqueColors.add(field.getColor());
        }
        return uniqueColors.size();
    }

    private boolean hasSameComp(Field thisField, Field startField) {
        return thisField.getComponent() == startField.getComponent();
    }


    /*
     * Getter und Setter
     */
    public Field[][] getBoard() {
        return pBoard.getBoard();
    }

    public void setBoard(Field[][] board) {
        pBoard.setBoard(board);
    }

    public Color[] getColors() {
        return pBoard.getColors();
    }

    public void setColors(Color[] colors) {
        pBoard.setColors(colors);
    }

    public Board getpBoard() {
        return pBoard;
    }

    public void setpBoard(Board pBoard) {
        this.pBoard = pBoard;
    }
}
