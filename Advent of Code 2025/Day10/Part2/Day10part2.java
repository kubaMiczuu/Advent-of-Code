package Day10.Part2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day10part2 {
    static double[][] GaussJordanElimination(double[][] A) {
        int n = A.length;
        int m = A[0].length - 1;

        //Gaussian-Jordan elimination
        int r = 0;
        for (int c = 0; c < m && r < n; c++) {

            //Finding a pivot
            int pivot = r;
            while (pivot < n && Math.abs(A[pivot][c]) < 1e-9) pivot++;
            if (pivot == n) continue;

            //Swap the pivot col
            double[] tmp = A[r];
            A[r] = A[pivot];
            A[pivot] = tmp;

            //Normalizing pivot
            double div = A[r][c];
            for (int j = c; j <= m; j++) A[r][j] /= div;

            //Eliminating
            for (int i = 0; i < n; i++) {
                if (i == r) continue;
                double factor = A[i][c];
                if (Math.abs(factor) < 1e-9) continue;
                for (int j = c; j <= m; j++) A[i][j] -= factor * A[r][j];
            }

            r++;
        }

        return A;
    }


    static  int[] computeX(double[] base, double[][] coef, int... t) {
        int m = base.length;
        int freeCount = coef.length;

        int[] x = new int[m];

        for (int i = 0; i < m; i++) {
            double val = base[i];

            for (int f = 0; f < freeCount; f++) {
                val += coef[f][i] * t[f];
            }

            if (Math.abs(val - Math.round(val)) > 1e-9) return null;
            int iv = (int)Math.round(val);
            if (iv < 0) return null;

            x[i] = iv;
        }

        return x;
    }

    static int sumArray(int[] arr) {
        int s = 0;
        for (int v : arr) s += v;
        return s;
    }

    static int[] solveMachine(double[][] A, boolean[] isPivot, int[] pivotRow) {
        int m = isPivot.length;

        List<Integer> freeVars = new ArrayList<>();
        for (int col = 0; col < m; col++) {
            if (!isPivot[col]) freeVars.add(col);
        }

        int freeCount = freeVars.size();

        if (freeCount == 0) {
            int[] x = new int[m];
            for (int col = 0; col < m; col++) {
                int row = pivotRow[col];
                x[col] = (int)Math.round(A[row][m]);
            }
            return x;
        }

        double[] base = new double[m];
        double[][] coef = new double[freeCount][m];

        for (int col = 0; col < m; col++) {
            if (!isPivot[col]) {
                int idx = freeVars.indexOf(col);
                base[col] = 0;
                coef[idx][col] = 1;
                continue;
            }

            int row = pivotRow[col];
            base[col] = A[row][m];

            for (int f = 0; f < freeCount; f++) {
                int fv = freeVars.get(f);
                coef[f][col] = -A[row][fv];
            }
        }

        int RANGE = 200;
        int[] bestX = null;
        int bestSum = Integer.MAX_VALUE;

        if (freeCount == 1) {
            for (int t1 = -RANGE; t1 <= RANGE; t1++) {
                int[] x = computeX(base, coef, t1);
                if (x == null) continue;
                int sum = sumArray(x);
                if (sum < bestSum) {
                    bestSum = sum;
                    bestX = x;
                }
            }
        }

        else if (freeCount == 2) {
            for (int t1 = -RANGE; t1 <= RANGE; t1++) {
                for (int t2 = -RANGE; t2 <= RANGE; t2++) {
                    int[] x = computeX(base, coef, t1, t2);
                    if (x == null) continue;
                    int sum = sumArray(x);
                    if (sum < bestSum) {
                        bestSum = sum;
                        bestX = x;
                    }
                }
            }
        }

        else if (freeCount == 3) {
            for (int t1 = -RANGE; t1 <= RANGE; t1++) {
                for (int t2 = -RANGE; t2 <= RANGE; t2++) {
                    for (int t3 = -RANGE; t3 <= RANGE; t3++) {
                        int[] x = computeX(base, coef, t1, t2, t3);
                        if (x == null) continue;
                        int sum = sumArray(x);
                        if (sum < bestSum) {
                            bestSum = sum;
                            bestX = x;
                        }
                    }
                }
            }
        }

        return bestX;
    }

    static void main() throws FileNotFoundException {
        Scanner input = new Scanner(new File("Advent of Code 2025/Day10/puzzle.txt"));

        List<List<Integer>> powerLevels = new ArrayList<>();
        List<List<Integer>> targetPowerLevels = new ArrayList<>();
        List<List<List<Integer>>> buttons = new ArrayList<>();

        while (input.hasNextLine()) {
            String line = input.nextLine();

            List<Integer> power = new ArrayList<>();
            List<Integer> targetPower = new ArrayList<>();
            int startPowers = line.indexOf('{')+1;
            int endPowers = line.indexOf('}');
            String[] powers = line.substring(startPowers, endPowers).split(",");
            for (int i = 0; i < powers.length; i++) {
                targetPower.add(Integer.parseInt(powers[i]));
                power.add(0);
            }
            powerLevels.add(power);
            targetPowerLevels.add(targetPower);

            int startButtons = line.indexOf("(");
            int endButtons = line.indexOf("{") - 1;
            String allButtons = line.substring(startButtons, endButtons);
            List<List<Integer>> tempButtons = new ArrayList<>();
            List<Integer> current = null;
            for (char c : allButtons.toCharArray()) {
                if (c == '(') {
                    current = new ArrayList<>();
                }
                else if (Character.isDigit(c)) {
                    current.add(c - '0');
                }
                else if (c == ')') {
                    tempButtons.add(current);
                    current = null;
                }
            }
            buttons.add(tempButtons);
        }
        input.close();

        int totalPresses = 0;

        for (int i = 0; i < targetPowerLevels.size(); i++) {
            int n = targetPowerLevels.get(i).size();
            int m = buttons.get(i).size();

            int[] t = new int[n];
            int[] x = new int[m];
            for (int j = 0; j < n; j++) {
                t[j] = targetPowerLevels.get(i).get(j);
            }

            int[][] M = new int[n][m];
            for (int col = 0; col < m; col++) {
                for (int row : buttons.get(i).get(col)) {
                    M[row][col] = 1;
                }
            }

            double[][] A = new double[n][m+1];
            for(int j = 0; j < n; j++) {
                for(int k = 0; k < m; k++) {
                    A[j][k] = M[j][k];
                }
                A[j][m] = t[j];
            }

            A = GaussJordanElimination(A);
            boolean[] isPivot = new boolean[m];
            int[] pivotRow = new int[m];
            Arrays.fill(pivotRow, -1);

            for (int row = 0; row < n; row++) {
                int pivotCol = -1;
                for (int col = 0; col < m; col++) {
                    if (Math.abs(A[row][col] - 1.0) < 1e-9) {
                        pivotCol = col;
                        break;
                    }
                    if (Math.abs(A[row][col]) > 1e-9) break;
                }
                if (pivotCol != -1) {
                    isPivot[pivotCol] = true;
                    pivotRow[pivotCol] = row;
                }
            }

            int[] solution = solveMachine(A, isPivot, pivotRow);
            if (solution == null) {
                System.out.println("Machine " + i + " is impossible. Base/coef:");
                System.out.println("A row: " + Arrays.toString(A[pivotRow[0]]));

                continue;
            }

            int presses = sumArray(solution);
            totalPresses += presses;
        }
        System.out.println("Total presses: " + totalPresses);
    }
}
