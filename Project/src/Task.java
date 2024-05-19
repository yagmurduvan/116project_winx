package Project.src;

public class Task {
    private String type;
    private double size;
    private int speed;
    private double minplus;
    private double duration ;
    private double finishTime;
    private boolean state;
    ;
    public boolean isState() {
        return state;
    }
    public void setState(boolean state) {
        this.state = state;
    }
    public Task(String type, double size) {
        if (size < 0) {
            throw new IllegalArgumentException("Size cannot be negative.");
        }
        this.type = type;
        this.size = size;
        this.speed=getSpeed();
        this.minplus=getMinplus();
        this.duration=getDuration();
        this.finishTime=getFinishTime();
        this.state=true;
    }
    public double getFinishTime() {
        return finishTime;
    }
    public void setFinishTime(double finishTime) {
        this.finishTime =finishTime+ getFinishTime();
    }
    public double getDuration() {
        return duration;
    }
    public void setDuration(double duration) {
        this.duration = duration;
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
    public void spendTime(double time) {
        setFinishTime(getFinishTime()+time);
    }
}