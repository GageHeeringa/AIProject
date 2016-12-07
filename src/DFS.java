import java.util.HashSet;
import java.util.LinkedList;

import org.jgrapht.graph.AsUnweightedDirectedGraph;
import org.jgrapht.graph.DefaultEdge;


public class DFS<VertexType> implements GraphDepthSearch<VertexType> {
	
	@Override
	public int distance(VertexType source, VertexType destination,
			AsUnweightedDirectedGraph<VertexType, DefaultEdge> graph, Integer nodesGen[]) {
		LinkedList<VertexType> path;
		HashSet<VertexType> touched;
		
		
		
		if(source.equals(destination))
			return 0;
		
		path = new LinkedList<VertexType>();
		touched = new HashSet<VertexType>();

		VertexType id = source;
		boolean nextFound;
		while( true ){
			
			nextFound = false;
			for(DefaultEdge edge : graph.outgoingEdgesOf(id)){
				VertexType v = graph.getEdgeTarget(edge);
				if(!touched.contains(v)){
					if(v == destination){
						nodesGen[0] = touched.size();
						return path.size()+1;
					}
						
					id = v;
					nextFound = true;
					break;
				}
			}
			if(!nextFound)
				id = null;
			
			if(id != null){
				touched.add(id);
				path.add(id);
			}else if(id == null && !path.isEmpty()){
				id = path.removeLast();
			}else{
				nodesGen[0] = touched.size();
				return -1;
			}
		}
	}

	@Override
	public String name() {
		return "DFS";
	}

}
