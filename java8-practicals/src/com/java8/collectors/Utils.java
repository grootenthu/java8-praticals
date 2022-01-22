package com.java8.collectors;

import java.util.Arrays;
import java.util.List;

public class Utils {

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
