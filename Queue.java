/**
 * A Linked List implementation of Queue in Java
 * @author Chijioke Umezinwa
 * 
 */

package DataStructuresAlgorithmsSourceCode;

public class Queue<T> implements Iterable<T>{
    private SinglyLinkedList<T> list = new SinglyLinkedList<T>();

    public LinkedQueue() {}
    public LinkedQueue(T firstElem) {
        offer(firstElem);
    }
    // Return the size of the queue
    public int size() {
        return list.size();
    }

    // Returns whether or not the queue is empty
    public boolean isEmpty() {
        return size() == 0;
    }

    // Peek the element at the front of the queue
    // The method throws an error is the queue is empty
    public T peek() {
        if (isEmpty()) throw new RuntimeException("Queue Empty");
        return list.peekFirst();
    }

    // Poll an element from the front of the queue
    // The method throws an error is the queue is empty
    public T poll() {
        if (isEmpty()) throw new RuntimeException("Queue Empty");
        return list.removeFirst();
    }

    // Add an element to the back of the queue
    public void offer(T elem) {
        list.addLast(elem);
    }

    // Return an iterator to alow the user to traverse
    // through the elements found inside the queue
    @Override
        public java.util.Iterator<T> iterator() {
        return list.iterator();
    }
}