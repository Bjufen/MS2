package gui;

import java.awt.*;

public class ButtonDesign_2 extends ButtonDesign{
    private int number;
    public ButtonDesign_2(String text) {
        super(text);
    }
    public ButtonDesign_2(){
        super();
    }
    public ButtonDesign_2(Color colour, int number){
        super(colour);
        this.setText(Integer.toString(number));
        setNumber(number);
    }

    public void updateColour(Color color, int number){
        this.setBackground(color);
        this.setText(Integer.toString(number));
        setNumber(number);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
