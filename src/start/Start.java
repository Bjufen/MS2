//Yusuf Shehadeh, 7395116
package start;

/*
 * Siehe Hinweise auf dem Aufgabenblatt.
 */

import gui.Frame;
import logic.Board;
import logic.Field;
import testing.Testing;

import java.util.ArrayList;

public class Start {

    public static void main(String[] args) {
        Frame frame = new Frame();

/*        int[] values0 = {4, 5, 2, 0, 1,
                3, 0, 3, 4, 3,
                4, 1, 4, 2, 0,
                2, 4, 3, 1, 4,
                0, 5, 2, 4, 0};

        int[] values1 = {4, 5, 2, 0, 1,
                3, 0, 3, 4, 3,
                4, 1, 4, 2, 0,
                2, 4, 3, 1, 4,
                5, 5, 2, 4, 0};

        int[] values2 = {4, 5, 2, 0, 3,
                3, 0, 3, 4, 3,
                4, 1, 4, 2, 0,
                2, 4, 3, 1, 4,
                5, 5, 2, 4, 0};

        int[] values3 = {4, 5, 2, 0, 3,
                3, 0, 3, 4, 3,
                4, 1, 4, 2, 0,
                2, 4, 3, 1, 4,
                2, 2, 2, 4, 0};

        int[] values4 = {4, 5, 2, 0, 0,
                3, 0, 3, 4, 0,
                4, 1, 4, 2, 0,
                2, 4, 3, 1, 4,
                2, 2, 2, 4, 0};

        int[] values5 = {4, 5, 2, 0, 0,
                3, 0, 3, 4, 0,
                4, 1, 4, 2, 0,
                3, 4, 3, 1, 4,
                3, 3, 3, 4, 0};

        int[] values6 = {4, 5, 2, 4, 4,
                3, 0, 3, 4, 4,
                4, 1, 4, 2, 4,
                3, 4, 3, 1, 4,
                3, 3, 3, 4, 0};

        Field[][] initBoard = new Field[5][5];
        Field[][] board1 = new Field[5][5];
        Field[][] board2 = new Field[5][5];
        Field[][] board3 = new Field[5][5];
        Field[][] board4 = new Field[5][5];
        Field[][] board5 = new Field[5][5];
        Field[][] board6 = new Field[5][5];

        int index = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                initBoard[i][j] = new Field(i, j, values0[index] + 1);
                board1[i][j] = new Field(i, j, values1[index] + 1);
                board2[i][j] = new Field(i, j, values2[index] + 1);
                board3[i][j] = new Field(i, j, values3[index] + 1);
                board4[i][j] = new Field(i, j, values4[index] + 1);
                board5[i][j] = new Field(i, j, values5[index] + 1);
                board6[i][j] = new Field(i, j, values6[index] + 1);
                index++;
            }
        }

        Testing test0 = new Testing(initBoard);
        Testing test1 = new Testing(board1);
        Testing test2 = new Testing(board2);
        Testing test3 = new Testing(board3);
        Testing test4 = new Testing(board4);
        Testing test5 = new Testing(board5);
        Testing test6 = new Testing(board6);

        ArrayList<Testing> testings = new ArrayList<>();
        testings.add(test0);
        testings.add(test1);
        testings.add(test2);
        testings.add(test3);
        testings.add(test4);
        testings.add(test5);
        testings.add(test6);


        for (Testing testing : testings) {
            for (int i = 0; i <= 9; i++) {
                System.out.println("ToBoard with " + i + " moves possible: " + test0.toBoard(testing.getBoard(), i));
                System.out.println("-----------------------------------\n");
            }
            System.out.println("---------------------------------------------------------------------\n");
        }*/
    }
}
