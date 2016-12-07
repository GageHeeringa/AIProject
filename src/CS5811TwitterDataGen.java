import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Collections;

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

		GraphDepthSearch searches[] = {new DFS<Integer>(), new BFS<Integer>(), new GreedyDFS<Integer>(),
				new GreedyBFS<Integer>(), new BidirectionalDFS<Integer>(), new BidirectionalBFS<Integer>(),
				new GreedyBidirectionalDFS<Integer>(), new GreedyBidirectionalBFS<Integer>()};

		
		ArrayList<LinkedList<Integer>> times = new ArrayList<LinkedList<Integer>>();
		ArrayList<LinkedList<Integer>> nodeGenCounts = new ArrayList<LinkedList<Integer>>();
		
		for(int i = 0; i < searches.length; i++){
			times.add(new LinkedList<Integer>());
			nodeGenCounts.add(new LinkedList<Integer>());
		}
			


		for (int i = 0; i < numberUsers; i++)
			for (int j = 0; j < numberUsers; j++)
				if (i != j) {
					for(int k = 0; k < times.size(); k++) {
						//System.out.print(k);
						Integer[] data = timeTestSearch(i, j, twitterUsers, searches[k]);
						times.get(k).add(data[0]); 
						nodeGenCounts.get(k).add(data[1]);
					}
					//System.out.println();
				}
		
		for(int i = 0; i < times.size(); i++)
			System.out.println(getStatString(searches[i].name(), times.get(i), nodeGenCounts.get(i)));

		System.out.println("");
		System.out.println("");
		System.out.println("The graph used is:");
		System.out.println(twitterUsers.toString());

		System.out.println("");
		System.out.println("");
		for(int i = 0; i < times.size(); i++){
			System.out.println(searches[i].name() + " times: ");
			for(Integer j : times.get(i)){
				System.out.print(Double.valueOf(j/1000000000.0).toString() + "s, ");
			}
			System.out.println("");
			System.out.println("");
		}
	}
	
	static Integer[] timeTestSearch(Integer source, Integer destination, AsUnweightedDirectedGraph<Integer, DefaultEdge> twitterUsers, GraphDepthSearch<Integer> toTest){
		int timeRec = (int) System.nanoTime();
		
		Integer nodesGen[] = new Integer[1]; // Hacky way to pass counter pointer
		nodesGen[0] = new Integer(0);
		
		toTest.distance(source, destination, twitterUsers, nodesGen);  // Run test
		
		// System.out.printf("NodesGen( %s ) = %s\n", toTest.name(), nodesGen[0].toString());
		
		Integer ret[] = new Integer[2];
		ret[0] = Integer.valueOf((int) (System.nanoTime() - timeRec)); // Time
		ret[1] = nodesGen[0]; // Nodes generated
		
		return ret;
	}

	static String getStatString(String name, LinkedList<Integer> times, LinkedList<Integer> nodeGenCounts) {
		String toReturn = "";
		double mean, median, nintyfifth, nintyninth, nodeGenAvg;

		Collections.sort(times);

		mean = 0;
		for (Integer i : times)	mean += i;
		mean /= times.size();
		
		nodeGenAvg = 0; // Nodes generated avg
		for (Integer i : nodeGenCounts)	nodeGenAvg += i; 
		nodeGenAvg /= nodeGenCounts.size();
		

		median = times.get((int) (times.size() * .5));
		nintyfifth = times.get((int) (times.size() * .95));
		nintyninth = times.get((int) (times.size() * .99));
		
		mean /= 1000000000;
		median /= 1000000000;
		nintyfifth /= 1000000000;
		nintyninth /= 1000000000;

		toReturn = name + ":\tMean(" + mean + "s)\tMedian(" + median
				+ "s)\t";
		toReturn += "95th percentile(" + nintyfifth + "s)\t99th percentile("
				+ nintyninth + "s)";
		toReturn += "\n" + name + ":\tNodes_Generated_Mean(" + nodeGenAvg + " nodes)";

		return toReturn;
	}

}

/*----OLD METHOD DESIGN BEFORE OBJECT ORIENTED DESIGN----*/
/* BUILD FOLLOWER LIST METHOD */
/*
 * public int[] buildFollowerList (int id, int numFollowers){
 * 
 * int newFollower = 0; boolean invalidFollow = false; int currentNumFollowers =
 * 0; int [] followers = new int [numFollowers];
 * 
 * for (int i = 0; i < numFollowers; i = i + 1){ do { invalidFollow = false;
 * newFollower = (int)(Math.random() * 317000000); if (newFollower == id){
 * invalidFollow = true; continue; } for (int j = 0; j <= currentNumFollowers; j
 * = j +1){ if (newFollower == followers[j]) { invalidFollow = true; break; } }
 * } while (invalidFollow == true);
 * 
 * followers[i] = newFollower; currentNumFollowers = currentNumFollowers + 1; }
 * 
 * return followers; }
 */
/* CREATE RANDOM ID METHOD */
/*
 * public int createRandomID() {
 * 
 * int id = (int)(Math.random() * 317000000); //last reported Twitter average
 * users in 2016
 * 
 * return id; }
 */
/* CREATE RANDOM NUMBER OF FOLLOWERS METHOD */
/*
 * public int createRandomNumberOfFollowers() {
 * 
 * int numFollowers = (int)(Math.random() * 700); //average followers is 208
 * 
 * return numFollowers; }
 */
/* PRINT USER DATA METHOD */
/*
 * public void printUserData(int id, int[] followers, int numFollowers) {
 * 
 * int lineCounter = 0;
 * 
 * System.out.println("The user's Twitter ID is: " + id);
 * System.out.println("The number of followers for the user is: " +
 * numFollowers); System.out.println("The follower IDs are: "); for (int i = 0;
 * i < numFollowers; i = i + 1){ System.out.print(followers[i] + " ");
 * lineCounter = lineCounter + 1; if (lineCounter == 10){
 * System.out.println(" "); lineCounter = 0; } } }
 */
