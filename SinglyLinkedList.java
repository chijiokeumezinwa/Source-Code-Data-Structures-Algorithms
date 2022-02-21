/**
*A singly linked list implementation in java
*
* @author Chijioke Umezinwa
*/

package DataStructuresAlgorithms;

public class SinglyLinkedList<T> implements Iterable<T> {
	private int size = 0;
	private Node<T> head = null;
	private Node<T> tail = null;

	private static class Node<T> {
		private T data;
		private Node<T> next;

		public Node(T data){
			this.data = data;
		}

		@Override
	    public String toString() {
	      return data.toString();
	    }
	}

	public void clear() {
		Node<T> trav = head;
		while(trav != null){
			Node<T> next = trav.next;
			trav.next=null;
			trav.data=null;
			trav=next;
		}

		head=tail=trav=null;
		size=0;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size()==0;
	}

	public void add(T elem){
		addLast(elem);
	}

	public void addLast(T elem){
		if(isEmpty())
			head=tail=new Node<T> (elem);
		else{
			tail.next=new Node<T> (elem);
			tail=tail.next;
		}
		size++;
	}

	public void addFirst(T elem){
		if(isEmpty())
			head=tail=new Node<T>(elem);
		else{
			Node<T> newNode = new Node<>(elem);
			newNode.next=head;
			head=newNode;
		}
		size++;
	}

	public void addAt(int index, T elem){
		if(index<0 || index>size)
			throw new IllegalArgumentException();
		if(index == 0)
			addFirst(elem);
		else if(index == size)
			addLast(elem);
		else{
			Node<T> current = head;
			for(int i =1; i<index; i++)
				current = current.next;
			Node<T> temp = current.next;
			current.next=new Node<T>(elem);
			(current.next).next=temp;
			size++;
		}
	}

	public T peekFirst(){
		if(isEmpty())
			throw new RuntimeException("Empty List");
		return head.data;
	}

	public T peekLast(){
		if(isEmpty())
			throw new RuntimeException("Empty List");
		return tail.data;
	}

	public T removeFirst(){
		if(isEmpty())
			throw new RuntimeException("Empty List");
		else{
			Node<T> temp = head;
			head=head.next;
			size--;
			if(head==null)
				tail=null;
			return temp.data;
		}
	}

	public T removeLast(){
		if(isEmpty())
			throw new RuntimeException("Empty List");
		else if(size==1){
			Node<T> temp=head;
			head=tail=null;
			size=0;
			return temp.data;
		}
		else{
			Node<T> current = head;
			for(int i =0; i< size-2; i++)
				current=current.next;
			Node<T> temp = tail;
			tail=current;
			tail.next=null;
			size--;
			return temp.data;
		}
	}

	public T removeAt(int index){
		if(index<0 || index>=size)
			throw new IllegalArgumentException();
		else if(index==0)
			return removeFirst();
		else if(index==size-1)
			return removeLast();
		else{
			Node<T>previous = head;
			for(int i =1; i< index; i++)
				previous = previous.next;
			Node<T> current = previous.next;
			previous.next=current.next;
			size--;
			return current.data;
		}
	}

	public boolean contains(Object obj){
		return indexOf(obj) != -1;
	}

	public int indexOf(Object obj){
		int index=0;
		Node<T> trav = head;
		if(obj == null){
			for(;trav != null; trav=trav.next, index++){
				if(trav.data==null)
					return index;
			}
		}
		else{
			for(;trav != null; trav=trav.next, index++){
				if(obj.equals(trav.data))
					return index;
			}
		}
		return -1;
	}

	public boolean contains(T elem){
		return indexOf(elem) != -1;
	}

	public int indexOf(T elem){
		int index=0;
		Node<T> trav =head;
		for(;trav != null; trav=trav.next, index++){
			if(trav.data==elem)
				return index;
		}
		return -1;
	}

	public int lastIndexOf(T elem){
		int index=0;
		int lastSavedIndex=-1;
		Node<T> trav=head;
		for(;trav != null; trav=trav.next, index++){
			if(trav.data == elem)
				lastSavedIndex=index;
		}
		return lastSavedIndex;
	}

	public T get(int index){
		if(index<0 || index>=size)
			throw new IllegalArgumentException();
		else if(index == 0)
			return peekFirst();
		else if(index == size-1)
			return peekLast();
		else{
			Node<T> previous = head;
			for(int i = 1; i< index; i++)
				previous=previous.next;
			Node<T> current = previous.next;
			return current.data;
		}
	}

	public T set(int index, T elem){
		if(index < 0 || index >= size)
			throw new IllegalArgumentException();
		else if(index == 0){
			T remove = removeFirst();
			addFirst(elem);
			return remove;
		}
		else if(index == size-1){
			T remove = removeLast();
			add(elem);
			return remove;
		}	
		else{
			T remove = removeAt(index);
			addAt(index,elem);
			return remove;
		}

	}

	@Override
	public java.util.Iterator<T> iterator() {
		return new LinkedListIterator();
	}
	
	private class LinkedListIterator implements java.util.Iterator<T>{
		private <T> current = head;

		@Override
		public boolean hasNext(){
			return (current != null);
		}

		@Override
		public T next(){
			T t = current.data;
			current=current.next;
			return e;
		}

		@Override
		public void remove(){
			throw new UnsupportedOperationException();
		}
	}	
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("[ ");
		Node<T> trav = head;
		while(trav != null){
			sb.append(trav.data);
			if(trav.next != null)
				sb.append(", ");
			trav=trav.next;
		}

		sb.append(" ]");
		return sb.toString();

	}
}