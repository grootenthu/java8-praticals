package com.java8.parallel;

import java.util.List;

/**
 * Observing Threads & Order of execution
 * @author groot
 *
 */
public class ParallelDemo1 {

	public static void main(String[] args) {
		
		List<Integer> numbers = List.of(1,2,3,4,5,6,7,8,9,10,11,12,13);
		
		//using sequential programming
//		numbers.stream()
//				.map(number -> transform(number))
//				.forEach(e -> {});
		
		//using parallel programming
		//ordering of execution is not guaranteed
//		numbers.parallelStream()
//				.map(number -> transform(number))
//				.forEach(e -> {});
		
		//some methods are inherently ordered
		//some methods are unordered but may have an ordered counterpart
		
		//forEach in unordered, to use ordering use forEachOrdered
		//Note that the forEachOrdered does not convert execution pipeline to sequential
		//It only imposes ordering and is still concurrent
		//forEachOrdered does not guarantees ordering unless the stream guarantees ordering
		//example if your stream is on a list and list guarantees ordering, however set does not ordering
		numbers.parallelStream()
				.map(number -> transformOrdered(number))
				.forEachOrdered(System.out::println);
	}
	
	public static int transform(int number) {
		System.out.println("t: " + number + "--" + Thread.currentThread());
		sleep(500);
		return number * 1;
	}
	
	public static int transformOrdered(int number) {
		System.out.println("to: " + number + "--" + Thread.currentThread());
		sleep(500);
		return number * 1;
		
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