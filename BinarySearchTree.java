/**
 * An implementation of Binary Search Tree
 * 
 * @author Chijioke Umezinwa
 */

package DataStructuresAlgorithmsSourceCode;

public class BinarySearchTree <T> extends Comparable<T> implements Iterable<T>{
	//Tracks the number of nodes in BST
	private int nodeCount=0;

	//tracks root node
	private Node<T> root=null;

	//internal node class
	private static class Node<T>{
		private T data;
		private Node<T> left;
		private Node<T> right;

		public Node(T data, Node<T>left, Node<T>right){
			this.data=data;
			this.left=left;
			this.right=right;
		}
	}

	//returns number of nodes in BST
	public int size(){
		return nodeCount;
	}
	//isEmpty
	public boolean isEmpty(){
		return size()==0;
	}

	//add a node to BST and returns true if successful
	public boolean add(T elem){
		//check to see if elem is already in tree
		//ignore if its already present in tree
		if(contains(elem))
			return false;
		//otherwise add the elem to tree
		else{
			root=add(root,elem);
			nodeCount++;
			return true;
		}
	}

	//Private method to recursively add a value in the binary tree
	private Node<T> add(Node<T> node, T elem){
		//Base case: found a leaf node
		if(node==null)
			node=new Node<T>(elem,null,null);
		else{
			//pick a subtree to insert new element
			if(elem.compareTo(node.data) < 0)
				node.left = add(node.left, elem);
			else
				node.right = add(node.right, elem);
		}

		return node;
	}

	//Remove a value from this binary tree if it exists, O(n)
	public boolean remove(T elem){
		//Make sure the node we want to remove exists before
		//removing it
		if(contains(elem)){
			root=remove(root,elem);
			nodeCount--;
			return true;
		}

		return false;
	}

	//Private method to recursively remove a value from binary tree
	private Node<T> remove(Node<T> node, T elem){
		//node is null
		if(node == null)
			return null;

		//find whether the element we're removing is either in
		//left or right subtree
		int cmp = elem.compareTo(node.data);

		//Dig into left subtree, the value we're looking for is 
		//smaller than the current value
		if(cmp < 0)
			node.left=remove(node.left, elem);

		//Dig into right subtree, the value we're looking for is
		//bigger than the current value
		else if(cmp > 0)
			node.right=remove(node.right, elem);

		//we found the node we want to remove
		else{
			//This is the case with only a right subtree or no 
			//subtree at all. We just swap the node we want to remove
			//with its right child
			if(node.left == null)
				return node.right;

			//This is the case with only a left subtree or no 
			//subtree at all. We just swap the node we want to remove
			//with its left child
			else if(node.right == null)
				return node.left;

			//When removing a node from a binary tree with a left and
			//right child, we have two possible successors: the largest
       		//value in the left subtree or the smallest value in the right
        	//subtree. We'll choose the smallest value in the right subtree.
        	//We find the smallest value in the right subtree by traversing 
        	//as far left as possible in the right subtree.
			else{
				//Find the leftmost node in the right subtree
				Node<T> tmp = findMin(node.right);

				//Swap the data
				node.data = tmp.data;

				//Go into the right subtree and remove the leftmost node we
				//found and swapped data with. This prevents us from having
				//two nodes in our tree with the same value.
				node.right = remove(node.right, tmp.data);

				//If instead we wanted to find the largest node in the left
				//subtree as opposed to smallest node in the right subtree
				//here is what we would do:
				//Node<T> tmp = findMax(node.left);
				//node.data = tmp.data;
				//node.left = remove(node.left, tmp.data);
			}
		}
		return node;
	}

	//Helper method to find the leftmost node (which has the smallest value)
	private Node<T> findMin(Node<T> node){
		while(node.left != null)
			node=node.left;
		return node;
	}

	//Helper method to find the rightmost node (which has the largest value)
	private Node<T> findMax(Node<T> node){
		while(node.right != null)
			node=node.right;
		return node;
	}

	//returns true is the element exists in the tree
	public boolean contains(T elem){
		return contains(root, elem);
	}

	//private recursive method to find an element in the tree
	private boolean contains(Node<T> node, T elem){
		//Base case: reached bottom, value not found
		if(node == null)
			return false;
		//find whether the element we're looking is either in
		//left or right subtree.
		int cmp = elem.compareTo(node.data);

		//Dig into left subtree, the value we're looking for is 
		//smaller than the current value
		if(cmp < 0)
			return contains(node.left, elem);

		//Dig into right subtree, the value we're looking for is
		//bigger than the current value
		else if(cmp > 0)
			return contains(node.right, elem);

		//We found the value we were looking for
		else
			return true;
	}

	//Computes the height of the tree, O(n)
	public int height(){
		return height(root);
	}

	//Recursive helper method to compute the height of the tree
	private int height(Node<T> node){
		if(node == null)
			return 0;
		return Math.max(height(node.left), height(node.right)) + 1;
	}

	//This method returns an iterator for a given TreeTraversalOrder.
	//The ways in which you can traverse the tree are in four different ways:
	//preorder, inorder, postorder, and levelorder
	public java.util.Iterator<T> traverse(TreeTraversalOrder order){
		switch(order){
			case PRE_ORDER:
				return new preOrderTraversal();
			case IN_ORDER:
				return new inOrderTraversal();
			case POST_ORDER:
				return new postOrderTraversal();
			case LEVEL_ORDER:
				return new levelOrderTraversal();
			default:
				return null;
		}
	}

	//Returns as iterator to traverse the tree in pre order
	//preorder looks like this:
	//preorder(node):
	//	if(node==null) return;
	//	print(node.value)
	//	preorder(node.left);
	//	preorder(node.right);
	private class preOrderTraversal implements java.util.Iterator<T>{
		final int expectedNodeCount = nodeCount;
		final java.util.Stack<Node> stack = new java.util.Stack<>();
		stack.push(root);

		@Override
		public boolean hasNext(){
			if(expectedNodeCount != nodeCount)
				throw new java.util.ConcurrentModificationException();
			return root != null && !stack.isEmpty();
		}

		@Override
		public T next(){
			if(expectedNodeCount != nodeCount)
				throw new java.util.ConcurrentModificationException();

			Node<T> node = stack.pop();
			if(node.right != null)
				stack.push(node.right);
			if(node.left != null)
				stack.push(node.left);

			return node.data;
		}

		@Override
		public void remove(){
			throw new UnsupportedOperationException();
		}
	}

	//Returns as iterator to traverse the tree in order
	//inorder looks like this:
	//inorder(node):
	//	if(node==null) return;
	//	inorder(node.left);
	//	print(node.value)
	//	inorder(node.right);
	private class inOrderTraversal implements java.util.Iterator<T>{
		final int expectedNodeCount = nodeCount;
		final java.util.Stack<Node> stack = new java.util.Stack<>();
		stack.push(root);

		Node<T> trav = root;

		@Override
		public boolean hasNext(){
			if(expectedNodeCount != nodeCount)
				throw new java.util.ConcurrentModificationException();
			return root != null && !stack.isEmpty();
		}

		@Override
		public T next(){
			if(expectedNodeCount != nodeCount)
				throw new java.util.ConcurrentModificationException();

			//Dig left
			while(trav != null && trav.left != null){
				stack.push(trav.left);
				trav = trav.left;
			}

			Node<T> node = stack.pop();

			//Try moving down right once
			if(node.right != null){
				stack.push(node.right);
				trav = node.right;
			}
			return node.data;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	//Returns as iterator to traverse the tree in post order
	//postorder looks like this:
	//postorder(node):
	//	if(node==null) return;
	//	postorder(node.left);
	//	postorder(node.right);
	//	print(node.value);
	private class postOrderTraversal implements java.util.Iterator<T>{
		final int expectedNodeCount = nodeCount;
		final java.util.Stack<Node> stack1 = new java.util.Stack<>();
		final java.util.Stack<Node> stack2 = new java.util.Stack<>();

		stack1.push(root);

		while(!stack1.isEmpty()){
			Node<T> node = stack1.pop();
			if(node != null){
				stack2.push(node);
				if(node.left != null)
					stack1.push(node.left);
				if(node.right != null)
					stack1.push(node.right);
			}
		}

		@Override
		public boolean hasNext(){
			if(expectedNodeCount != nodeCount)
				throw new java.util.ConcurrentModificationException();
			return root != null && !stack2.isEmpty();
		}

		@Override
		public T next(){
			if(expectedNodeCount != nodeCount)
				throw new java.util.ConcurrentModificationException();
			return stack2.pop().data;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	// Returns as iterator to traverse the tree in level order
	private class levelOrderTraversal implements java.util.Iterator<T>{
		final int expectedNodeCount = nodeCount;
		final java.util.Queue<Node> queue = new java.util.LinkedList<>();
		queue.offer(root);

		@Override
		public boolean hasNext() {
			if (expectedNodeCount != nodeCount)
				throw new java.util.ConcurrentModificationException();
			return root != null && !queue.isEmpty();
		}

		@Override
		public T next() {
			if (expectedNodeCount != nodeCount)
				throw new java.util.ConcurrentModificationException();
			Node<T> node = queue.poll();
			if (node.left != null)
				queue.offer(node.left);
			if (node.right != null)
				queue.offer(node.right);
			return node.data;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}