package com.java8.collectors;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.partitioningBy;

public class CollectorsExamples {
	
	public static void main(String[] args) {
		
		//1. map names as key and age as the value
		//example 1
		System.out.println(Utils.createDistinctPeople().stream()
									.distinct()
							        .collect(Collectors.toMap(person -> person.getName(), person -> person.getAge())));
		
		//example 2
		System.out.println(Utils.createDistinctPeople().stream()
									.distinct()
							        .collect(Collectors.toMap(Person::getName, Person::getAge)));
		
		//2. print the ages of all persons
		//example 1
		List<Integer> ages = Utils.createPeople().stream()
									.map(Person::getAge)
									.collect(Collectors.toList());
		
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
	}
}
