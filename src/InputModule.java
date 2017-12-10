import java.util.Scanner;


public class InputModule {
    //Parse input file for requests and spawn clients
    private Master masterRef;
    private int maxClients  = 20;
    public InputModule(Master mRef){
        masterRef = mRef;
        Client myClient = new Client(masterRef,1);
    }
}
