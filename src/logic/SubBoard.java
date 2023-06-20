package logic;

import java.util.Iterator;

public class SubBoard extends Board{
    public SubBoard(int aColors, int aRows, int aColumns) {
        super(aColors, aRows, aColumns);
    }

    public SubBoard(Field[][] initBoard) {
        super(initBoard);
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
}
