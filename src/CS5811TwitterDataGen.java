import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.graph.AsUnweightedDirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class CS5811TwitterDataGen {

	public static void main(String[] args) {

		// GENERATE DATA////////////////////////////////////////////////

		//int numberUsers = 1000;
		int numberUsers = 15;
		int numberFollowingConstant = (int) Math.sqrt(numberUsers);

		AsUnweightedDirectedGraph<Integer, DefaultEdge> twitterUsers;
		twitterUsers = new AsUnweightedDirectedGraph<Integer, DefaultEdge>(
				new DefaultDirectedGraph<Integer, DefaultEdge>(DefaultEdge.class));

		for (int i = 0; i < numberUsers; i++) {
			twitterUsers.addVertex(Integer.valueOf(i));
		}

		for (int i = 0; i < numberUsers; i++) {
			int numberFollowing = (int) (Math.random() * numberFollowingConstant) + 1;
			for (int j = 0; j < numberFollowing; j++) {
				int temp = (int) (Math.random() * numberUsers);
				if (temp != i)
					twitterUsers.addEdge(Integer.valueOf(i),
							Integer.valueOf(temp));
			}
		}

		// TEST IMPLEMENTATION//////////////////////////////////////////

		ArrayList<GraphDepthSearch<Integer>> searches;
		searches = new ArrayList<GraphDepthSearch<Integer>>();
		searches.add(new DFS<Integer>());
		searches.add(new BFS<Integer>());
		searches.add(new GreedyDFS<Integer>());
		searches.add(new GreedyBFS<Integer>());
		searches.add(new BidirectionalDFS<Integer>());
		searches.add(new BidirectionalBFS<Integer>());
		searches.add(new GreedyBidirectionalDFS<Integer>());
		searches.add(new GreedyBidirectionalBFS<Integer>());

		
		ArrayList<LinkedList<FoundSearchData>> results = new ArrayList<LinkedList<FoundSearchData>>();
		
		for(int i = 0; i < searches.size(); i++)
			results.add(new LinkedList<FoundSearchData>());


		for (int i = 0; i < numberUsers; i++)
			for (int j = 0; j < numberUsers; j++)
				if (i != j) 
					for(GraphDepthSearch<Integer> algo : searches) 
						results.get(searches.indexOf(algo)).add(timeTestSearch(i, j, twitterUsers, algo));
					
		
		for(int i = 0; i < results.size(); i++)
			System.out.println(getStatString(searches.get(i).name(), results.get(i)));

		System.out.println("");
		System.out.println("");
		System.out.println("The graph used is:");
		System.out.println(twitterUsers.toString());

		System.out.println("");
		System.out.println("");
		for(GraphDepthSearch<Integer> algo : searches){
			System.out.println(algo.name() + " times(s): ");
			for(FoundSearchData j : results.get(searches.indexOf(algo)))
				System.out.print(Double.valueOf(j.time).toString() + ", ");
			System.out.println("");
			
			System.out.println(algo.name() + " distances: ");
			for(FoundSearchData j : results.get(searches.indexOf(algo)))
				System.out.print(Double.valueOf(j.degreeOfSeparation).toString() + ", ");
			System.out.println("");

			System.out.println(algo.name() + " Number of Explored Nodes: ");
			for(FoundSearchData j : results.get(searches.indexOf(algo)))
				System.out.print(Double.valueOf(j.numberOfExploredNodes).toString() + ", ");
			
			System.out.println("");
			System.out.println("");
		}
	}
	
	static FoundSearchData timeTestSearch(Integer source, Integer destination, AsUnweightedDirectedGraph<Integer, DefaultEdge> twitterUsers, GraphDepthSearch<Integer> toTest){
		long timeRec = System.nanoTime();
		
		FoundSearchData results = toTest.distance(source, destination, twitterUsers);
		
		results.time = (System.nanoTime() - timeRec)/1000000000.0;
		
		return results;
	}

	static String getStatString(String name, LinkedList<FoundSearchData> runRecords) {
		String toReturn = "";
		LinkedList<Double> times = new LinkedList<Double>();
		LinkedList<Integer> dist = new LinkedList<Integer>();
		LinkedList<Integer> nExploredNodes = new LinkedList<Integer>();
		

		LinkedList<Double> timesOfNotFound = new LinkedList<Double>();
		LinkedList<Integer> nExploredNodesOfNotFound = new LinkedList<Integer>();
		
		for(FoundSearchData i : runRecords){
			if(i.degreeOfSeparation != -1){
				times.add(i.time);
				dist.add(i.degreeOfSeparation);
				nExploredNodes.add(i.numberOfExploredNodes);
			}else{
				timesOfNotFound.add(i.time);
				nExploredNodesOfNotFound.add(i.numberOfExploredNodes);
			}
		}

		toReturn = statBreakDownDouble(name, "times", times) + "\n";
		toReturn += statBreakDownInteger(name, "Distances", dist) + "\n";
		toReturn += statBreakDownInteger(name, "Number of Explored Nodes", nExploredNodes) + "\n";
		toReturn += statBreakDownDouble(name, "times of not found", times) + "\n";
		toReturn += statBreakDownInteger(name, "Number of Explored Nodes of not found", nExploredNodes);
		
		

		return toReturn;
	}
	
	static String statBreakDownDouble(String name, String dataName, List<Double> rawData){
		double mean, median, nintyFifth, nintyNinth;
		String toReturn;
		
		Collections.sort(rawData);

		mean = 0;
		for (Double i : rawData)	mean += i;
		mean /= rawData.size();

		median = rawData.get((int) (rawData.size() * .5));
		nintyFifth = rawData.get((int) (rawData.size() * .95));
		nintyNinth = rawData.get((int) (rawData.size() * .99));

		toReturn = name + "'s " + dataName + ":\tMean(" + mean + ")\tMedian("; 
		toReturn += median + ")\t" + "95th percentile(" + nintyFifth;
		toReturn += ")\t99th percentile(" + nintyNinth + ")";
		
		return toReturn;
	}
	
	static String statBreakDownInteger(String name, String dataName, List<Integer> rawData){
		double mean, median, nintyFifth, nintyNinth;
		String toReturn;
		
		Collections.sort(rawData);

		mean = 0;
		for (Integer i : rawData)	mean += i;
		mean /= rawData.size();

		median = rawData.get((int) (rawData.size() * .5));
		nintyFifth = rawData.get((int) (rawData.size() * .95));
		nintyNinth = rawData.get((int) (rawData.size() * .99));

		toReturn = name + "'s " + dataName + ":\tMean(" + mean + ")\tMedian("; 
		toReturn += median + ")\t" + "95th percentile(" + nintyFifth;
		toReturn += ")\t99th percentile(" + nintyNinth + ")";
		
		return toReturn;
	}

}
