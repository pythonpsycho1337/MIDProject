public class Response extends Message {
    //NOT IN USE, IGNORE
    private int returnValue;
    private int workerId;

    public Response(int wId,int cId, String functionName, int returnValue) {
        super(cId, functionName);
        this.workerId = wId;
        this.returnValue = returnValue;
    }

    public int getWorkerId(){
        return workerId;
    }

    public int getReturnValue(){
        return returnValue;
    }

}
