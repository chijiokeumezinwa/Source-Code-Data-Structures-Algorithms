/**
 * A Binary Min Heap implementation of a priority Queue in Java
 * This implementation uses a hash table to track elements for 
 * quick removals
 * @author Chijioke Umezinwa
 */

package DataStructuresAlgorithmsSourceCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class PriorityQueueBinaryHeapQuickRemovals<T extends Comparable<T>> {
	//Dynamic List to track elements inside the heap
	private List<T> heap = null;

	//This map tracks the possible indices a particular 
	//node value may have in the heap. This mapping allows
	//us to have O(log(n)) removals and O(1) element
	//containment check at the cost of some additional 
	//space and minor overhead 
	private Map<T, TreeSet<Integer>> map = new HashMap<>();

	//construct an initially empty priority queue
	public PriorityQueueBinaryHeapQuickRemovals(){
		this(1);
	}

	//construct a priority queue with an initial capacity
	public PriorityQueueBinaryHeapQuickRemovals(int size){
		heap = new ArrayList<>(size);
	}

	//construct a priority queue using heapify in O(n) time
	public PriorityQueueBinaryHeapQuickRemovals(T[] elems){
		int heapSize = elems.length;
		heap = new ArrayList<T>(heapSize);

		//place all elements in the heap as well as in hash table
		for(int i = 0; i<heapSize;i++){
			mapAdd(elems[i],i);
			heap.add(elems[i]);
		}

		//Heapify process O(n)
		for(int i = Math.max(0, (heapSize/2)-1); i>=0; i--)
			sink(i);
	}

	public PriorityQueueBinaryHeapQuickRemovals(Collection<T> elems){
		this(elems.size());
		for(T elem : elems)
			add(elem);
	}

	public boolean isEmpty(){
		return size()==0;
	}

	public int size(){
		return heap.size();
	}

	public void clear(){
		heap.clear();
		map.clear();
	}

	//return value of element with lowest priority in this PQ.
	//If PQ is empty, return null
	public T peek(){
		if(isEmpty())
			return null;
		return heap.get(0);
	}

	//Poll returns root of heap
	public T poll(){
		return removeAt(0);
	}

	//Test if an element is in heap, O(1)
	public boolean contains(T elem){
		//Map lookup to check containment
		if(elem == null)
			return false;
		return map.containsKey(elem);
	}

	//Add an element to PQ, the element must
	//be not null, O(log(n))
	public void add(T elem){
		if(elem == null)
			throw new IllegalArgumentException();

		heap.add(elem);
		int indexOfLastElem = size() - 1;
		mapAdd(elem, indexOfLastElem);

		swim(indexOfLastElem);
	}

	//Tests if the value of node i<= node j
	//This method assumes i&j are valid indices, O(1)
	public boolean less(int i, int j){
		T node1 = heap.get(i);
		T node2 = heap.get(j);
		return node1.compareTo(node2) <= 0;
	}

	//Performs bubbling up aka swim operation,O(log(n))
	private void swim(int k){
		//grab the index of next parent node 
		int parent = (k-1)/2;

		//keep bubbling up while we have not reached the root
		//and we're still less than our parent
		while(k>0 && less(k,parent)){
			//exchange k with parent
			swap(parent,k);
			k=parent;

			//grab the index of next parent node to k
			parent = (k-1)/2;
		}
	}

	//Performs bubbling down aka sink operation, O(log(n))
	private void sink(int k){
		int heapSize = size();

		while(true){
			int left = 2 * k + 1;//left child node
			int right = 2 * k + 2;//right child node

			int smallest = left;//Assume left is the smallest node of the two children

			//find which node is smallest left or right
			//if right is smallest, set smallest to right
			if(right < heapSize && less(right,left))
				smallest=right;

			//stop if we are outside the boundaries of the heap
			//or stop if we cannot bubble down k anymore
			if(left >= heapSize && less(k,smallest))
				break;
			//move the node down the tree after finding smallest node
			swap(smallest, k);
			k=smallest;
		}
	}

	//Swap two nodes, assume i and j are valid indexes
	private void swap(int i, int j){
		T i_elem=heap.get(i);
		T j_elem=heap.get(j);

		heap.set(i, j_elem);
		heap.set(j, i_elem);

		mapSwap(i_elem, j_elem, i, j);
	}

	//Removes a particular element in the heap, O(log(n))
	public boolean remove(T element){
		if (element == null)
			return false;
		// Logarithmic removal with map, O(log(n))
		Integer index = mapGet(element);
		if (index != null)
			removeAt(index);
		return index != null;
	}

	// Removes a node at particular index, O(log(n))
	private T removeAt(int i){
		if (isEmpty())
			return null;

		int indexOfLastElem = size() - 1;
		T removed_data = heap.get(i);
		swap(i, indexOfLastElem);

		//Remove the value
		heap.remove(indexOfLastElem);
		mapRemove(removed_data, indexOfLastElem);

		//We will return the last element data if this
		//is the desired element we want to remove
		if (i == indexOfLastElem)
			return removed_data;

		T elem = heap.get(i);

		//Try to bubble down the element
		sink(i);

		//If bubbling down does not, try bubbling up
		if (heap.get(i).equals(elem))
			swim(i);
		return removed_data;
	}

	// Recursively checks if this heap is a min heap
	// This method is just for testing purposes to make
	// sure the heap invariant is still being maintained
	// Called this method with k=0 to start at the root
	public boolean isMinHeap(int k){
		// If we are outside the bounds of the heap return true
		int heapSize = size();
		if (k >= heapSize)
			return true;

		int left = 2 * k + 1;
		int right = 2 * k + 2;

		// Make sure that the current node k is less than
		// both of its children left, and right if they exist
		// return false otherwise to indicate an invalid heap
		if (left < heapSize && !less(k, left))
			return false;

		if (right < heapSize && !less(k, right))
			return false;

		// Recurse on both children to make sure they're also valid heaps
		return isMinHeap(left) && isMinHeap(right);
	}

	// Add a node value and its index to the map
	private void mapAdd(T value, int index){
		TreeSet<Integer> set = map.get(value);

		// New value being inserted in map
		if (set == null){
			set = new TreeSet<>();
			set.add(index);
			map.put(value, set);
		}
		// Value already exists in map
		else{
			set.add(index);
		}
	}

	// Removes the index at a given value, O(log(n))
	private void mapRemove(T value, int index){
		TreeSet<Integer> set = map.get(value);
		set.remove(index); // TreeSets take O(log(n)) removal time
		if (set.size() == 0)
			map.remove(value);
	}

	// Extract an index position for the given value
	// NOTE: If a value exists multiple times in the heap the highest
	// index is returned (this has arbitrarily been chosen)
	private Integer mapGet(T value){
		TreeSet<Integer> set = map.get(value);
		if (set != null)
			return set.last();
		return null;
	}

	// Exchange the index of two nodes internally within the map
	private void mapSwap(T val1, T val2, int val1Index, int val2Index){
		Set<Integer> set1 = map.get(val1);
		Set<Integer> set2 = map.get(val2);

		set1.remove(val1Index);
		set2.remove(val2Index);

		set1.add(val2Index);
		set2.add(val1Index);
	}

	@Override
	public String toString(){
		return heap.toString();
	}
}

