//Yusuf Shehadeh, 7395116
package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

public class MenuPanel extends JPanel {

    private DisplayPanel displayPanel;

    private JPanel timerPanel, helpPanel, playerStartPanel, boardSpecPanel, strategyPanel, startPlayPanel;
    private JButton helpButton, startStop, playPause;
    private JRadioButton player1, player2, strategy1, strategy2, strategy3;
    private ButtonGroup players, strategies;
    private JLabel playerTitle, strategyTitle, rowsTitle, colsTitle, coloursTitle, timerTitle;
    private JComboBox<Integer> rows, cols, colours;
    private Integer[] val4To9 = {4, 5, 6, 7, 8, 9};
    private Integer[] val3To10 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private String helpMessage = "";
    private Timer timer;
    private int elapsedTime = 0;

    public MenuPanel(DisplayPanel displayPanel) {
        this.displayPanel = displayPanel;
        this.setPreferredSize(new Dimension(200, getHeight()));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        addTimerPanel();
        addHelpPanel();
        addPlayerStartPanel();
        addBoardSpecPanel();
        addStrategyPanel();
        addStartPlayPanel();

    }

    private void addTimerPanel() {
        timerPanel = new JPanel();

        timerTitle = new JLabel("00:00:00");
        timerTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        timerPanel.add(timerTitle);

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                elapsedTime++;
                int hour = elapsedTime / 3600;
                int minute = (elapsedTime / 60) % 60;
                int second = elapsedTime % 60;
                timerTitle.setText(String.format("%02d:%02d:%02d", hour, minute, second));
            }
        });

        this.add(timerPanel);
    }

    private void addHelpPanel() {
        helpPanel = new JPanel();
        helpButton = new JButton("Help");
        helpButton.addActionListener(e -> JOptionPane.showMessageDialog(this.getParent(), helpMessage));
        helpPanel.add(helpButton);
        this.add(helpPanel);
    }

    private void addPlayerStartPanel() {
        playerStartPanel = new JPanel();
        playerStartPanel.setLayout(new BoxLayout(playerStartPanel, BoxLayout.Y_AXIS));
        playerTitle = new JLabel("Starting Player");
        player1 = new JRadioButton("Player 1", true);
        player2 = new JRadioButton("Player 2");
        players = new ButtonGroup();
        players.add(player1);
        players.add(player2);
        playerStartPanel.add(playerTitle);
        playerStartPanel.add(player1);
        playerStartPanel.add(player2);
        this.add(playerStartPanel);
    }

    private void addBoardSpecPanel() {
        boardSpecPanel = new JPanel();
        boardSpecPanel.setLayout(new BoxLayout(boardSpecPanel, BoxLayout.Y_AXIS));
        coloursTitle = new JLabel("Amount of Colours");
        colours = new JComboBox<Integer>(val4To9);
        colours.setSelectedItem(5);
        rowsTitle = new JLabel("Amount of Rows");
        rows = new JComboBox<Integer>(val3To10);
        rows.setSelectedItem(6);
        colsTitle = new JLabel("Amount of Columns");
        cols = new JComboBox<Integer>(val3To10);
        cols.setSelectedItem(6);
        boardSpecPanel.add(coloursTitle);
        boardSpecPanel.add(colours);
        boardSpecPanel.add(rowsTitle);
        boardSpecPanel.add(rows);
        boardSpecPanel.add(colsTitle);
        boardSpecPanel.add(cols);
        this.add(boardSpecPanel);
    }

    private void addStrategyPanel() {
        strategyPanel = new JPanel();
        strategyPanel.setLayout(new BoxLayout(strategyPanel, BoxLayout.Y_AXIS));
        strategyTitle = new JLabel("Computer Strategy");
        strategy1 = new JRadioButton("Strategy 1", true);
        strategy2 = new JRadioButton("Strategy 2");
        strategy3 = new JRadioButton("Strategy 3");
        strategies = new ButtonGroup();
        strategies.add(strategy1);
        strategies.add(strategy2);
        strategies.add(strategy3);
        strategyPanel.add(strategyTitle);
        strategyPanel.add(strategy1);
        strategyPanel.add(strategy2);
        strategyPanel.add(strategy3);
        this.add(strategyPanel);
    }

    private void addStartPlayPanel() {
        startPlayPanel = new JPanel();
        startPlayPanel.setLayout(new BoxLayout(startPlayPanel, BoxLayout.Y_AXIS));
        startStop = new JButton("Start");
        startStop.addActionListener(new ActionListener() {
            @SuppressWarnings("DataFlowIssue")
            @Override
            public void actionPerformed(ActionEvent e) {
                if (startStop.getText().equals("Start")) {
                    startStop.setText("Stop");
                    //loadBoard()
                    playPause.setEnabled(true);
                    displayPanel.start((int) colours.getSelectedItem(), (int) rows.getSelectedItem(), (int) cols.getSelectedItem());
                    System.out.println("Starting Player: " +getStarter() +
                            "\n#Colours: " + colours.getSelectedItem() +
                            "\n#Rows: " + rows.getSelectedItem() +
                            "\n#Columns: " + cols.getSelectedItem() +
                            "\nChosen Strategy: " + getStrategy());
                } else {
                    if (!playPause.getText().equals("Play"))
                        playPause.setText("Play");
                    startStop.setText("Start");
                    timer.stop();
                    elapsedTime = 0;
                    timerTitle.setText("00:00:00");
                    //removeGame()
                    playPause.setEnabled(false);
                    enableAllOptions();
                }
            }
        });

        playPause = new JButton("Play");
        playPause.setEnabled(false);
        playPause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (playPause.getText().equals("Play")) {
                    playPause.setText("Pause");
                    timer.start();
                    disableAllOptions();
                    //startGame()
                } else {
                    playPause.setText("Play");
                    timer.stop();
                    enableAllOptions();
                    //pauseGame
                }
            }
        });
        startPlayPanel.add(startStop);
        startPlayPanel.add(playPause);
        this.add(startPlayPanel);
    }


    private int getStarter() {
        ButtonModel selectedModel = players.getSelection();
        return selectedModel == player1.getModel() ? 1 : 2;
    }

    private int getStrategy(){
        ButtonModel selectedModel = strategies.getSelection();
        if (selectedModel == strategy1.getModel())
            return 1;
        else return selectedModel == strategy2.getModel() ? 2 : 3;
    }

    private void disableAllOptions() {
        disableRadioButtons(players, playerTitle);
        disableRadioButtons(strategies, strategyTitle);
        disableDropdown(colours, coloursTitle);
        disableDropdown(rows, rowsTitle);
        disableDropdown(cols, colsTitle);

    }

    private void enableAllOptions() {
        enableRadioButtons(players, playerTitle);
        enableRadioButtons(strategies, strategyTitle);
        enableDropdown(colours, coloursTitle);
        enableDropdown(rows, rowsTitle);
        enableDropdown(cols, colsTitle);
    }

    public void disableDropdown(JComboBox<Integer> dropdown, JLabel label) {
        dropdown.setEnabled(false);
        label.setEnabled(false);
    }

    public void enableDropdown(JComboBox<Integer> dropdown, JLabel label) {
        dropdown.setEnabled(true);
        label.setEnabled(true);

    }

    private void disableRadioButtons(ButtonGroup buttonGroup, JLabel title) {
        Enumeration<AbstractButton> buttons = buttonGroup.getElements();
        while (buttons.hasMoreElements()) {
            AbstractButton button = buttons.nextElement();
            button.setEnabled(false);
        }
        title.setEnabled(false);
    }

    private void enableRadioButtons(ButtonGroup buttonGroup, JLabel title) {
        Enumeration<AbstractButton> buttons = buttonGroup.getElements();
        while (buttons.hasMoreElements()) {
            AbstractButton button = buttons.nextElement();
            button.setEnabled(true);
        }
        title.setEnabled(true);
    }

    public JPanel getTimerPanel() {
        return timerPanel;
    }

    public void setTimerPanel(JPanel timerPanel) {
        this.timerPanel = timerPanel;
    }

    public JPanel getHelpPanel() {
        return helpPanel;
    }

    public void setHelpPanel(JPanel helpPanel) {
        this.helpPanel = helpPanel;
    }

    public JPanel getPlayerStartPanel() {
        return playerStartPanel;
    }

    public void setPlayerStartPanel(JPanel playerStartPanel) {
        this.playerStartPanel = playerStartPanel;
    }

    public JPanel getBoardSpecPanel() {
        return boardSpecPanel;
    }

    public void setBoardSpecPanel(JPanel boardSpecPanel) {
        this.boardSpecPanel = boardSpecPanel;
    }

    public JPanel getStrategyPanel() {
        return strategyPanel;
    }

    public void setStrategyPanel(JPanel strategyPanel) {
        this.strategyPanel = strategyPanel;
    }

    public JPanel getStartPlayPanel() {
        return startPlayPanel;
    }

    public void setStartPlayPanel(JPanel startPlayPanel) {
        this.startPlayPanel = startPlayPanel;
    }

    public JButton getHelpButton() {
        return helpButton;
    }

    public void setHelpButton(JButton helpButton) {
        this.helpButton = helpButton;
    }

    public JButton getStartStop() {
        return startStop;
    }

    public void setStartStop(JButton startStop) {
        this.startStop = startStop;
    }

    public JButton getPlayPause() {
        return playPause;
    }

    public void setPlayPause(JButton playPause) {
        this.playPause = playPause;
    }

    public JRadioButton getPlayer1() {
        return player1;
    }

    public void setPlayer1(JRadioButton player1) {
        this.player1 = player1;
    }

    public JRadioButton getPlayer2() {
        return player2;
    }

    public void setPlayer2(JRadioButton player2) {
        this.player2 = player2;
    }

    public JRadioButton getStrategy1() {
        return strategy1;
    }

    public void setStrategy1(JRadioButton strategy1) {
        this.strategy1 = strategy1;
    }

    public JRadioButton getStrategy2() {
        return strategy2;
    }

    public void setStrategy2(JRadioButton strategy2) {
        this.strategy2 = strategy2;
    }

    public JRadioButton getStrategy3() {
        return strategy3;
    }

    public void setStrategy3(JRadioButton strategy3) {
        this.strategy3 = strategy3;
    }

    public ButtonGroup getPlayers() {
        return players;
    }

    public void setPlayers(ButtonGroup players) {
        this.players = players;
    }

    public ButtonGroup getStrategies() {
        return strategies;
    }

    public void setStrategies(ButtonGroup strategies) {
        this.strategies = strategies;
    }

    public JLabel getPlayerTitle() {
        return playerTitle;
    }

    public void setPlayerTitle(JLabel playerTitle) {
        this.playerTitle = playerTitle;
    }

    public JLabel getStrategyTitle() {
        return strategyTitle;
    }

    public void setStrategyTitle(JLabel strategyTitle) {
        this.strategyTitle = strategyTitle;
    }

    public JLabel getRowsTitle() {
        return rowsTitle;
    }

    public void setRowsTitle(JLabel rowsTitle) {
        this.rowsTitle = rowsTitle;
    }

    public JLabel getColsTitle() {
        return colsTitle;
    }

    public void setColsTitle(JLabel colsTitle) {
        this.colsTitle = colsTitle;
    }

    public JLabel getColoursTitle() {
        return coloursTitle;
    }

    public void setColoursTitle(JLabel coloursTitle) {
        this.coloursTitle = coloursTitle;
    }

    public JLabel getTimerTitle() {
        return timerTitle;
    }

    public void setTimerTitle(JLabel timerTitle) {
        this.timerTitle = timerTitle;
    }

    public JComboBox<Integer> getRows() {
        return rows;
    }

    public void setRows(JComboBox<Integer> rows) {
        this.rows = rows;
    }

    public JComboBox<Integer> getCols() {
        return cols;
    }

    public void setCols(JComboBox<Integer> cols) {
        this.cols = cols;
    }

    public JComboBox<Integer> getColours() {
        return colours;
    }

    public void setColours(JComboBox<Integer> colours) {
        this.colours = colours;
    }

    public Integer[] getVal4To9() {
        return val4To9;
    }

    public void setVal4To9(Integer[] val4To9) {
        this.val4To9 = val4To9;
    }

    public Integer[] getVal3To10() {
        return val3To10;
    }

    public void setVal3To10(Integer[] val3To10) {
        this.val3To10 = val3To10;
    }

    public String getHelpMessage() {
        return helpMessage;
    }

    public void setHelpMessage(String helpMessage) {
        this.helpMessage = helpMessage;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public int getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(int elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public DisplayPanel getDisplayPanel() {
        return displayPanel;
    }

    public void setDisplayPanel(DisplayPanel displayPanel) {
        this.displayPanel = displayPanel;
    }
}