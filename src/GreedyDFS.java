import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

import org.jgrapht.graph.AsUnweightedDirectedGraph;
import org.jgrapht.graph.DefaultEdge;


/**
 * Perform greedy search for degree of separation between two users in a social network.
 * 
 * @author Gage Heeringa
 * @author Josh Marshall
 */
public class GreedyDFS<VertexType> implements GraphDepthSearch<VertexType>{


	/**
	 * Given two user IDs, find their degree of separation using greedy heuristic.
	 */
	@Override
	public int distance( VertexType x, VertexType y, AsUnweightedDirectedGraph<VertexType, DefaultEdge> g, Integer nodesGen[]){
		LinkedList<VertexType> path;
		HashSet<VertexType> touched;
		
		if(x.equals(y))
			return 0;
		
		path = new LinkedList<VertexType>();
		touched = new HashSet<VertexType>();

		// Until (Case) 'y' is adjacent or (Case) greedy heuristic gives node with no following or (Case) path too long
		VertexType id = x;
		while( true ){
			ArrayList<VertexType> following;
			int max;

			following = new ArrayList<VertexType>();
			for(DefaultEdge edge : g.outgoingEdgesOf(id)){
				VertexType v = g.getEdgeTarget(edge);
				if(!touched.contains(v)){
					following.add(v);
				}
			}

			if( following.contains(y) ){
				nodesGen[0] = touched.size();
				return path.size()+1;
			}
				

			// Greedy heuristic ( max # followings )
			max = -1;
			id = null;
			
			for(VertexType e : following){
				if(g.outDegreeOf(e) > max){
					max = g.outDegreeOf(e);
					id = e;
				}
			}
			
			if(id != null){
				path.add(id);
				touched.add(id);
			}else if(id == null && !path.isEmpty()){
				id = path.removeLast();
				continue;
			}else{
				nodesGen[0] = touched.size();
				return -1;
			}
		}
	}

	@Override
	public String name() {
		return "Greedy DFS";
	}

}

