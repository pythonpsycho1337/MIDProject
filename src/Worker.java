import java.util.concurrent.ConcurrentLinkedQueue;

import static java.lang.Thread.sleep;

public class Worker implements Runnable{
    //Essential variables
    private Master masterRef;
    private int id;
    private ConcurrentLinkedQueue<Request> currentRequest;//By design the queue will never have more than 1 element. It is used
    //for the concurrency protection

    //Logging variables
    private int workDone = 0;//Keeps track of milliseconds worked
    private String executedFunctionCalls = "";//Keep track of the function calls that has been done in order
    private int callsTellmenow = 0;//Calls to tell me now
    private int callsCountPrimes = 0;//Calls to countPrimes
    private int callsOracle418 = 0;//Calls to Oracle418

    public Worker(Master mRef, int idNum){
        masterRef = mRef;
        id = idNum;
        currentRequest = new ConcurrentLinkedQueue<Request>();
        System.out.println("Worker "+String.valueOf(id)+" initialized");
    }

    public Request getCurrentRequest(){
        return currentRequest.peek();
    }

    public int getWorkDone(){
        return workDone;
    }

    public String getExecutedFunctionCalls(){
        return executedFunctionCalls;
    }

    public int getCallsTellmenow() {
        return callsTellmenow;
    }

    public int getCallsCountPrimes() {
        return callsCountPrimes;
    }

    public int getCallsOracle418() {
        return callsOracle418;
    }

    @Override
    public void run(){
        masterRef.add_worker(this);
        waitForRequest();
    }

    public void waitForRequest(){
        while(true){
            while(currentRequest.peek() == null){//Polling
                try {
                    Thread.sleep(0,10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            execute_work(currentRequest.peek());//Response ignored for now..
            currentRequest.remove();
            masterRef.add_worker(this);
        }
    }

    public void handle_request(Request request){
        //Called from the master to ask the worker to handle a request
        currentRequest.add(request);
        System.out.println("[Worker"+String.valueOf(id)+"] Received request of client "+String.valueOf(request.getId()));
    }

    private Response execute_work(Request request){
        //Execute the client requested function call
        if (request.getFname().equals("tellmenow")){
            int returnValue = tellMeNow();
        }
        else if(request.getFname().equals("countPrimes")){
            int returnValue = countPrimes(request.getArgs()[0]);
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
        executedFunctionCalls += "t ";
        callsTellmenow += 1;
        return 0;
    }


    private int countPrimes(int n){
        functionCall(n*10);
        executedFunctionCalls += "c"+String.valueOf(n)+" ";
        callsCountPrimes += 1;
        return 0;
    }

    private int oracle418(){
        functionCall(200);
        executedFunctionCalls += "o ";
        callsOracle418 += 1;
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
