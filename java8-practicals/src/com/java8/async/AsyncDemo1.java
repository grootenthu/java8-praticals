package com.java8.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Creating a completable future
 * @author groot
 *
 */
public class AsyncDemo1 {

	public static CompletableFuture<Integer> create() {
		return CompletableFuture.supplyAsync(() -> 2);
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		//CompletableFuture<Integer> future = create();
		
		//Famous or popular functional interfaces
		//Supplier<T> T get()             - factories (Does not take any input but returns a value)
		//Predicate<T> boolean test(T)    - filter (takes input and return boolean)
		//Function<T,R> R apply(T)        - map (takes input and gives back transformed output)
		//Consumer<T> void accept(T)      - forEach (takes data but never returns anything)
		
		//Completable Future also uses same concept
		
		//CompletableFuture<Void> future2 = future.thenAccept(data -> System.out.println(data)); //called thenAccept because in java script they are called then-able
		
		create()
			.thenAccept(data -> System.out.println(data))
			.thenRun(() -> System.out.println("This never dies"))
			.thenRun(() -> System.out.println("This never dies really"));
		
		System.out.println(create().get()); ///bad idea to use, the best thing to do with get is to forget
		System.out.println(create().getNow(0)); //non blocking, however will return 0 immediately if the async task has not finished. so not useful either.
		
		
	}
}
