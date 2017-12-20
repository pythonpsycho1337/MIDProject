public class Main {
    //Initalization class, starts the program
    public static void main(String[] args){
        Master master = new Master();
        InputModule inputModule = new InputModule(master);
        new Thread(master).start();
        new Thread(inputModule).start();
    }
}
