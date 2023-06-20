package gui;

import javax.swing.*;
import java.awt.*;

public class ButtonDesign extends JButton {
    Color colour;
    public ButtonDesign(String text) {
        super(text);
/*        this.setFont(new Font("Times New Roman", Font.BOLD, 14));
        this.setBackground(Color.GREEN);
        this.setForeground(Color.BLACK);*/
    }

    public ButtonDesign(){
        super();
    }

    public ButtonDesign(Color colour){
        this.setBackground(colour);
        this.colour = colour;

    }

    public void updateColour(Color colour){
        this.colour = colour;
        this.setBackground(colour);
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension size = super.getPreferredSize();
        int largestDimension = Math.max(size.width, size.height);
        return new Dimension(largestDimension, largestDimension);
    }
}
