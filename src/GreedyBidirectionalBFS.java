import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.AbstractMap.SimpleEntry;

import org.jgrapht.graph.AsUnweightedDirectedGraph;
import org.jgrapht.graph.DefaultEdge;


public class GreedyBidirectionalBFS<VertexType> implements GraphDepthSearch<VertexType> {

	@Override
	public int distance(VertexType source, VertexType destination,
			AsUnweightedDirectedGraph<VertexType, DefaultEdge> graph, Integer nodesGen[]) {

		int numSteps;
		if(source == destination) return 0;
		numSteps = 0;

		Hashtable<VertexType, Integer> sourceUserExplored = new Hashtable<VertexType, Integer>();
		Hashtable<VertexType, Integer> endUserExplored = new Hashtable<VertexType, Integer>();

		LinkedList<SimpleEntry<VertexType, Integer>> sourceUserQueue;
		LinkedList<SimpleEntry<VertexType, Integer>> endUserQueue;
		sourceUserQueue = new LinkedList<SimpleEntry<VertexType, Integer>>();
		endUserQueue = new LinkedList<SimpleEntry<VertexType, Integer>>();
		
		sourceUserQueue.add(new SimpleEntry<VertexType, Integer>(source, 1));
		sourceUserExplored.put(source, Integer.valueOf(1));
		
		endUserQueue.add(new SimpleEntry<VertexType, Integer>(destination, 1));
		endUserExplored.put(destination, Integer.valueOf(1));
		
		
		while(sourceUserQueue.size() > 0 && endUserQueue.size() > 0){
		    
		    numSteps += 1;
		    LinkedList<SimpleEntry<VertexType, Integer>> nextQueue = new LinkedList<SimpleEntry<VertexType, Integer>>();
		    
		    while(sourceUserQueue.size() > 0){
		    	VertexType expand = sourceUserQueue.removeFirst().getKey();
		    	if(destination == expand){
		    		nodesGen[0] = sourceUserExplored.size() + endUserExplored.size();
		    		return numSteps;
		    	}
		    	for(DefaultEdge toFollowEdge : graph.outgoingEdgesOf(expand)){
		    		VertexType toFollow = graph.getEdgeTarget(toFollowEdge);
		    		if(endUserExplored.contains(toFollow)){
		    			nodesGen[0] = sourceUserExplored.size() + endUserExplored.size();
		    			return numSteps;
		    		}
		    			
		   			if(!sourceUserExplored.containsKey(toFollow)){
			    		sourceUserExplored.put(toFollow, Integer.valueOf(1));
			    		SimpleEntry<VertexType, Integer> tmp;
			    		tmp = new SimpleEntry<VertexType, Integer>(toFollow, graph.outDegreeOf(toFollow));
	    				nextQueue.add(tmp);
	    			}
		   		}
		    }

			Collections.sort(nextQueue, new PairComparator());
		    
		    sourceUserQueue = nextQueue;

///////////////////////////////////////////////////////////////////////////////////////////////////////
		    
		    numSteps += 1;
		    nextQueue = new LinkedList<SimpleEntry<VertexType, Integer>>();
		    
		    while(endUserQueue.size() > 0){
		    	VertexType expand = endUserQueue.removeFirst().getKey();
		    	for(DefaultEdge toFollowEdge : graph.incomingEdgesOf(expand)){
		    		VertexType toFollow = graph.getEdgeSource(toFollowEdge);
		    		if(sourceUserExplored.contains(toFollow)){
		    			nodesGen[0] = sourceUserExplored.size() + endUserExplored.size();
		    			return numSteps;
		    		}
		   			if(!endUserExplored.containsKey(toFollow)){
			    		endUserExplored.put(toFollow, Integer.valueOf(1));
			    		SimpleEntry<VertexType, Integer> tmp;
			    		tmp = new SimpleEntry<VertexType, Integer>(toFollow, graph.inDegreeOf(toFollow));
	    				nextQueue.add(tmp);
	    			}
		   		}
		    }

			Collections.sort(nextQueue, new PairComparator());
		    
		    endUserQueue = nextQueue;		    
			
		}
		
		nodesGen[0] = sourceUserExplored.size() + endUserExplored.size();
		return -1;
	}

	@Override
	public String name() {
		return "Greedy Bidirectional BFS";
	}

}
