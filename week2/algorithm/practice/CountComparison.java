package week2.algorithm.practice;

import java.util.*;
import java.io.*;

public class CountComparison {
	public static int[] genArray(File file){
		ArrayList<Integer> readList = new ArrayList<Integer>();
		int[] genArray = null;
		Scanner inputScan = null;
		try{
			inputScan = new Scanner(new FileReader(file));
			while(inputScan.hasNextInt()){
				readList.add(inputScan.nextInt());
			}
			genArray = new int[readList.size()];
			for(int i = 0; i < readList.size(); i++){
				genArray[i] = readList.get(i);
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(inputScan != null){
				inputScan.close();
			}
		}
		
		return genArray;
	}
	//Choose the first element as the pivot
	public static int countCompFirst(int[] num, int start, int end){
		if(start == end){
			return 0;
		}else{
			int numComp = end - start;
			int pivot = num[start];
			int i = start + 1;
			int temp = 0;
			for(int j = start + 1; j <= end; j++){
				if(num[j] < pivot){
					temp = num[i];
					num[i] = num[j];
					num[j] = temp;
					i++;
				}
			}
			//To swap the pivot and the last element which is less than the pivot
			temp = num[start];
			num[start] = num[i - 1];
			num[i - 1] = temp;
			int leftCompNum = i > start + 1 ? countCompFirst(num, start, i - 2) : 0;
			int rightCompNum = i <= end ? countCompFirst(num, i, end) : 0;
			
			return leftCompNum + rightCompNum + numComp;
		}
	}
	//Choose the last element as the pivot
	public static int countCompLast(int[] num, int start, int end){
		if(start == end){
			return 0;
		}else{
			int numComp = end - start;
			int pivot = num[end];
			//To swap the pivot with the first element
			int temp = num[start];
			num[start] = num[end];
			num[end] = temp;
			int i = start + 1;
			for(int j = start + 1; j <= end; j++){
				if(num[j] < pivot){
					temp = num[i];
					num[i] = num[j];
					num[j] = temp;
					i++;
				}
			}
			//To swap the pivot and the last element which is less than the pivot
			temp = num[start];
			num[start] = num[i - 1];
			num[i - 1] = temp;
			int leftCompNum = i > start + 1 ? countCompLast(num, start, i - 2) : 0;
			int rightCompNum = i <= end ? countCompLast(num, i, end) : 0;
			
			return leftCompNum + rightCompNum + numComp;
		}
	}
	//Choose the medium as the pivot
	public static int countCompMed(int[] num, int start, int end){
		if(start == end){
			return 0;
		}else{
			int med = (start + end) / 2;
			int[] pivotArray = {num[start], num[med], num[end]};
			int pivotIndex = medIndex(pivotArray, start, med, end);
			int numComp = end - start;
			int pivot = num[pivotIndex];
			int temp = 0;
			if(pivotIndex != start){
				temp = num[start];
				num[start] = num[pivotIndex];
				num[pivotIndex] = temp;
			}
			
			int i = start + 1;
			for(int j = start + 1; j <= end; j++){
				if(num[j] < pivot){
					temp = num[i];
					num[i] = num[j];
					num[j] = temp;
					i++;
				}
			}
			//To swap the pivot and the last element which is less than the pivot
			temp = num[start];
			num[start] = num[i - 1];
			num[i - 1] = temp;
			int leftCompNum = i > start + 1 ? countCompMed(num, start, i - 2) : 0;
			int rightCompNum = i <= end ? countCompMed(num, i, end) : 0;
			
			return leftCompNum + rightCompNum + numComp;
		}
	}
	
	//The helper function
	public static int medIndex(int[] num, int start, int med, int end){
		if(num[0] < Math.max(num[1], num[2]) && num[0] > Math.min(num[1],num[2])) return start;
		else if(num[1] < Math.max(num[0], num[2]) && num[1] > Math.min(num[0], num[2])) return med;
		else return end;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File inputFile = new File("QuickSort.txt");
		int[] inputArray = genArray(inputFile);
		int[] inputArray2 = genArray(inputFile);
		int[] inputArray3 = genArray(inputFile);
		int numFirstComp = countCompFirst(inputArray, 0, inputArray.length - 1);
		System.out.println("The number of comparison taking first element as pivot is " + numFirstComp);
		int numLastComp = countCompLast(inputArray2, 0, inputArray2.length - 1);
		System.out.println("The number of comparison taking last element as pivot is " + numLastComp);
		int numMedComp = countCompMed(inputArray3, 0, inputArray3.length - 1);
		System.out.println("The number of comparison taking medium element as pivot is " + numMedComp);
		for(int i = 0; i < inputArray3.length; i++){
			System.out.println(inputArray3[i]);
		}
		
	}

}
