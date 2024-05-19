package Project.src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Reader {
    public static boolean formaterror = false;

    static ArrayList<Task> tasks_special = new ArrayList<Task>();

    static ArrayList<Task> tasks = new ArrayList<Task>();

    static ArrayList<Job> jobs = new ArrayList<>();

    static ArrayList<Station> stations = new ArrayList<>();

    static ArrayList<Double> minimum = new ArrayList<>();

    static ArrayList<Task> remaining = new ArrayList<>();

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

    public static void readFileForTask(String filePath, ArrayList<String> list) {
        Scanner reader = null;
        boolean isJobTypeFound = false; // Flag to check if "(JOBTYPES" is found
        HashSet<String> uniqueTaskTypes = new HashSet<>();

        try {
            reader = new Scanner(Paths.get(filePath));
            String firstWord = null;
            String lastWord = null;
            while (reader.hasNext()) {
                String nextWord = reader.next();

                // Check if "(JOBTYPES" is found, if found break the loop
                if (nextWord.equals("(JOBTYPES")) {
                    isJobTypeFound = true;
                    break;
                }
                // Check if task type starts with a letter
                if (!Character.isLetter(nextWord.charAt(0)) && !isInteger(nextWord) && nextWord.charAt(0) != '(') {
                    throw new IllegalArgumentException(" Task type must start with a letter.");
                }
                // Check if task type is unique
                if (Character.isLetter(nextWord.charAt(0)) && !uniqueTaskTypes.add(nextWord)) {
                    throw new IllegalArgumentException("Duplicate task type found: " + nextWord);
                }
                // Check if size is not negative
                if ((isInteger(nextWord) || isDouble(nextWord)) && Double.parseDouble(nextWord) < 0) {
                    throw new IllegalArgumentException("Size cannot be negative.");
                }
                // Add word to the list
                list.add(nextWord);

                // Update first and last word
                if (firstWord == null) {
                    firstWord = nextWord;
                }
                lastWord = nextWord;
            }

            // Check if the first word starts with '(' and last word ends with ')'
            if (!firstWord.startsWith("(")) {
                throw new IllegalArgumentException("Error in line 1 The first value must start with '('");
            }
            if (!lastWord.endsWith(")")) {
                throw new IllegalArgumentException("Error in line 1The last value must end with ')'");
            }

        } catch (IOException e) {
            System.out.println("Problem: file not read please write absolute path");
            return;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        // Check if "(JOBTYPES" is not found
        if (!isJobTypeFound) {
            throw new IllegalArgumentException("(JOBTYPES not found.");
        }
        System.out.println("List: " + list);
    }

    public static void taskMaker(String file1Name) {//declare the task
        ArrayList<String> readers = new ArrayList<String>();
        readFileForTask(file1Name, readers);
        boolean isFinished = true;
        for (int i = 1; i < readers.size(); i++) {
            while (isFinished && !isInteger(readers.get(i))) {
                ArrayList<String> taskInfos = new ArrayList<>();
                taskInfos.add(readers.get(i));
                int index = i;

                while (true) {
                    String type = "";
                    if (index == readers.size() - 1) {

                        System.out.println("last object was created");
                        if (readers.get(index).contains(")")) {
                            type = readers.get(index).replace(")", "");
                        }

                        if (!isInteger(type)) {
                            Task t = null;
                            t = new Task(type);
                            System.out.println(t.toString());
                            tasks.add(t);
                        }
                        isFinished = false;
                        i = readers.size();
                        break;
                    }
                    index++;

                    if (isInteger(readers.get(index))) {
                        taskInfos.add(readers.get(index));
                        System.out.println("object was created");
                        Task t = null;
                        t = new Task(readers.get(index - 1), Double.parseDouble(readers.get(index)));
                        System.out.println(t.toString());
                        tasks.add(t);


                    } else if (index != readers.size() - 1) {
                        i = index;
                        if (isInteger(readers.get(index + 1))) {
                            break;
                        } else {
                            Task t = null;
                            t = new Task(readers.get(index));
                            System.out.println(t.toString());
                            tasks.add(t);
                        }
                    }
                }
            }
        }
        System.out.println("");
    }

    public static boolean isInteger2(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void checkFormat(String filePath) {

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isJobTypesSection = false;
            boolean isStationsSection = false;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (!line.startsWith("(")) {
                    formaterror = true;
                    throw new IllegalArgumentException(" Every line must start with '('");
                }
                if (line.contains("JOBTYPES")) {
                    isJobTypesSection = true;
                    isStationsSection = false;
                    if (isJobTypesSection) {
                        if (!line.startsWith("(") || line.endsWith(")")) {
                            formaterror = true;
                            throw new IllegalArgumentException("Line must start with '('but must not finish with ')'");
                        }
                    }
                }
                if (line.contains("STATIONS")) {
                    isJobTypesSection = false;
                    isStationsSection = true;
                    if (isStationsSection) {
                        if (!line.startsWith("(") || line.endsWith(")")) {
                            formaterror = true;
                            throw new IllegalArgumentException("Line must start with '('but must not finish with ')'");
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Problem: file not read please write absolute path");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public static void jobFileReader(String filePath) { //read jobfile and declare the jobs
        Set<String> uniqueJobIDs = new HashSet<>();
        Scanner reader = null;
        try {
            reader = new Scanner(Paths.get(filePath));
            while (reader.hasNext()) {

                Job j = new Job();
                String jobID = reader.next();
                String jobTypeID = reader.next();
                if (!Character.isLetter(jobID.charAt(0))) {
                    throw new IllegalArgumentException("JobID must start with a letter.");
                }
                if (!Character.isLetter(jobTypeID.charAt(0))) {
                    throw new IllegalArgumentException("JobTypeID must start with a letter.");
                }

                if (!uniqueJobIDs.add(jobID)) {
                    throw new IllegalArgumentException("JobID must be unique.");
                }

                j.setJobID(jobID);
                j.setJobTypeID(jobTypeID);
                j.setStartime(reader.nextInt());
                j.setDuration(reader.nextInt());
                jobs.add(j);
                System.out.println(j.toString());
            }
        } catch (IOException e) {
            System.out.println("Problem: file not read please write absolute path");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
    public static void controller(String[] array2) {
        int index_job = 0;
        int index_task = 0;
        String element = null;
        double original_size = 0;
        int i = 0;
        try{
            while (index_job < jobs.size()) {
                if (isInteger2(array2[i]) && Integer.parseInt(array2[i]) < 0) {
                    throw new IllegalArgumentException("Size cannot be negative.");
                }

                if (isDouble(array2[i]) && Double.parseDouble(array2[i]) < 0) {
                    throw new IllegalArgumentException("Size cannot be negative.");
                }

                if (array2[i].contains("))")) {
                    break;
                }

                if (array2[i].equals(jobs.get(index_job).getJobTypeID())) {
                    i++;
                    while (index_task < tasks.size()) {
                        String type;
                        String type2;
                        type = array2[i].replace(")", "");
                        type2 = array2[i].replace("))", "");
                        if (array2[i].equals(tasks.get(index_task).getType())) {
                            i++;
                            if (isInteger(array2[i])) {
                                if (tasks.get(index_task).getSize() == 0) {
                                    String task = tasks.get(index_task).getType();
                                    Task t = new Task(task, Integer.parseInt(array2[i]));
                                    jobs.get(index_job).addTask(t);
                                    tasks_special.add(t);
                                    index_task = 0;
                                } else {
                                    String task = tasks.get(index_task).getType();
                                    Task t = new Task(task, Integer.parseInt(array2[i]));
                                    jobs.get(index_job).addTask(t);
                                    tasks_special.add(t);
                                    index_task = 0;
                                }
                            } else if (array2[i].contains(")")) {
                                element = array2[i].replace(")", "");
                                if (isInteger(element)) {
                                    if (tasks.get(index_task).getSize() == 0) {
                                        String task = tasks.get(index_task).getType();
                                        Task t = new Task(task, Integer.parseInt(element));
                                        jobs.get(index_job).addTask(t);
                                        tasks_special.add(t);
                                        index_task = 0;
                                    } else {
                                        String task = tasks.get(index_task).getType();
                                        Task t = new Task(task, Integer.parseInt(element));
                                        jobs.get(index_job).addTask(t);
                                        tasks_special.add(t);
                                        index_task = 0;
                                    }
                                }
                                index_job = 0;
                                i++;
                                break;
                            } else if (array2[i].contains("))")) {
                                element = array2[i].replace("))", "");
                                if (isInteger(element)) {
                                    String task = tasks.get(index_task).getType();
                                    Task t = new Task(task, Integer.parseInt(element));
                                    jobs.get(index_job).addTask(t);
                                    tasks_special.add(t);
                                    index_task = 0;
                                } else {
                                    String task = tasks.get(index_task).getType();
                                    Task t = new Task(task, Integer.parseInt(element));
                                    tasks.get(index_task).setSize(Integer.parseInt(element));
                                    jobs.get(index_job).addTask(t);
                                    tasks_special.add(t);
                                    index_task = 0;
                                }
                                index_job = 0;
                                break;
                            } else {
                                if (tasks.get(index_task).getSize() == 0) {
                                    String taskType = tasks.get(index_task).getType();
                                    jobs.get(index_job).addTask(tasks.get(index_task));
                                    tasks_special.add(tasks.get(index_task));
                                    index_task = 0;
                                    i--;
                                    throw new Exception("Size of the task is not set. -> "+ taskType);
                                } else {
                                    double size = tasks.get(index_task).getSize();
                                    original_size = size;
                                    String task = tasks.get(index_task).getType();
                                    Task t = new Task(task, original_size);
                                    jobs.get(index_job).addTask(t);
                                    tasks_special.add(t);
                                    index_task = 0;
                                    i--;
                                }
                            }

                            i++;
                        } else if (type.equals(tasks.get(index_task).getType()) || type2.equals(tasks.get(index_task).getType())) {
                            if (tasks.get(index_task).getSize() == 0) {
                                String task = tasks.get(index_task).getType();
                                Task t = new Task(task, Integer.parseInt(element));
                                jobs.get(index_job).addTask(t);
                                tasks_special.add(t);
                                index_task = 0;
                            } else {
                                String task = tasks.get(index_task).getType();
                                Task t = new Task(task, tasks.get(index_task).getSize());
                                jobs.get(index_job).addTask(t);
                                tasks_special.add(t);
                                index_task = 0;
                            }
                            i++;
                            index_job = 0;
                            break;
                        } else {
                            index_task++;
                        }
                    }
                } else {
                    index_job++;
                }
                if (i == array2.length) {
                    i--;
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return; // If an exception is thrown, return and do not execute the rest of the code
        }
        System.out.println("");
        System.out.println("For each job was assigned each tasks , here is the job's last situations");
        for (Job j : jobs) {
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
                    } else if (line.contains("(STATIONS")) {
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
        Set<String> uniqueStationIDs = new HashSet<>();
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
                    if (!uniqueStationIDs.add(tokens[0])) {
                        throw new IllegalArgumentException("StationID must be unique.");
                    }
                }
            }
            array3 = new String[tempArray.size()];
            tempArray.toArray(array3);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        stationmaker1(array3);
        stationtask(array3);
    }

    public static void stationmaker1(String[] array) {
        try{
            for (int a = 0; a < array.length; a++) {
                Station s = null;
                if (array[a].contains("(")) {
                    String new_s = array[a].replace("(", "");
                    if (!Character.isLetter(new_s.charAt(0))) {
                        throw new IllegalArgumentException("StationID must start with a letter.");
                    }
                    s = new Station(new_s);
                    s.setStationID(new_s);
                    if (isInteger(array[a + 1])) {
                        s.setCapacity(Integer.parseInt(array[a + 1]));
                        a = a + 2;
                        switch (array[a]) {
                            case "Y":
                                s.setMULTIFLAG(true);

                                break;
                            case "N":
                                s.setMULTIFLAG(false);

                                break;
                        }
                        a++;
                        switch (array[a]) {
                            case "Y":
                                s.setFIFOFLAG(true);

                                break;
                            case "N":
                                s.setFIFOFLAG(false);

                                break;
                        }
                    } else {
                        switch (array[a + 1]) {
                            case "Y":
                                s.setMULTIFLAG(true);

                            case "N":
                                s.setMULTIFLAG(false);

                        }
                        switch (array[a + 2]) {
                            case "Y":
                                s.setFIFOFLAG(true);

                            case "N":
                                s.setFIFOFLAG(false);

                        }
                    }
                    stations.add(s);
                }
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            // Skip to the next iteration if the exception is thrown
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
                    if(tasks_special.get(task_index).getType().equals(array[array_index])) {

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
                                tasks_special.remove(task_index);

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
        System.out.println("******************************************************");
        System.out.println("For each station was assigned each tasks , here is the station's last situations");
        for(Station s : stations) {
            s.printlist();
        }
        System.out.println("******************************************************");
    }

    public  static void definespeed() {
        double percentage=0.0;
        double top=0.0;
        double min=0.0;
        int randomNumber=0;
        for(Station s : stations) {
            for(int index=0;index <s.getTasklist().size();index++) {
                if(s.getTasklist().get(index).getMinplus()!=0) {
                    percentage=(s.getTasklist().get(index).getSpeed()*s.getTasklist().get(index).getMinplus())/100;
                    top=s.getTasklist().get(index).getSpeed()+percentage;
                    min=s.getTasklist().get(index).getSpeed()-percentage;
                    Random rd = new Random(System.currentTimeMillis());
                    double randomDouble = rd.nextDouble(top - min +1) + min;
                    randomNumber = (int) randomDouble;
                    s.getTasklist().get(index).setSpeed(randomNumber);
                }
            }
        }
    }

    public static void durationCalculater() {

        for(Station s : stations) {
            for(int i=0;i<s.getTasklist().size();i++) {
                if(s.getTasklist().get(i).getSpeed()!=0) {
                    s.getTasklist().get(i).setDuration(s.getTasklist().get(i).getSize()/s.getTasklist().get(i).getSpeed());
                }
            }
        }
        duration_for_station();
        showSituation();
    }

    public static void duration_for_station() {
        double max=0;
        double total=0;
        //hepsinin bitirme sürelerini buluyorum
        for(Station s: stations) {
            if((s.isMULTIFLAG()&& s.isFIFOFLAG())){
                for(Task t :  s.getTasklist()) {
                    if(max<=t.getDuration()) {
                        max=t.getDuration();
                        s.setTotal_finish(max);//eğer multiyse en uzun süreli iş bitene kadar hepsi bitmiş olur
                    }
                }
                System.out.println(s.getStationID() + " finishing time  "+ s.getTotal_finish());
            }else {
                for(Task t: s.getTasklist() ) {
                    total+=t.getDuration();
                }
                s.setTotal_finish(total);
                System.out.println(s.getStationID() + " finishing time "+ s.getTotal_finish());
            }
            max=0;
        }
        System.out.println("******************************************************");
    }

    public static void showSituation() {//fifo ise ilk olanı total_duration yapıyorum diğer türlü en kısa sürelisi total olacak
        for(Station s : stations) {
            if(s.getTasklist().size()!=0) {
                if(!s.isFIFOFLAG()){
                    s.getTasklist().sort(Comparator.comparingDouble(Task::getDuration));
                    s.setTotal_duration(s.getTasklist().get(0).getDuration());
                }else {
                    s.setTotal_duration(s.getTasklist().get(0).getDuration());
                }
            }
        }
        double find_time=0;
        int c =0;//index of fifo and not multi
        int d=0;//index of not fifo and multi
        int counter1=0;
        int counter2=0;
        int counter3=0;
        double finish_time1=0;
        double finish_time2=0;
        double finish_time3=0;
        for(Station s : stations) {
            if(find_time<s.getTotal_finish()) {
                find_time=s.getTotal_finish();
            }
        }
        remainingTasks();
        for(double time=0;time<find_time+5;time++) {
            for(Station s : stations) {
                s.setDuration(s.getTotal_duration()-time);
                if(s.isMULTIFLAG()) {
                    for(int a=0;a<s.getTasklist().size();a++) {
                        if(a<s.getTasklist().size()&&s.getTasklist().get(a).isState()) {
                            if(s.getTasklist().get(a).getDuration()<=time || s.getTasklist().get(a).getDuration()<=(time-finish_time1)) {
                                System.out.println("in "+time+" time "+s.getStationID()+" , "+s.getTasklist().get(a).getType()+" finished ");
                                s.getTasklist().get(a).setFinishTime(time);
                                s.getTasklist().get(a).setState(false);
                                counter1++;
                            }
                        }
                        if(a<s.getTasklist().size() &&s.getTasklist().get(a).isState()){
                            System.out.println("in "+time+" time "+s.getStationID()+" , "+s.getTasklist().get(a)+" being done now, it's duration = "+s.getTasklist().get(a).getDuration());

                        }
                        if(counter1==s.getTasklist().size()) {
                            s.setState(true);
                            finish_time1=time;
                        }

                    }

                }else if(s.isFIFOFLAG()&&!s.isMULTIFLAG()) {
                    if(c<s.getTasklist().size()&&s.getTasklist().get(c).isState()) {
                        System.out.println("in "+time+" time "+s.getStationID()+" , "+s.getTasklist().get(c)+ " being done now, duration = "+s.getTasklist().get(c).getDuration());
                        if(s.getTotal_duration()<=time || s.getTasklist().get(c).getDuration()<=(time-finish_time2)) {
                            System.out.println("in "+time+" time "+s.getStationID()+" , "+s.getTasklist().get(c)+ " finished");
                            s.getTasklist().get(c).setState(false);
                            s.getTasklist().get(c).setFinishTime(time);
                            System.out.println(s.getTasklist().get(c).getFinishTime());
                            counter2++;
                            if(c!=0) {
                                System.out.println("in "+time+" time "+s.getStationID()+" , "+s.getTasklist().get(c-1)+ " is previous completed task");
                            }
                            c++;
                            if(c<s.getTasklist().size()) {
                                System.out.println("in "+time+" time "+s.getStationID()+" , "+s.getTasklist().get(c)+ " This is the next task waiting in the queue , duration= "+s.getTasklist().get(c).getDuration());
                                s.setTotal_duration(s.getTasklist().get(c).getDuration());
                            }
                        }
                        if(counter2==s.getTasklist().size()) {
                            s.setState(true);
                            finish_time2=time;
                        }
                    }

                }else if(!s.isFIFOFLAG()&&!s.isMULTIFLAG()) {
                    s.getTasklist().sort(Comparator.comparingDouble(Task::getDuration));
                    if(d<s.getTasklist().size()) {
                        if(s.getTotal_duration()<=time || s.getTasklist().get(d).getDuration()<=(time-finish_time3)) {
                            System.out.println("in "+time+" time "+s.getStationID()+" , "+s.getTasklist().get(d)+ " finished");
                            s.getTasklist().get(d).setState(false);
                            s.getTasklist().get(d).setFinishTime(time);
                            counter3++;
                            if(d!=0) {
                                System.out.println("in "+time+" time "+s.getStationID()+" , "+s.getTasklist().get(d-1)+ " is previous completed task");
                            }
                            d++;
                            if(d<s.getTasklist().size()) {
                                System.out.println("in "+time+" time "+s.getStationID()+" , "+s.getTasklist().get(d)+ " This is the next task waiting in the queue , duration= "+s.getTasklist().get(d).getDuration());
                                s.setTotal_duration(s.getTasklist().get(d).getDuration());
                            }
                        }
                    }
                    if(d<s.getTasklist().size()&&s.getTasklist().get(d).isState()) {
                        System.out.println("in "+time+" time "+s.getStationID()+" , "+s.getTasklist().get(d)+ " being done now, it's duration = "+s.getTasklist().get(d).getDuration());
                    }
                    if(counter3==s.getTasklist().size()) {
                        s.setState(true);
                        finish_time3=time;
                    }
                }
                if(s.isState()) {
                    remainingTasks2(s);
                }
            }
        }
        System.out.println("******************************************************");
    }

    public static void totalDuration() {
        double duration_job=0.0;
        for(Job j : jobs) {
            for( Task t :j.getTasklist()) {
                duration_job+=t.getFinishTime()+j.getStartime();
            }
            System.out.println(j.getJobID()+"'s duration time "+duration_job);
            if(j.getDuration()>duration_job) {
                System.out.println("in "+j.getJobID()+" no delay");
                System.out.println("start time "+j.getStartime()+" finish time "+duration_job+j.getStartime());
            }if(j.getDuration()==duration_job) {
                System.out.println("in "+j.getJobID()+" all tasks finish in time");
                System.out.println("start time "+j.getStartime()+" finish time "+duration_job+j.getStartime());
            }if(j.getDuration()<duration_job) {
                System.out.println("in "+j.getJobID()+","+(duration_job-j.getDuration())+" there is a delay");
                System.out.println("start time "+j.getStartime()+" finish time "+duration_job+j.getStartime());
            }
            System.out.println("");
        }
    }

    public static void remainingTasks() {
        Random rd = new Random();

        for(Task t :tasks_special) {
            if(t.getSpeed()==0) {
                t.setDuration(rd.nextDouble(3)+0.5);
                remaining.add(t);
            }
        }
    }

    public static void remainingTasks2(Station s) {
        while (!remaining.isEmpty()) {
            if(s.isState()) {
                if (s.getCapacity() >= 1) {
                    s.addTask(remaining.get(0));
                    remaining.remove(0); // Görevi remaining listesinden kaldır
                    s.setState(false);
                    s.setDuration(remaining.get(0).getDuration());
                }
            }
            else if(!s.isState())  {
                remainingTasks3() ;
            }
        }
    }

    public static void remainingTasks3() {
        int b = 0;
        // İkinci döngü: Kalan görevleri istasyonlara ata
        while (!remaining.isEmpty() && b < remaining.size()) {
            stations.sort(Comparator.comparingDouble(Station::getDuration));
            if (stations.get(0).getCapacity() >= 1) {
                stations.get(0).addTask(remaining.get(b));
                stations.get(0).setDuration(stations.get(0).getDuration() + remaining.get(b).getDuration());
                stations.get(0).setDuration(remaining.get(b).getDuration());
                remaining.remove(b); // Görevi remaining listesinden kaldır
            }
        }
    }

}
