import java.util.Hashtable;
import java.util.LinkedList;

import org.jgrapht.graph.AsUnweightedDirectedGraph;
import org.jgrapht.graph.DefaultEdge;


public class BFS<VertexType> implements GraphDepthSearch<VertexType> {

	@Override
	public FoundSearchData distance(VertexType source, VertexType destination,
			AsUnweightedDirectedGraph<VertexType, DefaultEdge> graph) {

		int numSteps = 0;
		
		if(source == destination){
			return new FoundSearchData(0, 0);
		}

		Hashtable<VertexType, Integer> startUserExplored = new Hashtable<VertexType, Integer>();
		
		LinkedList<VertexType> startUserQueue;
		startUserQueue = new LinkedList<VertexType>();
		

		startUserQueue.add(source);
		startUserExplored.put(source, Integer.valueOf(1));
		
		while(numSteps < graph.vertexSet().size()){
		    
		    numSteps++;
		    LinkedList<VertexType> nextQueue = new LinkedList<VertexType>();
		    
		    while(startUserQueue.size() > 0){
		    	VertexType expand = startUserQueue.removeFirst();
		    	for(DefaultEdge toFollowEdge : graph.outgoingEdgesOf(expand)){
		    		VertexType toFollow = graph.getEdgeTarget(toFollowEdge);
		   			if(!startUserExplored.containsKey(toFollow)){
		   				if(toFollow == destination){
		   					return new FoundSearchData(numSteps + 1, startUserExplored.size());
		   				}
			    		startUserExplored.put(toFollow, Integer.valueOf(1));
	    				nextQueue.add(toFollow);
	    			}
		   		}
		    	
		    }
		    startUserQueue = nextQueue;
		}
		
		return new FoundSearchData(-1, startUserExplored.size());
	}

	@Override
	public String name() {
		return "BFS";
	}

}
