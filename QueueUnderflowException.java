/**
 * the queueunderflow exception class which sends a message
 * if the user tries to execute a dequeue operation on an empty queue
 * @author binya
 *
 */
public class QueueUnderflowException extends Exception {
	public QueueUnderflowException(String queueUnderflow_Ex) {
		super(queueUnderflow_Ex);
	}

}