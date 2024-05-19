package Project.src;

import java.util.ArrayList;

public class Station {
    private String stationID;
    private int capacity;
    private double duration;
    private int speed;
    private boolean MULTIFLAG;
    private boolean FIFOFLAG;
    private ArrayList<Task> tasklist;
    private double total_finish;
    private double total_duration;
    private boolean state;
    public Station(String stationID) {
        this.stationID = stationID;
        this.capacity = getCapacity();
        this.duration = getDuration();
        this.speed = getSpeed();
        this.MULTIFLAG = isMULTIFLAG();
        this.FIFOFLAG = isFIFOFLAG();
        this.tasklist = new ArrayList<>();
        this.total_finish=getTotal_finish();
        this.total_duration=getTotal_duration();
        this.state=isState();
    }
    public boolean isState() {
        return state;
    }
    public void setState(boolean state) {
        this.state = state;
    }
    public double getTotal_duration() {
        return total_duration;
    }
    public void setTotal_duration(double duration) {
        this.total_duration += duration;
    }
    public double getTotal_finish() {
        return total_finish;
    }
    public void setTotal_finish(double total_finish) {
        this.total_finish = total_finish;
    }
    public String getStationID() {
        return stationID;
    }
    public void setStationID(String stationID) {
        this.stationID = stationID;
    }
    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    public double getDuration() {
        return duration;
    }
    public void setDuration(double duration) {
        this.duration = duration;
    }
    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public boolean isMULTIFLAG() {
        return MULTIFLAG;
    }
    public void setMULTIFLAG(boolean mULTIFLAG) {
        MULTIFLAG = mULTIFLAG;
    }
    public boolean isFIFOFLAG() {
        return FIFOFLAG;
    }
    public void setFIFOFLAG(boolean fIFOFLAG) {
        FIFOFLAG = fIFOFLAG;
    }
    public ArrayList<Task> getTasklist() {
        return tasklist;
    }
    public void setTasklist(ArrayList<Task> tasklist) {
        this.tasklist = tasklist;
    }
    public void addTask(Task tasks) {
        tasklist.add(tasks);
    }
    public void printlist() {
        for (Task task : tasklist) {
            System.out.println(getStationID()+task);
        }
    }
}