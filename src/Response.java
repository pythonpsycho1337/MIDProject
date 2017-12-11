public class Response extends Message {
    public Object returnValue;

    public int getId(){
        return this.id;
    }
    public String getFname(){
        return this.fname;
    }
    public Object getResponce(){ return this.returnValue; }

}
