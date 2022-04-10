/**
 * This is where I practice implementing data structures
 * 
 * @author Chijioke Umezinwa
 */

public class scratchWork{


}

//first lets implement a singly linked list
// x => x => ... x
public class singlyLinkedList<T> implements Iterable<T>{
	//we first record the size
	private int size = 0;
	//then we record the head and tail of this list
	private Node<T> head=null;
	private Node<T> tail=null;

	//next we need to establish a node class for this list
	private class Node<T>{
		//the node attributes: data, next
		T data;
		Node<T> next;

		public Node(T data){
			this.data=data;
			this.next=null;
		}

		//we can create a to string method to make it easier
		//to return the value of a node
		@Override
		public String toString(){
			return data.toString();
		}
	}

	//Next we need to create the clear method.
	public void clear(){
		//We create a spare node to traverse the list with
		Node<T> trav=head;

		//We then traverse the list, clearing each element along
		//the way
		while(trav != null){
			//we need a spare node to store th next node
			Node<T> next = trav.next;
			trav.data=null;
			trav.next=null;
			trav=next;
		}

		//nullify head, tail, and trav
		head=tail=trav=null;

		//make size zero
		size=0;
	}

	//lets make a size method
	public int size(){
		return size;
	}

	//lets make a isEmpty method to see if list is empty
	public boolean isEmpty(){
		return size()==0;
	}

	//now lets make a generic add method that really just adds 
	//to back of list
	public void add(T data){
		addLast(data);
	}

	//lets implement addLast
	public void addLast(T data){
		//check to see if list is empty
		if(isEmpty())
			head=tail=new Node<T>(data);
		else{
			tail.next=new Node<T>(data);
			tail=tail.next;
		}
		size++;
	}

	//lets implement addFirst
	public void addFirst(T data){
		//check to see if list is empty
		if(isEmpty())
			head=tail=new Node<T>(data);
		else{
			//Make a new node that holds the data you want to add
			//to the front of the list
			Node<T> newNode = new Node<>(data);
			//then make newNode point to head
			newNode.next=head;
			//make head equal to newnode
			head=newNode;
		}
		size++;
	}

	//lets implement addAt, a method that allows you to 
	//add an element at any index in the list
	public void addAt(int index, T data){
		//lets check our index
		if(index < 0 || index > size)
			throw new IllegalArgumentException("Illegal Index");

		//if index is 0, call addFirst
		if(index == 0)
			addFirst(data);

		//if index is equal to size, call addLast
		else if(index == size)
			addlast(data);

		else{
			//Create a spare node to traverse the list
			Node<T> current = head;
			for(int i = 1 ; i < index ; i++)
				current=current.next;

			//make a spare node to store the node currently 
			//at desired index
			Node<T> temp = current.next;
			//make the new node at this index by pointing the 
			//next pointer of the node before temp to a new node
			//with data you want to add
			current.next = new Node<T>(data);
			//finally reattach the node stored in temp
			(current.next).next=temp;
			//increment size
			size++;

		}
	}

	//we can make some peek methods- nothing too complicated
	public T peekFirst(){
		//check to see if list is empty
		if(isEmpty())
			throw new RuntimeException("Empty List");
		return head.data;//return data of head
	}

	public T peekLast(){
		//check to see if list is empty
		if(isEmpty())
			throw new RuntimeException("Empty List");
		return tail.data;//return data of tail
	}

	//next lets try to implement remove first
	public T removeFirst(){
		if(isEmpty())
			throw new RuntimeException("Empty List");
		else{
			//make a spare node to store head;
			Node<T> temp = head;
			//make head equal to its next node
			head = head.next;
			if(head==null){
				//if head is null, nullify tail and
				//make size zero
				tail=null;
				size=0;
			}
			size--;
			return temp.data;
		}
	}

	//lets try to implement remove last
	public T removeLast(){
		//check to see if the list is empty
		if(isEmpty())
			throw new RuntimeException("Empty List");

		//if size is just 1.
		else if(size == 1){
			//store only node in temp node
			Node<T> temp = head;
			//nullify head, tail, and make size zero
			head=tail=null;
			size=0;
			return temp.data;
		}
		else{
			//store tail node in temp node
			Node<T> temp = tail;

			//next we have to traverse through the list]
			//stopping only at the node just before tail
			Node <T> current = head;
			for(int i = 0 ; i < size - 2 ; i++)
				current = current.next;

			//tail is now going to equal current
			tail = current;
			//nullify tail's next node;
			tail.next = null;
			//decrement size
			size--;
			return temp.data;
		}
	}

	//lets implement remove at, a method that removes any 
	//element at any index in list
	public T removeAt(int index){
		//check the index
		if(index < 0 || index >= size)
			throw new IllegalArgumentException("Illegal Index");
		//if index is 0 call remove first
		else if(index == 0)
			return removeFirst();
		//if index is equal to size-1 call remove last
		else if(index == size - 1)
			return removeLast();

		else{
			//we have to create a spare node to traverse the list
			Node<T> previous = head;
			for(int i = 1 ; i < index ; i++)
				previous = previous.next;

			//create a new node previous to store the node we want to remove
			Node <T> current = previous.next;

			//skip over the desired node we want to remove
			previous.next = current.next;
			//decrement the size
			size--;
			return current.data;
		}
	}

	//lets implement remove, a method that removes the first
	//sighting of an element in the list
	public T remove(T element){
		//if the element is not in the list, throw an exception
		if(!contains(element))
			throw new RuntimeException("Element not in list");
		//find index of element we want to remove
		int element = indexOf(element);
		//call the removeAt method to actually remove the desired
		//element and return its result
		return removeAt(element)
	}

	//lets implement the contains method, a method that checks
	//to see if an element is in the list
	public boolean contains(T element){
		return indexOf(element) != -1;
	}

	//lets implement indexOf, a method that returns the index
	//of an element
	public int indexOf(T element){
		//make an index equal to zero
		int index = 0;

		//make a spare node to traverse the list
		Node<T> trav = head;
		for(; trav != null ; trav=trav.next , index++){
			if(trav.data == element)
				//the index is returned
				return index;
		}
		//if no index is reached, -1 is returned
		return -1;
	}

	//lets implement lastIndexOf, a method that returns the last
	//known index of an element
	public int lastIndexOf(T element){
		//make an index equal to zero
		int index = 0;
		//make a lastindexof variable equal to -1
		int lastIndexOf = -1;

		//make a spare node to travers the list
		Node<T> trav = head;
		for(; trav != null ; trav = trav.next , index++){
			if(trav.data == element)
				lastIndexOf = indexOf;
		}
		//lastIndexOf gets returned.
		return lastIndexOf;
	}

	//lets implement get method
	public T get(int index){
		//check the index.
		if(index < 0 || index >= size)
			throw new IllegalArgumentException("Illegal Index");
		//if index is 0 return peekfirst
		else if(index == 0)
			return peekFirst();

		//if index is size - 1 return peeklast
		else if(index == size - 1)
			return peekLast();

		else{
			//Make a spare node to go through list
			Node <T> prev = head;
			for(int i = 1 ; i < index ; i++)
				prev = prev.next;
			//make a spare node to store desired node
			Node<T> current = prev.next;
			//return the value of the node
			return current.data;
		}
	}

	//lets implement the set method, a method that will
	//change the node at a given index.
	public T set(int index, T element){
		//lets check the index
		if(index < 0 || index >= size)
			throw new IllegalArgumentException("Illegal Index");
		//check to see if list is empty
		else if(isEmpty())
			throw new RuntimeException("Empty List");

		//check on index if its zero
		else if(index == 0){
			//collect former head's data
			T data = removeFirst();
			//add new head
			addFirst(element);
			//return data
			return data;
		}

		else if(index == size - 1){
			//collect former tail's data
			T data = removeLast();
			//add new tail
			addLast(element);
			//return data
			return data;
		}

		else{
			//collect data of removed node
			T data = removeAt(index);
			//add an element at desired index
			addAt(index , element);
			//return data
			return data;
		}
	}

	/**
	 * Iterator!!! cover later
	 */

}

