package week5.algorithm.practice;

import java.util.*;
import java.io.*;

public class DijkstraComputing {
//The method which reads the input and
	public static void genLists(File inputFile, Vertex[] inputVertice){
		Scanner inputScan = null;
		try{
			inputScan = new Scanner(new FileReader(inputFile));
			while(inputScan.hasNextLine()){
				String nodesLine = inputScan.nextLine();
				String[] nodes = nodesLine.split("\\s+");
				int nodeNum = Integer.parseInt(nodes[0]);
				if(inputVertice[nodeNum] == null) inputVertice[nodeNum] = new Vertex(nodeNum);
				for(int i = 1; i < nodes.length; i++){/*To read each adjacent nodes from the current node*/
					String[] nodeinfo = nodes[i].split(",");
					int nextNodeNum = Integer.parseInt(nodeinfo[0]);
					int nextWeight = Integer.parseInt(nodeinfo[1]);
					if(inputVertice[nextNodeNum] == null) inputVertice[nextNodeNum] = new Vertex(nextNodeNum);
					Vertex currNode = inputVertice[nodeNum];
					Vertex nextNode = inputVertice[nextNodeNum];
					Edge currEdge = new Edge(currNode, nextNode, nextWeight);
					//Upadte each nodes of the edge
					if(!currNode.containsEdge(currEdge)) currNode.addAdjacent(currEdge);
					if(!nextNode.containsEdge(currEdge)) nextNode.addAdjacent(currEdge);
				}
			}
			/*Check the input vertice
			for(Vertex eachVertex : inputVertice){
				if(eachVertex != null){
					System.out.println("input has vertex :" + eachVertex);
					ArrayList<Edge> adEdges = eachVertex.getAjacent();
					for(int i = 0 ; i < adEdges.size(); i++){
						Edge currEdge = adEdges.get(i);
						System.out.println("vertex has edge :" + currEdge);
					}
				}
			}*/
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(inputScan != null){
				inputScan.close();
			}
		}
	}
	/*
	 *The method builds up the heap which store the smallest vertex within it 
	 */
	public static minHeap buildHeap(Vertex[] inputVertice , Vertex source){
		int heapSize = inputVertice.length;
		minHeap inputHeap = new minHeap(heapSize);
		for(Vertex eachVertex: inputVertice){
			if(eachVertex != null && !eachVertex.equals(source)){
				inputHeap.insertVertex(eachVertex);
			}
		}
		return inputHeap;
	}
	  
	/*
	 *The method would perform Dijkstra Algorithm and returns the shortest path between
	 *the source and other vertice, set 1000000 to indicate infinite
	 */
	public static int[] Dijkstra(Vertex[] inputVertice, minHeap inputHeap, Vertex source, int nodeNumber){
		HashSet<Vertex> visitedSet = new HashSet<Vertex>();
		visitedSet.add(source);
		int[] shortestResults = new int[inputVertice.length];
		shortestResults[source.getVertexNum()] = 0;
		int iterNumber = nodeNumber - 1;
		while(iterNumber > 0){
			System.out.println("------------------------------------------");
			System.out.println("This is the " + iterNumber + " th number.");
			Vertex minVertex = inputHeap.extractMin();
			visitedSet.add(minVertex);
			System.out.println("Vertex: " + minVertex + " is extracted. ");
			int minVertexNumber = minVertex.getVertexNum();
			shortestResults[minVertexNumber] = minVertex.getDistance();
			ArrayList<Edge> minAdjacent = minVertex.getAjacent();
			for(Edge eachEdge : minAdjacent){
				Vertex otherSideVertex = eachEdge.getOtherSide(minVertex);
				if((shortestResults[minVertexNumber] + eachEdge.getWeight()) < otherSideVertex.getDistance() && !visitedSet.contains(otherSideVertex)){
					inputHeap.updateVertex(otherSideVertex, (shortestResults[minVertexNumber] + eachEdge.getWeight()));
					//System.out.println(otherSideVertex + " is updated. And distance is " + (shortestResults[minVertexNumber] + eachEdge.getWeight()));
				}
			}
			inputHeap.printStack();
			iterNumber--;
			//System.out.println("The heap has size " + inputHeap.size());
		}
		
		return shortestResults;
	}
	 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File inputFile = new File("dijkstraData.txt");
		int GraphSizeLimit = 250;//To add entries.
		int nodeNumber = 200;
		Vertex[] inputVertice = new Vertex[GraphSizeLimit];
		/*Build up the graph*/
		System.out.println("-------------------------------------------");
		System.out.println("Start reading files ....");
		genLists(inputFile, inputVertice);
		System.out.println("-------------------------------------------");
		System.out.println("The graph has been built ....");
		int sourceIndex = 1;
		Vertex sourceNode = inputVertice[sourceIndex];
		sourceNode.setDistance(0);
		//Get each vertex on the other hand side of the source vertex
		ArrayList<Edge> initialOut = sourceNode.getAjacent();
		for(Edge eachInit: initialOut){
			Vertex otherSide = eachInit.getOtherSide(sourceNode);
			otherSide.setDistance(eachInit.getWeight());
		}
		minHeap inputHeap = buildHeap(inputVertice, sourceNode);
		inputHeap.printStack();
		System.out.println("-------------------------------------------");
		System.out.println("The vertice heap has been built ....");
		System.out.println("The heap has the size" + inputHeap.size());
		int[] shortestDis = Dijkstra(inputVertice, inputHeap, sourceNode, nodeNumber);
		System.out.println("-------------------------------------------");
		System.out.println("The Dijkstra computing has been finished ....");
		//for(int i = 0; i < shortestDis.length; i++){
			//if(shortestDis[i] != 0) System.out.println("From source to " + i + " th vertx is " + shortestDis[i]);
		//}
		System.out.println("From source to " + 7 + " th vertx is " + shortestDis[7]);
		System.out.println("From source to " + 37 + " th vertx is " + shortestDis[37]);
		System.out.println("From source to " + 59 + " th vertx is " + shortestDis[59]);
		System.out.println("From source to " + 82 + " th vertx is " + shortestDis[82]);
		System.out.println("From source to " + 99 + " th vertx is " + shortestDis[99]);
		System.out.println("From source to " + 115 + " th vertx is " + shortestDis[115]);
		System.out.println("From source to " + 133 + " th vertx is " + shortestDis[133]);
		System.out.println("From source to " + 165 + " th vertx is " + shortestDis[165]);
		System.out.println("From source to " + 188 + " th vertx is " + shortestDis[188]);
		System.out.println("From source to " + 197 + " th vertx is " + shortestDis[197]);
	}

}

//The directed edge class which represent the directed edge with head and tail end
class Edge{
	private Vertex endpoint1;
	private Vertex endpoint2;
	private int weight;//The weight should be non-negative
	
	public Edge(Vertex endpoint1, Vertex endpoint2, int weight){
		this.endpoint1 = endpoint1;
		this.endpoint2 = endpoint2;
		this.weight = weight;
	}
	public Vertex getHead(){
		return endpoint1;
	}
	
	public Vertex getTail(){
		return endpoint2;
	}
	
	public Vertex getOtherSide(Vertex currVertex){
		Vertex otherEnd = currVertex.equals(endpoint1) ? endpoint2: endpoint1;
		return otherEnd;
	}
	
	public int getWeight(){
		return this.weight;
	}
	//@override the equals method and toString() method
	public boolean equals(Object object){
		Edge otherEdge = (Edge) object;
		if((object == null) || !(object instanceof Edge)) return false;
		Boolean equalCondition1 = this.getHead().equals(otherEdge.getHead()) && this.getTail().equals(otherEdge.getTail());
		Boolean equalCondition2 = this.getHead().equals(otherEdge.getTail()) && this.getTail().equals(otherEdge.getHead());
		Boolean equalCondition3 = this.getWeight() == otherEdge.getWeight();
		return (equalCondition1 || equalCondition2) && equalCondition3;
	}

	public String toString(){
		Vertex headpoint = this.getHead();
		Vertex tailpont = this.getTail();
		return tailpont.toString() + " :----------: " + headpoint.toString();
	}
}
//The vertex class which reservegetAjacent the edges which take it as the tail end.
class Vertex{
	private int vertexNum;
	private int distance;
	private ArrayList<Edge> adjacentEdges;
	
	public Vertex(int vertexNum){
		this.vertexNum = vertexNum;
		this.distance = 1000000;
		this.adjacentEdges = new ArrayList<Edge>();
	}
	
	public int getVertexNum(){
		return vertexNum;
	}
	
	public void setDistance(int distance){
		this.distance = distance;
	}
	
	public int getDistance(){
		return distance;
	}
	
	public Boolean containsEdge(Edge inputEdge){
		return this.adjacentEdges.contains(inputEdge);
	}
	
	public void addAdjacent(Edge nextHop){
		adjacentEdges.add(nextHop);
	}
	
	public ArrayList<Edge> getAjacent(){
		return adjacentEdges;
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
//The minimum Heap class that implements the heap data structure and store the cost of the neighboring vertice
class minHeap{
	private Vertex[] heapArray;
	private HashMap<Vertex, Integer> indexMap;
	
	public minHeap(int heapSize){
		heapArray = new Vertex[heapSize];
		indexMap = new HashMap<Vertex, Integer>();
	}
	
	public Vertex extractMin(){
		Vertex minVertex = heapArray[1];
		heapArray[1] = null;
		Vertex bottomVertex = null;
		for(int i =  heapArray.length - 1; i >= 0; i--){
			if(heapArray[i] != null){
				bottomVertex = heapArray[i];
				heapArray[i] = null;
				break;
			}
		}
		/*Initialize the bottom vertex position and update the value in the 
		*index map value associate with the key
		*/
		heapArray[1] = bottomVertex;
		int currIndex = 1;
		indexMap.put(bottomVertex, currIndex);
		//Boolean continueFlag = true;
		/*implement the bubble down section here*/
		int leftChildIndex = 2 * currIndex;
		int rightChildIndex = 2 * currIndex + 1;
		while(leftChildIndex < heapArray.length && leftChildIndex < heapArray.length){
			//System.out.println("Test");
			if(heapArray[leftChildIndex] == null && heapArray[rightChildIndex] == null) {
				//System.out.println("go down case 1");
				break;
			}
			//Swap with the only left child if possible
			if(heapArray[leftChildIndex] != null && heapArray[rightChildIndex] == null){
				//System.out.println("go down case 2");
				if(heapArray[leftChildIndex].getDistance() < heapArray[currIndex].getDistance()){
					swap(leftChildIndex, currIndex, heapArray, indexMap);
					currIndex = leftChildIndex;
				}else break;
			}else if(heapArray[leftChildIndex] == null && heapArray[rightChildIndex] != null){
				//System.out.println("go down case 3");
				if(heapArray[rightChildIndex].getDistance() < heapArray[currIndex].getDistance()){
					swap(rightChildIndex, currIndex, heapArray, indexMap);
					currIndex = leftChildIndex;
				}else break;
			}else{
				//System.out.println("go down case 4");
				int leftChildValue = heapArray[leftChildIndex].getDistance();
				int rightChildValue = heapArray[rightChildIndex].getDistance();
				int currValue = heapArray[currIndex].getDistance();
				if(leftChildValue >= currValue && rightChildValue >= currValue) break;
				else{
					if(leftChildValue > rightChildValue){
						swap(rightChildIndex, currIndex, heapArray, indexMap);
						currIndex = rightChildIndex;
					}else{
						swap(leftChildIndex, currIndex, heapArray, indexMap);
						currIndex = leftChildIndex;
					}
				}
				
			}
			leftChildIndex = 2 * currIndex;
			rightChildIndex = 2 * currIndex + 1;
			
		}
		return minVertex;
	}
	
	public void insertVertex(Vertex insertVertex){
		/*index initialization*/
		int upCurrIndex = 0;
		int upNextIndex = 0;
		
		for(int i = heapArray.length - 1; i >= 0; i--){
			if(heapArray[i] != null){
				upCurrIndex = i + 1;
				upNextIndex = upCurrIndex / 2;
				break;
			}
		}
		
		if(upCurrIndex == 0 && upNextIndex == 0){/*the heap is empty*/
			heapArray[1] = insertVertex;
			indexMap.put(insertVertex, 1);
		}else{
			heapArray[upCurrIndex] = insertVertex;
			indexMap.put(insertVertex, upCurrIndex);
			//System.out.println("-------------------------------------------");
			//System.out.println("The upCurrIndex is " + upCurrIndex);
			//System.out.println("The upNextIndex is " + upNextIndex);
			
			while(heapArray[upCurrIndex].getDistance() < heapArray[upNextIndex].getDistance() && upCurrIndex > 1 && upNextIndex >= 1){
				swap(upCurrIndex, upNextIndex, heapArray, indexMap);
				upCurrIndex = upNextIndex;
				upNextIndex = upNextIndex / 2;
				//System.out.println("-------------------------------------------");
				//System.out.println("The upCurrIndex is " + upCurrIndex);
				//System.out.println("The upNextIndex is " + upNextIndex);
				if(upNextIndex == 0) break;
			}
		}
	}
	/*
	 * The vertex position would be updated only in the case that the distance value would be lower
	*/
	public void updateVertex(Vertex updateVertex, int newDistance){
		int updateIndex = indexMap.get(updateVertex);
		//bubble up the vertex in the case the heap would be updated only in the case that 
		//the updated value is lower than the origianl distance value
		updateVertex.setDistance(newDistance);
		int parentIndex = updateIndex / 2;
		//System.out.println("-------------------------------------------");
		//System.out.println("The updateIndex is " + updateIndex);
		//System.out.println("The parentIndex is " + parentIndex);
		while(heapArray[updateIndex].getDistance() < heapArray[parentIndex].getDistance() && updateIndex > 1 && parentIndex >= 1){
			swap(updateIndex, parentIndex, heapArray, indexMap);
			updateIndex = parentIndex;
			parentIndex = parentIndex / 2;
			//System.out.println("-------------------------------------------");
			//System.out.println("The updateIndex is " + updateIndex);
			//System.out.println("The parentIndex is " + parentIndex);
			if(parentIndex == 0) break;
		}
		
		//insertVertex(updateVertex);
	}
	
	public static void swap(int index1, int index2, Vertex[] heapArray, HashMap<Vertex, Integer> indexMap){
		//Update the HashMap with the vertex and its index in the array
	    indexMap.put(heapArray[index1], index2);
	    indexMap.put(heapArray[index2], index1);
	    //Now make the swap
		Vertex temp = heapArray[index1];
		heapArray[index1] = heapArray[index2];
		heapArray[index2] = temp;
	}
	
	public void printStack(){
		//boolean checkBit = true;
		for(int i = 0; i < heapArray.length; i++){
			if(heapArray[i] != null && heapArray[i].getDistance() != 1000000){
				//if(heapArray[i].getDistance() > heapArray[2*i].getDistance() || heapArray[i].getDistance() > heapArray[2*i + 1].getDistance()) checkBit = false;
				System.out.println("vertex: " + heapArray[i] + " has the distance which is" + heapArray[i].getDistance() + " index is: " + i);
			}
		}
		//if(checkBit) System.out.println("The heap is well built.");
	}
	
	public int size(){
		int sizeNum = 0;
		for(int i = 0; i < heapArray.length; i++){
			if(heapArray[i] != null) sizeNum ++;
		}
		return sizeNum;
	}
}