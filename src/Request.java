import java.lang.reflect.Array;

public class Request extends Message{
    public int[] args;

    public int[] getArgs(){
        return args;
    }

    public Request(int idNum, String functionName, int[] args) {
        super(idNum, functionName);
        this.args = args;
    }
}
