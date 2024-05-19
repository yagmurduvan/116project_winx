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

}
