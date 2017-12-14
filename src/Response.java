public class Response extends Message {
    public int returnValue;

    public Response(int idNum, String functionName, int returnValue) {
        super(idNum, functionName);
        this.returnValue = returnValue;
    }

    public int getResponse(){
        return returnValue;
    }

}
