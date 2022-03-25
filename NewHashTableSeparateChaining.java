/**
 * A new way of writing a hash table with Separate Chaining
 * 
 * @author Chijioke Umezinwa
 */

package DataStructuresAlgorithmsSourceCode;
import java.util.*;

public class Entry<K,V>{
	K key;
	V value;

	public Entry(K key, V value){
		this.key=key;
		this.value=value;
	}

	public K getKey(){
		return key;
	}

	public V getValue(){
		return value;
	}

	@Override
	public String toString(){
		return key + " => " + value;
	}
}

public class NewHashTableSeparateChaining<K,V> implements Iterable<K>{
	//defines the initial hash table size
	private static final int DEFAULT_CAPACITY = 3;

	//defines the max hash table size. 1 << 30 is same as 2^30
	private static int MAXIMUM_CAPACITY = 1 << 30;

	//Current hash-table capacity. Capacity is a power of 2
	private int capacity;

	//Define default load factor
	private static float DEFAULT_MAX_LOAD_FACTOR = 0.75f;

	//Specify a load factor used in the hash table
	private float loadFactorThreshold;

	//The number of entries in the map
	private int size=0;

	//Hash table is an array with each cell being a linked list
	private LinkedList<Entry<K,V>>[]table;

	//Construct hash table with default capacity and default 
	//max load factor
	public NewHashTableSeparateChaining(){
		this(DEFAULT_CAPACITY, DEFAULT_MAX_LOAD_FACTOR)
	}

	//Construct hash table with specified capacity and default
	//max load factor
	public NewHashTableSeparateChaining(int initialCapacity){
		this(initialCapacity, DEFAULT_MAX_LOAD_FACTOR);
	}

	//Construct hash table with specified capacity and max
	//load factor
	public NewHashTableSeparateChaining(int initialCapacity, float loadFactorThreshold){
		//Constrains capacity to stated max capacity
		if(initialCapacity > MAXIMUM_CAPACITY)
			this.capacity=MAXIMUM_CAPACITY;
		else
			this.capacity=trimToPower2(initialCapacity);

		this.loadFactorThreshold=loadFactorThreshold;
		table=new LinkedList[capacity];
	}

	public int size(){
		return size;
	}

	public boolean isEmpty(){
		return size()==0;
	}

	//clears hash table
	public void clear(){
		size=0;
		removeEntries();
	}

	//checks the existence of a key
	public boolean containsKey(K key){
		if(getKey() != null)
			return true;
		else
			return false;
	}

	//returns true if value exists in hash table
	public boolean containsValue(V value){
		//go through array of linked lists
		for(int i = 0; i < capacity; i++){
			//stop at an occupied index in array
			if(table[i] != null){
				//the bucket is the specific linked list stored
				//at an index in the hash table
				LinkedList<Entry<K, V>> bucket = table[i];
				//go through all elements of linked list
				for(Entry<K, V> entry: bucket){
					if(entry.getValue().equals(value))
						return true;
				}
			}
		}

		return false;
	}

	//Return a set consisting of the keys in this hash table
	public java.util.Set<K> keySet(){
		//create set to hold the set we want to get
		java.util.Set<K> set = new java.util.HashSet<K>();

		//go through array of linked lists
		for(int i = 0; i < capacity; i++){
			//find and stop at an occupied index in array
			if(table[i] != null){
				//the bucket is the specific linked list stored
				//at an index in the hash table
				LinkedList<Entry<K, V>> bucket = table[i];
				for(Entry<K, V> entry: bucket)
					set.add(entry.getKey());
			}
		}

		return set;
	}

	//Return a set of entries in the hash table
	public java.util.Set<Entry<K,V>> entrySet(){
		//create set to hold the set we want to get
		java.util.Set<Entry<K, V>> set = new java.util.HashSet<>();

		//go through array of linked lists
		for(int i = 0; i < capacity; i++){
			//find and stop at an occupied index in array
			if (table[i] != null){
				//the bucket is the specific linked list stored
				//at an index in the hash table
				LinkedList<Entry<K, V>> bucket = table[i];
				//go through all elements of bucket and add it to set
				for(Entry<K, V> entry: bucket)
					set.add(entry);
			}
		}
		return set;
	}

	//Return a set consisting of the values in this map 
	public java.util.Set<V> values(){
		java.util.Set<V> set = new java.util.HashSet<>();

		for (int i = 0; i < capacity; i++){
			if (table[i] != null){
				//the bucket is the specific linked list stored
				//at an index in the hash table
				LinkedList<Entry<K, V>> bucket = table[i];
				for (Entry<K, V> entry: bucket)
					set.add(entry.getValue());
			}
		}
		return set;
	}

	//Return the value that matches the specified key
	public V get(K key){
		//find the index
		//hashcode is a method from the object class
		int bucketIndex = hash(key.hashCode());
		//find the index and store it in bucket
		if(table[bucketIndex] != null){
			//the bucket is the specific linked list stored
			//at an index in the hash table
			LinkedList<Entry<K, V>> bucket = table[bucketIndex];
			//go through all elements of bucket and see if it matches the key
			for(Entry<K, V> entry: bucket){
				if(entry.getKey().equals(key))
					return entry.getValue();
			}
		}

		return null;
	}

	//Add an entry (key, value) into the hash table
	public V put(K key, V value){
		//check if key exists already
		if (get(key) != null){
			//collect the index where key is
			int bucketIndex = hash(key.hashCode());

			//the bucket is the specific linked list stored
			//at an index in the hash table
			LinkedList<Entry<K, V>> bucket = table[bucketIndex];

			//go through bucket
			for (Entry<K, V> entry: bucket){
				//if the key of the entry matches the key of the new entry
				if (entry.getKey().equals(key)){
					V oldValue = entry.getValue();
					// Replace old value with new value
					entry.value = value;
					// Return the old value for the key
					return oldValue;
				}
			}
		}

		//Check load factor
		if(size >= capacity * loadFactorThreshold){
			if (capacity == MAXIMUM_CAPACITY)
				throw new RuntimeException("Exceeding maximum capacity");
			rehash();
		}

		int bucketIndex = hash(key.hashCode());

		//Create a linked list for the bucket if not already created
		if(table[bucketIndex] == null){
			table[bucketIndex] = new LinkedList<Entry<K, V>>();
		}

		//Add a new entry (key, value) to hashTable[index]
		table[bucketIndex].add(new Entry<K, V>(key, value));

		size++;

		return value;
	}	

	//Remove the entries for the specified key
	public void remove(K key){
		int bucketIndex = hash(key.hashCode());

		//Remove the first entry that matches the key from a bucket
		if(table[bucketIndex] != null){
			//the bucket is the specific linked list stored
			//at an index in the hash table
			LinkedList<Entry<K, V>> bucket = table[bucketIndex];
			for(Entry<K, V> entry: bucket){
				if (entry.getKey().equals(key)){
					bucket.remove(entry);
					size--;
					break;
				}
			}
		}
	}

	//Hash Function
	private int hash(int hashCode){
		return supplementalHash(hashCode) & (capacity - 1);
	}

	//Ensure the hashing is evenly distributed 
	private static int supplementalHash(int h){
		h ^= (h >>> 20) ^ (h >>> 12);
		return h ^ (h >>> 7) ^ (h >>> 4);
	}

	//Return a power of 2 for initialCapacity 
	private int trimToPowerOf2(int initialCapacity){
		int capacity = 1;
		while (capacity < initialCapacity){
			capacity <<= 1; // Same as capacity *= 2. <<= is more efficient
		}

		return capacity;
	}

	//Remove all entries from each bucket 
	private void removeEntries(){
		for (int i = 0; i < capacity; i++){
			if (table[i] != null){
				table[i].clear();
			}
		}
	}

	//rehash the hash table
	private void rehash(){
		//Get entries
		java.util.Set<Entry<K, V>> set = entrySet();

		//Same as capacity *= 2. <= is more efficient
		capacity <<= 1;

		//Create a new hash table
		table = new LinkedList[capacity];

		size = 0; // Reset size to 0

		for (Entry<K, V> entry: set){
			//Store to new table
			put(entry.getKey(), entry.getValue());
		}
	}

	//Return an iterator to iterate over all the keys in this map
	@Override
	public java.util.Iterator<K> iterator(){
		final int elementCount = size();

		return new java.util.Iterator<K>(){
			int bucketIndex = 0;
			java.util.Iterator<Entry<K, V>> bucketIter = (table[0] == null) ? null : table[0].iterator();

			@Override
			public boolean hasNext(){
  				//An item was added or removed while iterating
				if (elementCount != size)
					throw new java.util.ConcurrentModificationException();
  				//No iterator or the current iterator is empty
				if (bucketIter == null || !bucketIter.hasNext()){
  					//Search next buckets until a valid iterator is found
					while (++bucketIndex < capacity){
						if(table[bucketIndex] != null){
  							//Make sure this iterator actually has elements
							java.util.Iterator<Entry<K, V>> nextIter = table[bucketIndex].iterator();
							if (nextIter.hasNext()){
								bucketIter = nextIter;
								break;
							}
						}
					}
				}
				return bucketIndex < capacity;
			}

			@Override
			public K next(){
				return bucketIter.next().key;
			}

			@Override
			public void remove(){
				throw new UnsupportedOperationException();
			}
		};

	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder("[");

		for (int i = 0; i < capacity; i++){
			if (table[i] != null && table[i].size() > 0){
				for (Entry<K, V> entry: table[i])
					builder.append(entry);
			}
		}

		builder.append("]");
		return builder.toString();
	}

}


