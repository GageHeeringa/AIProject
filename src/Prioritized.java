import org.jgrapht.graph.AsUnweightedDirectedGraph;
import org.jgrapht.graph.DefaultEdge;


public class Prioritized<VertexType> {
	
	int distance(VertexType startUser, VertexType endUser, 
				AsUnweightedDirectedGraph<VertexType, DefaultEdge> userDB){
		int dist = 0;
		/*
		  numSteps = 0;
		  if ( startUser == endUser ):
		    return numSteps
		  
		  explored = dict
		  startUserQueue = Queue()
		  endUserQueue = Queue()
		  
		  for vert in userDB.keys():
		    explored[vert] = 0
		  
		  startUserQueue.put(Queue(startUser))
		  endUserQueue.put(Queue(endUser))
		  
		  while True:
		    numSteps += 1
		    expandQueue = startUserQueue.get()
		    nextExpandQueue = Queue()
		    for expand in expandQueue:
		      for vert in userDB[expand]:
		        if explored[vert] != 1 :
		          if explored[vert] == 2 :
		            return numSteps
		          else :
		            explored[vert] = 1
		            nextExpandQueue.put(vert)
		    startUserQueue.put(nextExpandQueue)
		    
		    numSteps += 1
		    expandQueue = endUserQueue.get()
		    nextExpandQueue = Queue()
		    for expand in expandQueue:
		      for vert in userDB[expand]:
		        if explored[vert] != 2 :
		          if explored[vert] == 1 :
		            return numSteps
		          else :
		            explored[vert] = 2
		            nextExpandQueue.put(vert)
		    endUserQueue.put(nextExpandQueue)
			 */
		
		return dist;
	}
	
}
