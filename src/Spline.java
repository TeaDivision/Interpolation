public class Spline {
    static double[] Tridiagonal_matrix_algorithm(double[] h, double[] y, int n) {
        double[] A = new double[n], B = new double[n], C = new double[n], F = new double[n], M = new double[n];

        B[0] = 2;
        C[0] = 1;
        F[0] = 3.3722;
        for (int i = 1; i < n - 1; ++i) {
            A[i] = h[i] / 6;
            B[i] = (h[i] + h[i + 1]) / 3;
            C[i] = h[i + 1] / 6;
            F[i] = (y[i + 1] - y[i]) / h[i + 1] - (y[i] - y[i - 1]) / h[i];
        }
        A[n - 1] = 0.5;
        B[n - 1] = 2;
        F[n - 1] = 3.3614;

        //прямой ход
        double[] alpha = new double[n], beta = new double[n];
        alpha[1] = -C[0] / B[0];
        beta[1] = F[0] / B[0];
        for (int i = 1; i < n - 1; ++i) {
            alpha[i + 1] = -C[i] / (A[i] * alpha[i] + B[i]);
            beta[i + 1] = (F[i] - A[i] * beta[i]) / (A[i] * alpha[i] + B[i]);
        }
        //обратный ход
        M[n - 1] = (F[n - 1] - A[n - 1] * beta[n - 1]) / (B[n - 1] + A[n - 1] * alpha[n - 1]);
        for (int i = n - 2; i >= 0; --i) {
            M[i] = alpha[i + 1] * M[i + 1] + beta[i + 1];
        }
        for (double elem : M)
            System.out.println(elem + " ");

        return M;
    }
    static void Spline_interpolation(double[] values, double[] y, double[] x, int n) {
        double[] h = new double[n];
        double[] M = new double[n];
        for (int i = 1; i < n; ++i) {
            h[i] = x[i] - x[i - 1];
        }

        M = Tridiagonal_matrix_algorithm(h, y, n);

        for (double value : values) {
            int i = 1;
            while (x[i - 1] < value) {
                ++i;
            }
            --i;

            double result = 0.;
            result = M[i - 1] * Math.pow(x[i] - value, 3) / (6 * h[i]) +
                    M[i] * Math.pow(value - x[i - 1], 3) / (6 * h[i]) +
                    (y[i - 1] - M[i - 1] * h[i] * h[i] / 6) * (x[i] - value) / h[i] +
                    (y[i] - M[i] * h[i] * h[i] / 6) * (value - x[i - 1]) / h[i];

            System.out.print("\nValue at " + value + " is " + result);
        }
    }

    public static void main(String[] args) {
        int n_s = 6;
        double[] x_s = {0.1, 0.15, 0.19, 0.25, 0.28, 0.30};
        double[] y_s = { 1.1052, 1.1618, 1.2092, 1.2840, 1.3231, 1.3499 };
        double[] values = { 0.13, 0.18, 0.20, 0.27 };
        Spline_interpolation(values, y_s, x_s, n_s);
    }
}
