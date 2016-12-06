import java.util.Hashtable;
import java.util.LinkedList;

import org.jgrapht.graph.AsUnweightedDirectedGraph;
import org.jgrapht.graph.DefaultEdge;


public class BidirectionalBFS<VertexType> implements GraphDepthSearch<VertexType>{
	
	public int distance(VertexType startUser, VertexType endUser, 
				AsUnweightedDirectedGraph<VertexType, DefaultEdge> userDB){
		
		int numSteps = 0;
		if(startUser == endUser) return numSteps;

		Hashtable<VertexType, Integer> startUserExplored = new Hashtable<VertexType, Integer>();
		Hashtable<VertexType, Integer> endUserExplored = new Hashtable<VertexType, Integer>();
		
		LinkedList<VertexType> startUserQueue, endUserQueue;
		startUserQueue = new LinkedList<VertexType>();
		endUserQueue   = new LinkedList<VertexType>();
		

		startUserQueue.add(startUser);
		endUserQueue.add(endUser);
		startUserExplored.put(startUser, Integer.valueOf(1));
		endUserExplored.put(endUser, Integer.valueOf(1));
		
		
		while(startUserQueue.size() > 0 && endUserQueue.size() > 0){
		    
		    numSteps += 1;
		    LinkedList<VertexType> nextQueue = new LinkedList<VertexType>();
		    
		    while(startUserQueue.size() > 0){
		    	VertexType expand = startUserQueue.removeFirst();
		    	if(endUserExplored.containsKey(expand)){
		    		return numSteps;
		    	}
		    	for(DefaultEdge toFollowEdge : userDB.outgoingEdgesOf(expand)){
		    		VertexType toFollow = userDB.getEdgeTarget(toFollowEdge);
		   			if(!startUserExplored.containsKey(toFollow)){
			    		startUserExplored.put(toFollow, Integer.valueOf(1));
	    				nextQueue.add(toFollow);
	    			}
		   		}
		    	
		    }
		    startUserQueue = nextQueue;
			

		    numSteps += 1;
		    nextQueue = new LinkedList<VertexType>();
		    
		    while(endUserQueue.size() > 0){
		    	VertexType expand = endUserQueue.removeFirst();
		    	if(startUserExplored.containsKey(expand)){
		    		return numSteps;
		    	}
		    	for(DefaultEdge toFollowEdge : userDB.incomingEdgesOf(expand)){
		    		VertexType toFollow = userDB.getEdgeSource(toFollowEdge);
		    		if(!endUserExplored.containsKey(toFollow)){
			    		endUserExplored.put(toFollow, Integer.valueOf(1));
		   				nextQueue.add(toFollow);
	    			}
	    		}
	    	}
		    endUserQueue = nextQueue;
		}
		
		return -1;
	}

	@Override
	public String name() {
		return "Bidirectional BFS";
	}
	
}
