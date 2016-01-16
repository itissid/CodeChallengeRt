package me.itissid.reuters;

import me.itissid.reuters.datastructures.Node;
/**
 * 
 * @author sidharth
 *
 */
public class LastBytesRead implements ILastBytesRead {
	// TODO: The requirements are for 1024 bytes to be read but this could be made configurable if need be.
	public static final int BYTES_TO_BE_READ = 1024;
	// Reusable buffer for printing the data
	private final byte[] tempBuffer = new byte[BYTES_TO_BE_READ];
	
	/**
	 * We store the data in a double ended queue.
	 * New data goes at the end of the queue.
	 */
	private final Node queueHead = new Node(null); // the head of the dequeue
	private Node queueTail = queueHead; // tail of the dequeue
	public LastBytesRead() {	}
	
	/**
	 * Adding a byte is constant time operation O(1)  
	 */
	public void recordByte(byte b) {
		queueTail.setNext(new Node(b));
		queueTail.getNext().setPrev(queueTail);
		queueTail = queueTail.getNext();

	}

	/**
	 * Printing the last N bytes is a constant time operation and uses
	 * BYTES_TO_BE_READ of space.
	 */
	public String printLastBytes() {
		Node tempTail = queueTail;
		int i = 0;
		while(tempTail != queueHead && i < BYTES_TO_BE_READ){
			// maintain the correct order
			tempBuffer[BYTES_TO_BE_READ - i - 1] = tempTail.getData();
			tempTail = tempTail.getPrev();
			i++;
		}
		return new String(tempBuffer, BYTES_TO_BE_READ - i, i);
	}

}