import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

import org.jgrapht.graph.AsUnweightedDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class GreedyBidirectionalDFS<VertexType> implements
		GraphDepthSearch<VertexType> {

	@Override
	public FoundSearchData distance(VertexType source, VertexType destination,
			AsUnweightedDirectedGraph<VertexType, DefaultEdge> graph) {
		LinkedList<VertexType> pathFromSource;
		HashSet<VertexType> touchedFromSource;
		LinkedList<VertexType> pathFromDest;
		HashSet<VertexType> touchedFromDest;

		if (source.equals(destination))
			return new FoundSearchData(0, 0);

		pathFromSource = new LinkedList<VertexType>();
		touchedFromSource = new HashSet<VertexType>();
		pathFromDest = new LinkedList<VertexType>();
		touchedFromDest = new HashSet<VertexType>();

		// Until (Case) 'y' is adjacent or (Case) greedy heuristic gives node
		// with no following or (Case) path too long
		VertexType idFromSource = source;
		VertexType idFromDest = destination;
		while (true) {

			ArrayList<VertexType> followingFromSource = new ArrayList<VertexType>();
			for (DefaultEdge edge : graph.outgoingEdgesOf(idFromSource)) {
				VertexType v = graph.getEdgeTarget(edge);
				if (!touchedFromSource.contains(v)) {
					if (touchedFromDest.contains(v)) {
						return new FoundSearchData(pathFromSource.size() + pathFromDest.size() + 1, touchedFromSource.size()
								+ touchedFromDest.size());
					}

					followingFromSource.add(v);
					break;
				}
			}

			int max = -1;
			idFromSource = null;

			for (VertexType e : followingFromSource) {
				if (graph.outDegreeOf(e) > max) {
					max = graph.outDegreeOf(e);
					idFromSource = e;
				}
			}

			if (idFromSource != null) {
				touchedFromSource.add(idFromSource);
				pathFromSource.add(idFromSource);
			} else if (!pathFromSource.isEmpty()) {
				idFromSource = pathFromSource.removeLast();
				continue;
			} else {
				return new FoundSearchData(-1, touchedFromSource.size() + touchedFromDest.size());
			}

			// ///////////////////////////////////////////////////////////////////////

			ArrayList<VertexType> followingFromDest = new ArrayList<VertexType>();
			for (DefaultEdge edge : graph.incomingEdgesOf(idFromDest)) {
				VertexType v = graph.getEdgeSource(edge);
				if (!touchedFromDest.contains(v)) {
					if (touchedFromSource.contains(v)) {
						return new FoundSearchData(pathFromSource.size() + pathFromDest.size() + 1, touchedFromSource.size()
								+ touchedFromDest.size());
					}

					idFromDest = v;
					break;
				}
			}

			max = -1;
			idFromDest = null;

			for (VertexType e : followingFromDest) {
				if (graph.outDegreeOf(e) > max) {
					max = graph.outDegreeOf(e);
					idFromDest = e;
				}
			}

			if (idFromDest != null) {
				touchedFromDest.add(idFromDest);
				pathFromDest.add(idFromDest);
			} else if (!pathFromDest.isEmpty()) {
				idFromDest = pathFromDest.removeLast();
				continue;
			} else {
				return new FoundSearchData(-1, touchedFromSource.size() + touchedFromDest.size());
			}

		}
	}

	@Override
	public String name() {
		return "Greedy Bidirectional DFS";
	}

}
