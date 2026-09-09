public class AW3_IntegerToBinary {

    // AW 3: integer -> binary string
    public static String intToBinary(int n) {
        if (n == 0) return "0";
        if (n < 0) return "-" + intToBinaryPositive(-n);
        return intToBinaryPositive(n);
    }

    private static String intToBinaryPositive(int n) {
        String result = "";

        while (n > 0) {
            int bit = n % 2;
            result = (char) ('0' + bit) + result;
            n /= 2;
        }

        return result;
    }

    public static void main(String[] args) {
        System.out.println("AW3: " + intToBinary(173));
    }
}
