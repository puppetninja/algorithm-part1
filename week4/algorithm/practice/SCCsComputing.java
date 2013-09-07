package week4.algorithm.practice;

import java.text.NumberFormat;
import java.util.*;
import java.io.*;
/*
 * This complements the Kosaruju's Algorithm, which computes the graph minimum cut
 * @author Chao Zhang
 * @date 02/08/2013
 */

public class SCCsComputing {
	//The method which reads the input and
	public static void genLists(File inputFile, Vertex[] inputVertice, ArrayList<Edge> inputEdges){
		Scanner inputScan = null;
		try{
			inputScan = new Scanner(new FileReader(inputFile));
			StringBuilder readBuilder = new StringBuilder();
			while(inputScan.hasNextLine()){
				readBuilder.append(inputScan.nextLine());
				readBuilder.append(",");
			}	
			String inputString = readBuilder.toString();
			String[] inputSplit = inputString.split(",");
			//System.out.println(inputSplit.length + "lines have been read");
			
			for(int i = 0; i < inputSplit.length; i++){
				String currLine = inputSplit[i];
				String[] nodes = currLine.split("\\s+");
				
				int nodeNum = Integer.parseInt(nodes[0]);
				int nextHopNum = Integer.parseInt(nodes[1]);
				Vertex currVertex = null;
				Vertex nextVertex = null;
				//Check the current vertex as tail end of the edge
				if(inputVertice[nodeNum] != null) currVertex = inputVertice[nodeNum];
				else{
					currVertex = new Vertex(nodeNum);
					inputVertice[nodeNum] = currVertex;
				}
				//Check the next hop vertex as the head of the edge
				if(inputVertice[nextHopNum] != null) nextVertex = inputVertice[nextHopNum];
				else{
					nextVertex = new Vertex(nextHopNum);
					inputVertice[nextHopNum] = nextVertex;
				}
				//Update the both vertice of the edges 
				Edge currEdge = new Edge(currVertex, nextVertex);
				currVertex.addNextHop(currEdge);
				nextVertex.addTerminateHop(currEdge);
				inputEdges.add(currEdge);
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(inputScan != null){
				inputScan.close();
			}
		}
	}
	//The method reverse the whole graph
	public static void reverseGraph(Vertex[] currVertice, ArrayList<Edge> currEdges){
		for(int i = 0; i < currEdges.size(); i++)
			currEdges.get(i).edgeReverse();
		for(int i = 0; i < currVertice.length; i++)
			if(currVertice[i] != null) currVertice[i].vertexReverse();
	}
	//The method which performs the DFS-loop in reverse graph
	public static void DFSLoopReverse(Vertex[] currVertice, int globalFinish, Vertex[] finishTime){
		for(int i = currVertice.length - 1; i >= 0 ; i--){
			if(currVertice[i] != null && currVertice[i].getVisit() == false){
				Vertex source = currVertice[i];
				globalFinish = DFSReverse(currVertice, source, globalFinish, finishTime);
			}
		}
		//sort the finishing time in decrease order
		//for(int i = finishTime.length - 1; i >= 0 ; i--)
			//if(finishTime[i] != null) System.out.println(i + " th finishing vertex is : " + finishTime[i]);
		
	}
	//The DFS sub routine which will return the glabalFinish time 
	public static int DFSReverse(Vertex[] currVertice, Vertex source, int globalFinish, Vertex[] finishTime){
		source.setVisit();//mark source as visited
		ArrayList<Edge> nextHops = source.getNextHops();
		for(Edge nextHop: nextHops)
			if(!nextHop.getHead().getVisit()) globalFinish = DFSReverse(currVertice, nextHop.getHead(), globalFinish, finishTime);
		globalFinish++;
		finishTime[globalFinish] = source;
		return globalFinish;
	}
	//The method which performs the DFS-loop in the original graph
	public static void DFSLoop(Vertex[] currVertice, Vertex[] finishTime, HashMap<Vertex, Integer> leaders){
		for(int i = finishTime.length - 1; i >= 0; i--){
			//if(finishTime[i] != null) System.out.println("Vertex " + finishTime[i] + "is visited at " + i + " th time") ;
			if(finishTime[i] != null && finishTime[i].getVisit() == false){
				Vertex source = finishTime[i];
				source.setVisit();
				leaders.put(source, 1);
				//System.out.println(source + " has leader " + source);
				DFS(currVertice, source, source, finishTime, leaders);
			}
		}
	}
	//The DFS sub routine which will set all the leaders of each SCCs;
	public static void DFS(Vertex[] currVertice, Vertex source, Vertex currVertex,Vertex[] finishTime, HashMap<Vertex, Integer> leaders){
		ArrayList<Edge> nextHops = currVertex.getNextHops();
		for(Edge nextHop: nextHops){
			Vertex nextVertex = nextHop.getHead();
			if(!nextVertex.getVisit()){
				nextVertex.setVisit();
				int memberVert = leaders.get(source) + 1;
				leaders.put(source, memberVert);
				//System.out.println(nextVertex + " has leader " + source);
				DFS(currVertice, source, nextVertex, finishTime, leaders);
			}
		}
	}
	//Calculate the top 5 leaders
	public static void calStat(HashMap<Vertex, Integer> leaders){
		int[] memberNumbers = new int[leaders.size()];
		Iterator iter = leaders.entrySet().iterator();
		int iterIndex = 0;
	    while (iter.hasNext()) {
	    	Map.Entry pairs = (Map.Entry)iter.next();
	    	int SCCnumber = (int) pairs.getValue();
	    	memberNumbers[iterIndex] = SCCnumber;
	    	iterIndex++;
	    }
		Arrays.sort(memberNumbers);
		
		for(int i  = 0 ; i < memberNumbers.length; i++){
			 System.out.println("The " + i + " th SCC size is " + memberNumbers[i]);
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File inputFile = new File("SCC.txt");
		int GraphSizeLimit = 900000;
		Vertex[] inputVertice = new Vertex[GraphSizeLimit];
		ArrayList<Edge> inputEdges = new ArrayList<Edge>();
		/*Build up the graph*/
		System.out.println("-------------------------------------------");
		System.out.println("Start reading files ....");
		genLists(inputFile, inputVertice, inputEdges);
		System.out.println("-------------------------------------------");
		System.out.println("The graph has been built ....");
		/*reverse the whole graph and perform the DFS loop*/
		int globalFinish = 0;
		Vertex[] finishTime = new Vertex[900000];/*Used in the first DFS to keep track of the finishing time*/
		reverseGraph(inputVertice, inputEdges);
		DFSLoopReverse(inputVertice, globalFinish, finishTime);
		System.out.println("-------------------------------------------");
		System.out.println("The graph reversion has been performed ....");
		/*Perform the DFS Loop in the original graph*/
		/*reset the whole graph*/
		reverseGraph(inputVertice, inputEdges);
		for(Vertex vertex: inputVertice)
			if(vertex != null) vertex.getReset();
		//int[] leaders = new int[900000];/*Used in the second DFS to keep track of the leaders*/
		HashMap<Vertex, Integer> leaderMaps = new HashMap<Vertex, Integer>();
		DFSLoop(inputVertice, finishTime, leaderMaps);
		System.out.println(" ");
		System.out.println("------------------------------------------------------------------");
		calStat(leaderMaps);
		Runtime runtime = Runtime.getRuntime();
		NumberFormat format = NumberFormat.getInstance();
		long allocatedMemory = runtime.totalMemory();
		System.out.println("allocated memory: " + format.format(allocatedMemory / 1024));
	}

}
//The directed edge class which represent the directed edge with head and tail end
class Edge{
	private Vertex headpoint;
	private Vertex tailpoint;
	
	public Edge(Vertex tailpoint, Vertex headpoint){
		this.tailpoint = tailpoint;
		this.headpoint = headpoint;
	}
	
	public Vertex getHead(){
		return headpoint;
	}
	
	public Vertex getTail(){
		return tailpoint;
	}
	
	public void setHead(int setNum){
		headpoint.setVerteNum(setNum);
	}
	
	public void setTail(int setNum){
		tailpoint.setVerteNum(setNum);
	}
	
	public void edgeReverse(){
		Vertex temp = headpoint;
		headpoint = tailpoint;
		tailpoint = temp;
	}
	
	//@override the equals method and toString() method
	public boolean equals(Object object){
		Edge otherEdge = (Edge) object;
		if((object == null) || !(object instanceof Edge)) return false;
		return this.getHead().equals(otherEdge.getHead()) && this.getTail().equals(otherEdge.getTail());
	}

	public String toString(){
		Vertex headpoint = this.getHead();
		Vertex tailpont = this.getTail();
		return tailpont.toString() + " :---------->: " + headpoint.toString();
	}
}
//The vertex class which reserve the edges which take it as the tail end.
class Vertex{
	private int vertexNum;
	private Boolean visited;
	private ArrayList<Edge> nextHops;
	private ArrayList<Edge> terminateHops;
	
	public Vertex(int vertexNum){
		this.vertexNum = vertexNum;
		this.visited = false;
		this.nextHops = new ArrayList<Edge>();
		this.terminateHops = new ArrayList<Edge>();
	}
	
	public void setVerteNum(int num){
		this.vertexNum = num;
	}
	
	public int getVertexNum(){
		return vertexNum;
	}
	
	public void setVisit(){
		this.visited = true;
	}
	
	public Boolean getVisit(){
		return visited;
	}

	public void getReset(){
		this.visited = false;
	}
	
	
	public void addNextHop(Edge nextHop){
		nextHops.add(nextHop);
	}
	
	public void addTerminateHop(Edge terminateHop){
		terminateHops.add(terminateHop);
	}
	
	public ArrayList<Edge> getNextHops(){
		return nextHops;
	}
	
	public void vertexReverse(){
		//reverse the nextHop and terminatedHop edges
		ArrayList<Edge> temp = nextHops;
		nextHops = terminateHops;
		terminateHops = temp;
	}
	//@override equals() method and toString() method
	public boolean equals(Object object){
		Vertex otherVertex = (Vertex) object;
		if((object == null) || !(object instanceof Vertex)) return false;
		return this.vertexNum == otherVertex.getVertexNum();
	}
	
	public String toString(){
		return "Vertex: " + this.getVertexNum();
	}
}