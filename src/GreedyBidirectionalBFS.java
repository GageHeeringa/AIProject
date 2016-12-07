import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.AbstractMap.SimpleEntry;

import org.jgrapht.graph.AsUnweightedDirectedGraph;
import org.jgrapht.graph.DefaultEdge;


public class GreedyBidirectionalBFS<VertexType> implements GraphDepthSearch<VertexType> {

	@Override
	public FoundSearchData distance(VertexType source, VertexType destination,
			AsUnweightedDirectedGraph<VertexType, DefaultEdge> graph) {

		int numSteps;
		if(source == destination) return new FoundSearchData(0, 0);
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
		    	
		    	for(DefaultEdge toFollowEdge : graph.outgoingEdgesOf(expand)){
		    		VertexType toFollow = graph.getEdgeTarget(toFollowEdge);
		    		if(endUserExplored.contains(toFollow))
		    			return new FoundSearchData(numSteps, sourceUserExplored.size() + endUserExplored.size());
		    			
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
		    		if(sourceUserExplored.contains(toFollow))
		    			return new FoundSearchData(numSteps, sourceUserExplored.size() + endUserExplored.size());
		   
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
		
		return new FoundSearchData(-1, sourceUserExplored.size() + endUserExplored.size());
	}

	@Override
	public String name() {
		return "Greedy Bidirectional BFS";
	}

}
