import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import org.jgrapht.graph.AsUnweightedDirectedGraph;
import org.jgrapht.graph.DefaultEdge;


/**
 * Perform greedy search for degree of separation between two users in a social network.
 * 
 * @author Gage Heeringa
 * @author Josh Marshall
 */
public class Greedy<VertexType> {

	/**
	 * Given two user IDs, find their degree of separation using greedy heuristic.
	 */
	int degSep( VertexType x, VertexType y, AsUnweightedDirectedGraph<VertexType, DefaultEdge> g){
		System.out.println("X = " + x + "\nY = " + y);
		if(x.equals(y)){
			if( g.containsVertex(x) ) // Make sure they're in the graph
				return 0;
			else
				return -1;
		}

		ArrayList<VertexType> path = new ArrayList<VertexType>();

		// Until (Case) 'y' is adjacent or (Case) greedy heuristic gives node with no following or (Case) path too long
		VertexType id = x;
		while( true ){
			path.add(id);

			// Case: path length reached 325
			if( path.size() > 325){
				System.out.printf("--- Case: path length exceeded 325\n");
				return -1;
			}

			System.out.printf("\nPath = %s\n", path.toString());
			DefaultEdge[] followingEdges = new DefaultEdge[g.outDegreeOf(id)];
			g.outgoingEdgesOf(id).toArray(followingEdges);
			Set<VertexType> following = new HashSet<VertexType>();
			for(int i = 0; i < followingEdges.length; i++)
				following.add(g.getEdgeTarget(followingEdges[i]));

			// Case: greedy heuristic gives node with no following
			if( following == null || following.size() == 0){
				System.out.printf("--- Case: greedy heuristic gives node with no following\n");
				return -1;
			}

			// Case: 'y' is adjacent
			if( following.contains(y) ){
				System.out.printf("--- Case: 'y' is adjacent\n");
				//path.add(y);
				return path.size();
			}

			// Greedy heuristic ( max # followings )
			Iterator< VertexType > iterator = following.iterator();
			VertexType e = iterator.next();
			int max = 0;
			if( g.containsVertex(e) )
				max = g.outDegreeOf(e);
			VertexType maxID = e;
			System.out.printf("Initial Max = (%s, %d)\n", maxID, max);

			while( iterator.hasNext() ){
				int k = 0;
				if( g.containsVertex(e) )
					k = g.outDegreeOf(e);

				if(k > max){
					max = k;
					maxID = e;
					System.out.printf("New Max = (%s, %d)\n", maxID, max);
				}

				// avoid revisiting IDs
				do{
					e = iterator.next();
				} while( path.contains(e));
			}

			id = maxID;
		}
	}

}


///**
// * Represent a social network.
// * 
// * @author Gage Heeringa
// */
//class Graph {
//
//	HashMap<String, Set<String>> _vertices; 
//
//	/**
//	 * Construct a new, empty graph.
//	 */
//	Graph(){
//		_vertices = new HashMap<String, Set<String>>();
//	}
//
//	/**
//	 * Add vertex to graph.
//	 */
//	void add(String s, Set<String> list){
//		if(!_vertices.containsKey(s)){
//			_vertices.put(s, list);
//		}
//	}
//
//	public String toString(){
//		String s = "Vertices:\n";
//
//		Iterator< Entry<String, Set<String>> > iterator = _vertices.entrySet().iterator();
//		while(iterator.hasNext()) {
//			Entry<String, Set<String>> e = iterator.next();
//			s += e.getKey().toString() + " follows:\n[";
//
//			for(String b : e.getValue()){
//				s += b.toString() + ", ";
//			}
//
//			s += "]\n\n";
//		}
//
//		return s;
//	}
//}
