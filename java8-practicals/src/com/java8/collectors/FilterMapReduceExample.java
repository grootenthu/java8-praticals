package com.java8.collectors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FilterMapReduceExample {
	
	public static void main(String[] args) {
		
		//1. print all peoples in the list
		//createPeople().forEach(System.out::println); // here is for each is an internal iterator
		
		//2. print only the people whose age is > 30
		createPeople().stream()	//stream also are internal iterator
					  .filter(person -> person.age > 30)
					  .forEach(System.out::println);
		
		//3. print all the peoples names
		createPeople().stream()
					  .map(person -> person.getName())  //map is a transformation function
					  .forEach(System.out::println);
		
		//4. print the total age of all persons
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
		
		//5. print the list of names, in upper case, of those who are older then 30
		
		//example 1 Don't do this
		//It is wrong to say that following code works, it actually behaves
		//forEach below is an impure function because it is mutating a list
		//worst kind of mutability is shared mutability
		//there could be issues if we convert stream to parallelStream
		List<String> namesOlderThan30 = new ArrayList<>();
		
		createPeople().stream()
					  .filter(person -> person.getAge() > 30)
					  .map(Person::getName)
					  .map(String::toUpperCase)
					  .forEach(name -> namesOlderThan30.add(name));
		
		System.out.println(namesOlderThan30);
		
		//example 2
		//We must avoid shared mutability
		//Following code works but is not concise
		System.out.println(createPeople().stream()
		  .filter(person -> person.getAge() > 30)
		  .map(Person::getName)
		  .map(String::toUpperCase)
		  .reduce(new ArrayList<String>(),
				  (names, name) -> {
					  names.add(name);   //local mutable but not shared mutable
					  return names;
				  },
				  (names1, names2) -> {
					  names1.addAll(names2);
					  return names1;
		  }));
		
		//example3
		//The right ways is to delegate, be declarative & leave it to the API's
		System.out.println(createPeople().stream()
				  .filter(person -> person.getAge() > 30)
				  .map(Person::getName)
				  .map(String::toUpperCase)
				  .collect(Collectors.toList()));    //delegate our work to a collector
		
		
		
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
