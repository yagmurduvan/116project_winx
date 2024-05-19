package Project.src;

import java.util.ArrayList;

public class Job {
    private String jobID;
    private String jobTypeID;
    private int startime;
    private int duration;
    private ArrayList<Task> tasklist;

    public Job(String jobTypeID,String jobID, int startime, int duration) {
        this.jobTypeID=jobTypeID;
        this.jobID = jobID;
        this.startime=startime;
        this.duration = duration;
        this.tasklist=new ArrayList<>();
    }
    public Job() {
        this.jobTypeID="";
        this.jobID ="";
        this.startime=0;
        this.duration = 0;
        this.tasklist=new ArrayList<>();
    }
    @Override
    public String toString() {
        return "Job [" + (jobID != null ? "jobID=" + jobID + ", " : "")
                + (jobTypeID != null ? "jobTypeID=" + jobTypeID + ", " : "") + "startime=" + startime + ", duration="
                + duration + ", " + (tasklist != null ? "tasklist=" + tasklist : "") + "]";
    }
    public String getJobTypeID() {
        return jobTypeID;
    }
    public void setJobTypeID(String jobTypeID) {
        this.jobTypeID = jobTypeID;
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
    public int getStartime() {
        return startime;
    }
    public void setStartime(int startime) {
        this.startime = startime;
    }
    public void printTasklist() {
        for (Task task : tasklist) {
            System.out.println(getJobTypeID()+task);
        }
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
}