public class Worker implements Runnable{
    private Master masterRef;
    public Worker(Master mRef){
        masterRef = mRef;
        System.out.println("Worker initialized");
    }

    @Override
    public void run(){

    }
}
