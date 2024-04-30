package Project.src;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

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

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void readFile(String filePath, ArrayList<String> tasks) {
        Scanner reader= null;
        try {
            reader =new Scanner(Paths.get(filePath));
            while(reader.hasNext()) {
                tasks.add(reader.next());
            }
        }catch(Exception e) {
            System.out.println("Problem: file not read please write absolute path")	;
        }
    }

    public static void taskMaker(String file1Name) {
        ArrayList<String> readers = new ArrayList<String>();
        ArrayList<Task> tasks=new ArrayList<Task>() ;

        readFile(file1Name,readers);

        //(tasktypes t1 3 t2 4 )
        boolean isFinished=true;
        for(int i=1;i<readers.size();i++) {
            while(isFinished&&!isInteger(readers.get(i))) {
                ArrayList<String> taskInfos = new ArrayList<>();
                taskInfos.add(readers.get(i));
                int index=i;

                while(true) {

                    if(index==readers.size()-1) {
                        System.out.println("son nesne oluşturuldu");

                        if(!isInteger(readers.get(index))) {

                            Task t=null;
                            t=new Task(readers.get(index));
                            System.out.println(t.toString());
                        }
                        isFinished=false;
                        i=readers.size();
                        break;
                    }
                    index++;
                    if(isInteger(readers.get(index))) {
                        taskInfos.add(readers.get(index));
                        System.out.println("nesne oluştur normal");
                        Task t =null;
                        t=new Task(readers.get(index-1),Integer.parseInt(readers.get(index)));
                        System.out.println(t.toString());


                    }else if(index!=readers.size()-1){
                        i=index;
                        if(isInteger(readers.get(index+1))) {
                            break;
                        }
                        else {
                            Task t=null;
                            t=new Task(readers.get(index));
                            System.out.println(t.toString());
                        }
                    }
                }
            }
        }
    }

}
