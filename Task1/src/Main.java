import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите слово: ");

        String finalString = scanner.next();
        scanner.close();

        System.out.println(Palindrome.isPalindrome(finalString));
    }
}