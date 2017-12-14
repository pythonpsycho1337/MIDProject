import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue; //See: https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ConcurrentLinkedQueue.html

import static java.lang.Math.round;

public class Master implements Runnable{
    private ConcurrentLinkedQueue<Request> requests;
    private ConcurrentLinkedQueue<Worker> workers;
    private Worker[] workerArray;
    private final int MAXWORKERS = 10;

    public Master(){
        System.out.println("Master initialized");
        requests = new ConcurrentLinkedQueue<Request>();
        workers = new ConcurrentLinkedQueue<Worker>();
        workerArray = new Worker[10];

        for(int i = 0; i < MAXWORKERS; i++){
            Worker worker = new Worker(this,i+1);
            workers.add(worker);
            workerArray[i] = worker;

        }
    }

    @Override
    public void run(){
        boolean running = true;
        System.out.println("Running master");
        while(running){
            while(!requests.isEmpty()){
                Request r = requests.poll();
                System.out.println("[Master] Handling request:"+Integer.toString(r.getId()));
                if (r.getId() == -1){//End signal
                    System.out.println("[Master] Received last request");
                    running = false;
                    break;
                }

                while(workers.isEmpty());//Wait for a worker
                Worker w = workers.poll();
                w.handle_request(r);
            }
            try{
                Thread.sleep(5);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        generateStatistics();
    }

    public void add_request(Request request){
        requests.add(request);
    }

    public void add_worker(Worker worker){
        System.out.println("[Master] recieved worker:"+Integer.toString(worker.getId()));
        workers.add(worker);
    }

    public void generateStatistics(){
        System.out.println("\n\n\n---------------Statstics---------------");

        float costSum = 0;
        for(int i = 0; i < MAXWORKERS;i++){//Calculate costSum
            costSum += workerArray[i].getWorkDone();
        }

        System.out.println("Work distribution:");
        for(int i = 0; i < MAXWORKERS;i++){//Calculate percentages
            float workDone = workerArray[i].getWorkDone();
            int percentage = round(100*workDone/costSum);
            System.out.println("\tWorker "+String.valueOf(i)+":"+String.valueOf(percentage)+"%");
        }
    }
}
