import org.jgrapht.graph.AsUnweightedDirectedGraph;
import org.jgrapht.graph.DefaultEdge;


public class GreedyBidirectionalBFS<VertexType> implements GraphDepthSearch<VertexType> {

	@Override
	public int distance(VertexType source, VertexType destination,
			AsUnweightedDirectedGraph<VertexType, DefaultEdge> graph) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String name() {
		return "Greedy Bidirectional BFS";
	}

}
