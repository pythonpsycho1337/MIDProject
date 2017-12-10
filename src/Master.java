import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketTimeoutException;
import java.util.Iterator;
import java.util.LinkedList;

public class Master implements Runnable{
    private LinkedList<Message> requests;
    private LinkedList<Worker> workers;
    public Master(){
        requests = new LinkedList<Message>();
        workers = new LinkedList<Worker>();

        Worker myWorker = new Worker(this);
        workers.add(myWorker);
        System.out.println("Master initialized");
    }

    @Override
    public void run(){
        boolean running = true;
        while(running){
            Iterator<Message> iterator = requests.iterator();
            while(iterator.hasNext()){
                Message M = iterator.next();

            }
            try{
                Thread.sleep(20);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public void handle_client_request(Client client, int req){

    }

    public void add_request(Message m){
        System.out.println("Message recieved:"+m.toString());
        requests.add(m);
    }
}
