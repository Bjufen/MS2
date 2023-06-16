package gui;

import javax.swing.*;
import java.awt.*;

public class ButtonDesign extends JButton {
    public ButtonDesign(String text) {
        super(text);
/*        this.setFont(new Font("Times New Roman", Font.BOLD, 14));
        this.setBackground(Color.GREEN);
        this.setForeground(Color.BLACK);*/
    }

    public ButtonDesign(){
        super();
    }

    public ButtonDesign(Color colour, int number){
        super(Integer.toString(number));
        this.setBackground(colour);
    }
}
