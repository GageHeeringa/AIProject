import java.util.AbstractMap.SimpleEntry;
import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedList;

import org.jgrapht.graph.AsUnweightedDirectedGraph;
import org.jgrapht.graph.DefaultEdge;


public class GreedyBFS<VertexType> implements GraphDepthSearch<VertexType> {

	@Override
	public int distance(VertexType source, VertexType destination,
			AsUnweightedDirectedGraph<VertexType, DefaultEdge> graph, Integer nodesGen[]) {

		int numSteps = 0;
		if(source == destination) return numSteps;

		Hashtable<VertexType, Integer> userExplored = new Hashtable<VertexType, Integer>();
		
		LinkedList<SimpleEntry<VertexType, Integer>> userQueue;
		userQueue = new LinkedList<SimpleEntry<VertexType, Integer>>();
		
		SimpleEntry<VertexType, Integer> sourceUser = new SimpleEntry<VertexType, Integer>(source, 1);
		userQueue.add(sourceUser);
		userExplored.put(source, Integer.valueOf(1));
		
		
		while(userQueue.size() > 0){
		    
		    numSteps += 1;
		    LinkedList<SimpleEntry<VertexType, Integer>> nextQueue = new LinkedList<SimpleEntry<VertexType, Integer>>();
		    
		    while(userQueue.size() > 0){
		    	VertexType expand = userQueue.removeFirst().getKey();
		    	if(destination == expand){
		    		nodesGen[0] = userExplored.size();
		    		return numSteps;
		    	}
		    	for(DefaultEdge toFollowEdge : graph.outgoingEdgesOf(expand)){
		    		VertexType toFollow = graph.getEdgeTarget(toFollowEdge);
		   			if(!userExplored.containsKey(toFollow)){
			    		userExplored.put(toFollow, Integer.valueOf(1));
			    		SimpleEntry<VertexType, Integer> tmp;
			    		tmp = new SimpleEntry<VertexType, Integer>(toFollow, graph.outDegreeOf(toFollow));
	    				nextQueue.add(tmp);
	    			}
		   		}
		    }

			Collections.sort(nextQueue, new PairComparator());
		    
		    userQueue = nextQueue;		    
			
		}
		
		nodesGen[0] = userExplored.size();
		return -1;
	}

	@Override
	public String name() {
		return "Greedy BFS";
	}

}
