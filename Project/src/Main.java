package Project.src;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        ArrayList<Task> tasks = new ArrayList<Task>();
        Scanner reader= null;
        try {
            reader =new Scanner(Paths.get("Untitled.txt"));
        }catch(Exception e) {
            System.out.println("problem")	;
        }
    }

}
