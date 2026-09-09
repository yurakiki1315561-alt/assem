public class AW5_AddBase {

    // AW 5: add two digit strings in base 2..10
    public static String addBase(String a, String b, int base) {
        int i = a.length() - 1;
        int j = b.length() - 1;
        int carry = 0;
        String result = "";

        while (i >= 0 || j >= 0 || carry != 0) {
            int x = 0;
            int y = 0;

            if (i >= 0) {
                x = a.charAt(i) - '0';
                i--;
            }

            if (j >= 0) {
                y = b.charAt(j) - '0';
                j--;
            }

            int sum = x + y + carry;
            int digit = sum % base;
            carry = sum / base;

            result = (char) ('0' + digit) + result;
        }

        return result;
    }

    public static void main(String[] args) {
        System.out.println("AW5: " + addBase("101101", "10011", 2));
    }
}
