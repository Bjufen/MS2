package logic;

import java.util.HashSet;
import java.util.Set;

public class Component {
    private Set<Field> component;
    private int color;

    public Component(Field field) {
        component = new HashSet<>();
        component.add(field);
        field.setComponent(this);
        color = field.getColor();
    }
    public Component() {
        component = new HashSet<>();
    }

    public void changeColor(int color){
        for (Field field : component){
            field.setColor(color);
        }
        this.color = color;
    }

    public void mergeOtherComponent(Component component){
        for (Field field : component.getComponent()){
            field.setComponent(this);
        }
        this.getComponent().addAll(component.getComponent());
    }

    public int getSize(){
        return component.size();
    }

    public Set<Field> getComponent() {
        return component;
    }

    public void setComponent(Set<Field> component) {
        this.component = component;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
