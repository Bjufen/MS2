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
        //Frame frame = new Frame();
        int[] values0 = {4, 5, 2, 0, 1,
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

        Field[][] initBoard = new Field[5][5];
        Field[][] board1 = new Field[5][5];
        Field[][] board2 = new Field[5][5];
        Field[][] board3 = new Field[5][5];
        Field[][] board4 = new Field[5][5];
        Field[][] board5 = new Field[5][5];

        int index = 0;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                initBoard[i][j] = new Field(i, j, values0[index] + 1);
                board1[i][j] = new Field(i, j, values1[index] + 1);
                board2[i][j] = new Field(i, j, values2[index] + 1);
                board3[i][j] = new Field(i, j, values3[index] + 1);
                board4[i][j] = new Field(i, j, values4[index] + 1);
                board5[i][j] = new Field(i, j, values5[index] + 1);
                index++;
            }
        }

        System.out.println("Board 0");
        Testing test0 = new Testing(initBoard);
        System.out.println("Board 1");
        Testing test1 = new Testing(board1);
        System.out.println("Board 2");
        Testing test2 = new Testing(board2);
        System.out.println("Board 3");
        Testing test3 = new Testing(board3);
        System.out.println("Board 4");
        Testing test4 = new Testing(board4);
        System.out.println("Board 5");
        Testing test5 = new Testing(board5);

        ArrayList<Testing> testings = new ArrayList<>();
        testings.add(test0);
        testings.add(test1);
        testings.add(test2);
        testings.add(test3);
        testings.add(test4);
        testings.add(test5);
		/*System.out.println(test1.isStartklar());
		System.out.println();
		System.out.println(test1.getpBoard());
		System.out.println(test1.minMovesFull());


        System.out.println(testing.getpBoard());
        System.out.println(testing.minMoves(2,2));
        System.out.println("IstStartklar");
        System.out.println(testing.isStartklar());
        System.out.println("EndConfig");
        System.out.println(testing.isEndConfig());
        System.out.println("Strat1");
        System.out.println(testing.testStrategy01());
        System.out.println("Strat2");
        System.out.println(testing.testStrategy02());
        System.out.println("Strat3");
        System.out.println(testing.testStrategy03());*/
        /*for (int i = 0; i <= 5; i++) {
            System.out.println("ToBoard with " + i + " moves possible: " + test0.toBoard(testings.get(i).getBoard(), i));
            System.out.println();
            System.out.println(test0.getpBoard());
            System.out.println(testings.get(i).getpBoard());
            System.out.println("---------------------------------------------------------------------\n");
        }*/
        //System.out.println(test4.toBoard(test5.getBoard(), 1));
        for (int i = 0; i < 5; i++){
            System.out.println("Number " + i +
                    "\nBoard:\n" + testings.get(i).getpBoard() +
                    "S1:\n" + testings.get(i).getpBoard().getS1().getComponent() +
                    "\nSize: " + testings.get(i).getpBoard().getS1().getSize() +
                    "\nS2:\n"  + testings.get(i).getpBoard().getS2().getComponent() +
                    "\nSize: " + testings.get(i).getpBoard().getS2().getSize() +
                    "\n----------------------------------------------------------------\n");
        }
       /* System.out.println(test4.getpBoard());
        System.out.println(test4.getpBoard().getS1().getComponent());*/

    }

}
