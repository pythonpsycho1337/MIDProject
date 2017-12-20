public class Worker implements Runnable{
    private Master masterRef;
    private int id;
    private Request currentRequest;
    private int workDone = 0;

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

    }

    public void handle_request(Request request){
        //Called from the master to ask the worker to handle a request
        currentRequest = request;
        Response R = execute_work(request);
        masterRef.add_worker(this);
        System.out.println("[Worker"+String.valueOf(id)+"] Received request "+String.valueOf(request.getId()));
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

        return new Response(request.getId(),request.getFname(),0);//0 meaning ok!
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
            Thread.sleep(cost);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        workDone += cost;
    }
}
