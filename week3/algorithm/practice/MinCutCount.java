package week3.algorithm.practice;

import java.util.*;
import java.io.*;
/*
 * This complements the Kargor's Algorithm, which computes the graph minimum cut
 * @author Chao Zhang
 * @date 25/07/2013
 */
public class MinCutCount {
	public static void genLists(File inputFile, ArrayList<Vertex> inputVertice, ArrayList<Edge> inputEdges){
		Scanner inputScan = null;
		try{
			inputScan = new Scanner(new FileReader(inputFile));
			while(inputScan.hasNextLine()){
				String nodesLine = inputScan.nextLine();
				String[] nodes = nodesLine.split("\\s+");
				int nodeNum = Integer.parseInt(nodes[0]);
				Vertex currVertex = new Vertex(nodeNum);
				for(int i = 1; i < nodes.length; i++){
					Edge currEdge = new Edge(currVertex, new Vertex(Integer.parseInt(nodes[i])));
					currVertex.addEdge(currEdge);
					if(!(inputEdges.contains(currEdge))) inputEdges.add(currEdge);
				}
				inputVertice.add(currVertex);
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(inputScan != null){
				inputScan.close();
			}
		}
	}
	//Copy methods which generate a new copy of the original vertice and edges
	public static ArrayList<Vertex> copyVertice(ArrayList<Vertex> originalVertice){
		ArrayList<Vertex> copyList = new ArrayList<Vertex>();
		for(int i = 0; i < originalVertice.size(); i++){
			Vertex currVertex = originalVertice.get(i);
			copyList.add(new Vertex(currVertex.getVertexNum(), copyEdges(currVertex.getOutgoingEdges())));
		}
		return copyList;
	}
	
	public static ArrayList<Edge> copyEdges(ArrayList<Edge> originalEdges){
		ArrayList<Edge> copyList = new ArrayList<Edge>();
		for(int i = 0; i < originalEdges.size(); i++){
			Edge currEdge = originalEdges.get(i);
			Vertex vertex1 = currEdge.getEndStart();
			Vertex vertex2 = currEdge.getEndEnd();
			copyList.add(new Edge(new Vertex(vertex1.getVertexNum()), new Vertex(vertex2.getVertexNum())));
		}
		return copyList;
	}
	//To get the MinCut of the graph
	public static int getMinCut(ArrayList<Vertex> vertice, ArrayList<Edge> edges){
		int iterBase = vertice.size();
		/***************To print out the vertice and its incident edges*************
		for(int i = 0; i < vertice.size(); i++){
			Vertex currVertex = vertice.get(i);
			//System.out.println("The vertex is " + currVertex.toString() + " . ");
			ArrayList<Edge> getEdges = currVertex.getOutgoingEdges();
			for(int j = 0; j < getEdges.size(); j++){
				Edge testEdge = getEdges.get(j);
				//System.out.println(testEdge.toString());
			}
			
			//System.out.println();
		}*/
		int minCut = Integer.MAX_VALUE;
		Random rand = new Random();
		for(int i = 0; i < iterBase * iterBase; i++){
			System.out.println("this is " + i + " iteration");
			ArrayList<Vertex> verticeClone = copyVertice(vertice);
			ArrayList<Edge> edgesClone = copyEdges(edges);
			//int iter = 0;
			while(verticeClone.size() > 2){
				//iter++;
				//*****************To compare the copy vertice and the original vertice********************
				//System.out.println("This is the " + iter + " round: ");
				//System.out.println("The original vertice is : ");
				for(int k = 0; k < vertice.size(); k++){
					Vertex currVertex = vertice.get(k);
					//System.out.println("The vertex is " + currVertex.toString() + " . ");
					ArrayList<Edge> getEdges = currVertex.getOutgoingEdges();
					for(int j = 0; j < getEdges.size(); j++){
						//Edge testEdge = getEdges.get(j);
						//System.out.println(testEdge.toString());
					}
					
					//System.out.println(" ");
				}
				//System.out.println(" ");
				//System.out.println(" ");
				//System.out.println("The copy vertice is : ");
				for(int k = 0; k < verticeClone.size(); k++){
					Vertex currVertex = verticeClone.get(k);
					//System.out.println("The vertex is " + currVertex.toString() + " . ");
					ArrayList<Edge> getEdges = currVertex.getOutgoingEdges();
					for(int j = 0; j < getEdges.size(); j++){
						//Edge testEdge = getEdges.get(j);
						//System.out.println(testEdge.toString());
					}
					
					//System.out.println();
				}
				//********************************The comparison section ends here*****************************
				int currEdgeSize = edgesClone.size();
				////System.out.println(currEdgeSize);
				int pickIndex = rand.nextInt(currEdgeSize);
				Edge edgeChosen = edgesClone.remove(pickIndex);
				//Delete all the edges connects the vertex connected by the edge.
				while(edgesClone.contains(edgeChosen)){
					edgesClone.remove(edgeChosen);
				}
				//Get the info of the edge
				//System.out.println("Edge: " + edgeChosen.toString() + " is chosen");
				Vertex vertex1 = edgeChosen.getEndStart();
				Vertex vertex2 = edgeChosen.getEndEnd();
				int vertex1Num = vertex1.getVertexNum();
				//System.out.println("vertex 1 num is " + vertex1Num);
				int vertex2Num = vertex2.getVertexNum();
				//System.out.println("vertex 2 num is " + vertex2Num);
				//***********************************The merging part*********************************************
				if(vertex1Num == Math.max(vertex1Num, vertex2Num)){
					int removeIndex = verticeClone.indexOf(vertex1);
					//System.out.println("The index to be removed is " + removeIndex);
					Vertex removeVertex = verticeClone.remove(removeIndex);//To remove the merged vertex in the vertice list
					ArrayList<Edge> vertexEdges = removeVertex.getOutgoingEdges();
					//System.out.println("The size of vertex edge is " + vertexEdges.size());
					//The section to remove the duplicate edge and update edge
					//To get the interferecing edges of the vertex2
					int vertex2Index = verticeClone.indexOf(vertex2);
					//System.out.println("update vertex index is" + vertex2.toString());
					Vertex vertexUpdate = verticeClone.get(vertex2Index);
					//ArrayList<Edge> vertex2Edges =  vertexUpdate.getOutgoingEdges();
					for(Edge eachEdge: vertexEdges){
						if(!eachEdge.equals(edgeChosen)){
							if((eachEdge.getEndStart()).equals(removeVertex)) eachEdge.setEndStart(vertex2Num);
							if((eachEdge.getEndEnd()).equals(removeVertex)) eachEdge.setEndEnd(vertex2Num);
							//if(!vertex2Edges.contains(eachEdge)) vertexUpdate.addEdge(eachEdge);
							vertexUpdate.addEdge(eachEdge);
						}
					}
					/*remove the edge in the updated vertex*/
					vertexUpdate.deleteEdge(edgeChosen);
					//Update the edges list
					for(Edge eachListEdge : edgesClone){
						if((eachListEdge.getEndStart()).equals(removeVertex)) eachListEdge.setEndStart(vertex2Num);
						if((eachListEdge.getEndEnd()).equals(removeVertex)) eachListEdge.setEndEnd(vertex2Num);
					}
					
					//System.out.println("Vertex is chosen: " + vertex1.toString() + " to be merged");
					//System.out.println("Now the updated vertex is " + vertexUpdate.toString() + " .");
					//System.out.println("it has edges:");
					ArrayList<Edge> updateEdges = vertexUpdate.getOutgoingEdges();
					for(int j = 0; j < updateEdges.size() ; j++){
						//System.out.println(updateEdges.get(j));
					}
					//System.out.println(" ");
					//System.out.println(" ");
				}else{
					int removeIndex = verticeClone.indexOf(vertex2);
					//System.out.println("The index to be removed is " + removeIndex);
					Vertex removeVertex = verticeClone.remove(removeIndex);
					ArrayList<Edge> vertexEdges = removeVertex.getOutgoingEdges();
					//System.out.println("The size of vertex edge is " + vertexEdges.size());
					/*
					for(int j = 0; j < vertexEdges.size(); j++){
						Edge modEdge =  vertexEdges.get(j);
						Vertex EndPoint = modEdge.getEndEnd();
						if(EndPoint.equals(vertex1)) vertexEdges.remove(modEdge);
						else modEdge.setEndStart(vertex1Num);
					}
					//System.out.println("The updated edges are: ");
					for(int j = 0; j < vertexEdges.size(); j++){
						//System.out.println(vertexEdges.get(j));
					}*/
					
					int vertex1Index = verticeClone.indexOf(vertex1);
					//System.out.println("update vertex index is" + vertex1.toString());
					Vertex vertexUpdate = verticeClone.get(vertex1Index);
					//ArrayList<Edge> vertex1Edges =  vertexUpdate.getOutgoingEdges();
					for(Edge eachEdge: vertexEdges){
						if(!eachEdge.equals(edgeChosen)){
							if((eachEdge.getEndStart()).equals(removeVertex)) eachEdge.setEndStart(vertex1Num);
							if((eachEdge.getEndEnd()).equals(removeVertex)) eachEdge.setEndEnd(vertex1Num);
							//if(!vertex1Edges.contains(eachEdge)) vertexUpdate.addEdge(eachEdge);
							vertexUpdate.addEdge(eachEdge);
						}
					}
					/*remove the edge in the updated vertex*/
					vertexUpdate.deleteEdge(edgeChosen);
					//Update the edges list
					for(Edge eachListEdge : edgesClone){
						if((eachListEdge.getEndStart()).equals(removeVertex)) eachListEdge.setEndStart(vertex1Num);
						if((eachListEdge.getEndEnd()).equals(removeVertex)) eachListEdge.setEndEnd(vertex1Num);
					}
					/*
					int vertex1Index = verticeClone.indexOf(vertex1);
					//System.out.println(vertex1Index);
					Vertex vertexInVertice = verticeClone.get(vertex1Index);
					vertexInVertice.addEdges(vertexEdges);
					*/
					//System.out.println("Vertex is chosen: " + vertex2.toString() + " to be merged");
					//System.out.println("Now the updated vertex is " + vertexUpdate.toString() + " .");
					//System.out.println("it has edges:");
					ArrayList<Edge> updateEdges = vertexUpdate.getOutgoingEdges();
					for(int j = 0; j < updateEdges.size() ; j++){
						//System.out.println(updateEdges.get(j));
					}
					//System.out.println(" ");
					//System.out.println(" ");
				}
				//**************************************The merging part ends here***********************************
			}
			minCut =  Math.min(edgesClone.size(), minCut);
		}
		return minCut;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File inputFile = new File("kargerMinCut.txt");
		//ArrayList<Vertex> inputVertice = new ArrayList<Vertex>();
		//ArrayList<Edge> inputEdges = new ArrayList<Edge>();
		//genLists(inputFile, inputVertice, inputEdges);
		//int minCut = getMinCut(inputVertice, inputEdges);
		////System.out.print(minCut);
		//File testFile = new File("test.txt");
		ArrayList<Vertex> inputVertice = new ArrayList<Vertex>();
		ArrayList<Edge> inputEdges = new ArrayList<Edge>();
		genLists(inputFile, inputVertice, inputEdges);
		int minCut = getMinCut(inputVertice, inputEdges);
		System.out.print(minCut);
	}

}

class Edge{
	private Vertex endpoint1;
	private Vertex endpoint2;
	
	public Edge(Vertex endpoint1, Vertex endpoint2){
		this.endpoint1 = endpoint1;
		this.endpoint2 = endpoint2;
	}
	
	public Vertex getEndStart(){
		return endpoint1;
	}
	
	public Vertex getEndEnd(){
		return endpoint2;
	}
	
	public void setEndStart(int endNum){
		endpoint1.setVerteNum(endNum);
	}
	
	public void setEndEnd(int endNum){
		endpoint2.setVerteNum(endNum);
	}
	
	public boolean equals(Object object){
		Edge otherEdge = (Edge) object;
		if((object == null) || !(object instanceof Edge)) return false;
		boolean equalCase1 = (this.getEndStart()).equals(otherEdge.getEndStart()) && (this.getEndEnd()).equals(otherEdge.getEndEnd());
		boolean equalCase2 = (this.getEndStart()).equals(otherEdge.getEndEnd()) && (this.getEndEnd()).equals(otherEdge.getEndStart());
		return equalCase1 || equalCase2;
	}
	
	public String toString(){
		Vertex startEnd = this.getEndStart();
		Vertex endEnd = this.getEndEnd();
		return startEnd.toString() + " :----------: " + endEnd.toString();
	}
}

class Vertex{
	private int vertexNum;
	private ArrayList<Edge> incidentEdges;
	
	public Vertex(int vertexNum){
		this.vertexNum = vertexNum;
		this.incidentEdges = new ArrayList<Edge>();
	}
	
	public Vertex(int vertexNum, ArrayList<Edge> outGoingEdges){
		this.vertexNum = vertexNum;
		this.incidentEdges = outGoingEdges;
	}
	
	public void setVerteNum(int num){
		this.vertexNum = num;
	}
	
	public int getVertexNum(){
		return vertexNum;
	}
	
	public ArrayList<Edge> getOutgoingEdges(){
		return incidentEdges;
	}
	
	public void addEdge(Edge newEdge){
		incidentEdges.add(newEdge);
	}
	
	public void addEdges(ArrayList<Edge> newEdgs){
		incidentEdges.addAll(newEdgs);
	}
	
	public void deleteEdge(Edge deletedEdge){
		incidentEdges.remove(deletedEdge);
	}
	//@override equals method
	public boolean equals(Object object){
		Vertex otherVertex = (Vertex) object;
		if((object == null) || !(object instanceof Vertex)) return false;
		return this.vertexNum == otherVertex.getVertexNum();
	}
	
	public String toString(){
		return "Vertex: " + this.getVertexNum();
	}
}