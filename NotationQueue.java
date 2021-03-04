import java.util.ArrayList;
/**
 * the generic queue class called NotationQueue
 * which implements the generic interface QueueInterface
 * @author binya
 *
 * @param <T>
 */
public class NotationQueue <T> implements QueueInterface<T> {
	//data fields
	protected T qList[];
	protected int front, back, length, size;
	
	/** provide two constructors 
	 * 1. takes an int as the size of the queue
	 * 2. default constructor - uses a default as the size of the queue
	 * 
	 */
	public NotationQueue (int size) {
		this.size = size;
		length = 0;
		qList = (T[]) new Object[size];
		front = -1;
		back = -1;
	}
	
	public NotationQueue () {
		this(20);
	}
	
	/**
	 * Number of elements in the Queue
	 * @return the number of elements in the Queue
	 */
	public int size() {
		return length;
	}
	
	/**
	 * Determines if Queue is empty
	 * @return true if Queue is empty, false if not
	 */
	public boolean isEmpty() {
		return front == -1;
	}
	
	/**
	 * Determines of the Queue is full
	 * @return true if Queue is full, false if not
	 */
	public boolean isFull() {
		return front == 0 && back == -1;
	}
	
	/**
	 * Deletes and returns the element at the front of the Queue
	 * @return the element at the front of the Queue
	 */
	public T dequeue() throws QueueUnderflowException {
		if(isEmpty()) {
			throw new QueueUnderflowException("Queue is empty");
		}
		else {
			length--;
			T element = qList[front];
			if(front == back) {
				front = -1;
				back = -1;
			}
			else {
				front++;
			}
			return element;
		}
	}
	
	/**
	 * Adds an element to the end of the Queue
	 * @param e the element to add to the end of the Queue
	 * @return true if the add was successful, false if not
	 */
	public boolean enqueue(T e) throws QueueOverflowException {
		if(back == -1) {
			front = 0; 
			back = 0;
			qList[back] = e;
		}
		else if (back + 1 > size) {
			throw new QueueOverflowException("Queue is full");
		}
		else if (back + 1 < size) {
			qList[++back] = e;
		}
		length++;
		return true;
	}
	
	/**
	 * Returns the string representation of the elements in the Queue, 
	 * the beginning of the string is the front of the queue
	 * @return string representation of the Queue with elements
	 */
	public String toString() {
		StringBuilder queueString = new StringBuilder();
		for(int count = front; count < back; count++) {
			queueString.append(qList[count].toString());
		}
		return queueString.toString();
	}
	
	/**
	 * Returns the string representation of the elements in the Queue, the beginning of the string is the front of the queue
	 * Place the delimiter between all elements of the Queue
	 * @return string representation of the Queue with elements separated with the delimiter
	 */
	public String toString(String delimiter) {
		StringBuilder queueString = new StringBuilder();
		for(int count = front; count < back; count++) {
			queueString.append(qList[count].toString());
			if(count < back)
				queueString.append(delimiter);
		}
		return queueString.toString();
	}
	
	/**
	  * Fills the Queue with the elements of the ArrayList, First element in the ArrayList
	  * is the first element in the Queue
	  * YOU MUST MAKE A COPY OF LIST AND ADD THOSE ELEMENTS TO THE QUEUE, if you use the
	  * list reference within your Queue, you will be allowing direct access to the data of
	  * your Queue causing a possible security breech.
	  * @param list elements to be added to the Queue
	  */
	public void fill(ArrayList<T> list) {
		qList = (T[]) new Object[size];
		length = 0;
		

		try {
			for(T qq: list) {
				enqueue(qq);
			}
		}
		catch (QueueOverflowException excep) {
			System.out.print(excep.toString());
		}
		
	}
}
