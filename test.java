package DataStructuresAlgorithmsSourceCode;

public class test{
	public static void main(String[] args){
		Stack<Integer> stack = new Stack<>();
		
		stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);

        /* Using an iterator */
 
        // 1. Using an iterator to iterate through a stack
        Iterator<Integer> itr = stack.iterator();
 
        // hasNext() returns true if the stack has more elements
        while (itr.hasNext())
        {
            // next() returns the next element in the iteration
            System.out.println(itr.next());
        }

	}
}