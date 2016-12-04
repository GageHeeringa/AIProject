import java.util.LinkedList;

import org.jgrapht.graph.AsUnweightedDirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;


public class CS5811TwitterDataGen {

	public static void main(String[] args) {

		System.out.println("Starting");

		//GENERATE DATA////////////////////////////////////////////////


		int numberUsers = 30000000;
		int numberFollowingConstant = (int) Math.log10(numberUsers);

		AsUnweightedDirectedGraph<String, DefaultEdge> twitterUsers;
		twitterUsers = new AsUnweightedDirectedGraph<String, DefaultEdge>
		(new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class));

		for(int i = 0; i < numberUsers; i++){
			twitterUsers.addVertex(Integer.toString(i));
		}

		for(int i = 0; i < numberUsers; i++){
			int numberFollowing = (int) (Math.random()*numberFollowingConstant) + 1;
			for(int j = 0; j < numberFollowing; j++){
				int temp = (int)(Math.random()*numberUsers);
				if(temp != i)
					twitterUsers.addEdge(Integer.toString(i), Integer.toString(temp));
			}
		}


		//TEST IMPLEMENTATION//////////////////////////////////////////

		Greedy<String> gagessearch = new Greedy<String>();
		Prioritized<String> joshssearch = new Prioritized<String>();
		Bidrectional_BFS<String> ankitassearch = new Bidrectional_BFS<String>();
		
		int timeRec;
		LinkedList<Integer> gageTimes, joshTimes, ankitaTimes;
		gageTimes = new LinkedList<Integer>();
		joshTimes = new LinkedList<Integer>();
		ankitaTimes = new LinkedList<Integer>();
		
		
		for(int i = 0; i < numberUsers; i++){
			for(int j = 0; j < numberUsers; j++){
				if(i != j){
					String x, y;
					x = Integer.toString(i);
					y = Integer.toString(j);
					
					timeRec = (int) System.nanoTime();
					int gageDist = gagessearch.degSep(x, y, twitterUsers); // Degrees of separation
					gageTimes.add(Integer.valueOf((int) (System.nanoTime()-timeRec)));

					timeRec = (int) System.nanoTime();
					int joshDist = joshssearch.distance(x, y, twitterUsers); // Degrees of separation
					joshTimes.add(Integer.valueOf((int) (System.nanoTime()-timeRec)));

					timeRec = (int) System.nanoTime();
					int ankitaDist = ankitassearch.FindDegree(x, y, twitterUsers); // Degrees of separation
					ankitaTimes.add(Integer.valueOf((int) (System.nanoTime()-timeRec)));

					String toPrint;
					if(gageDist != joshDist){
						toPrint = "Distance mismatch between Gage(" + Integer.toString(gageDist);
						toPrint = toPrint + ") and Josh(" + Integer.toString(joshDist);
						toPrint = toPrint + ") for inputs " + x + ", " + y;
						System.out.println(toPrint);
					}
					if(gageDist != ankitaDist){
						toPrint = "Distance mismatch between Gage(" + Integer.toString(gageDist);
						toPrint = toPrint + ") and Ankita(" + Integer.toString(ankitaDist);
						toPrint = toPrint + ") for inputs " + x + ", " + y;
						System.out.println(toPrint);
					}
					if(ankitaDist != joshDist){
						toPrint = "Distance mismatch between Ankita(" + Integer.toString(ankitaDist);
						toPrint = toPrint + ") and Josh(" + Integer.toString(joshDist);
						toPrint = toPrint + ") for inputs " + x + ", " + y;
						System.out.println(toPrint);
					}
					
				}
			}
		}
				
		
		System.out.println("Done!");

	}

}


/*----OLD METHOD DESIGN BEFORE OBJECT ORIENTED DESIGN----*/	
/*BUILD FOLLOWER LIST METHOD*/
/*	public int[] buildFollowerList (int id, int numFollowers){

	int newFollower = 0;
	boolean invalidFollow = false;
	int currentNumFollowers = 0;
	int [] followers = new int [numFollowers];

	for (int i = 0; i < numFollowers; i = i + 1){
		do {
			invalidFollow = false;
			newFollower = (int)(Math.random() * 317000000);
			if (newFollower == id){
				invalidFollow = true;
				continue;
			}
			for (int j = 0; j <= currentNumFollowers; j = j +1){
				if (newFollower == followers[j]) {
					invalidFollow = true; 
					break;
				}
			}
		} while (invalidFollow == true); 

		followers[i] = newFollower; 
		currentNumFollowers = currentNumFollowers + 1;
	}

	return followers;
}
 */	
/*CREATE RANDOM ID METHOD*/
/*	public int createRandomID() {

	int id = (int)(Math.random() * 317000000); //last reported Twitter average users in 2016

	return id;
}
 */	
/*CREATE RANDOM NUMBER OF FOLLOWERS METHOD*/ 
/*	public int createRandomNumberOfFollowers() {

	int numFollowers = (int)(Math.random() * 700); //average followers is 208

	return numFollowers;
}
 */	
/*PRINT USER DATA METHOD*/
/*	public void printUserData(int id, int[] followers, int numFollowers) {

	int lineCounter = 0;

	System.out.println("The user's Twitter ID is: " + id);
	System.out.println("The number of followers for the user is: " + numFollowers);
	System.out.println("The follower IDs are: ");
	for (int i = 0; i < numFollowers; i = i + 1){
		System.out.print(followers[i] + " ");
		lineCounter = lineCounter + 1;
		if (lineCounter == 10){
			System.out.println(" ");
			lineCounter = 0;
		}
	}
}
 */
