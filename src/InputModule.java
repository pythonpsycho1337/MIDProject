import jdk.internal.util.xml.impl.Input;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class InputModule implements Runnable{
    //InputModule
    //  Reads an input file and parses its content into requests
    //  Creates NUMCLIENTS clients and distirbutes the requests after ID
    private final int NUMOFCLIENTS = 10;
    private Client[] clients;
    private Master masterRef;
    public InputModule(Master mRef){
        masterRef = mRef;
        spawnClientsFromFile("input/client_requests.txt");
    }


    public void spawnClientsFromFile(String fileName) {
        clients = new Client[NUMOFCLIENTS];
        for(int id = 1;id <= NUMOFCLIENTS; id++){
            clients[id-1] = new Client(masterRef);
        }

        File file = new File(fileName); //Enter name of file here
        try {
            Scanner scfile = new Scanner(file);
            while (scfile.hasNextLine()) {
                String[] inputline = scfile.nextLine().split(",");
                System.out.println(inputline[1]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        Client client = new Client(masterRef);
        client.run();
    }
}
