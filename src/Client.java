import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

public class Client implements Runnable {
    private Master masterRef;
    private LinkedList<Message> messages;
    public Semaphore messagesSem;

    public Client(Master mRef, int req){
        masterRef = mRef;
        messages = new LinkedList<Message>();
        messagesSem = new Semaphore(1);
    }

    @Override
    public void run(){
        send_request(1);
        int answer = recieve_response();
        System.out.printf("\nClient happy, answer:"+Integer.toString(answer));
    }

    private void send_request(int req){
        Message m = new Message(this,req);
        masterRef.add_request(m);
    }

    private int recieve_response() {
        try {
            messagesSem.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Iterator<Message> iterator = messages.iterator();

        boolean hasNext = iterator.hasNext();
        while(!hasNext){
            messagesSem.release();

            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                messagesSem.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            iterator = messages.iterator();
            hasNext = iterator.hasNext();
        }//Wait for answer

        Message m = iterator.next();
        messagesSem.release();

        return m.getResponse();
    }

    public void store_message(Message message) throws InterruptedException {
        //Called from outside to store a message in client class
        messagesSem.acquire();
        System.out.println("Message received by client");
        messages.add(message);
        messagesSem.release();
    }


}
