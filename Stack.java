/**
 * A Linked List implementation of Stack in Java
 * @author Chijioke Umezinwa
 * 
 */

package DataStructuresAlgorithmsSourceCode;

public class Stack<T> implements Iterable<T>{
	private SinglyLinkedList<T> list= new SinglyLinkedList<T>();

	public Stack(){}

	public Stack(T firstElem){
		push(firstElem);
	}

	public int size(){
		return list.size();
	}

	public boolean isEmpty(){
		return size()==0;
	}

	public void push(T elem){
		list.addLast(elem);
	}

	public T pop(){
		if(isEmpty())
			throw new java.util.EmptyStackException();
		return list.removeLast();
	}

	public T peek(){
		if(isEmpty())
			throw new java.util.EmptyStackException();
		return list.peekLast();
	}

	public int search(T elem){
		return list.lastIndexOf(elem);
	}

	@Override
	public java.util.Iterator<T> iterator(){
		return list.iterator();
	}

}