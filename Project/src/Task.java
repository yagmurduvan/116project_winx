package Project.src;

import java.util.ArrayList;

public class Task {
    private String type;
    private double size;
    private int speed;
    private double minplus;
    public Task(String type, double size) {
        this.type = type;
        this.size = size;
        this.speed=getSpeed();
        this.minplus=getMinplus();
    }
    public double getMinplus() {
        return minplus;
    }
    public void setMinplus(double minplus) {
        this.minplus = minplus;
    }
    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public Task (String type) {
        this.type=type;
        this.size=0;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public double getSize() {
        return size;
    }
    public void setSize(double size) {
        this.size = size;
    }
    @Override
    public String toString() {
        return "Task [" + (type != null ? "type=" + type + ", " : "") + "size=" + size + "]";
    }
}
