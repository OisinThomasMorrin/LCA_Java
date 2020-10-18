import static org.junit.Assert.*;

import org.junit.Test;

public class LCATest {

	//	Prior to starting my code I am going to want to test all expected areas. 
	//	Need to test the LCA for two given nodes
	//	
	//	Visualizing a Binary Tree like so:
	//	
	//	    ______3______
	//     /             \
	//   __5__          __1__
	//  /     \        /     \
	// 6       2      0       8
	//        / \
	//       7   4

	LCA tree = new LCA();

	@Test
	public void testTree() {
		createTree();

		//testing that the LCA function is working when I implement it, order should not matter
		assertEquals("LCA = 3",3 ,tree.findLCA(1, 5));
		assertEquals("LCA = 3",3 ,tree.findLCA(5, 1));//change the order around
		
		//widening the parameters
		assertEquals("LCA = 3",3 ,tree.findLCA(6, 1));
		assertEquals("LCA = 2",2 ,tree.findLCA(7, 4));
		assertEquals("LCA = 1",1 ,tree.findLCA(0, 8));
	}

	@Test
	public void testConstructor() {

		Node rootNode = new Node(0);//only the root node 
		assertEquals("Only one node, therefor left side is equal to null",null ,rootNode.left);
		assertEquals("Only one node, therefor right side is equal to null",null ,rootNode.right);
		assertEquals("Value = 0",0 ,rootNode.value);
	}

	@Test
	public void testNullTree() {
		assertNull(tree.root);
	}

	@Test
	public void testSmallTree() {
		//create a new small tree
		tree.root = new Node(3);
		tree.root.left = new Node(5);
		
		assertEquals(3 ,tree.findLCA(3, 5));
		assertEquals(3 ,tree.findLCA(5, 3));
		assertEquals(5 ,tree.root.left.value);
		assertNull(tree.root.right);
		//assertEquals(null ,tree.root.right.value);
		assertEquals(3 ,tree.root.value);
	}
	
	@Test
	public void testNonExistingNodes(){
		createTree();
		
		//one element doesn't exist
		assertEquals("Will equal to -1 if does not exist",-1 ,tree.findLCA(1, 23));
		//neither elements exist
		assertEquals("Will equal to -2 if neither exist",-1,tree.findLCA(9,10));
		assertEquals("LCA = null",3 ,tree.findLCA(3, 3));
	}
	
	@Test
	public void testSameNode(){
		createTree();
		
		//same root element, LCA is the element and the root
		assertEquals("LCA = null",3 ,tree.findLCA(3, 3));
		
		//one root element and another, LCA is one of the elements and the root 
		assertEquals(3 ,tree.findLCA(3, 5));//checked that LCA can still be parent
		assertEquals(3 ,tree.findLCA(1, 3));
		
		//same non root element, LCA is the element itself
		assertEquals("LCA = 1",1 ,tree.findLCA(1, 1));
		assertEquals("LCA = 5",5 ,tree.findLCA(5, 5));
	}
	
	//Method to create a tree prevent duplication of code
	public void createTree(){
		tree.root = new Node(3);
		tree.root.left = new Node(5);
		tree.root.right = new Node(1);
		tree.root.left.left = new Node(6);
		tree.root.left.right = new Node(2);
		tree.root.right.left = new Node(0);
		tree.root.right.right = new Node(8);
		tree.root.left.right.left = new Node(7);
		tree.root.left.right.right = new Node(4);
	}
}