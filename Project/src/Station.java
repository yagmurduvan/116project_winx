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

    public Station(String stationID, int capacity, int duration, int speed, boolean mULTIFLAG, boolean fIFOFLAG,
                   ArrayList<Task> tasklist) {
        super();
        this.stationID = stationID;
        this.capacity = capacity;
        this.duration = duration;
        this.speed = speed;
        MULTIFLAG = mULTIFLAG;
        FIFOFLAG = fIFOFLAG;
        this.tasklist = tasklist;
    }

}
