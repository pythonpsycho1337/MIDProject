import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class InputModule implements Runnable{

    public void inputfile() {
        File file = new File(""); //Enter name of file here
        try {
            Scanner scfile = new Scanner(file);
            while (scfile.hasNextLine()) {
                String[] inputline = scfile.nextLine().split(" ");

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private Master masterRef;
    private int maxClients  = 20;
    public InputModule(Master mRef){
        masterRef = mRef;
    }

    public void run(){
        Client client = new Client(masterRef);
        client.run();
    }
}
