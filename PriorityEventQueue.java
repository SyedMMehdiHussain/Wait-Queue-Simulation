import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.Queue;

/*We are implementing the Queue interface on priorityEventQueue class which is generic
 * and is implemented by singly linked list 
 * 
 */
public class PriorityEventQueue<E extends Comparable<E>> extends AbstractQueue<E> implements Queue<E>{

	private Node<E> front;
	private Node<E> rear;
	private int size;
	/*
	 * this is the inner node class
	 */
	private static class Node<E> {
		
		/**
		 * @return the data
		 */
		@SuppressWarnings("unused")
		public E getData() {
			return data;
		}

		/**
		 * @param data the data to set
		 */
		@SuppressWarnings("unused")
		public void setData(E data) {
			this.data = data;
		}

		private E data;
		private Node<E> next;
		
		private Node(E dataItem) {
		data = dataItem;
		next = null;
		}
	
		private Node(E dataItem, Node<E> nodeRef) {
		data = dataItem;
		next = nodeRef;
		}
		
		public void displayNode() {
			  System.out.print( data + "  \n");
		}     
		
}
	private Node<E> getNode(int index) {
		Node<E> temp = front;

		for (int i = 0; i< index && temp != null; i++)
			temp=temp.next;
		return temp;
	}
	
	public E get (int index){
		if (index<0 || index > size)	
			throw new IndexOutOfBoundsException(Integer.toString(index));
		Node<E> temp = getNode(index);
		return temp.data;
}
	
	public E set (int index, E item){
		if (index<0 || index > size)	
			throw new IndexOutOfBoundsException(Integer.toString(index));
		Node<E> temp = getNode(index);
		E old = temp.data;
		temp.data = item;
		return old;
	}

	public PriorityEventQueue() {
	}

	
/**
 * this is the offer method which has been overridden. it adds the nodes through 
 * the rear and places each node in the queue at it its appropriate location in the order of the 
 * clock for which it dialed in	
 */
@Override
public boolean offer(E item) {
	Node<E> newNode  = new Node<>(item);
	Node<E> current = front;
    Node<E> previous=null;

		// 1) if queue is empty then
		if (front == null) {
			rear = newNode;
			front = rear;
			System.out.println(newNode.data);
			size++;
			return true;
		} 
		//2) if newNode should be placed at first place in queue
		if(item.compareTo(front.data)<0){
	         newNode.next=front;
	         front=newNode; 
	         size++;//first ---> newNode
	         System.out.println(newNode.data);
	         return true;
		 	}
		 
		if(item.compareTo(front.data)==0){
			previous=current;
    		current=current.next;
    	
          newNode.next=previous.next; //make new node's next point to previous node's next
          previous.next=newNode;  
          size++;//make previous nodes' next point to new node.
          System.out.println(newNode.data);
          return true;
		}
		//3) if newNode should be at some position other than first. 
		
	     while(current!=null){
	            if(item.compareTo(current.data)>0){
	                  if(current.next==null){ //means current is last node, insert new node after current.
	                         current.next=newNode;
	                         size++;
	                         System.out.println(newNode.data);
	                         return true;
	                  }
	                  
	                  previous=current;
	                  current=current.next;//move to next node.
	            
	            	}
	           
	            else{	
	                  newNode.next=previous.next; //make new node's next point to previous node's next
	                  previous.next=newNode;  
	                  size++;//make previous nodes' next point to new node.
	                  System.out.println(newNode.data);
	                  return true;
	            }
	     }
		//else {
			// Allocate a new node at end, store item in it, and
			// link it to old end of queue.
			rear.next = new Node<E>(item);
			rear = rear.next;

		return true;
}

@Override
public E poll() {
		E item = peek();
		// Retrieve item at front.
		if (item == null)
			return null;
		// Remove item at front.
		front = front.next;
		size--; 
		return item;
		// Return data at front of queue.
}

/*
 * this is the remove method which removes from the front
 * @see java.util.AbstractQueue#remove()
 */
public E remove(){
	E item = peek();
	//remove item from front
	front=front.next;
	size--;
	return item;	
}

@Override
public E peek(){
	
	if (isEmpty())
		try {
			throw new Exception();
		} catch (Exception e) {
			System.out.println("Queue Empty");
		}
		
	return front.data;
}

public int getSize(){
	return size;
}

@Override
public boolean isEmpty() {
	return size==0;
}


public void displayLinkedList() {
    System.out.print("Displaying the Priority Queue (head node to last node): ");
    Node<E> temp = front; // start at the beginning of linkedList
    while (temp != null){ // Executes until we don't find end of list.
           temp.displayNode();
           temp = temp.next; // move to next Node
    }
    System.out.println();
}
	
@Override
public int size() {
		return size;
	}

@Override
public Iterator<E> iterator() {
	return null;
}


}
