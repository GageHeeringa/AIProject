import org.jgrapht.graph.AsUnweightedDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class MultithreadingWrapper<VertexType> implements Runnable {

	Thread aThread;
	VertexType source, destination;
	AsUnweightedDirectedGraph<VertexType, DefaultEdge> graph;
	GraphDepthSearch<VertexType> searchAlgo;
	
	FoundSearchData results;

	public MultithreadingWrapper(VertexType inSource, VertexType inDestination, 
			AsUnweightedDirectedGraph<VertexType, DefaultEdge> inGraph, 
			GraphDepthSearch<VertexType> inSearchAlgo){
		source = inSource;
		destination = inDestination;
		graph = inGraph;
		searchAlgo = inSearchAlgo;
	}
	
	@Override
	public void run() {
		long timeRec = System.nanoTime();

		results = searchAlgo.distance(source, destination, graph);

		results.time = (System.nanoTime() - timeRec) / 1000000000.0;
	}

	public void start() {
		aThread = new Thread(this);
		aThread.start();
	}

	public void join() {
		try {
			aThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
