public class AW6_AddHex {

    // AW 6: add hexadecimal strings
    public static String addHex(String a, String b) {
        int i = a.length() - 1;
        int j = b.length() - 1;
        int carry = 0;
        String result = "";

        while (i >= 0 || j >= 0 || carry != 0) {
            int x = 0;
            int y = 0;

            if (i >= 0) {
                x = hexValue(a.charAt(i));
                i--;
            }

            if (j >= 0) {
                y = hexValue(b.charAt(j));
                j--;
            }

            int sum = x + y + carry;
            int digit = sum % 16;
            carry = sum / 16;

            result = hexDigit(digit) + result;
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
        System.out.println("AW6: " + addHex("6B4", "3FE"));
    }
}
