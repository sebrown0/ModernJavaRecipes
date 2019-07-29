package utils;
/**
 * 
 * @author Steve Brown
 *
 */
public class PalindromeEvaluator {
	
	public static boolean isPalindrome(String s) {
		String forward = s.toLowerCase().codePoints()
				.filter(Character::isLetterOrDigit)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
				.toString();
		
		String reverse = new StringBuilder(forward).reverse().toString();
		return forward.equals(reverse);
	}
}
