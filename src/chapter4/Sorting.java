package chapter4;

import static java.util.Comparator.*;
import java.util.List;
import java.util.stream.Collectors;

import helpers.Golfer;

public class Sorting {

	/*
	 * Sort a list of strings by their natural order.
	 */
	public static List<String> sortNatural(List<String> list){
		return list.stream().sorted().collect(Collectors.toList());
	}
	
	/*
	 * Sort a list of strings by their length max to min.
	 */
	public static List<String> sortByLengthMaxWithSorted(List<String> list){
		return list.stream()
				.sorted((s1, s2) -> s2.length() - s1.length())
				.collect(Collectors.toList());
	}
	
	/*
	 * Sort a list of strings by their length min to max.
	 */
	public static List<String> sortByLengthMinWithComparator(List<String> list){
		return list.stream()
				.sorted(comparingInt(String::length))
				.collect(Collectors.toList());
	}
	
	/*
	 * Sort a list of strings by their length and then alphabetically.
	 */
	public static List<String> sortByLengthThenAlpha(List<String> list){
		return list.stream()
				.sorted(comparingInt(String::length).thenComparing(naturalOrder()))
				.collect(Collectors.toList());
	}

	/*
	 * Sort Golfer(s) by the lowest score, then their last name and finally their first name. 
	 */
	public static List<Golfer> sortByScoreThenLastThenFirst(List<Golfer> list){
		return list.stream()
				.sorted(comparingInt(Golfer::getScore)
						.thenComparing(Golfer::getLast)
						.thenComparing(Golfer::getFirst))
				.collect(Collectors.toList());		
	}
}
