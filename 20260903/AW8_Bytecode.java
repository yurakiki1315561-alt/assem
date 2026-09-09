public class Q8_Bytecode {

    static int Y;
    static int X = (Y + 4) * 3;

    public static void main(String[] args) {
        System.out.println("Y = " + Y);
        System.out.println("X = " + X);
    }
}
