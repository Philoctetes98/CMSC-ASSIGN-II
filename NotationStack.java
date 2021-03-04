import java.util.ArrayList;
/**
 * the generic stack class called NotationStack
 * which implements the generic interface StackInterface
 * @author binya
 *
 * @param <T>
 */
public class NotationStack<T> implements StackInterface<T>{
	//data fields
	private T theList[];
	private int size;
	private int index;
	
	/**
	 * Provide two constructors
	 * 1. takes in an int as the size of the stack
	 * 2. default constructor - uses default as the size of the stack
	 * @param size of the stack
	 */
 
	public NotationStack(int size) {
		this.size = size;
		theList = (T[]) new Object[size];
		index = 0;
	}
	
	public NotationStack() {
		this(20);
	}
	
	/**
	 * Method which describes an empty stack
	 * and is used to determine if a stack is empty 
	 * @return a 0 index indicating an empty stack
	 */
	public boolean isEmpty() {
		return index == 0;
	}
	
	/**
	 * Method which describes a full stack
	 * and is used to determine if a stack is full 
	 * @return the size of the list or full stack  
	 */
	public boolean isFull() {
		return index == size;
	}
	
	/**
	 * Number of elements in the Stack
	 * @return the number of elements in the Stack
	 */
	public int size() {
		return index;
	}
	
	/**
	 * Method which deletes the element at the top of 
	 * the stack. throws the StackUnderflowException message
	 * if Stack is empty
	 * @return the element at the top of the Stack
	 */
	public T pop() throws StackUnderflowException {
		if (isEmpty()) {
			throw new StackUnderflowException ("Stack is empty");
		}
		return (theList[--index]);
	}
	
	/**
	 * Returns the element at the top of the Stack,
	 * does not pop it off the Stack. throws the StackUnderflowException message
	 * if Stack is empty
	 * @return the element at the top of the Stack
	 */
	public T top() throws StackUnderflowException {
		if (isEmpty()) {
			throw new StackUnderflowException ("Stack is empty");
		}
		return (theList[index-1]);
	}
	
	/**
	 * Adds an element to the top of the Stack
	 * throws the StackOverflowException message
	 * if Stack is full 
	 * @param e the element to add to the top of the Stack
	 * return true if the add was successful, false if not
	 */
	public boolean push(T e) throws StackOverflowException {
		if (isFull()) {
			throw new StackOverflowException ("Stack is full");
		}
		theList[index] = e;
		index++;
		return true;
	}
	
	/**
	 * Returns the elements of the Stack in a string from bottom to top, the beginning 
	 * of the String is the bottom of the stack
	 * @return an string which represent the Objects in the Stack from bottom to top
	 */
	public String toString() {
		StringBuilder stackString = new StringBuilder();
		for(int count = 0; count < index; count++) {
			stackString.append(theList[count].toString());
		}
		return stackString.toString();
	}
	
	/**
	 * Returns the string representation of the elements in the Stack, the beginning of the 
	 * string is the bottom of the stack
	 * Place the delimiter between all elements of the Stack
	 * @return string representation of the Stack from bottom to top with elements 
	 * separated with the delimiter
	 */
	public String toString(String delimiter) {
		StringBuilder stackString = new StringBuilder();
		for(int count = 0; count < index; count++) {
			stackString.append(theList[count].toString());
			if(count != (index - 1)) {
				stackString.append(delimiter);
			}
		}
		return stackString.toString();
	}
	
	/**
	  * Fills the Stack with the elements of the ArrayList, First element in the ArrayList
	  * is the first bottom element of the Stack
	  * YOU MUST MAKE A COPY OF LIST AND ADD THOSE ELEMENTS TO THE STACK, if you use the
	  * list reference within your Stack, you will be allowing direct access to the data of
	  * your Stack causing a possible security breech.
	  * @param list elements to be added to the Stack from bottom to top
	  */
	public void fill(ArrayList<T> list) {
		theList = (T[]) new Object[size];
		index = 0;
		
		try {
			for(T gg: list) {
				push(gg);
			}
		}
		catch (StackOverflowException excep) {
			System.out.print(excep.toString());
		}
		
	}

}
