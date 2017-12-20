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
            new Thread(worker).start();
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

                while(workers.isEmpty()) {//Wait for a worker
                    try {
                        Thread.sleep(0,10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                };
                Worker w = workers.poll();
                w.handle_request(r);
            }
            try{
                Thread.sleep(5);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        boolean continueCheck = true;
        while(continueCheck){
            continueCheck = false;
            for(int i = 0; i < this.MAXWORKERS; i++){
                //System.out.println("Checking workers");
                if (workerArray[i].getCurrentRequest() != null){
                    continueCheck = true;
                    break;
                }
            }
        }
        System.out.println("Generating statistics");
        generateStatistics();
    }

    public void add_request(Request request){
        requests.add(request);
    }

    public void add_worker(Worker worker){
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
            double workDone = workerArray[i].getWorkDone();
            double percentage = round(10000*100*workDone/costSum)/10000.0;
            System.out.println("\tWorker "+String.valueOf(i)+":"+String.valueOf(percentage)+"%");
            System.out.println("\t\tWork done:["+workerArray[i].getExecutedFunctionCalls()+"]");
            System.out.println("\t\tCalls to tellmenow:"+String.valueOf(workerArray[i].getCallsTellmenow()));
            System.out.println("\t\tCalls to countPrimes:"+String.valueOf(workerArray[i].getCallsCountPrimes()));
            System.out.println("\t\tCalls to oracle418:"+String.valueOf(workerArray[i].getCallsOracle418()));
            System.out.println();
        }

        System.out.println("\n------------End of Statstics------------");
    }
}
