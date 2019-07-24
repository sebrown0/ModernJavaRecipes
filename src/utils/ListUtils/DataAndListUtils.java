package utils.ListUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import utils.SimpleFunctions;
import utils.exceptions.ExceptionWrapper;

/**
 * Utility functions for working with data and lists.
 * 
 * @author Steve Brown
 *
 */
public class DataAndListUtils {

  /**
   * Strip null values from a generic list.
   * 
   * @param <T>   : Type of the list.
   * @param list  : List to strip nulls from.
   * @return      : A list with no nulls.
   */
  public static <T> List<T> removeNullsFromList(List<T> list){
    return list.stream()
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }
  
  /**
   * Replace null values in a list with a specified value.
   * 
   * @param list  : List to replace null in.
   * @param text  : The text to replace the null.
   * @return      : A list with the nulls replaced with specified text.
   */
  public static List<String> replaceNullsInList(List<String> list, String text){
    return list.stream()
        .map(s -> { return Objects.toString(s, text); })
        .collect(Collectors.toList());
  }
  
  /**
   * Get a count of all words in a string.
   * 
   * @param passage : The string to search.
   * @return        : A map of words and their frequency in the passage.
   */
  public static Map<String, Integer> fullWordCount(String passage){
    Map<String, Integer> words = new HashMap<>();
    Arrays.stream(removePunctuation(passage).toLowerCase().split("\\s+"))
        .forEach(w -> words.merge(w, 1, Integer::sum));
    return words;
  }
  
  /**
   * Count the frequency of a word in a passage of text.
   * 
   * @param passage : The string to search.
   * @param word    : The word to count.
   * @return        : The frequency of the word.
   */
  public static long countWord(String passage, String word){   
    return Arrays.stream(passage.split("\\s+")).filter(w -> w.contains(word)).count();
  }
  
  /**
   * Count the frequency of a words in a passage of text.
   * 
   * @implNote First does a replace all to remove punctuation from the passage.
   * 
   * @param passage : The string to search.
   * @param strings : The words to count.
   * @return        : A map of the words and their frequency.
   */
  public static Map<String, Integer> countWords(String passage, String... strings){
    Map<String, Integer> words = new HashMap<>();
    Arrays.stream(strings).forEach(s -> words.put(s, 0));
    Arrays.stream(removePunctuation(passage).split(" "))
      .forEach(word -> words.computeIfPresent(word, (k,v) -> v + 1));
    return words;
  }
  
  /**
   * Basic check to see if a map's key contains a string.
   * 
   * @param str   : The string to check for. 
   * @param words : Map with a String type as its key.
   * @return      : The key (word) if successful or an empty string if nothing found.
   */
  protected static String getWord(String str, Map<String, Integer> words) {
    return words.keySet().stream().filter(w -> str.contains(w)).findFirst().orElse("");
  }
 
  /**
   * Remove non word characters from a string and replace with a space.
   * 
   * @param str : The string to remove chars from.
   * @return    : A string with only word chars.
   */
  public static String removePunctuation(String str) {
    return str.toLowerCase().replaceAll("\\W", " ");
  }
  
  /**
   * Remove non word characters from a string and replace with delimiter.
   *  
   * @param str       : The string to parse.
   * @param delimiter : Character to delimit words in the string.
   * @return          : A string delimited by the delimiter provided.
   */
  public static String removePunctuationAndReplace(String str, String delimiter) {
    return str.toLowerCase().replaceAll("\\W", delimiter);
  }
  
  /**
   * Divide the numbers in a list by a factor.
   * 
   * @param list    : List of integers to divide.
   * @param factor  : Factor to divide them by.
   * @return        : The divided list.
   */
  public static List<Integer> divideNumsInList(List<Integer> list, Integer factor){
    return list.stream()
        .map(n -> SimpleFunctions.div.applyAsInt(n, factor))
        .collect(Collectors.toList());
  }
  
  /**
   * Encode values.
   *   
   * @param values  : The values to encode.
   * @return        : A list of encoded values.
   */
  public static List<String> encodeValues(String... values){
    return Arrays.stream(values)
        .map(s -> encodeString(s))
        .collect(Collectors.toList());
  }

  /**
   * Encode values with a generic exception wrapper.
   * 
   * @param values  : The values to encode.
   * @return        : A list of encoded values.
   */
  public static List<String> encodeValuesWithWrapper(String... values){
    return Arrays.stream(values)
        .map(ExceptionWrapper.wrap(s -> encodeString(s)))
        .collect(Collectors.toList());
  }
  
  /**
   * Separate function to encode string using "UTF-8". 
   * Deals with the checked exception.
   * 
   * @param s : String to encode.
   * @return  : Encoded string.
   */
  private static String encodeString(String s) {
    try {
      return URLEncoder.encode(s, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }
}
