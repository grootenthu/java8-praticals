package com.java8.parallel;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * Using parallel streams
 * 	- An IO problem 
 *  - A computational problem
 * 	- How many threads?
 *  - Formula for deciding no of threads
 *  - Default no of threads
 *  - Configuring number of threads JVM wide
 *  - Configuring programmatically
 * @author groot
 *
 */
public class ParallelDemo3 {
	
	public static void main(String[] args) throws Exception {
		
		//How many threads can I create? //bad question
		//How many threads should I create? //always think this way
		
		//Computation intensive vs. IO intensive
		//Computation intensive -> Number of threads <= number of cores
		//IO intensive -> Number of threads may be >= number of cores
		
		//						   no of cores
		// No of threads <=  -----------------------
		//						1 - blocking factor
		
		// 0 <= blocking factor < 1
		// for computation intensive blocking factor 0
		// for IO intensive if blocking factor is 0.5 depending upon how much percentage of time the CPU sleeps then no of threads can be twice than no of cores
		
		
		//default no of threads = number of cores on your machine
		System.out.println(Runtime.getRuntime().availableProcessors());
		System.out.println(ForkJoinPool.commonPool()); //parallelism = 7 if no of processors = 8 which includes main method
		
		
		//JVM wide no of threads using property
		//   ==>   -Djava.util.concurrent.ForkJoinPool.common.parallelism=100
		//This is a terrible idea though
		//because it might reduce performance
		
		//configure it programmatically
		List<Integer> numbers = List.of(1,2,3,4,5,6,7,8,9,10);
		process(numbers.parallelStream()
				.map(e -> transform(e)));
		
		//Parallel does not always means faster
		
		//When parallel makes no sense
		//for example using parallel stream with findFirst will always run sequentially
		//also when using filter operations you may end up using more objects than required on findFirst
		//and using parallel stream with findAny will run parallely
	}
	
	public static void process (Stream<Integer> stream) throws Exception {
		ForkJoinPool pool = new ForkJoinPool(100);
		
		pool.submit(() -> stream.forEach(e -> {}));
		
		pool.shutdown();
		pool.awaitTermination(10, TimeUnit.SECONDS);
		
	}
	
	public static int transform(int number) {
		System.out.println("t: " + number + "--" + Thread.currentThread());
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
