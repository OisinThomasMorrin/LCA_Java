import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DAGTest {

	DAG acyclic =new DAG(8);//create an acyclic graph
	DAG cycle = new DAG(9);//create a cycle graph
	DAG directAcyclic = new DAG(9);//creates an acyclic graph

	//testing the set up of the constructor
	@Test//must be non-negative
	public void testDAG() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {new DAG(-2);});//will throw IllegalException if out of bounds
	}

	//Test the indegree of a vertex in the graph
	@Test
	public void testIndegree(){
		acyclicGraph();//has no cycle
		cycleGraph();//has cycle
		Assertions.assertThrows(IllegalArgumentException.class, () -> { acyclic.indegree(-3);});
		assertEquals("A straight line of vertices so each will only have one", 1, acyclic.indegree(5));
		assertEquals("2 edges leading into vertex eight", 2, cycle.indegree(8));
	}

	//Test the outdegree of a vertex in the graph
	@Test
	public void testOutdegree(){
		acyclicGraph();//has no cycle
		cycleGraph();//has cycle
		Assertions.assertThrows(IllegalArgumentException.class, () -> {acyclic.outdegree(9);});
		assertEquals("0 only has one outdegree", 1, acyclic.outdegree(0));	
		assertEquals("0 for the cycle graph has two outdegree edges", 2, cycle.outdegree(0));
	}

	//Test the adjacency array
	@Test
	public void testAdj(){
		acyclicGraph();//has no cycle
		cycleGraph();//has cycle
		Assert.assertArrayEquals(new int[]{4}, acyclic.adj(3));//Adjacency array of 3 only travels to one vertex
		Assert.assertArrayEquals(new int[]{1,6}, cycle.adj(3));//Adjacency array of 3 in a cycle graph has two outdegrees to one and six
	}

	//test the amount of edges with in a graph
	@Test
	public void testE(){
		acyclicGraph();
		cycleGraph();
		directAcyclicGraph();
		assertEquals("Number of edges within the graph should be", 7, acyclic.E());
		assertEquals("Number of edges within the graph should be", 9, cycle.E());
		assertEquals("Number of verices within the graph should be", 9, directAcyclic.E());
	}

	//test the number of vertices within a graph
	@Test
	public void testV(){
		acyclicGraph();
		cycleGraph();
		directAcyclicGraph();
		assertEquals("Number of vertices within the graph should be", 8, acyclic.V());
		assertEquals("Number of vertices within the graph should be", 9, cycle.V());
		assertEquals("Number of verices within the graph should be", 9, directAcyclic.V());
	}

	//test that the vertex passed through is valid for the graph(Nonnegative
	@Test
	public void testValidVertex(){
		DAG validTest = new DAG(3);
		//try add negative values, will throw exception
		Assertions.assertThrows(IllegalArgumentException.class, () -> {validTest.addEdge(-1, 2);});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {validTest.addEdge(1, -2);});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {validTest.addEdge(-1, -2);});
		assertEquals("Should contain no edges", 0, validTest.E());
		//will not throw exception, but let it pass through
		validTest.addEdge(1, 2);
		assertEquals("Should contain 1 edge", 1, validTest.E());
	}

	//test that adding an edge between two vertices crates a connection
	@Test
	public void testAddEdge(){
		acyclicGraph();//has no cycle
		cycleGraph();//has cycle
		assertEquals("Number of edges for DAG is", 7, acyclic.E());
		Assertions.assertThrows(IllegalArgumentException.class, () -> {acyclic.addEdge(5, 8);});
		assertEquals("Number of edges for DAG should not increase, so still ", 7, acyclic.E());
		Assertions.assertThrows(IllegalArgumentException.class, () -> {acyclic.addEdge(-1, -1);});
		assertEquals("Number of edges for DAG should not increase, so still", 7, acyclic.E());
	}

	//test that the graph is acyclic, if there is a cycle the method will throw true that there is a cycle
	//with in the graph
	@Test
	public void testCycle(){
		acyclicGraph();//has no cycle
		cycleGraph();//has cycle
		directAcyclicGraph();//acyclic
		Assert.assertFalse(acyclic.hasCycle());//it is acyclic
		Assert.assertTrue(cycle.hasCycle());//there exists a cycle within the graph
		Assert.assertFalse(directAcyclic.hasCycle());//there exists a cycle within the graph
		DAG emptyGraph = new DAG(0);
		Assert.assertFalse(emptyGraph.hasCycle());//No graph therefore no cycle
	}

	//Testing the LCA method, will test for various problems that may arise
	@Test
	public void testLCA(){
		acyclicGraph();
		cycleGraph();
		directAcyclicGraph();
		//test the lca for both acyclic and cycled graph using two vertices
		Assertions.assertThrows(IllegalArgumentException.class, () -> { acyclic.findLCA(2, 3);});
		Assertions.assertThrows(IllegalArgumentException.class, () -> { cycle.findLCA(1, 4);});
		//showing different levels within the graph
		Assertions.assertThrows(IllegalArgumentException.class, () -> { directAcyclic.findLCA(3, 4);});
		Assertions.assertThrows(IllegalArgumentException.class, () -> { directAcyclic.findLCA(1, 4);});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {directAcyclic.findLCA(5, 2);});
		//swapping around the vertices v and w
		Assertions.assertThrows(IllegalArgumentException.class, () -> { directAcyclic.findLCA(1, 5);});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {directAcyclic.findLCA(5, 1);});
		//same v as w
		Assertions.assertThrows(IllegalArgumentException.class, () -> { acyclic.findLCA(3, 3);});
		//empty graph
		DAG emptyG = new DAG(0);
		Assertions.assertThrows(IllegalArgumentException.class, () -> { emptyG.findLCA(3, 3);});
	}

	//function to create an acyclic graph that I will use in the tests
	public void acyclicGraph(){
		//1->2->3->4->5->6->7
		acyclic.addEdge(0, 1);
		acyclic.addEdge(1, 2);
		acyclic.addEdge(2, 3);
		acyclic.addEdge(3, 4);
		acyclic.addEdge(4, 5);
		acyclic.addEdge(5, 6);
		acyclic.addEdge(6, 7);
	}

	public void directAcyclicGraph(){
		//  -> 1 -> 3 -> 5 ->
		//0    ^              7 -> 8             
		//  -> 2 -> 4 -> 6 ->      
		directAcyclic.addEdge(0, 1);
		directAcyclic.addEdge(0, 2);
		directAcyclic.addEdge(1, 3);
		directAcyclic.addEdge(2, 4);
		directAcyclic.addEdge(3, 5);
		directAcyclic.addEdge(4, 6);
		directAcyclic.addEdge(5, 7);
		directAcyclic.addEdge(6, 7);
		directAcyclic.addEdge(7, 8);
	}

	//function to create an graph(that cycles) that I will use in the tests
	public void cycleGraph(){
		//  -> 1    <- 3 -> 6 -> 8 
		//0    ->      ^         ^
		//  ->    2 -> 4         7
		cycle.addEdge(0, 1);
		cycle.addEdge(0, 2);
		cycle.addEdge(1, 2);
		cycle.addEdge(2, 4);
		cycle.addEdge(4, 3);
		cycle.addEdge(3, 1);
		cycle.addEdge(3, 6);
		cycle.addEdge(6, 8);
		cycle.addEdge(7, 8);
	}	
}
