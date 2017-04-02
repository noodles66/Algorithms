package org.algorithms.search;

public class ChainingHash  {
	private int N;
	private int M;
	private Node[] st;
	private class Node {
		String key = null;
		int value = 0;
		Node nextNode = null;
		
		public Node(String key, int value){
			this.key = key;
			this.value = value;
		}
	}
	
	public ChainingHash(int M){
		this.M = M;
		st =  new Node[M];
	}
	
	public int hash(String key){
		return (key.hashCode() & 0x7fffffff ) % M;
	}
	
	public void put(String key, int value){
		st[hash(key)] = put (st[hash(key)], key, value);
	}
	public Node put(Node node, String key, int value){
		if (node == null){
			node = new Node(key, value);
			N++;
			return node;
		}
		if (node.key == key){
			node.value = value;
			return node;
		}else{
			node.nextNode = put(node.nextNode, key, value );
			}
		return node;
	}
	
	public int get(String key){
		return get(st[hash(key)], key).value;
	}
	public Node get(Node node, String key){
		if (node == null){
			return null;
		}
		if (node.key == key){
			return node;
		}else{
			return get(node.nextNode, key);
		}
	}
	
	public void delete(String key){
		st[hash(key)] = delete(key, st[hash(key)]);
	}
	public Node delete(String key, Node node){
		if (node == null){
			return null;
		}
		if (node.key == key){
			return node.nextNode;
		}else{
			node.nextNode = delete(key, node.nextNode);
		}
		return node;
	}
	
	public void print(){
		for (int i = 0; i < M; i++){
			if (st[i] == null){
				System.out.println(2);
			}else{
				for (Node m = st[i]; m != null; m = m.nextNode){
					System.out.print(m.key + "," + m.value + " ");
				}
				System.out.println();
			}
		}
	}
	
	public static void main(String args[]){
		ChainingHash ch = new ChainingHash(5);
		ch.put("f", 6);
		ch.put("b", 2);
		ch.put("e", 5);
		ch.put("c", 3);
		ch.put("a", 1);
		ch.put("i", 9);
		ch.put("g", 7);
		ch.put("j", 10);	
		ch.put("k", 11);
		ch.put("h", 8);
		ch.delete("f");
		ch.print();	
		System.out.println(ch.get("a"));
	}
}
