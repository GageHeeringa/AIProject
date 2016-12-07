import org.jgrapht.graph.AsUnweightedDirectedGraph;
import org.jgrapht.graph.DefaultEdge;


public interface GraphDepthSearch<VertexType> {
	public FoundSearchData distance(VertexType source, VertexType destination, 
			AsUnweightedDirectedGraph<VertexType, DefaultEdge> graph);
	
	public String name();
}
