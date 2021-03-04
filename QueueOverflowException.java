/**
 * the queueoverflow exception class which sends a message
 * if the user tries to execute a enqueue operation on an full queue
 * @author binya
 *
 */
public class QueueOverflowException extends Exception{
	public QueueOverflowException(String overflow_Ex) {
		super(overflow_Ex);
	}

}