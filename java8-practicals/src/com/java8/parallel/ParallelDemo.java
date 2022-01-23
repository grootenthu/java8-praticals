package com.java8.parallel;

import java.util.List;
import java.util.stream.Stream;

public class ParallelDemo {
	
	public static void main(String[] args) {
		
		List<Integer> numbers = List.of(1,2,3,4,5,6,7,8,9,10,11,12,13);
		
		//using sequential programming
		numbers.stream()
				.map(number -> transform(number))
				.forEach(System.out::println);
		
		//using parallel programming there are two ways
		
		//1. If you are the creator of the stream then
		//using sequential programming
		numbers.parallelStream()
				.map(number -> transform(number))
				.forEach(System.out::println);
		
		//2. If you are not the creator of the stream
		use(numbers.stream());
	}
	
	public static void use(Stream<Integer> stream) {
		stream
			.parallel()
			.map(number -> transform(number))
			.forEach(System.out::println);
		
		//Be careful you can also do following
		stream
			.parallel() //parallel
			.map(number -> transform(number))
			.sequential() //as well as sequential
			.forEach(System.out::println);
		//This does not mean that the map operation runs parallely and forEach run sequentially
		// In this case the operation which is closest/just above the terminal operation wins
		//so entire pipeline will run sequentially
	}
	
	public static int transform(int number) {
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
