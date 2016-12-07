import java.util.HashSet;
import java.util.LinkedList;

import org.jgrapht.graph.AsUnweightedDirectedGraph;
import org.jgrapht.graph.DefaultEdge;


public class BidirectionalDFS<VertexType> implements GraphDepthSearch<VertexType>{

	@Override
	public int distance(VertexType source, VertexType destination,
			AsUnweightedDirectedGraph<VertexType, DefaultEdge> graph, Integer nodesGen[]) {
		LinkedList<VertexType> pathFromSource;
		HashSet<VertexType> touchedFromSource;
		LinkedList<VertexType> pathFromDest;
		HashSet<VertexType> touchedFromDest;
		
		if(source.equals(destination))
			return 0;

		pathFromSource = new LinkedList<VertexType>();
		touchedFromSource = new HashSet<VertexType>();
		pathFromDest = new LinkedList<VertexType>();
		touchedFromDest = new HashSet<VertexType>();

		// Until (Case) 'y' is adjacent or (Case) greedy heuristic gives node with no following or (Case) path too long
		VertexType idFromSource = source;
		VertexType idFromDest = destination;
		while( true ){
			boolean found = false;
			
			for(DefaultEdge edge : graph.outgoingEdgesOf(idFromSource)){
				VertexType v = graph.getEdgeTarget(edge);
				if(!touchedFromSource.contains(v)){
					if(touchedFromDest.contains(v)){
						nodesGen[0] = touchedFromSource.size() + touchedFromDest.size();
						return pathFromSource.size() + pathFromDest.size() + 1;
					}
						
					idFromSource = v;
					found = true;
					break;
				}
			}
			
			if(found){
				touchedFromSource.add(idFromSource);
				pathFromSource.add(idFromSource);
			}else if(!pathFromSource.isEmpty()){
				idFromSource = pathFromSource.removeLast();
			}else{
				nodesGen[0] = touchedFromSource.size() + touchedFromDest.size();
				return -1;
			}
			
/////////////////////////////////////////////////////////////////////////
			
			found = false;
			for(DefaultEdge edge : graph.incomingEdgesOf(idFromDest)){
				VertexType v = graph.getEdgeSource(edge);
				if(!touchedFromDest.contains(v)){
					if(touchedFromSource.contains(v)){
						nodesGen[0] = touchedFromSource.size() + touchedFromDest.size();
						return pathFromSource.size() + pathFromDest.size() + 1;
					}
						
					idFromDest = v;
					found = true;
					break;
				}
			}
			
			if(found){
				touchedFromDest.add(idFromDest);
				pathFromDest.add(idFromDest);
			}else if(!pathFromDest.isEmpty()){
				idFromDest = pathFromDest.removeLast();
				continue;
			}else{
				nodesGen[0] = touchedFromSource.size() + touchedFromDest.size();
				return -1;
			}
		}
	}

	@Override
	public String name() {
		return "Bidirectional DFS";
	}

}
