//Test

//Let's try the generic item implementation of a stack

public class Stack<Item> implements Iterable<Item>{
	//lets use the linked list to implement our stack
	private SinglyLinkedList<Item> list = new SinglyLinkedList<Item> ();

	public Stack(){}

	public Stack(Item firstElem){
		push(firstElem);
	}

	public int size(){
		return list.size();
	}

	public boolean isEmpty(){
		return size()==0;
	}

	public void push(Item elem){
		list.addLast(elem);
	}

	public Item pop(){
		if(isEmpty())
			throw new java.util.EmptyStackException();
		return list.removeLast(elem);
	}

	public Item peek(){
		if(isEmpty())
			throw new java.util.EmptyStackException();
		return list.peekLast();
	}

	public int search(Item elem){
		return list.lastIndexOf(elem);
	}

	//Iterator
	//how to use?
	//Stack <Item> stack = new Stack<>();
	//Iterator iter = stack.iterator();
	//while(iter.hasNext())
	//	System.out.println(iter.next());
	@Override
	public java.util.Iterator<Item> iterator(){
		return list.iterator();
	}
}

//ok lets now implement a queue the same way
public class Queue <Item> implements Iterable<Item>{
	//we'll implement the queue with a Linked List
	private SinglyLinkedList<Item> list = new SinglyLinkedList<Item>();

	public Queue(){}

	public Queue(Item firstElem){
		offer(firstElem);
	}

	public int size(){
		return list.size();
	}

	public boolean isEmpty(){
		return size()==0;
	}

	public void offer(Item elem){
		if(isEmpty())
			throw new RuntimeException("Queue Empty");
		list.addLast(elem);
	}

	public Item poll(){
		if(isEmpty())
			throw new RuntimeException("Queue Empty");
		return list.removeFirst();
	}

	public Item peek(){
		if(isEmpty())
			throw new RuntimeException("Queue Empty");
		return list.peekFirst();
	}

	public int search(Item elem){
		return list.lastIndexOf(elem);
	}

	//Iterator
	//How to use?
	//Queue <Item> queue = new Queue<Item>();
	//Iterator iter = queue.iterator();
	//while(iter.hasNext())
	//		System.out.print(iter.next()+ " ");
	@Override
	public java.util.Iterator<Item> iterator(){
		return list.iterator();
	}
}

//now lets try to implement a singly linked list
public class SinglyLinkedList <Item> implements Iterable<Item>{
	//size, nodes head and tail
	private int size=0;
	private Node<Item>head=null;
	private Node<Item>tail=null;

	//Node class
	private static class Node<Item>{
		//Data, and Node next
		private Item data;
		private Node<Item>next;

		public Node(Item data){
			this.data=data;
			next=null;
		}
	}

	public int size(){
		return size;
	}

	public boolean isEmpty(){
		return size()==0;
	}

	public void clear(){
		Node<Item> trav=head;

		while(trav!=null){
			Node<Item> next = trav.next;
			trav.data=null;
			trav.next=null;
			trav=next;
		}
		head=tail=trav=null;
		size=0;
	}

	//add
	public void add(Item elem){
		addLast(elem);
	}

	//addLast
	public void addLast(Item elem){
		if(isEmpty())
			head=tail=new Node<Item>(elem);
		else{
			tail.next=new Node<Item>(elem);
			tail=tail.next;
		}
		size++;
	}

	//addFirst
	public void addFirst(Item elem){
		if(isEmpty())
			head=tail=new Node<Item>(elem);
		else{
			Node<Item> newNode=new Node<>(elem);
			newNode.next=head;
			head=newNode;
		}
		size++;
	}

	//addAt
	public void addAt(int index, Item elem){
		if(index<0 || index>size)
			throw new IllegalArgumentException();
		else if(index==0)
			addFirst(elem);
		else if(index==size)
			addLast(elem);
		else{
			Node<Item> current=head;
			for(int i = 1; i<index; i++)
				current=current.next;
			Node<Item> temp = current.next;
			current.next=new Node<Item>(elem);
			(current.next).next=temp;
			size++;
		}
	}

	//peekFirst
	public Item peekFirst(){
		if(isEmpty())
			throw new RuntimeException("Empty List");
		return head.data;
	}

	//peekLast
	public Item peekLast(){
		if(isEmpty())
			throw new RuntimeException("Empty List");
		return tail.data;
	}

	//remove Last
	public Item removeLast(){
		if(isEmpty())
			throw new RuntimeException("Empty List");

		else if(size==1){
			Node<Item> temp = head;
			head=tail=null;
			size=0;
			return temp.data;
		}

		else{
			Node<Item> current=head;
			for(int i = 0;i<size-2;i++)
				current=current.next;
			Node<Item> temp=tail;
			tail=current;
			tail.next=null;
			size--;
			return temp.data;
		}
	}

	//removeFirst
	public Item removeFirst(){
		if(isEmpty())
			throw new RuntimeException("Empty List");
		else{
			Node<Item> temp =head;
			head=head.next;
			size--;
			if(head==null)
				tail=null;
			return temp.data;
		}
	}

	//remove At
	public Item removeAt(int index){
		if(index<0 || index>=size)
			throw new IllegalArgumentException();
		else if(index==0)
			return removeFirst();
		else if(index==size-1)
			return removeLast();
		else{
			Node<Item> previous=head;
			for(int i = 1; i<index;i++)
				previous=previous.next;
			Node<Item> temp= previous.next;
			previous.next=temp.next;
			size--;
			return temp.data;
		}
	}
	//removes method
	public Item remove(Object obj){
		//see if obj is in list
		if(!contains(obj))
			throw new RuntimeException("Object not in list");
		int index=indexOf(obj);
		return removeAt(index);
	}

	//contains method
	public boolean contains(Object obj){
		return indexOf(obj) != -1;
	}

	//indexOf 
	public int indexOf(Object obj){
		int index=0;//start at beginning of list
		Node<Item> trav=head;

		if(obj == null){
			for(;trav!=null;trav=trav.next,index++){
				if(trav.data==null)
					return index;
			}
		}
		else{
			for(;trav!=null;trav=trav.next,index++){
				if(obj.equals(trav.data))
					return index;
			}
		}
		return -1;
	}

	//lastIndexOf
	public int lastIndexOf(Item elem){
		int index=0;
		int lastSavedIndex=-1;
		Node<Item> trav=head;

		for(;trav!=null;trav=trav.next,index++){
			if(trav.data == elem)
				lastSavedIndex=index;
		}
		return lastSavedIndex;
	}

	//set method
	public int set(int index, Item elem){
		if(index<0 || index>=size)
			throw new IllegalArgumentException();
		else if(index==0){
			int data=removeFirst();
			addFirst(elem);
			return data;
		}
		else if(index==size-1){
			int data=removeLast();
			addLast(elem);
			return data;
		}
		else{
			int data=removeAt(index);
			addAt(index,elem);
			return data;
		}
	}

	//get method
	public int get(int index){
		if(index<0 || index>=size)
			throw new IllegalArgumentException();
		else if(index==0){
			return peekFirst();
		}
		else if(index==size-1){
			return peekLast();
		}
		else{
			Node<Item> trav=head;
			for(int i =1; i<index;i++)
				trav=trav.next;
			Node<Item>temp=trav.next;
			return temp.data;
		}
	}

	//Iterator
	@Override
	public java.util.Iterator<Item> iterator(){
		return new LinkedListIterator();
	}

	private class LinkedListIterator implements java.util.Iterator<Item>{
		private Node<Item> current=head;

		@Override
		public boolean hasNext(){
			return (current != null);
		}

		@Override
		public Item next(){
			Item item = current.data;
			current=current.next;
			return item;
		}

		@Override
		public void remove(){
			throw new UnsupportedOperationException();
		}
	}
}

//lets try to do a doubly linked list
public class DoublyLinkedList<Item> implements Iterable<Item>{
	//size, Node head and tail
	private int size=0;
	private Node<Item> head=null;
	private Node<Item> tail=null;

	private static class Node<Item>{
		//data, Node prev and next
		private Item data;
		private Node<Item> prev;
		private Node<Item> next;

		public Node(Item data, Node<Item> prev, Node<Item>next){
			this.data=data;
			this.prev=prev;
			this.next=next;
		}

		@Override
		public String toString(){
			return data.toString();
		}
	}

	public int size(){
		return size;
	}

	public boolean isEmpty(){
		return size()==0;
	}

	public void clear(){
		Node<Item> trav=head;
		while(trav != null){
			Node<Item> next=trav.next;
			trav.data=trav.prev=trav.next=null;
			trav=next;
		}
		head=tail=trav=null;
		size=0;
	}

	//add
	public void add(Item elem){
		addLast(elem);
	}

	//addLast
	public void addLast(Item elem){
		if(isEmpty())
			head=tail=new Node<Item>(elem,null,null);
		else{
			tail.next=new Node<Item>(elem,tail,null);
			tail=tail.next;
		}
		size++;
	}

	//addFirst
	public void addFirst(Item elem){
		if(isEmpty())
			head=tail=new Node<Item>(elem,null,null);
		else{
			Node<Item> newNode = new Node<>(elem,null,head);
			head.prev=newNode;
			head=head.prev;
		}
		size++;
	}

	//addat
	public void addAt(int index, Item elem){
		if(index<0 || index>size)
			throw new IllegalArgumentException();

		else if(index==0)
			addFirst(elem);

		else if(index==size)
			addLast(elem);

		else{
			Node<Item> trav=head;
			for(int i=1; i<index;i++)
				trav=trav.next;
			Node<Item> newNode = new Node<>(elem, trav, trav.next);
			trav.next.prev=newNode;
			trav.next=newNode;

			size++;
		}
	}

	//peekfirst
	public Item peekFirst(){
		if(isEmpty())
			throw new RuntimeException("Empty List");
		return head.data;
	}

	public Item peekLast(){
		if(isEmpty())
			throw new RuntimeException("Empty List");
		return tail.data;
	}

	public Item removeLast(){
		if(isEmpty())
			throw new RuntimeException("Empty List");
		else if(size==1){
			Node<Item> temp=head;
			head=tail=null;
			size=0;
			return temp.data;
		}
		else{
			Node<Item> temp=tail;
			tail=tail.prev;
			tail.next=null;
			return temp.data;
		}
	}

	public Item removeFirst(){
		if(isEmpty())
			throw new RuntimeException("Empty List");
		else{
			Node<Item> temp = head;
			head=head.next;
			size--;
			if(head==null)
				tail=null;
			head.prev=null;
			return temp.data;
		}
	}

	public Item removeAt(int index){
		if(index<0 || index>=size)
			throw new IllegalArgumentException();
		else if(index==0)
			return removeFirst();
		else if(index==size-1)
			return removeLast();
		else{
			Node<Item> trav = head;
			for(int i=1; i<index; i++)
				trav=trav.next;
			Node<Item> temp= trav.next;
			trav.next=temp.next;
			trav.next.prev=trav;

			temp.prev=null;
			temp.next=null;
			size--;
			return temp.data;
		}
	}

	public Item remove(Object obj){
		if(!contains(obj))
			throw new RuntimeException("Object is not in list");
		int index=indexOf(obj);
		return removeAt(index);
	}

	public boolean contains(Object obj){
		return indexOf(obj) != -1;
	}

	public int indexOf(Object obj){
		int index=0;
		Node<Item> trav=head;

		if(obj==null){
			for(;trav!=null;trav=trav.next,index++){
				if(trav.data==null)
					return index;
			}
		}
		else{
			for(;trav!=null;trav=trav.next,index++)
				if(obj.equals(trav.data))
					return index;
		}
		return -1;
	}

	public int lastIndexOf(Item elem){
		int index=0;
		int lastSavedIndex=-1;
		Node<Index> trav=head;

		for(;trav!=null;trav=trav.next,index++){
			if(trav.data==elem)
				lastSavedIndex=index;
		}
		return lastSavedIndex;
	}

	//set method
	public Item set(int index, Item elem){
		if(index<0 || index>=size)
			throw new IllegalArgumentException();
		else if(index==0){
			Item data= removeFirst();
			addFirst(elem);
			return data;
		}
		else if(index==size-1){
			Item data=removeLast();
			addLast(elem);
			return data;
		}
		else{
			Item data=removeAt(index);
			addAt(index,elem);
			return data;
		}
	}

	//get
	public Item get(int index){
		
	}

}