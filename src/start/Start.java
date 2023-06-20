//Yusuf Shehadeh, 7395116
package start;

/*
 * Siehe Hinweise auf dem Aufgabenblatt.
 */

import gui.Frame;
import logic.Board;
import logic.Field;
import testing.Testing;

public class Start {

    public static void main(String[] args) {
        //Frame frame = new Frame();
        Field[][] initBoard = new Field[5][5];
        int[] values =
                {4,	5,	2,	0,	1,
                        3,	0,	3	,4,	3,
                        4	,1	,4,	2,	0,
                        2	,4	,3,	1,	4,
                        0	,5	,2,	4,	0	};
        int index = 0;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                initBoard[i][j] = new Field(i, j, values[index] + 1);
                index++;
            }
        }

	/*Testing test1 = new Testing(5,5);
		System.out.println(test1.isStartklar());
		System.out.println();
		System.out.println(test1.getpBoard());
		System.out.println(test1.minMovesFull());*/

        Testing testing = new Testing(initBoard);
        System.out.println(testing.getpBoard());
        System.out.println("IstStartklar");
        System.out.println(testing.isStartklar());
        System.out.println("EndConfig");
        System.out.println(testing.isEndConfig());
        System.out.println("Strat1");
        System.out.println(testing.testStrategy01());
        System.out.println("Strat2");
        System.out.println(testing.testStrategy02());
        System.out.println("Strat3");
        System.out.println(testing.testStrategy03());

    }

}
