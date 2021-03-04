/**
 * the stackunderflow exception class which sends a message
 * if the user tries to execute a pop operation on an full stack
 * @author binya
 *
 */
public class StackOverflowException extends Exception{
	public StackOverflowException(String overflow_Ex) {
		super(overflow_Ex);
	}

}
