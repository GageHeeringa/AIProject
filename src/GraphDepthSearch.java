import org.jgrapht.graph.AsUnweightedDirectedGraph;
import org.jgrapht.graph.DefaultEdge;


public interface GraphDepthSearch<VertexType> {
	public int distance(VertexType source, VertexType destination, 
			AsUnweightedDirectedGraph<VertexType, DefaultEdge> graph, Integer nodesGen[]);
	
	public String name();
}
