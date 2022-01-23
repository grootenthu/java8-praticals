package com.java8.parallel;

import java.util.List;

/**
 * Parallel - filter & reduce
 * @author groot
 *
 */
public class ParallelDemo2 {

	public static void main(String[] args) {
		
		List<Integer> numbers = List.of(1,2,3,4,5,6,7,8,9,10,11,12,13);
		
		//Filter runs in parallel
//		numbers.parallelStream()
//			.filter(e -> check(e))
//			.forEachOrdered(System.out::println);
		
		//what about reduce
		
		//using sequential stream
		System.out.println(numbers.stream()
				.reduce(0, (total, e) -> add(total, e)));
		
		//using parallel stream
		System.out.println(numbers.parallelStream()
				.reduce(0, (total, e) -> add(total, e)));
		
		//Change the initial value from 0 to 30 for sequential, out put should be 91 + 30 = 121
		System.out.println(numbers.stream()
				.reduce(30, (total, e) -> add(total, e)));
		
		//Change the initial value from 0 to 30 for parallel, out put should be 91 + 30 = 121 however it is not
		System.out.println(numbers.parallelStream()
				.reduce(30, (total, e) -> add(total, e)));
		
		//reduce does not take an initial value, it takes an identity value
		//(int + ) ==> identity is 0 e.g. x + 0 = x
		//(int * ) ==> identity is 1 e.g. x * 1 = x
		//what we work with should be monoid
		//	identity + idempotent
		//do not pass to reduce a value which is not identity value else there will be issues in concurrent environment
		
		
		//Therefore java8 support concurrency for filter, map & reduce however for reduce one must understand carefully
		
	}
	
	public static int add(int a, int b) {
		int result = a+b;
		
		System.out.println("a = " + a + " b = " + b + " result" + result + "--" + Thread.currentThread());
		sleep(500);
		
		return result;
	}
	
	public static boolean check(int number) {
		System.out.println("c: " + number + "--" + Thread.currentThread());
		sleep(500);
		return true;
	}
	
	private static boolean sleep(int ms) {
		try {
			Thread.sleep(ms);
			return true;
		} catch (InterruptedException ie) {
			return false;
		}
	}
}
