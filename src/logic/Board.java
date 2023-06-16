package logic;


import java.awt.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class Board {

    private Field[][] board;
    private int aColors, aRows, aColumns;
    private Color[] colors;

    private Component s1, s2;
    private Set<Component> components;
    private boolean s1Turn;


    public Board(int aColors, int aRows, int aColumns) {
        this.aColors = aColors;
        this.aRows = aRows;
        this.aColumns = aColumns;
        setAmountofColors(aColors);
        createBoard();
        components = new HashSet<>();
        setAllComponents();

    }

    public Board(Field[][] initBoard) {
        setAmountofColors(getAllColorsBoard().length);
        components = new HashSet<>();
        setMidGameComponents();
    }


    private void setAllComponents() {
        for (int i = 0; i < getaRows(); i++) {
            for (int j = 0; j < getaColumns(); j++) {
                if ((i == getBoard().length - 1 && j == 0) || (i == 0 && j == getBoard()[0].length - 1))
                    continue;
                components.add(new Component(getBoard()[i][j]));
            }
        }
        setStartComponents();
    }

    //DANGER(Maybe)
    private void setMidGameComponents() {
        Set<Field> usedFields = new HashSet<>();
        for (Field[] row : getBoard()) {
            for (Field field : row) {
                if (usedFields.contains(field))
                    continue;
                Component component = new Component(field);
                usedFields.add(field);
                Set<Field> fields = new HashSet<>(component.getComponent());
                Set<Field> newFields = new HashSet<>();
                for (Iterator<Field> iterator = fields.iterator(); iterator.hasNext(); ) {
                    Field field1 = iterator.next();
                    for (Field neighbour : getAllNeighbors(field1)) {
                        if ((neighbour.getColor() != component.getColor()) || (usedFields.contains(neighbour)))
                            continue;
                        newFields.add(neighbour);
                        usedFields.add(neighbour);
                        neighbour.setComponent(component);
                    }
                    fields.addAll(newFields);
                    newFields.clear();
                }
                fields.addAll(newFields);
                component.setComponent(fields);
                components.add(component);
            }
        }
        setPlayerComponents();
    }


    private void setPlayerComponents() {
        for (Component component : components) {
            if (component.getComponent().contains(getBoard()[getBoard().length - 1][0])) {
                s1 = component;
                components.remove(component);
            }
            if (component.getComponent().contains(getBoard()[0][getBoard()[0].length - 1])) {
                s2 = component;
                components.remove(component);
            }
        }
    }

    private void setStartComponents() {
        s1 = new Component(getBoard()[getBoard().length - 1][0]);
        s2 = new Component(getBoard()[0][getBoard()[0].length - 1]);
    }


    public void makeTurnSinglePlayer(Component player, int color) {
        player.changeColor(color);
        mergeComponents(player);
    }

    public void makeTurn2Player(Component player, int color) {
        makeTurnSinglePlayer(player, color);
        changePlayer();
    }


    public void changePlayer() {
        this.setS1Turn(!isS1Turn());
    }

    public int predictSizeChange(Component player, int color) {
        Board copy = new Board(this.getBoard());
        int originalSize = player.getSize();
        int newSize;
        if (player == this.getS1()) {
            copy.makeTurnSinglePlayer(player, color);
            newSize = copy.getS1().getSize();
        } else {
            copy.makeTurnSinglePlayer(player, color);
            newSize = copy.getS2().getSize();
        }
        return newSize - originalSize;
    }

    public Set<Component> getAllNeighboringComponents(Component component) {
        Set<Component> neighbouringComponents = new HashSet<>();
        for (Field field : component.getComponent()) {
            for (Field field1 : getAllNeighbors(field)) {
                if (field1.getComponent() != component)
                    neighbouringComponents.add(field1.getComponent());
            }
        }
        return neighbouringComponents;
    }

    private Set<Field> getAllNeighbors(Field field) {
        Set<Field> neighbors = new HashSet<>();
        if (field.getRow() > 0)
            neighbors.add(getBoard()[field.getRow() - 1][field.getCol()]);
        if (field.getRow() < getBoard().length - 1)
            neighbors.add(getBoard()[field.getRow() + 1][field.getCol()]);
        if (field.getCol() > 0)
            neighbors.add(getBoard()[field.getRow()][field.getCol() - 1]);
        if (field.getCol() < getBoard()[0].length - 1)
            neighbors.add(getBoard()[field.getRow()][field.getCol() + 1]);
        return neighbors;
    }

    /*
    State: Player just changed the color of its component and now all neighbouring
    components, with the same color as the player component are added to the player
    component
     */
    public void mergeComponents(Component component) {
        for (Component neighbour : getAllNeighboringComponents(component)) {
            if (neighbour.getColor() != component.getColor())
                continue;
            components.remove(neighbour);
            component.mergeOtherComponent(neighbour);
        }
    }

    public void setAmountofColors(int amountofColors) {
        colors = new Color[amountofColors];
        for (int i = 0; i < colors.length; i++) {
            Random random = new Random();
            int red = random.nextInt(256);
            int green = random.nextInt(256);
            int blue = random.nextInt(256);
            colors[i] = new Color(red, green, blue);
        }
    }

    public int[] getAllColorsBoard() {
        Set<Integer> uniqueColors = new HashSet<>();
        for (Field[] row : board) {
            for (Field cell : row) {
                uniqueColors.add(cell.getColor());
            }
        }
        int[] colors = new int[uniqueColors.size()];
        int index = 0;
        for (int value : uniqueColors) {
            colors[index++] = value;
        }
        return colors;
    }

    private void createBoard() {
        board = new Field[getaRows()][getaColumns()];
        fillBoard();
    }

    //Not sure if all colours have to be present with this method but assuming it because it is supposed to
    private void fillBoard() {
        Random random = new Random();
        for (int i = 0; i < getaRows(); i++) {
            for (int j = 0; j < getaColumns(); j++) {
                board[i][j] = new Field(i, j, getRandomDistinctColor(i, j, random));
            }
        }
    }

    private int getRandomDistinctColor(int row, int col, Random random) {
        int randomColor;
        do {
            randomColor = random.nextInt(getColors().length);
        } while (!isDistinctValue(row, col, randomColor));
        return randomColor;
    }

    private boolean isDistinctValue(int row, int col, int value) {
        try {
            if (row > 0 && board[row - 1][col].getColor() == value) {
                return false;
            }
        } catch (NullPointerException ignored) {
        }
        try {
            if (row < board.length - 1 && board[row + 1][col].getColor() == value) {
                return false;
            }
        } catch (NullPointerException ignored) {
        }
        try {
            if (col > 0 && board[row][col - 1].getColor() == value) {
                return false;
            }
        } catch (NullPointerException ignored) {
        }
        try {
            if (col < board[row].length - 1 && board[row][col + 1].getColor() == value)
                return false;
        } catch (NullPointerException ignored) {
        }
        return true;
    }


    public int strategy(int strategy) {
        int bestColour = this.getAllColorsBoard()[0];
        int[] allColorsBoard = this.getAllColorsBoard();
        for (int i = 1; i < allColorsBoard.length; i++) {
            bestColour = switch (strategy) {
                case 1 -> strategy1(bestColour, allColorsBoard[i]);
                case 2 -> strategy2(bestColour, allColorsBoard[i]);
                default -> strategy3(bestColour, allColorsBoard[i]);
            };
        }
        return bestColour;
    }

    private int strategy1(int bestColor, int newColor) {
        int bestC = predictSizeChange(s2, bestColor);
        int newC = predictSizeChange(s2, newColor);
        if (bestC == newC) {
            return Math.min(bestColor, newColor);
        }
        return Math.min(bestC, newC);
    }

    private int strategy2(int bestColor, int newColor) {
        return strategyHelper(bestColor, newColor, this.getS1());
    }

    private int strategy3(int bestColor, int newColor) {
        return strategyHelper(bestColor, newColor, this.getS1());
    }

    private int strategyHelper(int bestColor, int newColor, Component s) {
        int bestC = predictSizeChange(s, bestColor);
        int newC = predictSizeChange(s, newColor);
        if (bestC == newC) {
            return Math.min(bestColor, newColor);
        }
        return Math.max(bestC, newC);
    }


    private void updateBoard() {
        fillBoard();
    }

    public int getaColors() {
        return aColors;
    }

    public void setaColors(int aColors) {
        this.aColors = aColors;
    }

    public int getaRows() {
        return aRows;
    }

    public void setaRows(int aRows) {
        this.aRows = aRows;
    }

    public int getaColumns() {
        return aColumns;
    }

    public void setaColumns(int aColumns) {
        this.aColumns = aColumns;
    }

    public Color[] getColors() {
        return colors;
    }

    public Color[] getNewColors(int amountColors) {
        setAmountofColors(amountColors);
        updateBoard();
        return colors;
    }

    public void setColors(Color[] colors) {
        this.colors = colors;
    }

    public Field[][] getBoard() {
        return board;
    }

    public void setBoard(Field[][] board) {
        this.board = board;
    }

    public Component getS1() {
        return s1;
    }

    public void setS1(Component s1) {
        this.s1 = s1;
    }

    public Component getS2() {
        return s2;
    }

    public void setS2(Component s2) {
        this.s2 = s2;
    }

    public Set<Component> getComponents() {
        return components;
    }

    public void setComponents(Set<Component> components) {
        this.components = components;
    }

    public boolean isS1Turn() {
        return s1Turn;
    }

    public void setS1Turn(boolean s1Turn) {
        this.s1Turn = s1Turn;
    }
}
