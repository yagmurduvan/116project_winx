package Project.src;

import java.util.ArrayList;

public class Station {
    private String stationID;
    private int capacity;
    private int duration;
    private int speed;
    private boolean MULTIFLAG;
    private boolean FIFOFLAG;
    private ArrayList<Task> tasklist;

    public Station(String stationID) {

        this.stationID = stationID;
        this.capacity = getCapacity();
        this.duration = getDuration();
        this.speed = getSpeed();
        this.MULTIFLAG = isMULTIFLAG();
        this.FIFOFLAG = isFIFOFLAG();
        this.tasklist = new ArrayList<>();
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
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

