import static java.lang.Math.abs;

public class LastMethod
{
    static double[] Gauss(double[][] A, double[] B, int n) {
        //Прямой ход
        for (int p = 0; p < n; ++p) {
            if (abs(A[p][p]) <= 1e-10) {
                System.out.println("Error: Нулевой коэффициент");
                return new double[n];
            }
            for (int i = p + 1; i < n; ++i) {
                double alpha = A[i][p] / A[p][p];
                B[i] -= alpha * B[p];
                for (int j = p; j < n; ++j) {
                    A[i][j] -= alpha * A[p][j];
                }
            }
        }
        if (A == new double[n][n]) {
            return new double[n];
        }
        //Обратный ход
        double[] X = new double[n];
        double sum = 0.;
        for (int i = n - 1; i >= 0; --i) {
            sum = 0.;
            for (int j = i + 1; j < n; ++j) {
                sum += A[i][j] * X[j];
            }
            X[i] = (B[i] - sum) / A[i][i];
        }

        return X;
    }
    static void smoothing_least_squares(double[] values, double[] x, double[] y, int n, int m) {
        double[][] alpha = new double[m][m];
        double[] beta = new double[m];

        double sum = 0.;

        for (int j = 0; j < m; ++j) {
            for (int k = 0; k < m + 1; ++k) {
                sum = 0.;
                if (k == m) {
                    for (int i = 0; i < n; ++i) {
                        sum += Math.pow(x[i], j) * y[i];
                    }
                    beta[j] = sum;
                } else {
                    for (int i = 0; i < n; ++i) {
                        sum += Math.pow(x[i], j + k);
                    }
                    alpha[j][k] = sum;
                }
            }
        }
        double[] a = new double[m];
        a = Gauss(alpha, beta, m);
        for (double value : values) {
            sum = 0.;
            for (int k = 0; k < m; ++k) {
                sum += a[k] * Math.pow(value, k);
            }
            System.out.print("\nValue at " + value + " is " + sum);
        }
    }

    public static void main(String[] args) {
        int n = 6; int m = 5;
        double[] x = {0.1, 0.15, 0.19, 0.25, 0.28, 0.30};
        double[] y_3 = { 1.1052, 1.1618, 1.2092, 1.2840, 1.3231, 1.3499 };
        double[] values = { 0.13, 0.18, 0.20, 0.27 };

        smoothing_least_squares(values, x, y_3, n, m);
    }
}
