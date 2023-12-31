//Yusuf Shehadeh, 7395116
package gui;

import logic.Board;
import logic.Field;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class DisplayPanel extends JPanel {

    private boolean firstStart = false;
    private JPanel eastPanel, westPanel, bottomPanel, topPanel, middlePanel, buttonPanel;
    private JLabel bottomLabel, player1Count, player2Count;
    private Board board;
    private ButtonDesign_2[] colourButtons;
    private ButtonDesign[][] boardButtons;
    private MenuPanel menuPanel;

    private int strategy;

    public DisplayPanel() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.black);
        eastPanel = new JPanel();
        westPanel = new JPanel();
        bottomPanel = new JPanel();
        topPanel = new JPanel();
        middlePanel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                Dimension size = getParent().getSize();
                int largestDimension = Math.min(size.width, size.height);
                return new Dimension(largestDimension, largestDimension);
            }
        };
    }


    public void start(int colours, int rows, int cols) {
        this.setBackground(new Color(238, 238, 238));
        loadBoard(colours, rows, cols);
        if (firstStart) {
            reloadBoard();
            reloadBottomPanel();
            reloadTopPanel();
        } else {
            showBoard();
            loadTopPanel();
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
        middlePanel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                Dimension size = getParent().getSize();
                int largestDimension = Math.min(size.width, size.height);
                return new Dimension(largestDimension, largestDimension);
            }
        };
        boardButtons = new ButtonDesign[board.getaRows()][board.getaColumns()];
        middlePanel.setLayout(new GridLayout(board.getaRows(), board.getaColumns()));
        for (Field[] row : board.getBoard()) {
            for (Field field : row) {
                addButton(field);
            }

        }
    }

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

    public void removeBoard(){
        boardButtons = new ButtonDesign[board.getaRows()][board.getaColumns()];
        middlePanel.removeAll();
        middlePanel.setLayout(new GridLayout(board.getaRows(), board.getaColumns()));
        for (Field[] row : board.getBoard()) {
            for (Field field : row) {
                if (field != null) {
                    ButtonDesign button = new ButtonDesign();
                    button.setEnabled(false);
                    middlePanel.add(button);
                    boardButtons[field.getRow()][field.getCol()] = button;
                }
            }
        }

    }

    public void turnBlack() {
        removeBoard();
        removeBottomPanel();
        removeTopPanel();
    }





    private void addButton(Field field) {
        ButtonDesign button = new ButtonDesign(board.getColors()[field.getColor()]);
        button.addActionListener(e -> {
            s1Move(field.getColor());
            CompletableFuture.runAsync(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException ignored) {
                }
            }).thenRun(this::s2Move);

        });
        middlePanel.add(button);
        boardButtons[field.getRow()][field.getCol()] = button;
    }

    private void checkDone() {
        if (board.isDone()) {
            getMenuPanel().finishGame();
        }
    }


    public void s1Move(int color) {
        setEnabledPlayercolours(true);
        board.makeTurnSinglePlayer(board.getS1(), color);
        reloadBoard();
        reloadBottomPanel();
        setEnabledPlayercolours(false);
        reloadTopPanel();
        setEnabledGame(false);
        checkDone();
    }

    public void s2Move() {
        if (menuPanel.isGameStarted()) {
            setEnabledGame(true);
            setEnabledPlayercolours(true);
            board.makeTurnSinglePlayer(board.getS2(), board.strategy(getStrategy()));
            reloadBoard();

            reloadBottomPanel();
            setEnabledPlayercolours(false);
            reloadTopPanel();
            checkDone();
        }
    }

    public void loadTopPanel(){
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.PAGE_AXIS));

        player1Count = new JLabel("Player 1 Component Size: " + getBoard().getS1().getSize(), SwingConstants.CENTER);
        player1Count.setFont(new Font("SansSerif", Font.BOLD, 24));

        player2Count = new JLabel("Player 2 Component Size: " + getBoard().getS2().getSize(), SwingConstants.CENTER);
        player2Count.setFont(new Font("SansSerif", Font.BOLD, 24));

        topPanel.add(player1Count);
        topPanel.add(player2Count);
    }

    public void reloadTopPanel(){
        player1Count.setText("Player 1 Component Size: " + getBoard().getS1().getSize());
        player2Count.setText("Player 2 Component Size: " + getBoard().getS2().getSize());
    }

    private void removeTopPanel() {
        player1Count.setText("");
        player2Count.setText("");
    }



    public void reloadBottomPanel() {
        colourButtons = new ButtonDesign_2[board.getColors().length];
        buttonPanel.removeAll();
        for (int i = 0, colourButtonsLength = colourButtons.length; i < colourButtonsLength; i++) {
            ButtonDesign_2 button;
            button = new ButtonDesign_2(board.getColors()[i], i + 1);
            buttonPanel.add(button);
            colourButtons[i] = button;
            ButtonDesign_2 finalButton = button;
            button.addActionListener(e -> {
                s1Move(finalButton.getNumber() - 1);
                CompletableFuture.runAsync(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException ignored) {
                    }
                }).thenRun(this::s2Move);
            });
            if (i == board.getColors().length - 1)
                break;
            buttonPanel.add(Box.createRigidArea(new Dimension(1, 0)));
        }
        bottomPanel.add(buttonPanel, 1);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void removeBottomPanel() {
        bottomPanel.add(buttonPanel, 1);
        this.add(bottomPanel, BorderLayout.SOUTH);
        buttonPanel.removeAll();
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
                s1Move(button.getNumber() - 1);
                CompletableFuture.runAsync(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException ignored) {
                    }
                }).thenRun(this::s2Move);
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
                            CompletableFuture.runAsync(() -> {
                                try {
                                    TimeUnit.SECONDS.sleep(1);
                                } catch (InterruptedException ignored) {
                                }
                            }).thenRun(() -> {
                                s2Move();
                            });
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
                            CompletableFuture.runAsync(() -> {
                                try {
                                    TimeUnit.SECONDS.sleep(1);
                                } catch (InterruptedException ignored) {
                                }
                            }).thenRun(() -> {
                                s2Move();
                            });
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

    public boolean isFirstStart() {
        return firstStart;
    }

    public void setFirstStart(boolean firstStart) {
        this.firstStart = firstStart;
    }

    public JPanel getEastPanel() {
        return eastPanel;
    }

    public void setEastPanel(JPanel eastPanel) {
        this.eastPanel = eastPanel;
    }

    public JPanel getWestPanel() {
        return westPanel;
    }

    public void setWestPanel(JPanel westPanel) {
        this.westPanel = westPanel;
    }

    public JPanel getBottomPanel() {
        return bottomPanel;
    }

    public void setBottomPanel(JPanel bottomPanel) {
        this.bottomPanel = bottomPanel;
    }

    public JPanel getTopPanel() {
        return topPanel;
    }

    public void setTopPanel(JPanel topPanel) {
        this.topPanel = topPanel;
    }

    public JPanel getMiddlePanel() {
        return middlePanel;
    }

    public void setMiddlePanel(JPanel middlePanel) {
        this.middlePanel = middlePanel;
    }

    public JPanel getButtonPanel() {
        return buttonPanel;
    }

    public void setButtonPanel(JPanel buttonPanel) {
        this.buttonPanel = buttonPanel;
    }

    public JLabel getBottomLabel() {
        return bottomLabel;
    }

    public void setBottomLabel(JLabel bottomLabel) {
        this.bottomLabel = bottomLabel;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public ButtonDesign_2[] getColourButtons() {
        return colourButtons;
    }

    public void setColourButtons(ButtonDesign_2[] colourButtons) {
        this.colourButtons = colourButtons;
    }

    public ButtonDesign[][] getBoardButtons() {
        return boardButtons;
    }

    public void setBoardButtons(ButtonDesign[][] boardButtons) {
        this.boardButtons = boardButtons;
    }

    public MenuPanel getMenuPanel() {
        return menuPanel;
    }

    public void setMenuPanel(MenuPanel menuPanel) {
        this.menuPanel = menuPanel;
    }

    public JLabel getPlayer1Count() {
        return player1Count;
    }

    public void setPlayer1Count(JLabel player1Count) {
        this.player1Count = player1Count;
    }

    public JLabel getPlayer2Count() {
        return player2Count;
    }

    public void setPlayer2Count(JLabel player2Count) {
        this.player2Count = player2Count;
    }
}
