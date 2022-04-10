/**
 * This will cover all of the necessary sorting algorithms.
 * @author Chijioke Umezinwa
 */

import java.util.Arrays;

public class SortingAlgorithms{

	/**
	 * Merge Sort algorithm
	 */
	public static int[] mergesort(int[] list){
		// Base case is when a single element (which is already sorted)
		if(list.length <= 1)
			return list;

		// Merge sort the first half
		int[] firstHalf= mergeSort(Arrays.copyOfRange(list, 0, n/2));
		// Merge sort the second half
		int[] secondHalf= mergeSort(Arrays.copyOfRange(list, n/2, n));
		merge(firstHalf, secondHalf);
	}

	//Merge two sorted arrays into a larger sorted array
	private static int[] merge(int[] list1, int[] list2){
		int newLength = list1.length + list2.length;

		int index1=0, index2=0;

		int [] result = new int[newLength];

		for (int i = 0; i < newLength; i++){
			//if we run out of elements in list 1
			//we just past the remaining elements from
			//list 2 onto result array
			if(index1 == list1.length)
				result[i] = list2[index2++];
			//if we run out of elements in list 2
			//we just past the remaining elements from
			//list 1 onto result array
			else if(index2 == list2.length)
				result[i] = list1[index1++];
			//we still have elements to actually go through
			//in both lists
			else{
				if(list1[index1] < list2[index2])
					result[i] = list1[index1++];
				else
					result[i] = list2[index2++];
			}
		}
		return result;
	}
}


