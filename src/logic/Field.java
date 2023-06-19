//Yusuf Shehadeh, 7395116
package logic;

/*
 * Siehe Hinweise zu Umgang mit dem Repository auf Aufgabenblatt.  
 */

public class Field {

	private int color;
	private int row;
	private int col;

	private Component component;
	
	public Field(int row, int col, int color) {
		this.row = row; 
		this.col = col; 
		this.color = color;  
	}

	public Field(){}

	
	
	
	/*
	 * Getter und Setter
	 */
	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public Component getComponent() {
		return component;
	}

	public void setComponent(Component component) {
		this.component = component;
	}

	@Override
	public String toString() {
		return "Field{" +
				"color=" + color +
				", row=" + row +
				", col=" + col +
				'}';
	}
}
