public class Main {
    public static void main(String[] args){
        Master master = new Master();
        InputModule inputModule = new InputModule(master);
        new Thread(master).start();
        new Thread(inputModule).start();
    }
}
