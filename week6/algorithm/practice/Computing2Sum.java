package week6.algorithm.practice;

import java.io.*;
import java.util.*;

public class Computing2Sum {
	public static long[] genInput(File inputFile){
		Scanner inputScan = null;
		long[] inputNumbers = null;
		try{
			inputScan = new Scanner(new FileReader(inputFile));
			StringBuilder readBuilder = new StringBuilder();
			while(inputScan.hasNextLine()){
				readBuilder.append(inputScan.nextLine());
				readBuilder.append(",");
			}	
			String inputString = readBuilder.toString();
			String[] stringSplits = inputString.split(",");
			inputNumbers = new long[stringSplits.length];
			for(int i = 0; i < stringSplits.length;i++){
				inputNumbers[i] = Long.parseLong(stringSplits[i]);
			}
			Arrays.sort(inputNumbers);
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(inputScan != null){
				inputScan.close();
			}
		}
		return inputNumbers;
	}
	
	public static HashSet<Long> genSumSet(long[] inputArray, long lowerRange, long upperRange){
		HashSet<Long> sumSet = new HashSet<Long>();
		for(int i = 0; i< inputArray.length; i++){
			long currNumber = inputArray[i];
			long lowerNumber = lowerRange - currNumber;
			long upperNumber = upperRange - currNumber;
			//System.out.println("This is the " + i + " iteration");
			int startIndex = getLowerIndex(inputArray, lowerNumber);
			int endIndex = getUpperIndex(inputArray, upperNumber);
			if(startIndex <= endIndex){
				for(int j = startIndex; j <= endIndex; j++)
					if(!sumSet.contains(currNumber + inputArray[j]))  sumSet.add(currNumber + inputArray[j]);
			}
			
		}
		return sumSet;
	}
	
	public static int getLowerIndex(long[] searchArray, long lowerNumber){
		if(lowerNumber < searchArray[0]) return 0;
		else if(lowerNumber  > searchArray[searchArray.length - 1]) return Integer.MAX_VALUE;
		else{
			int lowIndex = 0;
			int highIndex = searchArray.length - 1;
			while(lowIndex <= highIndex){
				int midIndex = lowIndex + (highIndex - lowIndex) / 2;
				if(searchArray[midIndex] > lowerNumber) highIndex = midIndex - 1;
				else if(searchArray[midIndex] < lowerNumber) lowIndex = midIndex + 1;
				else return midIndex;
			}

			if(searchArray[highIndex] > lowerNumber) return highIndex;
			else return highIndex + 1;
		}
	}
	
	public static int getUpperIndex(long[] searchArray, long upperNumber){
		if(upperNumber > searchArray[searchArray.length - 1]) return searchArray.length - 1;
		else if(upperNumber < searchArray[0]) return Integer.MIN_VALUE;
		else{
			int lowIndex = 0;
			int highIndex = searchArray.length - 1;
			while(lowIndex <= highIndex){
				int midIndex = lowIndex + (highIndex - lowIndex) / 2;
				if(searchArray[midIndex] > upperNumber) highIndex = midIndex - 1;
				else if(searchArray[midIndex] < upperNumber) lowIndex = midIndex + 1;
				else return midIndex;
			}

			if(searchArray[highIndex] > upperNumber) return highIndex - 1;
			else return highIndex;
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File inputFile = new File("algo1-programming_prob-2sum.txt");
		int lowerRange = -10000;
		int upperRange = 10000;
		long[] inputNumbers = genInput(inputFile);
		System.out.println("**********************************************");
		System.out.println("The input numbers have been read");
		//for(long eachNumber: inputNumbers)
			//System.out.println(eachNumber);
		System.out.println("**********************************************");
		System.out.println("Started to compute the distinct sums. ");
		HashSet<Long> resSet = genSumSet(inputNumbers, lowerRange, upperRange);
		System.out.println("**********************************************");
		System.out.println("The inputSet has the size of " + resSet.size());
	}

}