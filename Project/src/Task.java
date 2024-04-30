
package Project.src;
public class Task {
    private String type;
    private int size;

    public Task(String type, int size) {
        this.type = type;
        this.size = size;

    }
    public Task (String type) {
        this.type=type;
        this.size=1;
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
    @Override
    public String toString() {
        return "Task [" + (type != null ? "type=" + type + ", " : "") + "size=" + size + "]";
    }

}
