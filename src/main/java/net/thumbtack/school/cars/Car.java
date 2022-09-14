package net.thumbtack.school.cars;

import net.thumbtack.school.colors.Colored;
import net.thumbtack.school.colors.Color;
import net.thumbtack.school.colors.ColorException;

public class Car implements Colored {

    private String model;
    private int weight;
    private int maxSpeed;
    private Color color;

    public Car(String model, int weight, int maxSpeed, Color color) throws ColorException {
        this.model = model;
        this.weight = weight;
        this.maxSpeed = maxSpeed;
        setColor(color);
    }

    public Car(String model, int weight, int maxSpeed, String color) throws ColorException {
        this.model = model;
        this.weight = weight;
        this.maxSpeed = maxSpeed;
        setColor(Color.colorFromString(color));
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    @Override
    public void setColor(Color color) throws ColorException {
        Color.colorEqualsNull(color);
        this.color = color;
    }

    @Override
    public void setColor(String colorString) throws ColorException {
        this.color = Color.colorFromString(colorString);
    }

    @Override
    public Color getColor() {
        return color;
    }
}
