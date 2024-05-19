package Project.src;
public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("File name not given");
            return;
        }
        String[] files = args[0].split(",");
        if (files.length != 2) {
            System.out.println("Give full path address with seperated with komma ");
            return;
        }
        String workflow = files[0];
        String jobfile = files[1];
        try {
            Reader.checkFormat(workflow);
        } catch (Exception e) {
            System.out.println("Error in checkFormat: " + e.getMessage());
            return; // If an exception is thrown in checkFormat, return and do not execute the rest of the program
        }
        if (Reader.formaterror == false) {
            Reader.taskMaker(workflow); //read workflow , make task object and add task arraylist
            Reader.jobFileReader(jobfile); //read jobfile make job object and addd job arraylist
            Reader.jobgrouper(workflow);
        }
    }
}