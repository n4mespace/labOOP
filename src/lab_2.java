import java.util.Scanner;

/**
 * C5  = 8210 % 5  = 0 => C = a * B;
 * C7  = 8210 % 7  = 6 => char;
 * C11 = 8210 % 11 = 4 => sum( min() + max() )
 */

public class lab_2 {
    static char matrix[][] = { {'a', 'b', 'c', 'd'},
                               {'i', 'r', 'p', 'e'},
                               {'r', 'n', 'm', 'f'},
                               {'t', 's', 'k', 'g'} };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter one single char: \n");
        char B = scanner.next().charAt(0);
        final int n = matrix.length;
        final int m = matrix[0].length;

        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                matrix[i][j] *= B;

        // Print new matrix
        for (char[] line : matrix) {
            for (char elem : line) {
                System.out.print(elem + " ");
            }
            System.out.println();
        }

        char max = '\uffff'; // Max value of char = 65,535
        char min = '\u0000'; // Min value of char = 0
        char sum = '\u0000';

        // Calculating sum( min() + max() )
        // min for even, max for odd
        for (char[] matrix1 : matrix) {
            min = matrix1[0];
            max = matrix1[1];

            for (int j = 2; j < m; j += 2) {
                if (matrix1[j] < min) {
                    min = matrix1[j];
                }
            }

            for (int k = 3; k < m; k += 2) {
                if (matrix1[k] > max) {
                    max = matrix1[k];
                }
            }
            sum += min + max;
        }
        System.out.println("The sum of odd max and even min of each line :\n" + sum);
    }
}
