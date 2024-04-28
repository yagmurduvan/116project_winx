public class Task {
    private String type;
    private int size;
    //private int typeID;

    public Task(String type, int size) {
        super();
        this.type = type;
        this.size = size;

    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }

}
