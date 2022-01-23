package com.java8.collectors;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.Arrays;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.flatMapping;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.minBy;

import static java.util.Comparator.comparing;

public class CollectorsExamples {
	
	public static void main(String[] args) {
		
		//1. map names as key and age as the value
		//example 1
		System.out.println(Utils.createDistinctPeople().stream()
									.distinct()
							        .collect(toMap(person -> person.getName(), person -> person.getAge())));
		
		//example 2
		System.out.println(Utils.createDistinctPeople().stream()
									.distinct()
							        .collect(toMap(Person::getName, Person::getAge)));
		
		//2. print the ages of all persons
		//example 1
		List<Integer> ages = Utils.createPeople().stream()
									.map(Person::getAge)
									.collect(toList());
		
		//this ages list is mutable and you can perform following
		ages.add(99);
		
		System.out.println(ages);
		
		//example 2
		//if you care about immutablity 
		List<Integer> agesFinal = Utils.createPeople().stream()
				.map(Person::getAge)
				.collect(collectingAndThen(toList(), Collections::unmodifiableList));
		
		//agesFinal.add(99);     ->> this call will fail with an exception
		//Better way to do this is in java 10
		//   .collect(toUnmodifiableList())
		System.out.println(agesFinal);
		
		//3. create comma separated names in upper case of people older than 30
		
		//example 1 imperative way
		//notice that the output of below code will be PAULA,PAUL,JACK,
		//it takes a lot remove the accidental comma at the end
		//that is the reason why imperative code might result into accidental complexity
		for (Person person : Utils.createPeople()) {
			if (person.getAge() > 30) {
				System.out.print(person.getName().toUpperCase() + ",");
			}
		}
		
		//example 2
		//using collect and join
		System.out.println("\n" + Utils.createPeople().stream()
									.filter(person -> person.getAge() > 30)
									.map(Person::getName)
									.map(String::toUpperCase)
									.collect(joining(",")));
		
		//4. For a list of people divide them into two different collections. One collection will
		//have peoples with even age & the other will have odd age
		
		//example 1 using streams twice
		List<Person> evenAged = Utils.createPeople().stream()
									.filter(person -> person.getAge() % 2 == 0)
									.collect(toList());
		
		List<Person> oddAged = Utils.createPeople().stream()
				.filter(person -> person.getAge() % 2 != 0)
				.collect(toList());
		
		System.out.println(evenAged);
		System.out.println(oddAged);
		
		//example 2 using partitioning
		System.out.println(Utils.createPeople().stream()
									.collect(partitioningBy(person -> person.getAge() %2 ==0)));
		
		//5. group the people based on their name
		
		//example 1 imperative way
		Map<String, List<Person>> byName = new HashMap<>();
		
		for (Person person : Utils.createPeople()) {
			List<Person> list = null;
			if(byName.containsKey(person.getName())) {
				list = byName.get(person.getName());
			} else {
				list = new ArrayList<>();
				byName.put(person.getName(), list);
			}
			
			list.add(person);
		}
		
		System.out.println(byName);
		
		//example 2
		System.out.println(Utils.createPeople().stream()
									.collect(groupingBy(Person::getName)));
		
		//6. group the people's ages based on their name
		//groupingBy takes two arguments
		//	-> grouping(Function<T,R>) ===> return a Collector
		//  -> groupingBy(Function<T,R>, Collector)  ====> It is a collector which can recursively take another collector
		
		//example 1 - using grouping & mapping
		//In this case we are using Collector(Function, Collector(Function, Collector)
		System.out.println(Utils.createPeople().stream()
				.collect(groupingBy(Person::getName, mapping(Person::getAge, toList()))));
		
		//7. group the people based on the count of their names (i.e. frequency or occurrences)
		System.out.println(Utils.createPeople().stream()
				.collect(groupingBy(Person::getName, counting())));
		
		
		//8. group the people based on the count of their names, note that the returned count should be Integer and not Long
		//Notice the example above will always return a Map of type String, Long
		// groupingBy & mapping takes (Function, Collector)
		//counting itself is a collector and therefore we need (Collector, Function) which will transform the Long to Integer
		//collectingAndThen take (Collector, Function)
		System.out.println(Utils.createPeople().stream()
				.collect(groupingBy(Person::getName, collectingAndThen(counting(), Long::intValue))));
		
		//9. Get the maximum age of person from a list
		//using max
		System.out.println(Utils.createPeople().stream()
				.mapToInt(Person::getAge)
				.max());
		
		//10. Get the person object whose age is the maximum & minimum
		//maxBy & minBy is also an collect (reduce) operation
		System.out.println(Utils.createPeople().stream()
				.collect(maxBy(comparing(Person::getAge))));
		
		System.out.println(Utils.createPeople().stream()
				.collect(minBy(comparing(Person::getAge))));
		
		//11. Get the person's name whose age is the maximum & minimum
		System.out.println(Utils.createPeople().stream()
				.collect(collectingAndThen(
						maxBy(comparing(Person::getAge)), 
						person -> person.map(Person::getName).orElse(""))));
		
		//12. Flat map
		List<Integer> numbers = Arrays.asList(1,2,3);
		
		//one-to-one function
		//Stream<T>.map(func1to1)  ===> Stream<R>
		System.out.println(numbers.stream()
				.map(number -> number * 2) //one -to - one function
				.collect(toList()));
		
		//one-to-many function
		//Stream<T>.map(func1ton)  ===> Stream<List<R>>
		System.out.println(numbers.stream()
				.map(number -> Arrays.asList(number -1, number + 1)) //one -to - many function
				.collect(toList()));
		
		//If you don't want collection of collection
		//For one to many function you want Stream<T>.map(func1ton)  ===> Stream<R>
		System.out.println(numbers.stream()
				.flatMap(number -> Arrays.asList(number -1, number + 1).stream()) //one -to - many function
				.collect(toList()));
		
		//13. Flat mapping during collect
		//Get the name of person by age, however the name of person should be a list of characters
		System.out.println(Utils.createPeople().stream()
				.collect(groupingBy(Person::getAge, mapping(person -> person.getName().split(""), toList()))));
		
		//Above examples displays the list of arrays
		//flatMapping was added in Java 9
		System.out.println(Utils.createPeople().stream()
				.collect(groupingBy(Person::getAge, 
						flatMapping(person -> Stream.of(person.getName().split("")), toList()))));
		
		//14. Sorting
		System.out.println(Utils.createPeople().stream()
				.sorted(comparing(Person::getAge).thenComparing(Person::getName)) //there might be performance consequences, need to check
				.collect(toList()));
		
	}
}
