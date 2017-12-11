import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue; //See: https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ConcurrentLinkedQueue.html

public class Master implements Runnable{
    private ConcurrentLinkedQueue<Request> requests;
    private ConcurrentLinkedQueue<Worker> workers;

    public Master(){
        requests = new ConcurrentLinkedQueue<Request>();
        workers = new ConcurrentLinkedQueue<Worker>();

        Worker myWorker = new Worker(this);
        workers.add(myWorker);
        System.out.println("Master initialized");
    }

    @Override
    public void run(){
        boolean running = true;
        while(running){

            while(!requests.isEmpty()){
                Request r = requests.poll();
                while(workers.isEmpty());//Wait for a worker
                Worker w = workers.poll();
                w.add
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
