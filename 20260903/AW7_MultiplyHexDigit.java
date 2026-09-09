public class AW7_MultiplyHexDigit {

    // AW 7: one hex digit * hex string
    public static String multiplyHexDigit(char digit, String number) {
        int multiplier = hexValue(digit);
        int carry = 0;
        String result = "";

        for (int i = number.length() - 1; i >= 0; i--) {
            int x = hexValue(number.charAt(i));
            int product = x * multiplier + carry;

            int resultDigit = product % 16;
            carry = product / 16;

            result = hexDigit(resultDigit) + result;
        }

        while (carry > 0) {
            int resultDigit = carry % 16;
            carry /= 16;
            result = hexDigit(resultDigit) + result;
        }

        return result;
    }

    private static int hexValue(char c) {
        if (c >= '0' && c <= '9') return c - '0';
        if (c >= 'A' && c <= 'F') return c - 'A' + 10;
        if (c >= 'a' && c <= 'f') return c - 'a' + 10;
        return -1;
    }

    private static char hexDigit(int value) {
        if (value < 10) return (char) ('0' + value);
        return (char) ('A' + value - 10);
    }

    public static void main(String[] args) {
        System.out.println("AW7: " + multiplyHexDigit('A', "B608"));
    }
}
