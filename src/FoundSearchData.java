
public class FoundSearchData {
	int degreeOfSeparation;
	int numberOfExploredNodes;
	double time;
	
	public FoundSearchData(int inDegreeOfSeparation, int inNExploredNodes, double inTime){
		degreeOfSeparation = inDegreeOfSeparation;
		numberOfExploredNodes = inNExploredNodes;
		time = inTime;
	}
	
	public FoundSearchData(int inDegreeOfSeparation, int inNExploredNodes){
		degreeOfSeparation = inDegreeOfSeparation;
		numberOfExploredNodes = inNExploredNodes;
	}
}
