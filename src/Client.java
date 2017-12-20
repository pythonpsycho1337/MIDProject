import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue; //See: https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ConcurrentLinkedQueue.html

import static java.lang.Thread.sleep;

public class Client implements Runnable {
    //Client
    //  A class simulating requests from a real client to a server
    private Master masterRef;
    private ConcurrentLinkedQueue<Response> responses;

    public Client(Master mRef){
        //public Client(Master mRef)
        //      Desctiption:
        //          Constructs the client object
        //      Params:
        //          mREF: Reference to master class
        masterRef = mRef;
        responses = new ConcurrentLinkedQueue<Response>();
    }

    @Override
    public void run(){
        String [] functions = {"tellmenow", "countprimes", "oracle418"};
        Random random = new Random();
        int select;

        for(int i = 1;i <= 100; i++){
            select = random.nextInt(functions.length);
            send_request(new Request(i,functions[select],null));
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        send_request(new Request(-1,null,null));//Send "end of requests" message to master

        Response answer = recieve_response();
        System.out.printf("\nClient happy, answer:"+Integer.toString(answer.getResponse()));
    }

    private void send_request(Request req){
        System.out.println("[Client] Sending request "+String.valueOf(req.getId()));
        masterRef.add_request(req);
    }

    private Response recieve_response() {
        while(responses.isEmpty()){
            try {//Sleeping
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Response r = responses.poll();
        return r;
    }

    public void store_response(Response response){
        //Called from outside to store a response in client class
        System.out.println("[Client"+String.valueOf(response.getId())+"] Response "+String.valueOf(response.getId())+"Received");
        responses.add(response);
    }
}
