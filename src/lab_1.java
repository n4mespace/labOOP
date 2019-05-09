import java.util.Scanner;

/**
 * C2 = 8210 % 2 = 0
 * C3 = 8210 % 3 = 2
 * C5 = 8210 % 5 = 0
 * C7 = 8210 % 7 = 6
 *
 * O1 => '+'
 * final float C = 2
 * O2 => '*'
 * float i, j
 */

public class lab_1 {
    public static void main(String[] args)
            throws ArithmeticException {

        float S = 0;       // Sum
        Scanner scanner = new Scanner(System.in);

        float i = 1;
        float j = 1;

        System.out.println("Enter n, m:");
        float n = scanner.nextFloat();   // Changeable
        float m = scanner.nextFloat();   // Changeable

        final float C = 2;

        for (; i <= n; i++)
            for (; j <= m; j++)
                S += (i * j) / (i + C);

        if ((S != Double.POSITIVE_INFINITY &&
                S != Double.NEGATIVE_INFINITY))
            System.out.println("Result S = " + Math.floor(S) +
                    ' ' + '(' + S + ')');
        else
            System.out.println("Division by zero!");
    }
}
