public class AW4_IntegerToHex {

    // AW 4: integer -> hexadecimal string
    public static String intToHex(int n) {
        if (n == 0) return "0";
        if (n < 0) return "-" + intToHexPositive(-n);
        return intToHexPositive(n);
    }

    private static String intToHexPositive(int n) {
        String result = "";

        while (n > 0) {
            int digit = n % 16;
            result = hexDigit(digit) + result;
            n /= 16;
        }

        return result;
    }

    private static char hexDigit(int value) {
        if (value < 10) return (char) ('0' + value);
        return (char) ('A' + value - 10);
    }

    public static void main(String[] args) {
        System.out.println("AW4: " + intToHex(43981));
    }
}
