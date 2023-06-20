//Yusuf Shehadeh, 7395116
package start;

/*
 * Siehe Hinweise auf dem Aufgabenblatt.  
 */

import gui.Frame;
import logic.Board;
import testing.Testing;

public class Start {

	public static void main(String[] args) {
		//Frame frame = new Frame();

		Testing test1 = new Testing(5,7,9);

		System.out.println(test1.minMovesFull());
	}


}
