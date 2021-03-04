/**
 * the stackunderflow exception class which sends a message
 * if the user tries to execute a pop operation on an empty stack
 * @author binya
 *
 */
public class StackUnderflowException extends Exception {
	public StackUnderflowException(String underflow_Ex) {
		super(underflow_Ex);
	}

}
