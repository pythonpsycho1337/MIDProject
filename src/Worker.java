public class Worker implements Runnable{
    private Master masterRef;
    private Request currentRequest;
    public Worker(Master mRef){
        masterRef = mRef;
        System.out.println("Worker initialized");
    }

    @Override
    public void run(){

    }

    public void handle_request(Request request){
        //Called from outside to ask the worker to handle a request
        System.out.println("Request received by worker");
    }

}
