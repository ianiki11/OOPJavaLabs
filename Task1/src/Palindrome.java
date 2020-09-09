
public class Palindrome {

    private static String reverseString(String s) {
        StringBuilder str = new StringBuilder(s);
        return str.reverse().toString();
    }

    public static Boolean isPalindrome(String s) {
        String rs = reverseString(s);
        return s.equals(rs);
    }
}
