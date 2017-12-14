import java.lang.reflect.Array;

public class Request extends Message{
    public Object[] args;

    public Object[] getArgs(){
        return args;
    }

    public Request(int idNum, String functionName, Object[] args) {
        super(idNum, functionName);
        this.args = args;
    }
}
