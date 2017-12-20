import jdk.internal.util.xml.impl.Input;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.ConcurrentLinkedQueue;


public class InputModule implements Runnable{
    //InputModule
    //  Reads an input file and parses its content into requests

    private Master masterRef;
    public final String INPUTFILE = "input/client_requests.txt";
    public InputModule(Master mRef){
        masterRef = mRef;
        createRequestsFromFile(INPUTFILE);
    }



    public void createRequestsFromFile(String fileName) {
        File file = new File(fileName);
        try {
            Scanner scfile = new Scanner(file);
            if(scfile.hasNextLine()){
                scfile.nextLine(); //Skip header line in input file
            }
            while (scfile.hasNextLine()) {
                String[] inputline = scfile.nextLine().split(",");

                int cid = Integer.parseInt(inputline[0]);
                int[] args = new int[1];
                args[0] = Integer.parseInt(inputline[2]);

                Request req = new Request(cid,inputline[1],args);
                masterRef.add_request(req);
            }
            masterRef.add_request(new Request(-1,null,null));//"End of file" message
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        Client client = new Client(masterRef);
        client.run();
    }
}
