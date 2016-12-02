/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Iterator;
import org.jgrapht.graph.AsUnweightedDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class Bidrectional_BFS<VertexType> {
	int Degree_of_separation=0;
                    int dest_found=0;        
                    String Source_filename;
                    ArrayList<VertexType>source_list=new ArrayList<VertexType>();
                    ArrayList<VertexType>dest_list=new ArrayList<VertexType>();
                    int fromSource=1;
                    VertexType Source;
                    VertexType Destination;
    
public void FindDegree(VertexType x, VertexType y, AsUnweightedDirectedGraph<VertexType, DefaultEdge> g)
{
	Destination=x;
    Source=y;
    
	source_list.add(Source);
    dest_list.add(Destination);
    if( g.containsVertex(x) && g.containsVertex(y) )
    {
                    while(dest_found!=1)
                    {
                            finduser(g);
                            if(Degree_of_separation>=325)
                                break;
                        
                    }
                    if(dest_found==0)
                    {
                        System.out.println("Destination Not Found");
                        System.out.println("path lenght exceeds the limit");
                        
                    }
                    else
                        System.out.println("Degree of separation: "+Degree_of_separation);

    }
    else
    {
    System.out.println("Either of source or Destination is not in graph");	
    }
                        
                            
                
}

public void finduser(AsUnweightedDirectedGraph<VertexType, DefaultEdge> g)
{
    if(fromSource==1)
    {
        Source=source_list.get(source_list.size()-1);
        Destination=dest_list.get(dest_list.size()-1);
    }
    else
    {
        Source=dest_list.get(dest_list.size()-1);
        Destination=source_list.get(source_list.size()-1);
    }   
    
    System.out.println(Source);
    System.out.println(Destination);
    DefaultEdge[] followingEdges = new DefaultEdge[g.outDegreeOf(Source)];
	g.outgoingEdgesOf(Source).toArray(followingEdges);
	Set<VertexType> following = new HashSet<VertexType>();
	for(int i = 0; i < followingEdges.length; i++)
		following.add(g.getEdgeTarget(followingEdges[i]));

	if( following == null || following.size() == 0){
		System.out.printf("--- Case: greedy heuristic gives node with no following\n");
	}

	for(VertexType element : following)
    {
       
        if(fromSource==1)
        {
        if(dest_list.contains(element))
        {
            System.out.println("Destination Found");
            Degree_of_separation++;
            dest_found=1;
            break;
        }
        }
        else
        {
          if(source_list.contains(element))
        {
            System.out.println("Destination Found");
            Degree_of_separation++;
            dest_found=1;
            break;
        }  
        }
    }
                     
                        if(dest_found==0)
                        {
                                
                         List <Integer> following_no= new ArrayList<Integer>();
                         for(VertexType e : following)
                        {   
                        	 if( g.containsVertex(e) )
                        		 {
                        		 	DefaultEdge[] subfollowingEdges = new DefaultEdge[g.outDegreeOf(e)];
                        			g.outgoingEdgesOf(e).toArray(subfollowingEdges);
                        			Set<VertexType> subfollowing = new HashSet<VertexType>();
                        			following_no.add(subfollowing.size());
                        		 }
                        }
                         int max= following_no.get(0);
                         int max_index=0;
                         for(int i=1; i<following_no.size();i++)
                         {
                             if(following_no.get(i)>max)
                             {
                                 max=following_no.get(i);
                                 max_index=i;
                             }
                         }
                    	 Iterator itr = following.iterator();
                    	 int i=0;
                    	 while(itr.hasNext())
                         {
                             if(i==max_index)
                            	 break;
                    		 i++;
                         }

                         if(fromSource==1)
                         {
                        	 
                         source_list.add((VertexType)itr.next());
                         fromSource=0;
                             
                         }
                         else
                         {
                        	 dest_list.add((VertexType)itr.next());
                        	 fromSource=1;
                         
                         }   
                         Degree_of_separation++;
                          }
}
}
