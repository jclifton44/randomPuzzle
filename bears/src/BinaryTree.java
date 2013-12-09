
class Node<K extends Comparable<? super K>,V> { 
	public K key;
	public V value;
	public Node<K,V> left;
	public Node<K,V> right;
	public Node<K,V> parent;
	
	public Node(K key, V value, Node<K,V> left, Node<K,V> right){
		this.key = key;
		this.value = value;
		this.left = left;
		this.left = right;
		if(left != null) {
			left.parent = this;
		}
		if(right != null) {
			right.parent = this;
		}		
		this.parent = null;
	}
	
}
public class BinaryTree<K extends Comparable<? super K>,V> {
	public Node<K,V> root;
	public int index = -1;
	public V lookupNode(int n) {
		index = -1;
		Node<K,V> node = lookupByIndex(n,root);
		index = -1;
		if(node != null) {
			return node.value;
		}
		return null;
		
	}
	public Node<K,V> lookupByIndex(int nodeNumber, Node<K,V> n) {
		if(n.left != null) {
			Node<K,V> leftN = lookupByIndex(nodeNumber, n.left);
			if(leftN != null) {
				return leftN;
			}
		}
		index++;
		if(index == nodeNumber) {
			return n;
		}
		//index++;
		if(n.right != null) {
			Node<K,V> leftR = lookupByIndex(nodeNumber, n.right);
			if(leftR != null) {
				return leftR;
			}
		}
		return null;
		
	}
	public int numOfNodes(Node<K,V> n) {
		int num = 0;
		if(n == null) {
			return num;
		}
		num += numOfNodes(n.left); 
		num++;
		num += numOfNodes(n.right);
		return num;
	}
			
		
	public Node<K,V> lookup(K key) {
		Node<K,V> n = root;
		if(key == null) { return null; }
		while(n != null) {
			if(n.key.equals(key))
				return n;
			else if(key.compareTo(n.key) < 0) 
				n = n.left;
			else if(key.compareTo(n.key) > 0)
				n = n.right;
		}
		return n;
	}
	public void insertion(K key, V value) {
		Node<K,V> nodeInsert = new Node<K,V>(key,value,null,null);
		if(key == null) {return;}
		if (root == null) {
			root = nodeInsert;
		}
		Node<K,V> n = root;
		while(true) {
			if(key.compareTo(n.key) == 0) {
				n.value = value;
				return;
			} else if (key.compareTo(n.key) < 0) {
				if(n.left == null) {
					n.left = nodeInsert;
					break;
				} else {
					n = n.left;
				}
			} else if (key.compareTo(n.key) > 0) {
				if(n.right == null) {
					n.right = nodeInsert;
					break;
				} else {
					n = n.right;
				}
				
			}
		}	
		nodeInsert.parent = n;
	}
	public V getValue(K key){
		if(lookup(key) != null) {
			return lookup(key).value;
		} else { return null; }
	}
	public void goThroughTree() {
		Node<K,V> n = root;
		inorderTraversal(root);
		
	}
	public void inorderTraversal(Node<K,V> n) {
		if(n == null){
			return;
		}
		inorderTraversal(n.left);
		System.out.println(n.key + " -> " + n.value);
		inorderTraversal(n.right);
	}
	public void deleteNode(K key) {
		Node<K,V> n = this.lookup(key);
		if(n == null) {
			return;
		} else if(n.parent == null) {
			if (n.left == null && n.right == null) {
				root = null;
			} else if (n.left == null && n.right != null) {
				root = root.right;
				root.parent = null;
			} else if (n.left != null && n.right == null) {
				root = root.left;
				root.parent = null;
			} else if (n.left != null && n.right != null) {
				
				root = n.left;
				Node<K,V> tempNode = n.right;
				while(tempNode.left != null) {
					tempNode = tempNode.left;
				}	
				tempNode.left = root.right;
				root.right.parent = tempNode;
				root.right = n.right;
				n.right.parent = root;
				root.parent = null;
			}	
		} else if (n.left == null && n.right == null) {
			if(n == n.parent.left) {
				n.parent.left = null;
			}
			if(n == n.parent.right) {
				n.parent.right = null;
			}
		} else if (n.left == null && n.right != null) {
			if(n == n.parent.left) {
				n.parent.left = n.right;
			}
			if(n == n.parent.right) {
				n.parent.right = n.right;
			}	
		} else if (n.left != null && n.right == null) {
			if(n == n.parent.left) {
				n.parent.left = n.right;
			}
			if(n == n.parent.right) {
				n.parent.right = n.right;
			}	
		} else if(n == n.parent.left) {
			Node<K,V> tempNode = n.right; 
			while(tempNode.left != null) {
				tempNode = tempNode.left;
			}
			tempNode.left = n.left;
			n.left.parent = tempNode.left;
			n.parent.left = tempNode;
			tempNode.parent = n.parent;
		}	else if(n == n.parent.right) {
			Node<K,V> tempNode = n.left; 
			while(tempNode.right != null) {
				tempNode = tempNode.right;
			}
			tempNode.right = n.right;
			n.right.parent = tempNode.right;
			n.parent.right = tempNode;
			tempNode.parent = n.parent;
		}	
	
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
