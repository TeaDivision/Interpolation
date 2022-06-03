// Java program for implementing
// Newton divided difference formula
import java.text.*;
import java.math.*;

class GFG{
    // Function to find the product term
    static double proterm(int i, double value, double x[])
    {
        double pro = 1;
        for (int j = 0; j < i; j++) {
            pro = pro * (value - x[j]);
        }
        return pro;
    }

    // Function for calculating
// divided difference table
    static void dividedDiffTable(double x[], double y[][], int n)
    {
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n - i; j++) {
                y[j][i] = (y[j][i - 1] - y[j + 1]
                        [i - 1]) / (x[j] - x[i + j]);
            }
        }
    }

    // Function for applying Newton's
// divided difference formula
    static double applyFormula(double value, double x[],
                              double y[][], int n)
    {
        double sum = y[0][0];

        for (int i = 1; i < n; i++) {
            sum = sum + (proterm(i, value, x) * y[0][i]);
        }
        return sum;
    }

    // Function for displaying
// divided difference table
    static void printDiffTable(double y[][],int n)
    {
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.HALF_UP);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - i; j++) {
                String str1 = df.format(y[i][j]);
                System.out.print(str1+"\t ");
            }
            System.out.println("");
        }
    }

    // Driver Function
    public static void main(String[] args)
    {
        // number of inputs given
        double []values = {1.41,   1.42,  1.43, 1.44, 1.45, 1.46};
        double sum;
        int n = 9;
        double [] x = {1.0, 1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 1.7, 1.8};
        double [][] y = new double[n][n];

        // y[][] is used for divided difference
        // table where y[][0] is used for input
        //
        y[0][0] = 1.1752;
        y[1][0] = 1.3356;
        y[2][0] = 1.5094;
        y[3][0] = 1.6983;
        y[4][0] = 1.9043;
        y[5][0] = 2.1292;
        y[6][0] = 2.3755;
        y[7][0] = 2.6456;
        y[8][0] = 2.9421;

        // calculating divided difference table
        dividedDiffTable(x, y, n);

        // displaying divided difference table
        printDiffTable(y,n);

        // printing the value
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_UP);
        for(double value: values) {
            System.out.print("\nValue at " + df.format(value) + " is "
                    + df.format(applyFormula(value, x, y, n)));
        }
    }
}
// This code is contributed by mits
