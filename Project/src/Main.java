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

        String file1Name = files[0];
        String file2Name = files[1];

        Task.taskMaker(file1Name);
    }
}






