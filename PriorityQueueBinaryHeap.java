/**
 * A Binary Min Heap implementation of a priority queue in java
 * 
 * @author Chijioke Umezinwa
 */

package DataStructuresAlgorithmsSourceCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;


public class PriorityQueueBinaryHeap<T extends Comparable<T>>{
	private List<T> heap = null;

	public PriorityQueueBinaryHeap(){
		this(1);
	}
	public PriorityQueueBinaryHeap(int size){
		heap = new ArrayList<>(size);
	}
	public PriorityQueueBinaryHeap(T[] elems){
		int heapSize= elems.length;
		heap=new ArrayList<T>(heapSize);

		//place all elements in the heap
		for(int i=0; i<heapSize; i++)
			heap.add(elems[i]);
		//heapify process, o(N)-basically how to order it
		for(int i=Math.max(0, (heapSize/2)-1); i>=0; i--)
			sink(i);
	}
	public PriorityQueueBinaryHeap(Collection <T> elems){
		int heapSize=elems.size();
		heap=new ArrayList<T>(heapSize);

		//place all elements in the heap
		heap.addAll(elems);

		//heapify process, o(N)-basically how to order it
		for(int i = Math.max(0,(heapSize/2)-1); i>=0;i--)
			sink(i);
	}

	public boolean isEmpty(){
		return size()==0;
	}

	public void clear(){
		heap.clear();
	}

	public int size(){
		return heap.size();
	}

	public T peek(){
		if(isEmpty())
			return null;
		return heap.get(0);
	}

	public T poll(){
		return removeAt(0);
	}

	public boolean contains(T elem){
		for(int i = 0; i<size(); i++){
			if(heap.get(i).equals(elem))
				return true;
		}
		return false;
	}

	public void add(T elem){
		if(elem==null)
			throw new IllegalArgumentException();
		heap.add(elem);

		int indexOfLastElem = size() - 1;
		swim(indexOfLastElem);
	}

	private boolean less(int i, int j){
		T node1 = heap.get(i);
		T node2 = heap.get(j);
		return node1.compareTo(node2) <= 0;
	}

	private void swim(int k){
		//grab the index of the next parent node WRT to k
		int parent = (k-1) / 2;

		//keep swimming while we have not reached the root
		//and while we're less than our parent
		while(k>0 && less(k,parent)){
			//exchange k with the parent
			swap(parent, k);
			k = parent;

			//grab the index of the next parent node WRT to k
			parent=(k-1)/2;
		}
	}

	private void sink(int k){
		int heapSize=size();

		while(true){
			int left = 2 * k + 1; //left node
			int right = 2 * k + 2; //right node
			int smallest = left;//assume left is the smallest node

			//find which is smaller left or right
			//if right is smaller set smallest to be right
			if(right < heapSize && less(right, left))
				smallest = right;

			//stop if we're outside the bounds of the tree
			//or stop early if we cannot sink k anymore
			if(left >= heapSize || less(k,smallest))
				break;

			//move down the tree following the smallest node
			swap(smallest, k);
			k = smallest;
		}
	}

	private void swap(int i, int j){
		T elem_i = heap.get(i);
		T elem_j = heap.get(j);

		heap.set(i, elem_j);
		heap.set(j, elem_i);
	}

	public boolean remove(T element){
		if(element == null)
			return false;

		//Linear removal via search o(N)
		for(int i = 0; i< size(); i++){
			if(element.equals(heap.get(i))){
				removeAt(i);
				return true;
			}
		}
		return false;
	}

	public T removeAt(int index){
		if(isEmpty())
			return null;

		int indexOfLastElem = size()-1;
		T removed_data= heap.get(index);
		swap(index, indexOfLastElem);

		//obliterate the value
		heap.remove(indexOfLastElem);

		//check if the last element was removed
		if(index == indexOfLastElem)
			return removed_data;
		T elem = heap.get(index);

		//try sinking element
		sink(index);

		//if sinking did not work try swimming
		if(heap.get(index).equals(elem))
			swim(index);
		return removed_data;
	}

	//recursively checks if this heap is a min heap
	//call this method with k =0 to start at the root
	public boolean isMinHeap(int k){
		//if we are outside the bounds of the heap return true
		int heapSize = size();
		if(k >= heapSize)
			return true;

		int left = 2 * k + 1;
		int right = 2 * k + 2;

		//make sure the current node k is less than both
		//of its children left, and right if they exist
		//return false otherwise to indicate an invalid heap
		if(left < heapSize && !less(k, left))
			return false;
		if(right < heapSize && !less(k, right))
			return false;

		//recurse on both children to make sure theryre also valid heaps
		return isMinHeap(left) && isMinHeap(right);
	}

	@Override
	public String toString(){
		return heap.toString();
	}
}
