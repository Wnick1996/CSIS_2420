package undirectedGraph;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdOut;

/**
 *  The <tt>DepthFirstPaths</tt> class represents a data type for finding
 *  paths from a source vertex <em>s</em> to every other vertex
 *  in an undirected graph.
 *  <p>
 *  This implementation uses depth-first search.
 *  The constructor takes time proportional to <em>V</em> + <em>E</em>,
 *  where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 *  It uses extra space (not including the graph) proportional to <em>V</em>.
 *  <p>
 *  For additional documentation, see <a href="/algs4/41graph">Section 4.1</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class DepthFirstPathsModified {
    private boolean[] marked;    // marked[v] = is there an s-v path?
    private int[] edgeTo;        // edgeTo[v] = last edge on s-v path
    private final int s;         // source vertex

    /**
     * Computes a path between <tt>s</tt> and every other vertex in graph <tt>G</tt>.
     * @param G the graph
     * @param s the source vertex
     */
    public DepthFirstPathsModified(Graph G, int s) {
        this.s = s;
        edgeTo = new int[G.V()];
        marked = new boolean[G.V()];
        dfs_iterative(G, s);
    }

    // depth first search from v
//    private void dfs(Graph G, int v) {
//        marked[v] = true;
//        for (int w : G.adj(v)) {
//            if (!marked[w]) {
//                edgeTo[w] = v;
//                dfs(G, w);
//            }
//        }
//    }
    
    private void dfs_iterative(Graph G, int v) {
    	Stack<Integer> vertices = new Stack<>();
    	marked[v] = true;
    	vertices.push(v);
    	while (!vertices.isEmpty()) {
    		Integer vertex = vertices.peek();
    		boolean allMarked = true;
    		for (int i : G.adj(vertex)) {
    			if (!marked[i]) {
    				vertices.push(i);
    				edgeTo[i] = vertex;
    				marked[i] = true;
    				allMarked = false;
    				break;
    			}
    		}
    		if (allMarked) vertices.pop();
    	}
    }

    /**
     * Is there a path between the source vertex <tt>s</tt> and vertex <tt>v</tt>?
     * @param v the vertex
     * @return <tt>true</tt> if there is a path, <tt>false</tt> otherwise
     */
    public boolean hasPathTo(int v) {
        return marked[v];
    }

    /**
     * Returns a path between the source vertex <tt>s</tt> and vertex <tt>v</tt>, or
     * <tt>null</tt> if no such path.
     * @param v the vertex
     * @return the sequence of vertices on a path between the source vertex
     *   <tt>s</tt> and vertex <tt>v</tt>, as an Iterable
     */
    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<Integer>();
        for (int x = v; x != s; x = edgeTo[x])
            path.push(x);
        path.push(s);
        return path;
    }

    /**
     * Unit tests the <tt>DepthFirstPaths</tt> data type.
     */
    public static void main(String[] args) {
    	String fileName = "src/undirectedGraph/exercise.txt";
    	int s = 2; // start vertex
    	
    	In in = new In(fileName); //In in = new In(args[0]);
        Graph G = new Graph(in); //int s = Integer.parseInt(args[1]);
        DepthFirstPathsModified dfs = new DepthFirstPathsModified(G, s);
        
        StdOut.println("Adjacency List:");
        for (int v = 0; v < G.V(); v++) {
        	StdOut.print(v + ": ");
        	for (int adj : G.adj(v)) {
        		StdOut.print(adj + " ");
        	}
        	StdOut.print("\n");
        }
        StdOut.println();
        
        StdOut.println("Marked\tedgeTo");
        for (int v = 0; v < G.V(); v++) {
        	StdOut.println(dfs.hasPathTo(v) + "\t" + dfs.edgeTo[v]);
        }
        
        StdOut.println();
        for (int v = 0; v < G.V(); v++) {
            if (dfs.hasPathTo(v)) {
                StdOut.printf("%d to %d:  ", s, v);
                for (int x : dfs.pathTo(v)) {
                    if (x == s) StdOut.print(x);
                    else        StdOut.print("-" + x);
                }
                StdOut.println();
            }

            else {
                StdOut.printf("%d to %d:  not connected\n", s, v);
            }

        }


    }

}