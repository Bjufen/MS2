package logic;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SubBoard extends Board {

    int[] colours;

    public SubBoard(int aColors, int aRows, int aColumns) {
        super(aColors, aRows, aColumns);
        setS2(new Component());
    }

    public SubBoard(Field[][] initBoard) {
        super(initBoard);
        setS2(new Component());
    }

    public SubBoard(Field[][] initBoard, int[] colours) {
        super(initBoard, colours);
        setS2(new Component());
        this.colours = colours;
    }


    @Override
    public void initList() {
        setS1(new Component(getBoard()[getBoard().length - 1][0]));
    }

    @Override
    public void setPlayerComponents() {
        Iterator<Component> componentIterator = getComponents().iterator();

        while (componentIterator.hasNext()) {
            Component component = componentIterator.next();

            if (component.getComponent().contains(getBoard()[getBoard().length - 1][0])) {
                setS1(component);
                componentIterator.remove();  // Safe removal
                for (Field field : getS1().getComponent()) {
                    field.setComponent(getS1());
                }
            }
        }
    }

    @Override
    public void setStartComponents() {
        setS1(new Component(getBoard()[getBoard().length - 1][0]));
    }

    @Override
    public void makeTurnSinglePlayer(Component player, int color) {
        player.changeColor(color);
        mergeComponents(player);
    }


    @Override
    public int[] copyExcluding(int[] original, int excludeValue1, int excludeValue2) {
        int[] copy = new int[original.length - 2];  // Create a new array that's two elements shorter
        int j = 0;
        for (int i = 0; i < original.length; i++) {
            // Skip over the values that we're excluding
            if (original[i] == excludeValue1 || original[i] == excludeValue2) {
                continue;
            }
            // Copy over the element
            copy[j] = original[i];
            j++;
        }
        return copy;
    }



    public int strategies(int strategy, Component player) {
        int[] availableColours = copyExcluding(getColorValues(), getBoard()[getBoard().length - 1][0].getColor(), getBoard()[0][getBoard()[0].length - 1].getColor());
        int bestColour = availableColours[0];
        for (int i = 1; i < availableColours.length; i++) {
            bestColour = switch (strategy) {
                case 1 -> strategies1(bestColour, availableColours[i], player);
                case 2 -> strategies2(bestColour, availableColours[i], player);
                default -> strategies3(bestColour, availableColours[i], player);
            };
        }
        return bestColour;
    }


    public int strategies1(int bestColor, int newColor, Component player) {
        int bestC = getOccurences(player, bestColor);
        int newC = getOccurences(player, newColor);
        if (bestC == newC) {
            return Math.min(bestColor, newColor);
        }
        if (Math.min(bestC, newC) == bestC)
            return bestColor;
        else return newColor;
    }


    public int strategies2(int bestColor, int newColor, Component player) {
        int bestC = getOccurences(player, bestColor);
        int newC = getOccurences(player, newColor);
        if (bestC == newC) {
            return Math.min(bestColor, newColor);
        }
        if (Math.max(bestC, newC) == bestC)
            return bestColor;
        else return newColor;
    }


    public int strategies3(int bestColor, int newColor, Component player) {
        int bestC = getOccurences(player, bestColor);
        int newC = getOccurences(player, newColor);
        if (bestC == newC) {
            return Math.min(bestColor, newColor);
        }
        if (Math.max(bestC, newC) == bestC)
            return bestColor;
        else return newColor;
    }

    @Override
    public int[] getColorValues() {
        return colours;
    }

    /**
     * @param player
     * @param color
     * @return
     */
    @Override
/*    public int getOccurences(Component player, int color) {
        Set<Component> playerNeighbours = new HashSet<>(getAllNeighboringComponents(player));
        int[] occurrence = new int[getaColors()];
        System.out.println(java.util.Arrays.toString(occurrence));
        for (Component neighbour : playerNeighbours){

            occurrence[neighbour.getColor()]++;
            System.out.println(java.util.Arrays.toString(occurrence));
        }
        return occurrence[color];
    }*/

    public int getOccurences(Component player, int color) {
        Set<Component> playerNeighbours = new HashSet<>(getAllNeighboringComponents(player));
        int occurrence = 0;
        for (Component neighbour : playerNeighbours) {
            for (Field field : neighbour.getComponent()) {
                int fieldColor = field.getColor();
                if (fieldColor == color)
                    occurrence++;
            }
        }
        return occurrence;
    }

    @Override
    public void setMidGameComponents() {
        Set<Field> usedFields = new HashSet<>();
        for (Field[] row : getBoard()) {
            for (Field field : row) {
                if (usedFields.contains(field))
                    continue;
                Component component = new Component(field);
                component.setColor(component.getColor() + 1);
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
                getComponents().add(component);
            }
        }
        setPlayerComponents();
    }

    @Override
    public Component getS1() {
        return super.getS1();
    }

    @Override
    public void setS1(Component s1) {
        super.setS1(s1);
    }

    @Override
    public Component getS2() {
        return super.getS2();
    }

    @Override
    public void setS2(Component s2) {
        super.setS2(s2);
    }
}
