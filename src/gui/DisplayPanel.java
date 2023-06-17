//Yusuf Shehadeh, 7395116
package gui;

import logic.Board;
import logic.Field;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

public class DisplayPanel extends JPanel {

    //Set to false after config?
    private boolean firstStart = false;
    private JPanel eastPanel, westPanel, bottomPanel, topPanel, middlePanel, buttonPanel;
    private JLabel bottomLabel;
    private Board board;
    //Convert into arrayList?
    private ButtonDesign_2[] colourButtons;
    private ButtonDesign[][] boardButtons;

    private int strategy;

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
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_1) {
                    System.out.println("1 key pressed");
                } else if (keyCode == KeyEvent.VK_2) {
                    System.out.println("2 key pressed");
                } else if (keyCode == KeyEvent.VK_3) {
                    System.out.println("3 key pressed");
                } else if (keyCode == KeyEvent.VK_4) {
                    System.out.println("4 key pressed");
                } else if (keyCode == KeyEvent.VK_5) {
                    System.out.println("5 key pressed");
                } else if (keyCode == KeyEvent.VK_6) {
                    System.out.println("6 key pressed");
                } else if (keyCode == KeyEvent.VK_7) {
                    System.out.println("7 key pressed");
                } else if (keyCode == KeyEvent.VK_8) {
                    System.out.println("8 key pressed");
                } else if (keyCode == KeyEvent.VK_9) {
                    System.out.println("9 key pressed");
                }
            }
        });
    }


    public void start(int colours, int rows, int cols) {
        this.setBackground(new Color(238, 238, 238));
        loadBoard(colours, rows, cols);
        if (firstStart) {
            reloadBoard();
            reloadBottomPanel();
        } else {
            showBoard();
            loadBottomPanel(board.getColors());
            setKeyStrokes();
        }
        setBounds();
        setEnabledGame(false);
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
        repaint();
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
                addButton(field);
            }

        }
    }

    //fix
    public void reloadBoard() {
        boardButtons = new ButtonDesign[board.getaRows()][board.getaColumns()];
        middlePanel.removeAll();
        middlePanel.setLayout(new GridLayout(board.getaRows(), board.getaColumns()));
        for (Field[] row : board.getBoard()) {
            for (Field field : row) {
                if (field != null) {
                    addButton(field);
                }
            }
        }
    }

    private void addButton(Field field) {
        ButtonDesign button = new ButtonDesign(board.getColors()[field.getColor()]);
        button.addActionListener(e -> {
            s1Move(field.getColor());
            s2Move();
            System.out.println("Button clicked" +
                    "\nRow: " + field.getRow() +
                    "\nColumn: " + field.getCol() +
                    "\nColour: " + board.getColors()[field.getColor()].getRed() + "," + board.getColors()[field.getColor()].getGreen() + "," + board.getColors()[field.getColor()].getBlue() +
                    "\nColourNumber: " + (field.getColor() + 1) + "\n");

        });
        middlePanel.add(button);
        boardButtons[field.getRow()][field.getCol()] = button;
    }


    public void s1Move(int color) {
        setEnabledPlayercolours(true);
        board.makeTurnSinglePlayer(board.getS1(), color);
        reloadBoard();
        reloadBottomPanel();
        setEnabledPlayercolours(false);
    }

    public void s2Move() {
/*        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }*/
        setEnabledPlayercolours(true);
        board.makeTurnSinglePlayer(board.getS2(), board.strategy(getStrategy()));
        reloadBoard();
        reloadBottomPanel();
        setEnabledPlayercolours(false);
    }

    public void reloadBottomPanel() {
        colourButtons = new ButtonDesign_2[board.getColors().length];
        buttonPanel.removeAll();
        for (int i = 0, colourButtonsLength = colourButtons.length; i < colourButtonsLength; i++) {
            ButtonDesign_2 button = colourButtons[i];
            button = new ButtonDesign_2(board.getColors()[i], i + 1);
            buttonPanel.add(button);
            colourButtons[i] = button;
            ButtonDesign_2 finalButton = button;
            button.addActionListener(e -> {
                System.out.println("BottomPanelButton Clicked" +
                        "\nColour: " + finalButton.getBackground().getRed() + "," + finalButton.getBackground().getGreen() + "," + finalButton.getBackground().getBlue() +
                        "\nNumber: " + finalButton.getNumber() + "\n");
                s1Move(finalButton.getNumber() - 1);
                s2Move();
            });
            if (i == board.getColors().length - 1)
                break;
            buttonPanel.add(Box.createRigidArea(new Dimension(1, 0)));
        }

        bottomPanel.add(buttonPanel, 1);
        this.add(bottomPanel, BorderLayout.SOUTH);
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
            button.addActionListener(e -> {
                System.out.println("BottomPanelButton Clicked" +
                        "\nColour: " + button.getBackground().getRed() + "," + button.getBackground().getGreen() + "," + button.getBackground().getBlue() +
                        "\nNumber: " + button.getNumber());
                s1Move(button.getNumber() - 1);
                s2Move();
            });
            if (i == colours.length - 1)
                break;
            buttonPanel.add(Box.createRigidArea(new Dimension(1, 0)));
        }
        bottomPanel.add(buttonPanel, 1);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    public void setEnabledGame(Boolean value) {
        setEnabledBoard(value);
        setEnabledBottomPanel(value);
    }

    public void setEnabledGame(Boolean value, Color[] array) {
        setEnabledBoard(value, array);
        setEnabledBottomPanel(value, array);
    }

    private void setEnabledBottomPanel(Boolean value, Color[] array) {
        HashSet<Color> colours = new HashSet<>();
        Collections.addAll(colours, array);
        for (ButtonDesign_2 button : colourButtons) {
            if (colours.contains(button.getBackground()))
                button.setEnabled(value);
        }
    }

    private void setEnabledBoard(Boolean value, Color[] array) {
        HashSet<Color> colours = new HashSet<>();
        Collections.addAll(colours, array);
        for (ButtonDesign[] row : boardButtons) {
            for (ButtonDesign button : row) {
                if (colours.contains(button.getBackground()))
                    button.setEnabled(value);
            }
        }
    }

    private void setEnabledBottomPanel(Boolean value) {
        for (ButtonDesign_2 button : colourButtons)
            button.setEnabled(value);
    }

    private void setEnabledBoard(Boolean value) {
        for (ButtonDesign[] row : boardButtons) {
            for (ButtonDesign button : row)
                button.setEnabled(value);
        }
    }

    public void setEnabledPlayercolours(Boolean value) {
        Color[] playerColours = {boardButtons[boardButtons.length - 1][0].getBackground(),
                boardButtons[0][boardButtons[0].length - 1].getBackground()};
        setEnabledGame(value, playerColours);
    }

    private void setKeyStrokes() {
        for (int i = 1; i <= 9; i++) {
            final int j = i;
            // handle number keys at the top of the keyboard
            this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("pressed " + j), "key" + j);
            this.getActionMap().put("key" + j, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (j <= colourButtons.length) {
                        if (colourButtons[j - 1].isEnabled()) {
                            s1Move(j - 1);
                            s2Move();
                        }
                    }
                }
            });
            // handle number keys from the numeric keypad
            this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("NUMPAD" + j), "numpadKey" + j);
            this.getActionMap().put("numpadKey" + j, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (j <= colourButtons.length) {
                        if (colourButtons[j - 1].isEnabled()) {
                            s1Move(j - 1);
                            s2Move();
                        }
                    }
                }
            });
        }
    }


    public int getStrategy() {
        return strategy;
    }

    public void setStrategy(int strategy) {
        this.strategy = strategy;
    }
}
