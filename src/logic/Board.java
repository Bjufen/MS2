package logic;


import java.awt.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Board {

    private Field[][] board;
    private int aColors, aRows, aColumns;
    private Color[] colors;

    private Component s1, s2;
    private Set<Component> components;
    private Set<Color> colourSet;
    private boolean s1Turn;


    public Board(int aColors, int aRows, int aColumns) {
        this.aColors = aColors;
        this.aRows = aRows;
        this.aColumns = aColumns;
        setColourSet();
        setAmountofColors(aColors);
        createBoard();
        components = new HashSet<>();
        setAllComponents();

    }

    public Board(Field[][] initBoard) {
        board = initBoard;
        setColourSet();
        this.aRows = initBoard.length;
        this.aColumns = initBoard[0].length;
        this.aColors = getAllColorsBoard().length;
        setAmountofColors(aColors);
        components = new HashSet<>();
        setMidGameComponents();
        setaColors(colors.length);
        setaRows(initBoard.length);
        setaColumns(initBoard[0].length);
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
                for (Field field1 : component.getComponent()){
                    field1.setComponent(component);
                }
                components.add(component);
            }
        }
        setPlayerComponents();
    }


/*    private void setPlayerComponents() {
        for (Component component : components) {
            if (component.getComponent().contains(getBoard()[getBoard().length - 1][0])) {
                s1 = component;
                components.remove(component);
                for(Field field : s1.getComponent()){
                    field.setComponent(s1);
                }
                continue;
            }
            if (component.getComponent().contains(getBoard()[0][getBoard()[0].length - 1])) {
                s2 = component;
                components.remove(component);
                for(Field field : s2.getComponent()){
                    field.setComponent(s2);
                }
                continue;
            }
        }
    }*/
private void setPlayerComponents() {
    Iterator<Component> componentIterator = components.iterator();

    while (componentIterator.hasNext()) {
        Component component = componentIterator.next();

        if (component.getComponent().contains(getBoard()[getBoard().length - 1][0])) {
            s1 = component;
            componentIterator.remove();  // Safe removal
            for (Field field : s1.getComponent()) {
                field.setComponent(s1);
            }
            continue;
        }

        if (component.getComponent().contains(getBoard()[0][getBoard()[0].length - 1])) {
            s2 = component;
            componentIterator.remove();  // Safe removal
            for (Field field : s2.getComponent()) {
                field.setComponent(s2);
            }
            continue;
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

    /*public int predictSizeChange(Component player, int color) {
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
    }*/

    private Field[][] getCopyOfBoard(){
        Field[][] copy = new Field[this.getBoard().length][];
        for (int i = 0; i < this.getBoard().length; i++) {
            copy[i] = Arrays.copyOf(this.getBoard()[i], this.getBoard()[i].length);
        }
        return copy;
    }
    public int predictSizeChangeS1(int color) {
        Board copy = new Board(getCopyOfBoard());
        int originalSize = getS1().getSize();
        int newSize;
        copy.makeTurnSinglePlayer(getS1(), color);
        newSize = copy.getS1().getSize();
        return newSize - originalSize;
    }
    public int predictSizeChangeS2(int color) {
        Board copy = new Board(getCopyOfBoard());
        int originalSize = getS2().getSize();
        int newSize;
        copy.makeTurnSinglePlayer(getS2(), color);
        newSize = copy.getS2().getSize();
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
        Color[] temp = new Color[colourSet.size()];
        colors = new Color[amountofColors];
        Set<Color> usedColours = new HashSet<>();
        Random random = new Random();
        int randomNumber = random.nextInt(colourSet.size());
        Iterator<Color> iterator = colourSet.iterator();
        for (int i = 0; i < temp.length; i++) {
            Color selectedColour = iterator.next();
            while (usedColours.contains(selectedColour)){
                selectedColour = iterator.next();
            }
            temp[i] = selectedColour;
            usedColours.add(selectedColour);
        }
        shuffle(temp);
        colors = java.util.Arrays.copyOfRange(temp, 0, amountofColors);
    }

    private void setColourSet(){
        colourSet = new HashSet<>();
        colourSet.add(Color.GREEN);
        colourSet.add(Color.RED);
        colourSet.add(Color.MAGENTA);
        colourSet.add(Color.BLUE);
        colourSet.add(Color.YELLOW);
        colourSet.add(Color.CYAN);
        colourSet.add(Color.PINK);
        colourSet.add(Color.ORANGE);
        colourSet.add(new Color(160,32,240));
    }

    private void shuffle(Color[] array){
        Random rnd = ThreadLocalRandom.current();
        for (int i = array.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            Color a = array[index];
            array[index] = array[i];
            array[i] = a;
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
        int counter = 0;
        for (int i = 0; i < getaRows(); i++) {
            for (int j = 0; j < getaColumns(); j++) {
                if(counter < aColors) {
                    board[i][j] = new Field(i, j, counter);
                    counter++;
                    continue;
                }
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
        if(row == board.length - 1 && col == 0){
            return value != board[0][board[0].length - 1].getColor();
        }
        return true;
    }


    public int strategy(int strategy) {
        int[] availableColours = copyExcluding(this.getAllColorsBoard(),getS1().getColor(), getS2().getColor());
        int bestColour = availableColours[0];
        for (int i = 1; i < availableColours.length; i++) {
            System.out.println("Best colour before for loop: " + bestColour);
            System.out.println("New colour before for loop: " + availableColours[i]);
            bestColour = switch (strategy) {
                case 1 -> strategy1(bestColour, availableColours[i]);
                case 2 -> strategy2(bestColour, availableColours[i]);
                default -> strategy3(bestColour, availableColours[i]);
            };
            System.out.println("BestColour after for loop: " + bestColour);
        }
        System.out.println("BestColour: " + bestColour);
        return bestColour;
    }

    private int strategy1(int bestColor, int newColor) {
        int bestC = predictSizeChangeS2(bestColor);
        int newC = predictSizeChangeS2(newColor);
        System.out.println("SizeChange(S2) with bestColor: " + bestC);
        System.out.println("SizeChange(S2) with newColor: " + newC);
        if (bestC == newC) {
            return Math.min(bestColor, newColor);
        }
        if (Math.min(bestC, newC) == bestC)
            return bestColor;
        else return newColor;
    }

    private int strategy2(int bestColor, int newColor) {
        int bestC = predictSizeChangeS2(bestColor);
        int newC = predictSizeChangeS2(newColor);
        System.out.println("SizeChange(S2) with bestColor: " + bestC);
        System.out.println("SizeChange(S2) with newColor: " + newC);
        if (bestC == newC) {
            return Math.min(bestColor, newColor);
        }
        if (Math.max(bestC, newC) == bestC)
            return bestColor;
        else return newColor;
    }

    private int strategy3(int bestColor, int newColor) {
        int bestC = predictSizeChangeS1(bestColor);
        int newC = predictSizeChangeS1(newColor);
        System.out.println("SizeChange(S1) with bestColor: " + bestC);
        System.out.println("SizeChange(S1) with newColor: " + newC);
        if (bestC == newC) {
            return Math.min(bestColor, newColor);
        }
        if (Math.max(bestC, newC) == bestC)
            return bestColor;
        else return newColor;
    }

   /* private int strategyHelper(int bestColor, int newColor, Component s) {
        int bestC = predictSizeChange(s, bestColor);
        int newC = predictSizeChange(s, newColor);
        if (bestC == newC) {
            return Math.min(bestColor, newColor);
        }
        return Math.max(bestC, newC);
    }*/

    public int[] copyExcluding(int[] original, int excludeValue1, int excludeValue2) {
        int[] copy = new int[original.length - 2];  // Create a new array that's two elements shorter
            System.out.println("Array i: " + java.util.Arrays.toString(original));
            System.out.println("Array j: " + java.util.Arrays.toString(copy));
        int j = 0;
        for (int i = 0; i < original.length; i++) {
            System.out.println("Start of for loop" +
                    "\ni: " + i +
                    "\nj: " + j +
                    "\nOriginal value of index i = " + i + ": " + original[i] +
                    "\n");
            System.out.println("Array i: " + java.util.Arrays.toString(original));
            System.out.println("Array j: " + java.util.Arrays.toString(copy) + "\n");
            // Skip over the values that we're excluding
            if (original[i] == excludeValue1 || original[i] == excludeValue2) {
                System.out.println("if clause true\n");
                System.out.println("Array i: " + java.util.Arrays.toString(original));
                System.out.println("Array j: " + java.util.Arrays.toString(copy) + "\n");
                continue;
            }
            // Copy over the element
            System.out.println("Element to be added to copy: " + original[i]);
            copy[j] = original[i];
            j++;
            System.out.println("if clause false" +
                    "\nCopy value at index j = " + (j - 1) + ": " +  copy[j-1] + "\n");
            System.out.println("Array i: " + java.util.Arrays.toString(original));
            System.out.println("Array j: " + java.util.Arrays.toString(copy) + "\n");
        }
         System.out.println("Copy:" + java.util.Arrays.toString(copy));
        return copy;
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
