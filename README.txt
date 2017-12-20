--Procedure--
We use one client thread sending all the requests to the master.
The master keeps a queue for the incoming requests and a queue of available workers.
When the request queue is not empty, the master distributes one request to each worker. 
If there are more requests than workers, the master waits for an available worker to reappear. (The master may have several requests waiting but a worker may only have one!)
Activity of workers is measured using millisecond of time inside function calls. 



--Work distriubtion--
Thomas Peterson:
	Main.java
	Master.java
	Worker.java


Shanan Lynch:
	inputModule.java
	message.java
	request.java