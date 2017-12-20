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
            new Thread(clients[id-1]).start();

        }



        File file = new File(fileName); //Enter name of file here
        try {
            Scanner scfile = new Scanner(file);
            while (scfile.hasNextLine()) {
                String[] inputline = scfile.nextLine().split(",");
                int cid = Integer.parseInt(inputline[0]);
                Request req = new Request(cid,inputline[1],Integer.parseInt(inputline[2]));
                clients[cid-1].add(req);
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
