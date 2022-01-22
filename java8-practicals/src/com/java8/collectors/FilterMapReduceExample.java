package com.java8.collectors;

import java.util.Arrays;
import java.util.List;

public class FilterMapReduceExample {
	
	public static void main(String[] args) {
		
		//print all peoples in the list
		//createPeople().forEach(System.out::println); // here is for each is an internal iterator
		
		//print only the people whose age is > 30
		createPeople().stream()	//stream also are internal iterator
					  .filter(person -> person.age > 30)
					  .forEach(System.out::println);
		
		//print all the peoples names
		createPeople().stream()
					  .map(person -> person.getName())  //map is a transformation function
					  .forEach(System.out::println);
		
		//print the total age of all persons
		//example 1 reduce
		System.out.println(createPeople().stream()
					  .map(person -> person.getAge())
					  .reduce(0, (total, age) -> total +age));
		//example 2 reduce
		System.out.println(createPeople().stream()
				  .map(person -> person.getAge())
				  .reduce(0, (total, age) -> Integer.sum(total, age)));
		//example 3 reduce -> re-factored & concise
		System.out.println(createPeople().stream()
				  .map(person -> person.getAge())
				  .reduce(0, Integer::sum));
		
		
		
		
	}
	
	public static List<Person> createPeople() {
		return Arrays.asList(
				new Person("Sara", 20), 
				new Person("Sara", 22),
				new Person("Bob", 20),
				new Person("Paula", 32),
				new Person("Paul", 32),
				new Person("Jack", 3),
				new Person("Jack", 72),
				new Person("Jill", 11)
		);
	}
}

class Person {
	String name;
	Integer age;
	
	public Person(String name, Integer age) {
		super();
		this.name = name;
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
	@Override
	public String toString() {
		return this.name + " -- " + this.age;
	}
	
}
