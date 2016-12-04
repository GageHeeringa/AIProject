import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
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
		LinkedList<VertexType> path;
		HashSet<VertexType> touched;
		//System.out.println("X = " + x + "\nY = " + y);
		if(x.equals(y))
			return 0;
		
		path = new LinkedList<VertexType>();
		touched = new HashSet<VertexType>();

		// Until (Case) 'y' is adjacent or (Case) greedy heuristic gives node with no following or (Case) path too long
		VertexType id = x;
		while( true ){
			ArrayList<VertexType> following;
			int max;

			if(id != null)
				touched.add(id);
			
			if(id != null){
				path.add(id);
			}else if(id == null && !path.isEmpty()){
				id = path.removeLast();
				continue;
			}else{
				return -1;
			}

			following = new ArrayList<VertexType>();
			for(DefaultEdge edge : g.outgoingEdgesOf(id)){
				VertexType v = g.getEdgeTarget(edge);
				if(!touched.contains(v)){
					following.add(v);
				}
			}

			// Case: 'y' is adjacent
			if( following.contains(y) )
				return path.size()+1;

			// Greedy heuristic ( max # followings )
			VertexType maxID = null;
			max = -1;

			for(VertexType e : following){
				if(g.outDegreeOf(e) > max){
					max = g.outDegreeOf(e);
					maxID = e;
				}
			}

			id = maxID;
		}
	}

}

