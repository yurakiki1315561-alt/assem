public class AW2_HexToInteger {

    // AW 2: hexadecimal string -> integer
    public static int hexToInt(String s) {
        int value = 0;

        for (int i = 0; i < s.length(); i++) {
            int digit = hexValue(s.charAt(i));
            value = value * 16 + digit;
        }

        return value;
    }

    private static int hexValue(char c) {
        if (c >= '0' && c <= '9') return c - '0';
        if (c >= 'A' && c <= 'F') return c - 'A' + 10;
        if (c >= 'a' && c <= 'f') return c - 'a' + 10;
        return -1;
    }

    public static void main(String[] args) {
        System.out.println("AW2: " + hexToInt("FFFF"));
    }
}
