import java.util.LinkedList;
import java.util.Collections;

import org.jgrapht.graph.AsUnweightedDirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class CS5811TwitterDataGen {

	public static void main(String[] args) {

		System.out.println("Starting");

		// GENERATE DATA////////////////////////////////////////////////

		int numberUsers = 1000;
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

		Greedy<Integer> gagessearch = new Greedy<Integer>();
		Prioritized<Integer> joshssearch = new Prioritized<Integer>();

		int timeRec;
		LinkedList<Integer> gageTimes, joshTimes;
		gageTimes = new LinkedList<Integer>();
		joshTimes = new LinkedList<Integer>();

		for (int i = 0; i < numberUsers; i++) {
			for (int j = 0; j < numberUsers; j++) {
				if (i != j) {

					// System.out.print(">");
					timeRec = (int) System.nanoTime();
					int gageDist = gagessearch.degSep(i, j, twitterUsers); // Degrees
																			// of
																			// separation
					gageTimes
							.add(Integer.valueOf((int) (System.nanoTime() - timeRec)));

					// System.out.print("1");
					timeRec = (int) System.nanoTime();
					int joshDist = joshssearch.distance(i, j, twitterUsers); // Degrees
																				// of
																				// separation
					joshTimes
							.add(Integer.valueOf((int) (System.nanoTime() - timeRec)));

					// System.out.println("3");
					boolean debug = true;
					if (debug) {
						String toPrint;
						if (gageDist < joshDist) {
							toPrint = "Distance mismatch between Gage("
									+ Integer.toString(gageDist);
							toPrint = toPrint + ") and Josh("
									+ Integer.toString(joshDist);
							toPrint = toPrint + ") for inputs " + i + ", " + j;
							System.out.println(toPrint);
						}
					}
				}
			}
		}

		System.out.println(getStatString("Gage", gageTimes));
		System.out.println(getStatString("Josh", joshTimes));
		//System.out.println(getStatString("Ankita", ankitaTimes));

		System.out.println("Done!");

	}

	static String getStatString(String name, LinkedList<Integer> times) {
		String toReturn = "";
		double mean, median, nintyfifth, nintyninth;

		Collections.sort(times);

		mean = 0;
		for (Integer i : times)	mean += i;
		mean /= times.size();

		median = times.get((int) (times.size() * .5));
		nintyfifth = times.get((int) (times.size() * .95));
		nintyninth = times.get((int) (times.size() * .99));
		
		mean /= 1000000000;
		median /= 1000000000;
		nintyfifth /= 1000000000;
		nintyninth /= 1000000000;

		toReturn = name + " sort:\tMean(" + mean + "s)\tMedian(" + median
				+ "s)\t";
		toReturn += "95th percentile(" + nintyfifth + "s)\t99th percentile("
				+ nintyninth + "s)";

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
