import java.util.HashSet;
import java.util.LinkedList;

import org.jgrapht.graph.AsUnweightedDirectedGraph;
import org.jgrapht.graph.DefaultEdge;


public class DFS<VertexType> implements GraphDepthSearch<VertexType> {
	
	@Override
	public FoundSearchData distance(VertexType source, VertexType destination,
			AsUnweightedDirectedGraph<VertexType, DefaultEdge> graph) {
		LinkedList<VertexType> path;
		HashSet<VertexType> touched;
		
		
		
		if(source.equals(destination))
			return new FoundSearchData(0, 0);
		
		path = new LinkedList<VertexType>();
		touched = new HashSet<VertexType>();

		VertexType id = source;
		boolean nextFound;
		while( true ){
			
			nextFound = false;
			for(DefaultEdge edge : graph.outgoingEdgesOf(id)){
				VertexType v = graph.getEdgeTarget(edge);
				if(!touched.contains(v)){
					if(v == destination)
						return new FoundSearchData(path.size()+1, touched.size());
						
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
				return new FoundSearchData(-1, touched.size());
			}
		}
	}

	@Override
	public String name() {
		return "DFS";
	}

}
