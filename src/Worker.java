import java.util.concurrent.ConcurrentLinkedQueue;

import static java.lang.Thread.sleep;

public class Worker implements Runnable{
    private Master masterRef;
    private int id;
    private int workDone = 0;
    private Request currentRequest;//queue only used because of concurrency handling. Length will never be more than 1

    public Worker(Master mRef, int idNum){
        masterRef = mRef;
        id = idNum;
        System.out.println("Worker "+String.valueOf(id)+" initialized");
    }

    public int getId(){
        return id;
    }

    public Request getCurrentRequest(){
        return currentRequest;
    }

    public int getWorkDone(){
        return workDone;
    }

    @Override
    public void run(){
        masterRef.add_worker(this);
        waitForRequest();
    }

    public void waitForRequest(){
        while(true){
            while(currentRequest == null){
                try {
                    Thread.sleep(0,10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Response R = execute_work(currentRequest);
            currentRequest = null;
            System.out.println("Response ready");
            masterRef.add_worker(this);
        }
    }

    public void handle_request(Request request){
        //Called from the master to ask the worker to handle a request
        currentRequest = request;
        System.out.println("[Worker"+String.valueOf(id)+"] Received request of client "+String.valueOf(request.getId()));
    }

    private Response execute_work(Request request){
        //Execute the client requested function call
        if (request.getFname().equals("tellmenow")){
            int returnValue = tellMeNow();
        }
        else if(request.getFname().equals("countPrimes")){
            int returnValue = countPrimes(10);
        }
        else if(request.getFname().equals("418Oracle")){
            int returnValue = oracle418();
        }
        else{
            System.out.println("[Worker"+String.valueOf(this.id)+"] WARNING function name not recognized, dropping request");
        }

        return new Response(id,request.getId(),request.getFname(),0);//0 meaning ok!
    }

    //-----Client Callable Functions-----
    private int tellMeNow(){
        functionCall(5);
        return 0;
    }


    private int countPrimes(int n){
        functionCall(n*10);
        return 0;
    }

    private int oracle418(){
        functionCall(200);
        return 0;
    }

    private void functionCall(int cost){
        try {
            sleep(cost);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        workDone += cost;
    }
}
