import static org.junit.Assert.*;

import org.junit.Test;


public class BinaryTreeTest {

	@Test
	public void test() {
		BinaryTree<String,Integer> blah = new BinaryTree();
		blah.insertion("Anjay", 12);
		blah.insertion("Bnjay", 99);
		blah.insertion("Cnjay", 939);
		blah.insertion("1njay", 91111);
		System.out.println(blah.numOfNodes(blah.root));
		blah.insertion("1njdday", 91111);
		System.out.println(blah.numOfNodes(blah.root));
		blah.insertion("Dnjddday", 9);
		blah.insertion("Dasdfnjay", 9);
		System.out.println(blah.numOfNodes(blah.root));
		blah.goThroughTree();
		System.out.println("__-__-_-_-_-_-");
		for( int i = 0; i < blah.numOfNodes(blah.root); i++) {
			System.out.println(blah.lookupNode(i));
		}
		
	}

}
