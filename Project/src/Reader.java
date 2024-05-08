package Project.src;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Reader {

    static ArrayList<Task> tasks = new ArrayList<Task>();

    static ArrayList<Job> jobs = new ArrayList<>();

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static void readFileForTask(String filePath, ArrayList<String> list) {//read workflow untıl the (JOBTYPES and add task list that it read
        Scanner reader= null;
        try {
            reader =new Scanner(Paths.get(filePath));
            while(reader.hasNext()) {
                String nextWord = reader.next();
                if (nextWord.equals("(JOBTYPES")) {
                    break; // "task" kelimesini bulduktan sonra okumayı durdur
                }
                list.add(nextWord);
            }
        }catch(Exception e) {
            System.out.println("Problem: file not read please write absolute path")	;
        }
    }
    public static void jobFileReader(String filePath) { //read jobfile and declare the jobs

        Scanner reader= null;
        try {
            reader =new Scanner(Paths.get(filePath));
            while(reader.hasNext()) {

                Job j = new Job();
                j.setJobID(reader.next());
                j.setJobTypeID(reader.next());
                j.setStartime(reader.nextInt());
                j.setDuration(reader.nextInt());
                jobs.add(j);
                System.out.println(j.toString());
            }
        }catch(Exception e) {
            System.out.println("Problem: file not read please write absolute path")	;
        }
    }
    public static void taskMaker(String file1Name) {//declare the task
        ArrayList<String> readers = new ArrayList<String>();
        readFileForTask(file1Name,readers);
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
                            // System.out.println(t.toString());
                            tasks.add(t);
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
                        //System.out.println(t.toString());
                        tasks.add(t);


                    }else if(index!=readers.size()-1){
                        i=index;
                        if(isInteger(readers.get(index+1))) {
                            break;
                        }
                        else {
                            Task t=null;
                            t=new Task(readers.get(index));
                            // System.out.println(t.toString());
                            tasks.add(t);
                        }
                    }
                }
            }
        }


    }


}


