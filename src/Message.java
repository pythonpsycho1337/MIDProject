public class Message {
    private int id;
    private String fname;//function name

    public Message(int idNum,String functionName){
        id = idNum;
        fname = functionName;
    }

    public int getId(){
        return id;
    }
    public String getFname(){
        return fname;
    }
}
