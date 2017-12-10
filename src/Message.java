public class Message {
    private Object source;
    private int data;
    public Message(Object s, int d){
        source= s;
        data = d;
    }

    public int getRequest(){
        return data;
    }

    public int getResponse(){
        return data;
    }

    public String toString(){
        return Integer.toString(data);
    }

}
