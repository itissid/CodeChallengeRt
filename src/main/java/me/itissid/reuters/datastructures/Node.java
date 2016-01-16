package me.itissid.reuters.datastructures;

public class Node {
	// Pointer to the next node
	private Node next;
	// Pointer to the prev node.
	private Node prev;
	
	private final Byte data;
	
	public Node(Byte data) {
		this.data = data;
	}

	public byte getData() {
		return data;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}

	public Node getPrev() {
		return prev;
	}

	public void setPrev(Node prev) {
		this.prev = prev;
	}

	public String toString() {
		return new String(new byte[]{data});
	}
}
