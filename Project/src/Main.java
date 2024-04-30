package Project.src;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
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

    public static boolean isInteger(String str) {
        try {
            // String'i integer'a dönüştürmeye çalış
            Integer.parseInt(str);
            // Eğer istisnasız bir hata oluşmazsa, string integer'a dönüştürülebilir
            return true;
        } catch (NumberFormatException e) {
            // Eğer NumberFormatException hatası alınırsa, string integer'a dönüştürülemez
            return false;
        }
    }

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
        ArrayList<String> readers = new ArrayList<String>();
        ArrayList<Task> tasks=new ArrayList<>() ;

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



        for(Task t:tasks) {
            System.out.println(t.toString());
        }
        //System.out.println(t1.toString());
        //readFile(file2Name,string);






    }


}






