package com.java8.parallel;

import java.util.concurrent.ForkJoinPool;

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
public class Parallel3 {
	
	public static void main(String[] args) {
		
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
		
		
		
	}

}
