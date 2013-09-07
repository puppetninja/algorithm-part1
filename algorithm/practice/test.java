package algorithm.practice;
import java.util.*;


public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String test = "";
		char[] testArray = test.toCharArray();
		System.out.println("---------------------");
		for(char eachChar: testArray){
			System.out.println(eachChar);
		}
		System.out.println(testArray.length);
		System.out.println((char)('a' + 1));
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