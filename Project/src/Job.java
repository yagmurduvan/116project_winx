
import java.util.ArrayList;
public class Job {
    private String jobID;
    private int duration;
    private String type;
    private ArrayList<Task> tasklist;

    public Job(String jobID, int duration, String type, ArrayList<Task> tasklist) {
        super();
        this.jobID = jobID;
        this.duration = duration;
        this.type = type;
        this.tasklist = new ArrayList<>();
    }
    public String getJobID() {
        return jobID;
    }
    public void setJobID(String jobID) {
        this.jobID = jobID;
    }
    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void getTasklist() {//make void because we print the task list into the getTaskList
        for (Task task : tasklist) {//not need to return Arraylist
            System.out.println(task);
        }
    }
    public void setTasklist(ArrayList<Task> tasklist) {
        this.tasklist = tasklist;
    }

}