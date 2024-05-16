package Project.src;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Reader {

    static ArrayList<Task> tasks_special=new ArrayList<Task>() ;

    static ArrayList<Task> tasks=new ArrayList<Task>() ;

    static ArrayList<Job> jobs=new ArrayList<>();

    static ArrayList<Station> stations = new ArrayList<>();

    public static boolean isInteger(String str) {
        try {
            Double.parseDouble(str); // Double'a çevirme denemesi yap
            return true; // Başarılıysa true döndür
        } catch (NumberFormatException e) {
            // Double'a çevirme başarısız olduğunda Integer'a çevirme denemesi yap
            try {
                Integer.parseInt(str);
                return true; // Başarılıysa true döndür
            } catch (NumberFormatException ex) {
                return false; // Her iki dönüşüm de başarısız olduysa false döndür
            }
        }
    }




    public static void readFileForTask(String filePath, ArrayList<String> list) {//read workflow untıl the (JOBTYPES and add task list that it read
        Scanner reader= null;
        try {
            reader =new Scanner(Paths.get(filePath));
            while(reader.hasNext()) {
                String nextWord = reader.next();
                if (nextWord.equals(")")) {
                    break; // "task" kelimesini bulduktan sonra okumayı durdur
                }
                list.add(nextWord);
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
                    String type = "";
                    if(index==readers.size()-1) {

                        System.out.println("son nesne oluşturuldu");
                        if(readers.get(index).contains(")")) {
                            type=readers.get(index).replace(")", "");
                        }

                        if(!isInteger(type)) {
                            Task t=null;
                            t=new Task(type);
                            System.out.println(t.toString());
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
                        t=new Task(readers.get(index-1), Double.parseDouble(readers.get(index)));
                        System.out.println(t.toString());
                        tasks.add(t);


                    }else if(index!=readers.size()-1){
                        i=index;
                        if(isInteger(readers.get(index+1))) {
                            break;
                        }
                        else {
                            Task t=null;
                            t=new Task(readers.get(index));
                            System.out.println(t.toString());
                            tasks.add(t);
                        }
                    }
                }
            }
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

    public static void controller(String [] array2) {
        int index_job=0;
        int index_task=0;
        String element=null;
        boolean bool=true;
        double original_size=0;
        int i=0;
        while(index_job<jobs.size()) {
            if(array2[i].contains("))")) {
                break;
            }

            if(array2[i].equals(jobs.get(index_job).getJobTypeID())) {
                i++;
                while(index_task<tasks.size()) {
                    String type;
                    String type2;
                    type=array2[i].replace(")","");
                    type2=array2[i].replace("))","");
                    if(array2[i].equals(tasks.get(index_task).getType())) {
                        i++;

                        if(isInteger(array2[i])) {
                            if(tasks.get(index_task).getSize()==0) {
                                String task = tasks.get(index_task).getType();
                                Task t =new Task(task,Integer.parseInt(array2[i]));
                                jobs.get(index_job).addTask(t);
                                tasks_special.add(t);
                                //jobs.get(index_job).printTasklist();
                                index_task=0;
                            }else {
                                String task = tasks.get(index_task).getType();
                                Task t =new Task(task,Integer.parseInt(array2[i]));
                                jobs.get(index_job).addTask(t);
                                tasks_special.add(t);
                                //jobs.get(index_job).printTasklist();
                                index_task=0;
                            }
                        }else if(array2[i].contains(")")) {
                            element = array2[i].replace(")", "");
                            if(isInteger(element)) {
                                if(tasks.get(index_task).getSize()==0) {
                                    String task = tasks.get(index_task).getType();
                                    Task t =new Task(task,Integer.parseInt(element));
                                    jobs.get(index_job).addTask(t);
                                    tasks_special.add(t);
                                    //jobs.get(index_job).printTasklist();
                                    index_task=0;
                                }else {
                                    String task = tasks.get(index_task).getType();
                                    Task t =new Task(task,Integer.parseInt(element));
                                    jobs.get(index_job).addTask(t);
                                    tasks_special.add(t);
                                    //jobs.get(index_job).printTasklist();
                                    index_task=0;
                                }
                            }
                            index_job=0;
                            i++;
                            break;
                        }else if(array2[i].contains("))")) {
                            element = array2[i].replace("))", "");
                            if(isInteger(element)) {
                                String task = tasks.get(index_task).getType();
                                Task t =new Task(task,Integer.parseInt(element));
                                jobs.get(index_job).addTask(t);
                                tasks_special.add(t);
                                //jobs.get(index_job).printTasklist();
                                index_task=0;
                            }else {
                                String task = tasks.get(index_task).getType();
                                Task t =new Task(task,Integer.parseInt(element));
                                tasks.get(index_task).setSize(Integer.parseInt(element));
                                jobs.get(index_job).addTask(t);
                                tasks_special.add(t);
                                //jobs.get(index_job).printTasklist();
                                index_task=0;
                            }
                            index_job=0;
                            break;
                        }else {
                            if(tasks.get(index_task).getSize()==0) {
                                tasks.get(index_task).setSize(1);//ERROR
                                jobs.get(index_job).addTask(tasks.get(index_task));
                                tasks_special.add(tasks.get(index_task));
                                //jobs.get(index_job).printTasklist();
                                index_task=0;
                                i--;
                            }else {
                                double size = tasks.get(index_task).getSize();
                                original_size=size;
                                String task=tasks.get(index_task).getType();
                                Task t =new Task(task,original_size);
                                jobs.get(index_job).addTask(t);
                                tasks_special.add(t);
                                //jobs.get(index_job).printTasklist();
                                index_task=0;
                                i--;
                            }
                        }
                        i++;
                    }else if(type.equals(tasks.get(index_task).getType()) || type2.equals(tasks.get(index_task).getType())) {
                        if(tasks.get(index_task).getSize()==0) {
                            String task = tasks.get(index_task).getType();
                            Task t =new Task(task,Integer.parseInt(element));
                            jobs.get(index_job).addTask(t);
                            tasks_special.add(t);
                            //jobs.get(index_job).printTasklist();
                            index_task=0;
                        }else {
                            String task = tasks.get(index_task).getType();
                            Task t =new Task(task,Integer.parseInt(element));
                            jobs.get(index_job).addTask(t);
                            tasks_special.add(t);
                            //jobs.get(index_job).printTasklist();
                            index_task=0;
                        }
                        i++;
                        index_job=0;
                        break;
                    }
                    else {
                        index_task++;
                    }
                }
            }else {
                index_job++;
            }
            if(i==array2.length) {//herhangi bir şey yapınca bunu sil error veriyo mu bak eğer son güne kadar vermezse silip gönder
                i--;
            }
        }
        for(Job j : jobs) {
            j.printTasklist();
        }
    }

    public static void maketasklist() {
        for(Job j : jobs) {
            j.printTasklist();
        }
    }

    public static void jobgrouper(String filePath) {
        String[] array2;
        ArrayList<String> tempArray = new ArrayList<>();
        Scanner reader = null;
        boolean readJobTypes = false;
        String line;
        try {
            reader = new Scanner(Paths.get(filePath));
            while (reader.hasNext()) {
                line = reader.nextLine();

                if (line.trim().startsWith("(")) {
                    if (line.contains("(JOBTYPES")) {
                        readJobTypes = true;
                        continue;
                    } else if (line.contains(". (STATIONS")) {
                        break;
                    }
                }

                if (readJobTypes) {
                    line = line.replace("(", "").trim();
                    String[] tokens = line.split(" ");
                    tempArray.addAll(Arrays.asList(tokens));
                }
            }
            array2 = new String[tempArray.size()];
            tempArray.toArray(array2);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        controller(array2);
    }

    public static void stationReader(String filePath) {

        String[] array3;
        ArrayList<String> tempArray = new ArrayList<>();
        Scanner reader = null;
        boolean readStations = false; // İstasyonları okuyor mu?
        try {
            reader = new Scanner(Paths.get(filePath));
            while (reader.hasNext()) {
                String line = reader.nextLine();

                if (line.contains("(STATIONS")) {

                    readStations = true; // İstasyonları okumaya başla.
                    continue; // Bir sonraki satıra geç.
                }

                if (readStations) {

                    String[] tokens = line.split("\\s+");
                    tempArray.addAll(Arrays.asList(tokens));
                }
            }
            array3 = new String[tempArray.size()];
            tempArray.toArray(array3);

        } catch (IOException e) {
            e.printStackTrace();
            return;
        } finally {
            if (reader != null) {
                reader.close(); // Okuyucuyu kapat.
            }
        }
        for(String a : array3) {
            System.out.println(a);
        }
        stationmaker1(array3);
        stationtask(array3);
    }

    public static void stationmaker1(String [] array) {


        for(int a =0;a<array.length;a++) {
            Station s =null;
            if(array[a].contains("(")){
                String new_s = array[a].replace("(","");
                s=new Station(new_s);
                s.setStationID(new_s);
                if(isInteger(array[a+1])) {
                    s.setCapacity(Integer.parseInt(array[a+1]));
                    a=a+2;

                    switch(array[a]) {
                        case "Y":
                            s.setMULTIFLAG(true);

                            break;
                        case "N":
                            s.setMULTIFLAG(false);

                            break;
                    }
                    a++;
                    switch(array[a]) {
                        case "Y":
                            s.setFIFOFLAG(true);

                            break;
                        case "N":
                            s.setFIFOFLAG(false);

                            break;
                    }
                }else {
                    switch(array[a+1]) {
                        case "Y":
                            s.setMULTIFLAG(true);

                        case "N":
                            s.setMULTIFLAG(false);

                    }
                    switch(array[a+2]) {
                        case "Y":
                            s.setFIFOFLAG(true);

                        case "N":
                            s.setFIFOFLAG(false);

                    }
                }
                stations.add(s);
            }

        }
        for(Station s1 : stations) {
            System.out.println(s1.getStationID());
            System.out.println(s1.getCapacity());
            System.out.println(s1.isMULTIFLAG());
            System.out.println(s1.isFIFOFLAG());
        }

    }

    public static void stationtask(String [] array) {
        int array_index=0;
        int task_index=0;
        String newone="";
        String newone2="";
        boolean pharantesis=true;
        for(int station_index=0;station_index<stations.size();station_index++) {
            pharantesis=true;
            while(array_index<array.length&&pharantesis) {

                while(task_index<tasks_special.size()&&pharantesis) {
                    System.out.println(array[array_index]+" == " +tasks_special.get(task_index));
                    System.out.println();
                    if(tasks_special.get(task_index).getType().equals(array[array_index])) {
                        System.out.println("bnkmlşöiç");
                        stations.get(station_index).addTask(tasks_special.get(task_index));
                        array_index++;
                        if(array[array_index].contains(")")) {
                            newone=array[array_index].replace(")", "");
                            tasks_special.get(task_index).setSpeed(Integer.parseInt(newone));
                            pharantesis=false;
                            break;
                        }else {
                            tasks_special.get(task_index).setSpeed(Integer.parseInt(array[array_index]));
                        }
                        array_index++;
                        if(array[array_index].contains(")")) {
                            newone2=array[array_index].replace(")", "");
                            if(isInteger(newone2)) {
                                tasks_special.get(task_index).setMinplus(Double.parseDouble(newone2));
                                pharantesis=false;
                                break;
                            }
                        }else {
                            if(isInteger(array[array_index])) {
                                tasks_special.get(task_index).setMinplus(Integer.parseInt(array[array_index]));
                            }
                        }
                        tasks_special.remove(task_index);
                        task_index=0;
                    }else {
                        task_index++;

                    }
                    if(task_index==tasks_special.size()) {
                        array_index++;
                        task_index=0;
                    }
                    if(array_index==array.length) {
                        break;
                    }
                    if(pharantesis==false) {
                        break;
                    }
                }
            }
        }

        for(Station s : stations) {
            s.printlist();

        }
    }


}