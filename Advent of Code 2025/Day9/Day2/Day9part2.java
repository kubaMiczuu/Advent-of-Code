package Day9.Day2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day9part2 {
    static void main() throws FileNotFoundException {
        // ===== LOAD RED TILES =====
        Scanner input = new Scanner(new File("Advent of Code 2025/Day9/puzzle.txt"));
        List<int[]> redList = new ArrayList<>();

        while (input.hasNextLine()) {
            String[] line = input.nextLine().trim().split(",");
            int x = Integer.parseInt(line[0]);
            int y = Integer.parseInt(line[1]);
            redList.add(new int[]{x, y});
        }
        input.close();

        int n = redList.size();

        Set<Integer> xSet = new HashSet<>();
        Set<Integer> ySet = new HashSet<>();
        for (int[] p : redList) {
            xSet.add(p[0]);
            ySet.add(p[1]);
        }

        int[] xs = xSet.stream().mapToInt(Integer::intValue).toArray();
        int[] ys = ySet.stream().mapToInt(Integer::intValue).toArray();
        Arrays.sort(xs);
        Arrays.sort(ys);

        int nx = xs.length;
        int ny = ys.length;

        // Map original coordinate -> index in xs/ys
        Map<Integer, Integer> xIndex = new HashMap<>();
        Map<Integer, Integer> yIndex = new HashMap<>();
        for (int i = 0; i < nx; i++) xIndex.put(xs[i], i);
        for (int i = 0; i < ny; i++) yIndex.put(ys[i], i);

        // ===== POINT-IN-POLYGON (ray casting) =====
        java.util.function.BiPredicate<Integer, Integer> insidePolygon = (xx, yy) -> {
            boolean inside = false;
            int m = redList.size();
            for (int i = 0, j = m - 1; i < m; j = i++) {
                int xi = redList.get(i)[0], yi = redList.get(i)[1];
                int xj = redList.get(j)[0], yj = redList.get(j)[1];

                boolean intersect =
                        ((yi > yy) != (yj > yy)) &&
                                (xx < (double)(xj - xi) * (yy - yi) / (double)(yj - yi) + xi);

                if (intersect) inside = !inside;
            }
            return inside;
        };

        // ===== BUILD INSIDE-CELL GRID =====
        // Cells are between xs[i]..xs[i+1], ys[j]..ys[j+1]
        int cellNx = nx - 1;
        int cellNy = ny - 1;
        int[][] inside = new int[cellNx][cellNy];

        for (int i = 0; i < cellNx; i++) {
            int cx = (xs[i] + xs[i + 1]) / 2;
            for (int j = 0; j < cellNy; j++) {
                int cy = (ys[j] + ys[j + 1]) / 2;
                if (insidePolygon.test(cx, cy)) {
                    inside[i][j] = 1;
                } else {
                    inside[i][j] = 0;
                }
            }
        }

        // ===== PREFIX SUM OVER INSIDE GRID =====
        int[][] pref = new int[cellNx + 1][cellNy + 1];
        for (int i = 0; i < cellNx; i++) {
            int rowSum = 0;
            for (int j = 0; j < cellNy; j++) {
                rowSum += inside[i][j];
                pref[i + 1][j + 1] = pref[i][j + 1] + rowSum;
            }
        }

        // Helper to get sum of inside-cells in [ix1, ix2-1] x [iy1, iy2-1]
        java.util.function.IntBinaryOperator getInsideSum = (ix1, iy1) -> 0; // dummy to satisfy compiler
        // We'll use a small lambda-like helper via method:
        class RectSum {
            int sum(int ix1, int iy1, int ix2, int iy2) {
                // ix2, iy2 are exclusive
                return pref[ix2][iy2] - pref[ix1][iy2] - pref[ix2][iy1] + pref[ix1][iy1];
            }
        }
        RectSum rectSum = new RectSum();

        // ===== FIND LARGEST RECTANGLE =====
        int maxArea = 0;

        for (int i = 0; i < n; i++) {
            int[] a = redList.get(i);
            int ax = a[0], ay = a[1];

            for (int j = i + 1; j < n; j++) {
                int[] b = redList.get(j);
                int bx = b[0], by = b[1];

                // Opposite corners must differ in both axes
                if (ax == bx || ay == by) continue;

                int x1 = Math.min(ax, bx);
                int x2 = Math.max(ax, bx);
                int y1 = Math.min(ay, by);
                int y2 = Math.max(ay, by);

                // Map to compressed indices
                Integer ix1Obj = xIndex.get(x1);
                Integer ix2Obj = xIndex.get(x2);
                Integer iy1Obj = yIndex.get(y1);
                Integer iy2Obj = yIndex.get(y2);
                if (ix1Obj == null || ix2Obj == null || iy1Obj == null || iy2Obj == null) continue;

                int ix1 = ix1Obj;
                int ix2 = ix2Obj;
                int iy1 = iy1Obj;
                int iy2 = iy2Obj;

                // If rectangle spans no cells, skip
                if (ix2 <= ix1 || iy2 <= iy1) continue;

                int cellsX = ix2 - ix1;
                int cellsY = iy2 - iy1;
                int totalCells = cellsX * cellsY;

                int insideCells = rectSum.sum(ix1, iy1, ix2, iy2);
                if (insideCells != totalCells) continue; // not fully inside polygon

                // Rectangle is valid; compute area in tiles
                long width = (long) x2 - x1 + 1;
                long height = (long) y2 - y1 + 1;
                long area = width * height;

                if (area > maxArea) {
                    maxArea = (int) area;
                }
            }
        }

        System.out.println("Largest rectangle area = " + maxArea);
    }
}
