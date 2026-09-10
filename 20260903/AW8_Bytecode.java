public class Q8_Bytecode {

    static int Y;
    static int X = (Y + 4) * 3;

    public static void main(String[] args) {
        System.out.println("Y = " + Y);
        System.out.println("X = " + X);
    }
}
Compiled from "Calculator.java"
public class Calculator {
  public Calculator();
    Code:
       0: aload_0         // this 참조값을 피연산자 스택(Operand Stack)에 올림
       1: invokespecial #1 // Object 클래스의 기본 생성자<init>() 호출
       4: return          // 생성자 실행 종료 및 반환

  public void calculate();
    Code:
       0: iconst_5        // 정수 상수 5를 피연산자 스택에 푸시
       1: istore_1        // 스택 top의 값(5)을 지역 변수 1번(Y)에 저장

       2: iload_1         // 지역 변수 1번(Y)의 값(5)을 피연산자 스택에 로드
       3: iconst_4        // 정수 상수 4를 피연산자 스택에 푸시
       4: iadd            // 스택에서 두 정수(Y와 4)를 꺼내 더한 후 결과(9)를 스택에 푸시: (Y + 4)

       5: iconst_3        // 정수 상수 3을 피연산자 스택에 푸시
       6: imul            // 스택에서 두 정수(9와 3)를 꺼내 곱한 후 결과(27)를 스택에 푸시: (Y + 4) * 3

       7: istore_2        // 스택 top의 최종 계산 결과(27)를 지역 변수 2번(X)에 저장

       8: return          // calculate() 메서드 실행 종료 및 반환
}
