package week6.algorithm.practice;

import java.util.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class MedianMaintenance {
	public static void genInput(File inputFile, int[] inputArray){
		Scanner inputScan = null;
		int readIndex = 0;
		try{
			inputScan = new Scanner(new FileReader(inputFile));
			while(inputScan.hasNextLine()){
				String nodesLine = inputScan.nextLine();
				int currNum = Integer.parseInt(nodesLine.trim());
				inputArray[readIndex] = currNum;
				readIndex++;
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(inputScan != null){
				inputScan.close();
			}
		}
	}
	
	public static int[] genMedians(int[] inputArray){
		int[] medians = new int[inputArray.length];
		Comparator<Integer> maxComp = new maxComparator();
		Comparator<Integer> minComp = new minComparator();
		PriorityQueue<Integer> maxPQ = new PriorityQueue<Integer>(inputArray.length, maxComp);
		PriorityQueue<Integer> minPQ = new PriorityQueue<Integer>(inputArray.length, minComp);
		//Dual Heap initialization, assume the input array at least has size 2
		int firstInt = inputArray[0];
		int secInt = inputArray[1];
		if(firstInt > secInt){
			minPQ.add(firstInt);
			maxPQ.add(secInt);
		}else{
			minPQ.add(secInt);
			maxPQ.add(firstInt);
		}
		medians[0] = firstInt;
		medians[1] = maxPQ.peek();
		//Assign other integers
		for(int i = 2; i < inputArray.length; i++){
			int currNum = inputArray[i];
			Integer minPeek = minPQ.peek();
			
			if(currNum < minPeek){
				if(maxPQ.size() >= (i + 1) / 2){
					maxPQ.add(currNum);
					Integer moveNum = maxPQ.poll();
					minPQ.add(moveNum);
				}else{
					maxPQ.add(currNum);
				}
			}else{
				if(minPQ.size() >= (i + 1) / 2){
					minPQ.add(currNum);
					Integer moveNum = minPQ.poll();
					maxPQ.add(moveNum);
				}else{
					minPQ.add(currNum);
				}
			}
			
			/***************************************************************
			System.out.println("************************This is the " + i + " th iteration.");
			System.out.println("The max PQ is has the size is " + maxPQ.size() + " the root is " + maxPQ.peek());
			System.out.println("     ");
			System.out.println("The min PQ is has the size is " + minPQ.size() + " the root is " + minPQ.peek());			
			/**************************************************************/
			if((i + 1) % 2 == 1){/*The case that i is even */
				if(minPQ.size() > maxPQ.size()) medians[i] = minPQ.peek();
				else medians[i] = maxPQ.peek();
			}else{/*the case that i is odd*/
				medians[i] = maxPQ.peek();
				
			}
		}
		return medians;
	}
	
	public static void printPQ(PriorityQueue<Integer> PQ){
		while(PQ.size() > 0){
			System.out.println(PQ.poll());
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File inputFile = new File("Median.txt");
		int inputNumber = 10000;
		int[] inputArray = new int[inputNumber];
		genInput(inputFile, inputArray);
		int[] medians = genMedians(inputArray);
		for(int i = 0; i < medians.length; i++){
			System.out.println("The " + i + " iter median value is " + medians[i]);
		}
		int medSum = 0;
		for(int i = 0; i < medians.length; i++){
			medSum += medians[i];
		}
		int modValue = medSum % 10000;
		System.out.println("**********************************");
		System.out.println("The mod value is " + modValue);
	}

}

class maxComparator implements Comparator<Integer>{

	@Override
	public int compare(Integer o1, Integer o2) {
		// TODO Auto-generated method stub
		if(o1 > o2) return -1;
		if(o1 < o2) return 1;
		return 0;
	}
	
}

class minComparator implements Comparator<Integer>{

	@Override
	public int compare(Integer o1, Integer o2) {
		// TODO Auto-generated method stub
		if(o1 < o2) return -1;
		if(o1 > o2) return 1;
		return 0;
	}
	
}