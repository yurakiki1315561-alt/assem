public class UnsignedBinarySubtract {

    public static String subtract(String a, String b) {
        int i = a.length() - 1;
        int j = b.length() - 1;
        int borrow = 0;
        String result = "";

        while (i >= 0) {
            int x = a.charAt(i) - '0' - borrow;
            int y = (j >= 0) ? b.charAt(j) - '0' : 0;

            if (x < y) {
                x += 2;
                borrow = 1;
            } else {
                borrow = 0;
            }

            int digit = x - y;
            result = (char) ('0' + digit) + result;

            i--;
            j--;
        }

        // Remove unnecessary leading zeros, but keep one zero if needed.
        int k = 0;
        while (k < result.length() - 1 && result.charAt(k) == '0') {
            k++;
        }

        return result.substring(k);
    }

    public static void main(String[] args) {
        System.out.println("10001000 - 00000101 = "
                + subtract("10001000", "00000101"));

        System.out.println("11010110 - 00010001 = "
                + subtract("11010110", "00010001"));

        System.out.println("11110000 - 00101101 = "
                + subtract("11110000", "00101101"));
    }
}
