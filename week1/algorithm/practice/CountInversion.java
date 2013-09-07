package week1.algorithm.practice;

import java.util.*;
import java.io.*;

public class CountInversion {
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
	
	public static long countInversion(int start, int end, int[] num){
		//The termination case
		if(start == end){
			return 0;
		}else{
			int medium = (start + end) / 2;
			long leftCount = countInversion(start, medium, num);
			long rightCount = countInversion(medium + 1, end, num);
			int mergeCount = 0;
			int[] leftSubArray = new int[medium - start + 1];
			int[] rightSubArray = new int[end - medium];
			//To duplicate the left and right sub arrays.
			for(int i = start; i <= end ; i++){
				if(i <= medium) leftSubArray[i - start] = num[i];
				else rightSubArray[i - medium - 1] = num[i];
			}
			//merge and count the inversions
			int leftIter = 0;
			int rightIter = 0;
			int sortIter = start;
			while(leftIter < leftSubArray.length && rightIter < rightSubArray.length){
				if(leftSubArray[leftIter] >= rightSubArray[rightIter]){
					num[sortIter] = rightSubArray[rightIter];
					mergeCount = mergeCount + (leftSubArray.length - leftIter);
					rightIter++;
				}else{
					num[sortIter] = leftSubArray[leftIter];
					leftIter++;
				}
				
				sortIter++;
			}
			//The case the left is not iterated completely
			if(leftIter < leftSubArray.length){
				//mergeCount = mergeCount + (leftSubArray.length - leftIter - 1);
				for(int i = leftIter; i < leftSubArray.length ; i++){
					num[sortIter] = leftSubArray[i];
					sortIter++;
				}
			}
			//The case the right is not iterated completely
			if(rightIter < rightSubArray.length){
				for(int i = rightIter; i < rightSubArray.length; i++){
					num[sortIter] = rightSubArray[i];
					sortIter++;
				}
			}
			
			return leftCount + rightCount + mergeCount;
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File inputFile = new File("IntegerArray.txt");
		int[] inputArray = genArray(inputFile);
		//Small Test Case
		int[] testcase = {1,3,5,2,4,6};
		int[] testcase2 = {6,5,4,3,2,1};
		long num = countInversion(0, testcase.length - 1, testcase);
		long num2 = countInversion(0, testcase2.length - 1, testcase2);
		System.out.println("The number of inversions in the test case: " + num);
		System.out.println("The number of inversions in the test case2: " + num2);
		System.out.println("The merge sorted num array: ");
		for(int i = 0; i < testcase.length; i++){
			System.out.println(testcase[i]);
		}
		
		System.out.println("The merge sorted num2 array: ");
		for(int i = 0; i < testcase2.length; i++){
			System.out.println(testcase2[i]);
		}
		long inversionNum = countInversion(0, inputArray.length - 1, inputArray);
		System.out.println("The number of inversions in the file: " + inversionNum);
	}

}
