import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue; //See: https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ConcurrentLinkedQueue.html

import static java.lang.Math.round;

public class Master implements Runnable{
    //Keeps track of workers
    private ConcurrentLinkedQueue<Request> requests;//Queue of requests to handle
    private ConcurrentLinkedQueue<Worker> workers;//Queue of available workers
    private Worker[] workerArray;//Array of all the workers
    private final int MAXWORKERS = 10;//Maximum amount of workers

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
        //Distribute requests to workers
        boolean running = true;
        System.out.println("[Master] Initialized");
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
        //Add a request to the master's request queue
        requests.add(request);
    }

    public void add_worker(Worker worker){
        //Add a worker to the master's worker queue
        workers.add(worker);
    }

    public void generateStatistics(){
        //Generate the statistics of the run and
        //output them to the console and to [PROJECT ROOT]/output.txt
        String out = "";
        out += "\n\n\n---------------Statstics---------------\n";

        float costSum = 0;
        for(int i = 0; i < MAXWORKERS;i++){//Calculate costSum (Cost summary)
            costSum += workerArray[i].getWorkDone();
        }

        out += "Work distribution:\n";
        for(int i = 0; i < MAXWORKERS;i++){//Calculate percentages
            double workDone = workerArray[i].getWorkDone();
            double percentage = round(10000*100*workDone/costSum)/10000.0;
            out += "\tWorker "+String.valueOf(i)+":"+String.valueOf(percentage)+"%\n";
            out += "\t\tWork done:["+workerArray[i].getExecutedFunctionCalls()+"]\n";
            out += "\t\tCalls to tellmenow:"+String.valueOf(workerArray[i].getCallsTellmenow())+"\n";
            out += "\t\tCalls to countPrimes:"+String.valueOf(workerArray[i].getCallsCountPrimes())+"\n";
            out += "\t\tCalls to oracle418:"+String.valueOf(workerArray[i].getCallsOracle418())+"\n";
            out += "\n";
        }
        out += "\n------------End of Statstics------------";

        //Print statistics to console
        System.out.print(out);

        //output statistics to file
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("output.txt", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        writer.print(out);
        writer.close();
    }
}
