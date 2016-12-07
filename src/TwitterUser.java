
public class TwitterUser {
	
	int id = 0; 
	int numFollowers = 0;
	int[] followers;
	
	public TwitterUser() {
		
		id = (int)(Math.random() * 317000000); //last reported Twitter average users in 2016
		numFollowers = (int)(Math.random() * 700); //average followers is 208
		
		followers = new int [numFollowers];	
		buildFollowerList();
	}
	
	public TwitterUser(int idGiven) {
		id = idGiven;
		numFollowers = (int)(Math.random() * 700); //average followers is 208
		
		followers = new int [numFollowers];
		buildFollowerList();
	}
	
	public void buildFollowerList (){
		
		int newFollower = 0;
		boolean invalidFollow = false;
		int currentNumFollowers = 0;
		
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
	}
	
	public int getID(){
		return id;
	}
	
	public void setID(int idGiven){
		id = idGiven;
	}
	
	public int getNumFollowers(){
		return numFollowers;
	}
	
	//NEEDS WORK: needs to reset size of follower array when changed or is useless and will break setFollowerList
	public void setNumFollowers(int newNumFollowers){
		numFollowers = newNumFollowers;
	}
	
	public int[] getFollowerList(){
		return followers;
	}
	
	public void setFollowerList(){
		buildFollowerList();
	}
	
	public int getFollower(int index){
		return followers[index];
	}
	
	public void setFollower(int index, int newFollowerID){
		followers[index] = newFollowerID;
	}
	
	public void setFollower(int index){
		int newFollower = 0;
		boolean invalidFollow = false;
		
		do {
			invalidFollow = false;
			newFollower = (int)(Math.random() * 317000000);
			if (newFollower == id){
				invalidFollow = true;
				continue;
			}
			for (int j = 0; j <= numFollowers; j = j +1){
				if (newFollower == followers[j]) {
					invalidFollow = true; 
					break;
				}
			}
		} while (invalidFollow == true); 
		
		followers[index] = newFollower; 	
		}
	
	@Override
	public String toString() {
		int lineCounter = 0;
		
		StringBuilder twitterUserString = new StringBuilder();
		
		twitterUserString.append("The user's Twitter ID is: " + id + "\n");
		twitterUserString.append("The number of followers for the user is: " + numFollowers + "\n");
		twitterUserString.append("The follower IDs are: " + "\n");
		for (int i = 0; i < numFollowers; i = i + 1){
			twitterUserString.append(followers[i] + " ");
			lineCounter = lineCounter + 1;
			if (lineCounter == 10){
				twitterUserString.append("\n");
				lineCounter = 0;
			}
		}	
		
		return twitterUserString.toString();
	}
	
}
