//Yusuf Shehadeh, 7395116
package gui;

import logic.Board;
import logic.Field;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.http.HttpResponse;
import java.util.Arrays;

public class DisplayPanel extends JPanel {

    //Set to false after config?
    private boolean firstStart = false;
    private JPanel eastPanel, westPanel, bottomPanel, topPanel, middlePanel, buttonPanel;
    private JLabel bottomLabel;
    private Board board;
    //Convert into arrayList
    private ButtonDesign_2[] colourButtons;
    private ButtonDesign[][] boardButtons;

    public DisplayPanel() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.black);
        eastPanel = new JPanel();
        westPanel = new JPanel();
        bottomPanel = new JPanel();
        topPanel = new JPanel();
        middlePanel = new JPanel();
      /*  eastPanel.setBackground(new Color(190,190,190));
        westPanel.setBackground(new Color(190,190,190));
        bottomPanel.setBackground(new Color(190,190,190));
        topPanel.setBackground(new Color(190,190,190));*/
    }


    public void start(int colours, int rows, int cols) {
        bottomPanel = new JPanel();
        // bottomPanel.setBackground(new Color(190,190,190));
        this.setBackground(new Color(238, 238, 238));
        loadBoard(colours, rows, cols);
        if (firstStart) {
            reloadBoard();
        } else {
            showBoard();
            loadBottomPanel(board.getColors());
        }
        setBounds();
        firstStart = true;
    }

    public void setBounds() {
        this.add(middlePanel, BorderLayout.CENTER);
        this.add(eastPanel, BorderLayout.EAST);
        this.add(westPanel, BorderLayout.WEST);
        this.add(bottomPanel, BorderLayout.SOUTH);
        this.add(topPanel, BorderLayout.NORTH);
        resize();
    }

    public void resize() {
        eastPanel.setPreferredSize(new Dimension(this.getWidth() / 6, this.getHeight()));
        westPanel.setPreferredSize(new Dimension(this.getWidth() / 6, this.getHeight()));
        topPanel.setPreferredSize(new Dimension(this.getWidth(), this.getHeight() / 6));
        bottomPanel.setPreferredSize(new Dimension(this.getWidth(), this.getHeight() / 6));
    }

    public void loadBoard(int colours, int rows, int cols) {
        board = new Board(colours, rows, cols);
    }

    public void showBoard() {
        middlePanel = new JPanel();
        boardButtons = new ButtonDesign[board.getaRows()][board.getaColumns()];
        middlePanel.setLayout(new GridLayout(board.getaRows(), board.getaColumns()));
        for (Field[] row : board.getBoard()) {
            for (Field field : row) {
                ButtonDesign button = new ButtonDesign(board.getColors()[field.getColor()]);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //select new colour
                        //update board
                        //reload board
                        //wait two secs
                        //S2 turn
                        //reload board
                        System.out.println("Button clicked" +
                                "\nRow: " + field.getRow() +
                                "\nColumn: " + field.getCol() +
                                "\nColour: " + board.getColors()[field.getColor()].getRed() + "," + board.getColors()[field.getColor()].getGreen() + "," + board.getColors()[field.getColor()].getBlue() +
                                "\nColourNumber: " + (field.getColor() + 1)+"\n");

                    }
                });
                middlePanel.add(button);
                boardButtons[field.getRow()][field.getCol()] = button;
            }

        }
    }
//fix
    public void reloadBoard() {

        for (Field[] row : board.getBoard()) {
            for (Field field : row) {
                boardButtons[field.getRow()][field.getCol()].updateColour(board.getColors()[field.getColor()]);
                boardButtons[field.getRow()][field.getCol()].removeActionListener(boardButtons[field.getRow()][field.getCol()].getActionListeners()[0]);
                boardButtons[field.getRow()][field.getCol()].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Button clicked" +
                                "\nRow: " + field.getRow() +
                                "\nColumn: " + field.getCol() +
                                "\nColour: " + board.getColors()[field.getColor()].getRed() + "," + board.getColors()[field.getColor()].getGreen() + "," + board.getColors()[field.getColor()].getBlue() +
                                "\nColourNumber: " + (field.getColor() + 1) +"\n");
                    }
                });
            }
        }
        for (int i = 0; i< colourButtons.length; i++){
            colourButtons[i].updateColour(board.getColors()[i], i + 1);
        }
    }

    public void removeButtons() {
        for (int i = 0; i < boardButtons.length; i++) {
            ButtonDesign[] row = boardButtons[i];
            for (int j = 0; j < row.length; j++) {
                ButtonDesign button = row[j];
                middlePanel.remove(button);
                boardButtons[i][j] = null;
            }
        }
        bottomPanel.remove(buttonPanel);
        Arrays.fill(colourButtons, null);

    }

    public void loadBottomPanel(Color[] colours) {
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomLabel = new JLabel("Available Colours");
        bottomLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottomPanel.add(bottomLabel);
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        colourButtons = new ButtonDesign_2[colours.length];
        for (int i = 0; i < colours.length; i++) {
            ButtonDesign_2 button = new ButtonDesign_2(colours[i], i + 1);
            buttonPanel.add(button);
            colourButtons[i] = button;
            if (i == colours.length - 1)
                break;
            buttonPanel.add(Box.createRigidArea(new Dimension(1, 0)));
        }
        bottomPanel.add(buttonPanel, 1);
    }

}
