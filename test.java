//Test
//Singly Linked List

public class SinglyLinkedList{
	//size, head, and tail
	private int size= 0;
	private Node head=null;
	private Node tail=null;

	//Next we need to define the node class
	private static class Node{
		//data and next
		private int data;
		private Node next;

		public Node(int data){
			this.data=data;
			next=null;
		}
	}

	//next lets define the size, is empty methods
	public int size(){
		return size;
	}

	public boolean isEmpty(){
		return size()==0;
	}

	//next lets define the clear method
	public void clear(){
		Node trav= head;//start at head
		while(trav!=null){//while the node is not null
			Node next=trav.next;//store the next node of trav
			trav.data=null;
			trav.next=null;//nullify both aspects of the node
			trav=next;//move on to the next node
		}
		head=tail=trav=null;//nullify the remaining nodes
		size=0;//reset size to zero
	}

	//next lets define the add method
	public void add(int elem){
		addLast(elem);
	}

	//lets define the addlast method
	public void addLast(int elem){
		if(isEmpty())
			head=tail=new Node(elem);//make head and tail equal to a new node with elem stored
		else{
			tail.next=new Node(elem);//attach a new node with elem stored
			tail = tail.next;
		}
		size++;//increase the size at the end
	}

	//lets define the addfirst method
	public void addFirst(int elem){
		if(isEmpty())
			head=tail=new Node(elem);//make head and tail equal to a new node with elem stored
		else{
			Node newNode= new Node(elem);//make a new node with elem stored
			newNode.next=head;//make this new node's next node head
			head=newNode;//make head equal to newNode 
		}
		size++;
	}

	//lets define the addAt method
	public void addAt(int index, int elem){
		if(index<0 || index>size)
			throw new IllegalArgumentException();
		else if(index==0)
			addFirst(elem);
		else if(index==size)
			addLast(elem);
		else{
			Node current = head;//start at the head
			for(int i = 1; i<index; i++)
				current=current.next;//travel to desired index
			Node temp = current.next;//store the node at desired index
			current.next=new Node(elem);//make the new node at desired index
			(current.next).next=temp;//attach the node that was stored in temp to new node.
			size++;//increase the size
		}
	}

	//lets define the peekFirst method
	public int peekFirst(){
		if(isEmpty())
			throw new RuntimeException("Empty List");//if its empty throw exception
		return head.data;//return data of head
	}

	//lets define the peekLast method
	public int peekLast(){
		if(isEmpty())
			throw new RuntimeException("Empty List");//if empty throw exception
		return tail.data;//return data of tail
	}

	//lets define the removeLast method
	public int removeLast(){
		if(isEmpty())
			throw new RuntimeException("Empty List");
		else if(size==1){
			Node temp=head;//collect the only node
			head=tail=null;//nullify the head and tail
			size=0;//reset size
			return temp.data;//return data of the only node
		}
		else{
			Node previous = head;//start at head
			for(int i=0; i<size-2;i++)
				previous = previous.next;//travel through list until the node before tail
			Node temp= tail;//store tail in temp
			tail=previous;//make tail equal to previous aka the node before tail
			tail.next=null;//make tail's next node null
			size--;//decrease size by one
			return temp.data;//return data of the former tail node
		}
	}

	//lets define the removeFirst method
	public int removeFirst(){
		if(isEmpty())
			throw new RuntimeException("Empty List");
		else{
			Node temp = head;//store head in temp node
			head=head.next;//push head to next node
			if(head==null)
				tail=null;//if head is now null make tail null as well
			size--;//decrease size
			return temp.data;//return data of former head node
		}
	}

	//lets define the removeAt method
	public int removeAt(int index){
		if(index<0 || index>=size)
			throw new IllegalArgumentException();
		else if(index==0)
			return removeFirst();
		else if(index==size-1)
			return removeLast();
		else{
			//need help with this
			Node previous=head;//so i start at head
			for(int i = 1; i<index;i++)
				previous=previous.next;//keep going until just before desired index
			Node current= previous.next;//store node at desired index
			previous.next=current.next;//make the node just before desired index point
			//to the next node of the node at desired node. this skips the node at desired index.
			size--;//decrease size
			return current.data;//return the data of the deleted node.
		}
	}

	//lets define the remove method
	public int remove(Object obj){
		int index=indexOf(obj);
		if(index==-1)
			throw new RuntimeException("Object not in list");
		return removeAt(index);
	}
	//Remaining methods? Get, Set,index of, lastIndexOf, contains, traversal

	//lets define the contains method
	public boolean contains(Object obj){
		return indexOf(obj) != -1;//if -1, the list doesnt contain object
	}

	//lets define the indexOf method
	public int indexOf(Object obj){
		int index=0;//start at index 0-index keeps track of the place in list
		Node trav=head;//start at head

		if(obj==null){//if the desired object has value of null
			for(;trav!=null;trav=trav.next,index++){
				if(trav.data==null)
					return index;//return index at which there is a null value
			}
		}
		else{
			for(;trav!=null;trav=trav.next,index++){
				if(obj.equals(trav.data))
					return index;//return index where desired object is
			}
		}
		return -1;//desired object doesnt exist
	}

	//lets define the lastIndexOf method
	public int lastIndexOf(int elem){
		int index=0;//start at 0-this records place in list
		int lastSavedIndex=-1;//this stores the last known index of desired element
		Node trav=head;//start at beginning of list

		for(;trav!=null;trav=trav.next,index++){
			if(trav.data==elem)
				lastSavedIndex=index;//record the last known index of desired element
		}
		return lastSavedIndex;//return result
	}

	//lets define the get method
	public int get(int index){
		if(index<0 || index>=size)
			throw new IllegalArgumentException();
		else if(index==0)
			return peekFirst();
		else if(index==size-1)
			return peekLast();
		else{
			Node trav=head;
			for(int i=1; i<index;i++)
				trav=trav.next;
			Node current=trav.next;
			return current.data;
		}
	}

	//lets define the set method
	public int set(int index, int elem){
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
}

//Doubly Linked List
public class DoublyLinkedList{
	//attributes? size,head,tail

}