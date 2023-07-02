//Yusuf Shehadeh, 7395116
package gui;

import javax.swing.*;
import java.awt.*;

public class ButtonDesign extends JButton {
    Color colour;
    public ButtonDesign(String text) {
        super(text);
        this.setContentAreaFilled(false); // Set content area filled to false
        this.setBorderPainted(false); // Set border painted to false
        this.setOpaque(true); // Set opaque to true
    }

    public ButtonDesign(){
        super();
        this.setContentAreaFilled(false); // Set content area filled to false
        this.setBorderPainted(false); // Set border painted to false
        this.setOpaque(true); // Set opaque to true
    }

    public ButtonDesign(Color colour){
        this.setBackground(colour);
        this.colour = colour;
        this.setContentAreaFilled(false); // Set content area filled to false
        this.setBorderPainted(false); // Set border painted to false
        this.setOpaque(true); // Set opaque to true

    }

    public void updateColour(Color colour){
        this.colour = colour;
        this.setBackground(colour);
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension size = getParent().getSize();
        int largestDimension = Math.max(size.width, size.height);
        return new Dimension(largestDimension, largestDimension);
    }
}
