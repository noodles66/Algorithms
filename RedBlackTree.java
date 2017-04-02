package org.algorithms.search;

public class RedBlackTree<Key extends Comparable<Key>, Value > {
	private static final boolean Red = true;
	private static final boolean Black = false;
	public Node root;
	private class Node{
		Key key;
		Value value;
		Node leftnode;
		Node rightnode;
		int N = 0;
		boolean color;
		
		public Node(Key key, Value value, int N,boolean color){
			this.key = key;
			this.color = color;
			this.value = value;
			this.N = N;
		}
	}
	
	private int size(Node node){
		if (node == null){
			return 0;
		}
		return node.N;
	}
	private boolean isRed(Node node){
		if (node == null){
			return false;
		}else{
			return node.color == Red;
		}
	}
	
	private Node rotateLeft(Node node){
		Node x = node.rightnode;
		node.rightnode = x.leftnode;
		x.leftnode = node;
		node.N--;
		x.N++;
		x.color = node.color;
		node.color = Red;
		return x;
	}
	
	private Node rotateRight(Node node){
		Node x = node.leftnode;
		node.leftnode = x.rightnode;
		x.rightnode = node;
		node.N--;
		x.N++;
		x.color = node.color;
		node.color = Red;
		return x;
	}
	
	private void flipColors(Node node){
		node.color = Red;
		node.leftnode.color = Black;
		node.rightnode.color = Black;
	}
	
	public void put(Key key, Value value){
		root = put (key, value, root);
		root.color = Black;
	}
	public Node put(Key key, Value value, Node node){
		if (node == null){
			node = new Node(key, value, 1, Red);
			return node;
		}
		if (node.key.compareTo(key) < 0){
			node.leftnode = put(key, value, node.leftnode);
		}else if (node.key.compareTo(key) > 0){
			node.rightnode = put(key, value, node.rightnode);
		}else{
			node.value = value;
		}
		
		
		if (!isRed(node.leftnode) && isRed(node.rightnode)== Red){
			node = this.rotateLeft(node);
		}
		if (isRed(node.leftnode) && isRed(node.leftnode.leftnode)){
			node = this.rotateRight(node);
		}
		if (isRed(node.leftnode)&& isRed(node.rightnode)){
			this.flipColors(node);
		}
		//这三个判断条件是有顺序之分的
		node.N = size(node.leftnode) + size(node.rightnode);
		return node;
	}
	
	public Value get(Key key){
		return get(root, key);
	}
	public Value get(Node node, Key key){	//通过比较递归地找key值对应的值
		if (node == null){
			return null;
		}
		if (node.key.compareTo(key) == 0){
			return node.value;
		}else if (node.key.compareTo(key) < 0 ){
			return get(node.rightnode, key);
		}else{
			return get(node.leftnode, key);
		}
	}
	
	public Key min (){
		return min(root).key;
	}
	public Node min(Node node){					//递归找最左侧的节点
		if (node == null){
			return null;
		}
		if (node.leftnode == null){
			return node;
		}else{
			return min(node.leftnode);
		}
	}
	
	private void flipColorsDelete(Node node){
		node.color = Black;
		node.leftnode.color = Red;
		node.rightnode.color = Red;
	}
	private Node moveRedLeft(Node node){
		this.flipColorsDelete(node);
		if(isRed(node.rightnode.leftnode)){
			node.rightnode = this.rotateRight(node.rightnode);
			node = this.rotateLeft(node);
		}
		return node;
	}
	
	public void deleteMin(){
		if (root.leftnode.color != Red && root.rightnode.color != Red ){
			root.color = Red;
		}
		root = deleteMin(root);
		root.color = Black;
	}
	public Node deleteMin(Node node){
		if (node == null){
			return null;
		}
		if (!isRed(node.leftnode) && !isRed(node.leftnode.leftnode)){
			node = moveRedLeft(node);
		}
		node.leftnode = deleteMin(node.leftnode);
		
		if (isRed(node.rightnode)){
			node = this.rotateLeft(node);
		}
		if (!isRed(node.leftnode) && isRed(node.rightnode)== Red){
			node = this.rotateLeft(node);
		}
		if (isRed(node.leftnode) && isRed(node.leftnode.leftnode)){
			node = this.rotateRight(node);
		}
		if (isRed(node.leftnode)&& isRed(node.rightnode)){
			this.flipColors(node);
		}
		node.N = size(node.leftnode) + size(node.rightnode);
		return node;
	}
	
	private Node moveRedRight(Node node){
		flipColors(node);
		if (!isRed(node.leftnode.leftnode)){
			node = this.rotateRight(node);
		}
		return node;
	}
	public void deleteMax(){
		if (root.leftnode.color != Red && root.rightnode.color != Red){
			root.color = Red;
		}
		root = deleteMax(root);
		root.color = Black;
	}
	public Node deleteMax(Node node){
		if (node.leftnode.color == Red){
			node = this.rotateRight(node);
		}
		if (node.rightnode == null){
			return null;
		}
		if (!isRed(node.rightnode) && !isRed(node.rightnode.leftnode)){
			node = moveRedRight(node);
		}
		node.rightnode = deleteMax(node.rightnode);
		
		if (!isRed(node.leftnode) && isRed(node.rightnode)== Red){
			node = this.rotateLeft(node);
		}
		if (isRed(node.leftnode) && isRed(node.leftnode.leftnode)){
			node = this.rotateRight(node);
		}
		if (isRed(node.leftnode)&& isRed(node.rightnode)){
			this.flipColors(node);
		}
		node.N = size(node.leftnode) + size(node.rightnode);
		return node;
		}
	
	public void delete(Key key){
		if (root.leftnode.color != Red && root.rightnode.color != Red){
			root.color = Red;
		}
		root = delete(root, key);
		root.color = Black;
	}
	public Node delete(Node node, Key key){
		if (key.compareTo(node.key)<0){
			if (node.leftnode.color != Red && node.leftnode.leftnode.color != Red){
				node = moveRedLeft(node);
			}
			node.leftnode = delete(node.leftnode, key);
		}
		else{
			if (isRed(node.leftnode)){
				node = rotateRight(node);
			}
			if(key.compareTo(node.key) == 0 && (node.rightnode == null)){
				return null;
			}
			if (!isRed(node.rightnode) && !isRed(node.rightnode.leftnode)){
				node = moveRedRight(node);
			}
			if (key.compareTo(node.key) == 0){
				node.key = min(node.rightnode).key;
				node.value = get(node.rightnode, min(node.rightnode).key);
				node.rightnode = deleteMin(node.rightnode);
			}
			else{
				node.rightnode = delete(node.rightnode, key);
			}
		}
		
		if (!isRed(node.leftnode) && isRed(node.rightnode)== Red){
			node = this.rotateLeft(node);
		}
		if (isRed(node.leftnode) && isRed(node.leftnode.leftnode)){
			node = this.rotateRight(node);
		}
		if (isRed(node.leftnode)&& isRed(node.rightnode)){
			this.flipColors(node);
		}
		node.N = size(node.leftnode) + size(node.rightnode);
		return node;
	}
	
	public void print(){
		print(root);
	}
	public void print(Node node){				//打印，先打印左子树，再打印节点，再打印右子树
		if (node == null){
			return;
		}
		print(node.leftnode);
		System.out.print(node.key + "," + node.color + " ");
		print(node.rightnode);
	}
	public static void main(String args[]){
		RedBlackTree<String, Integer> rbt = new RedBlackTree<String, Integer>();
		rbt.put("f", 6);
		rbt.put("b", 2);
		rbt.put("e", 5);
		rbt.put("c", 3);
		rbt.put("a", 1);
		rbt.put("i", 9);
		rbt.put("g", 7);
		rbt.put("j", 10);	
		rbt.put("k", 11);
		rbt.put("h", 8);
		rbt.deleteMin();
		rbt.print();
	}
}
