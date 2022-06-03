import jdk.nashorn.internal.ir.SplitReturn;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int n = 9;
        double [] X = {1.0, 1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 1.7, 1.8};
        double [][] Y = new double[n][n];

    }

    static double proterm(int i, double value, double[] x)
    {
        double pro = 1;
        for (int j = 0; j < i; j++) {
            pro = pro * (value - x[j]);
        }
        return pro;
    }
    static void dividedDiffTable(double[] x, double[][] y, int n)
    {
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n - i; j++) {
                y[j][i] = (y[j][i - 1] - y[j + 1]
                        [i - 1]) / (x[j] - x[i + j]);
            }
        }
    }
    static double Formula(double value, double[] x, double[][] y, int n)
    {
        double sum = y[0][0];
        for (int i = 1; i < n; i++) {
            sum = sum + (proterm(i, value, x) * y[0][i]);
        }
        return sum;
    }

    static void printDiffTable(double[][] y, int n)
    {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - i; j++) {
                System.out.print(String.format("%.4f",y[i][j]) + "\t");
            }
            System.out.println();

        }
    }
    static void applyFormula(double[] values, double[] x, double[][] y, int n)
    {
        dividedDiffTable(x, y, n);

        printDiffTable(y, n);

        for (double value : values)
            System.out.println("\nValue at " + value + " is " + Formula(value, x, y, n));
    }

    public static void gilbertMatrix(int n){
        double [][]H = new double[n][n+1];
        double []x = new double[n];

        for(int i = 0; i < n; i++){
            H[i][n] = 0.0;
            x[i] = 18 * (i+1);
            for(int j = 0; j < n; j++){
                H[i][j] = 1.0/(i+j+1);
            }
        }

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                H[i][n] += x[j]*H[i][j];
            }
        }
        double[] res = sqrtMethod(H);
        double sum = 0.0;
        for(int i = 0; i < n; i++){
            sum+=(res[i]-x[i]) * (res[i]-x[i]);
        }
        System.out.print("\n Результат работы метода квадратного корня \n");
        printArray(res);

        System.out.print("\n Идеальный ответ \n");
        printArray(x);

        System.out.print("\n Евклидова норма \n");
        System.out.println(Math.sqrt(sum) + "\n");
    }

    public static double[] sqrtMethod(double [][]Array) {
        int n = Array.length;
        double[][] A = new double[n][n];
        double[] B = new double[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                A[i][j] = Array[i][j];
            }
            B[i] = Array[i][n];
        }

        double[][] G = new double[n][n];
        G[0][0] = Math.sqrt(A[0][0]);
        for (int j = 1; j < n; j++) {
            G[0][j] = A[0][j] / G[0][0];
        }
        for (int i = 1; i < n; i++) {
            double sum_ii = 0.0;
            for (int k = 0; k < i; k++) {
                sum_ii += G[k][i] * G[k][i];
            }
            G[i][i] = Math.sqrt(A[i][i] - sum_ii);
            for (int j = 0; j < n; j++) {
                double sum_ij = 0.0;
                for (int k = 0; k < i; k++) {
                    sum_ij += G[k][i] * G[k][j];
                }
                if (i != j) {
                    if (i < j) {
                        G[i][j] = (A[i][j] - sum_ij) / G[i][i];
                    } else {
                        G[i][j] = 0.0;
                    }
                }
            }
        }

        double[] y = new double[n];
        for (int i = 0; i < n; i++) {
            double sum_y = 0.0;
            for (int k = 0; k < i; k++) {
                sum_y += G[k][i] * y[k];
            }
            y[i] = (B[i] - sum_y) / G[i][i];
        }

        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int k = i + 1; k < n; k++) {
                sum += G[i][k] * x[k];
            }
            x[i] = (y[i] - sum) / G[i][i];
        }
        return x;
    }



    public static double[][] inverseMatrix(double [][] matrix){
        double[][] temp = new double[matrix.length][matrix[0].length + 1];
        double[][] result = new double[matrix[0].length][matrix.length];
        for(int k = 0; k < matrix.length; k++){
            for(int i = 0; i < matrix.length; i++){
                for(int j = 0; j < matrix.length; j++){
                    temp[i][j] = matrix[i][j];
                }
                temp[i][matrix.length] = i == k ? 1 : 0;
            }
            double x[] = upTriangleMatrix(toUpTriangleMatrixSelectMain(temp));
            for(int i = 0; i < matrix.length; i++){
                result[i][k] = x[i];
            }
        }
        return result;
    }

    public static double[] upTriangleMatrix(double arr[][]) {
        int n = arr.length, m = arr[0].length;
        double[] x = new double[n];

        x[n - 1] = arr[n - 1][m - 1] / arr[n - 1][n - 1];

        for (int i = n - 2; i >= 0; i--) {
            double sum = 0.0;
            for (int j = m - 2; j >= i; j--) {
                sum += arr[i][j] * x[j];
            }
            x[i] = (arr[i][m - 1] - sum) / arr[i][i];
        }
        return x;
    }

    public static double[][] toUpTriangleMatrixSelectMain(double arr[][]){
        int size = arr.length;
        for(int i = 0; i < size-1; i++){
            double maxCoef = arr[i][i];
            int maxLine = i;
            for(int k = i+1; k < size; k++){
                if(Math.abs(maxCoef) < Math.abs(arr[k][i])){
                    maxLine = k;
                    maxCoef = arr[k][i];
                }
            }
            for(double a : arr[maxLine]){
                a /= maxCoef;
            }
            double temp[];
            temp = arr[maxLine];
            arr[maxLine] = arr[i];
            arr[i] = temp;
            for(int k = i+1; k < arr.length; k++) {
                if (arr[k][i] != 0.0) {
                    double coef = arr[i][i] / arr[k][i];
                    for (int j = i; j < arr[0].length; j++) {
                        arr[k][j] -= arr[i][j] / coef;
                    }
                }
            }
        }
        return arr;
    }

    public static double[][] toUpTriangleMatrixForward(double arr[][]) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int k = i + 1; k < arr.length; k++) {
                if (arr[k][i] != 0.0) {
                    double coef = arr[i][i] / arr[k][i];
                    for (int j = i; j < arr[0].length; j++) {
                        arr[k][j] -= arr[i][j] / coef;
                    }
                }
            }
        }
        return arr;
    }

    public static double det(double[][] matrix){
        matrix = toUpTriangleMatrixForward(matrix);
        double result = 1.0;
        for(int i = 0; i < matrix.length; i++){
            result*=matrix[i][i];
        }
        return result;
    }

    public static double[][] readMatrix(String path) {
        File file = new File(path);
        Scanner in = null;
        try {
            in = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int n = in.nextInt(), m = in.nextInt();

        double array[][] = new double[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                array[i][j] = Double.parseDouble(in.next());
            }
        }
        return array;
    }





    public static void printMatrix(double [][] matrix){
        for(int i = 0; i < matrix.length; i++){
                for(int j = 0; j < matrix[0].length; j++){
                    System.out.print(String.format("%.2f",matrix[i][j]) + " ");
                }
                System.out.println();

        }
    }

    public static void printArray(double [] array){
        for(int i = 0; i < array.length; i++){
            System.out.print(String.format("%.2f",array[i]) + " ");
        }
    }
}
