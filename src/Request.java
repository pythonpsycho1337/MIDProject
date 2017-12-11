import java.lang.reflect.Array;

public class Request extends Message{
    public Object[] args;

    public int getId(){
        return this.id;
    }
    public String getFname(){
        return this.fname;
    }
    public Object[] getArgs(){ return this.args; }
}
