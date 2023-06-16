//Yusuf Shehadeh, 7395116
package testing;

/*
 * Siehe Hinweise auf dem Aufgabenblatt.
 */

import logic.Board;
import logic.Component;
import logic.Field;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Testing {

    private Field[][] board;
    private Board pBoard;


    public Testing(Field[][] initBoard) {
        this.board = initBoard;
        pBoard = new Board(initBoard);

    }

    public Testing(int aColors, int aRows, int aColumns) {
        pBoard = new Board(aColors, aRows, aColumns);
        this.board = pBoard.getBoard();
    }


    public boolean isStartklar() {
        if (!isDifferent())
            return false;
        if (!allColorsPresent())
            return false;
        return cornersDifferent();
    }

    public boolean isEndConfig() {
        return pBoard.getAllColorsBoard().length == 2;
    }


    public int testStrategy01() {
        return this.pBoard.strategy(1);
    }

    public int testStrategy02() {
        return this.pBoard.strategy(2);
    }

    public int testStrategy03() {
        return this.pBoard.strategy(3);
    }

    //todo
    public boolean toBoard(Field[][] anotherBoard, int moves) {
        if (this.getBoard().length != anotherBoard.length)
            return false;
        if (this.getBoard()[0].length != anotherBoard[0].length)
            return false;
        if (!this.pBoard.isS1Turn())
            return  false;
        Board anotherField = new Board(anotherBoard);
        for (int i = 0; i < anotherBoard.length; i++) {
            for (int j = 0; j < anotherBoard[0].length; j++) {
                if (!(anotherField.getS1().getComponent().contains(getBoard()[i][j]) || anotherField.getS2().getComponent().contains(getBoard()[i][j]))) {
                    if (anotherBoard[i][j].getColor() != getBoard()[i][j].getColor())
                        return false;
                }
            }
        }
        /*
        * Not final solution
        * Still needs some adjustments*/
        int uC1 = getUniqueColours(anotherField.getS1().getComponent(), getBoard().length - 1, 0);
        int uC2 = getUniqueColours(anotherField.getS2().getComponent(), 0, getBoard()[0].length - 1);
        if (uC1 != uC2 && uC1 != uC2 + 1)
            return false;
        int minMoves = uC1 + uC2;

        return minMoves <= moves;
    }


    private int getUniqueColours(Set<Field> playerComponents, int row, int col){
        Set<Integer> uniqueColors = new HashSet<>();
        for (Field field : playerComponents){
            if (!hasSameComp(getBoard()[field.getRow()][field.getCol()], getBoard()[row][col]))
                continue;
            uniqueColors.add(field.getColor());
        }
        return uniqueColors.size();
    }

    private boolean hasSameComp(Field thisField, Field startField){
        return thisField.getComponent() == startField.getComponent();
    }

    public int minMoves(int row, int col) {
        Board temp = new Board(getBoard());
        Component needle = temp.getBoard()[row][col].getComponent();
        Component s1 = temp.getS1();
        int currentColor = s1.getColor();
        int moves = 0;
        while (!temp.getComponents().contains(needle)) {
            if (currentColor == getColors().length - 1)
                currentColor = 0;
            else currentColor++;
            temp.makeTurnSinglePlayer(s1, currentColor);
            moves++;
        }
        return moves;
    }


    public int minMovesFull() {
        Field s2 = getBoard()[0][getBoard()[0].length - 1];
        return minMoves(s2.getRow(), s2.getCol());
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
}
