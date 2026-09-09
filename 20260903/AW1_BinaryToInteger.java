public class AW1_BinaryToInteger {

    // AW 1: binary string -> integer
    public static int binaryToInt(String s) {
        int value = 0;

        for (int i = 0; i < s.length(); i++) {
            int bit = s.charAt(i) - '0';
            value = value * 2 + bit;
        }

        return value;
    }

    public static void main(String[] args) {
        System.out.println("AW1: " + binaryToInt("11111111"));
    }
}
