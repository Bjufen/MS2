//Yusuf Shehadeh, 7395116
package gui;

import logic.Board;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.net.http.HttpResponse;

public class DisplayPanel extends JPanel {

    private JPanel eastPanel, westPanel, bottomPanel, topPanel, middlePanel, buttonPanel;
    private JLabel bottomLabel;
    private Board board;
    public DisplayPanel() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.black);
        eastPanel = new JPanel();
        westPanel = new JPanel();
        bottomPanel = new JPanel();
        topPanel = new JPanel();
        middlePanel = new JPanel();
        eastPanel.setBackground(new Color(190,190,190));
        westPanel.setBackground(new Color(190,190,190));
        bottomPanel.setBackground(new Color(190,190,190));
        topPanel.setBackground(new Color(190,190,190));
        middlePanel.setBackground(Color.GREEN);
    }


    public void start(int colours, int rows, int cols){
        bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(190,190,190));
        this.setBackground(new Color(238,238,238));
        System.out.println(colours);
        System.out.println(rows);
        System.out.println(cols);
        loadBoard(colours, rows, cols);
        loadBottomPanel(board.getColors());
        setBounds();
    }

    public void setBounds(){
        this.add(middlePanel, BorderLayout.CENTER);
        this.add(eastPanel, BorderLayout.EAST);
        this.add(westPanel, BorderLayout.WEST);
        this.add(bottomPanel, BorderLayout.SOUTH);
        this.add(topPanel, BorderLayout.NORTH);
        resize();
    }

    public void resize(){
      eastPanel.setPreferredSize(new Dimension(this.getWidth()/6, this.getHeight()));
      westPanel.setPreferredSize(new Dimension(this.getWidth()/6, this.getHeight()));
      topPanel.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()/6));
      bottomPanel.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()/6));
    }

    public void loadBoard(int colours, int rows, int cols){
        board = new Board(colours,rows,cols);
    }

    public void loadBottomPanel(Color[] colours){
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomLabel = new JLabel("Available Colours");
        bottomLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottomPanel.add(bottomLabel);
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        for (int i = 0; i < colours.length; i++) {
            ButtonDesign_2 button = new ButtonDesign_2(colours[i], i+1);
            buttonPanel.add(button);
        }
        bottomPanel.add(buttonPanel);
    }

}
