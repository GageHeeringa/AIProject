import java.util.Hashtable;
import java.util.LinkedList;

import org.jgrapht.graph.AsUnweightedDirectedGraph;
import org.jgrapht.graph.DefaultEdge;


public class Prioritized<VertexType> {
	
	int distance(VertexType startUser, VertexType endUser, 
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
		
		while(numSteps < 300){
		    numSteps += 1;
		    LinkedList<VertexType> nextQueue = new LinkedList<VertexType>();
		    
		    while(startUserQueue.size() > 0){
		    	VertexType expand = startUserQueue.removeFirst();
		    	if(endUserExplored.containsKey(expand)){
		    		return numSteps;
		    	}
		    	if(!startUserExplored.containsKey(expand)){
		    		startUserExplored.put(expand, Integer.valueOf(1));
		    		for(DefaultEdge toFollowEdge : userDB.outgoingEdgesOf(expand)){
		    			VertexType toFollow = userDB.getEdgeTarget(toFollowEdge);
		    			if(!startUserExplored.containsKey(toFollow)){
		    				nextQueue.add(toFollow);
		    			}
		    		}
		    	}
		    }
		    //TODO: reorder
		    startUserQueue = nextQueue;
			

		    numSteps += 1;
		    nextQueue = new LinkedList<VertexType>();
		    
		    while(endUserQueue.size() > 0){
		    	VertexType expand = endUserQueue.removeFirst();
		    	if(startUserExplored.containsKey(expand)){
		    		return numSteps;
		    	}
		    	if(!endUserExplored.containsKey(expand)){
		    		endUserExplored.put(expand, Integer.valueOf(1));
		    		for(DefaultEdge toFollowEdge : userDB.outgoingEdgesOf(expand)){
		    			VertexType toFollow = userDB.getEdgeTarget(toFollowEdge);
		    			if(!endUserExplored.containsKey(toFollow)){
		    				nextQueue.add(toFollow);
		    			}
		    		}
		    	}
		    }
		    //TODO: reorder
		    endUserQueue = nextQueue;
		}
		
		return numSteps;
	}
	
}
