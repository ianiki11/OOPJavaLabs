public class Main {

    public static void main(String[] args) {
        int num = 2;
        while (num <= 100) {
            if (Primes.isPrime(num)) {
                System.out.println(num);
            }
            num++;
        }
    }
}
