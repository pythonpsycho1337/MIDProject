import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue; //See: https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ConcurrentLinkedQueue.html

import static java.lang.Thread.sleep;

public class Client implements Runnable {
    private Master masterRef;
    private ConcurrentLinkedQueue<Response> responses;

    public Client(Master mRef, int req){
        masterRef = mRef;
        responses = new ConcurrentLinkedQueue<Response>();
    }

    @Override
    public void run(){
        send_request(1);
        Response answer = recieve_response();
        System.out.printf("\nClient happy, answer:"+Integer.toString(answer.getResponse()));
    }

    private void send_request(int req){
        Response r = new Response(this,req);
        masterRef.add_request(r);
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
        System.out.println("Message received by client");
        responses.add(response);
    }


}
