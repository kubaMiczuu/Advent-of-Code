package RotatingCalipers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import static java.lang.Math.abs;

public class Main {
    static long cross(int[] left, int[] middle, int[] right) {
        long x1 = middle[0] - left[0];
        long y1 = middle[1] - left[1];
        long x2 = right[0] - middle[0];
        long y2 = right[1] - middle[1];
        return x1*y1-x2*y2;
    }

    static List<int[]> hull = new ArrayList<>();
    static List<int[]> makeConvexHull(List<int[]> redTiles) {
        List<int[]> lowerHull = new ArrayList<>();
        for (int[] p : redTiles) {
            while(lowerHull.size() >= 2 && cross(lowerHull.get(lowerHull.size()-2), lowerHull.get(lowerHull.size()-1),p) <= 0) {
                lowerHull.remove(lowerHull.size()-1);
            }
            lowerHull.add(p);
        }
        List<int[]> upperHull = new ArrayList<>();
        for(int i = redTiles.size()-1; i >= 0; i--) {
            int[] p = redTiles.get(i);
            while(upperHull.size() >= 2 && cross(upperHull.get(upperHull.size()-2), upperHull.get(upperHull.size()-1),p) <= 0) {
                upperHull.remove(upperHull.size()-1);
            }
            upperHull.add(p);
        }
        lowerHull.remove(lowerHull.size()-1);
        upperHull.add(lowerHull.get(lowerHull.size()-1));
        hull.addAll(lowerHull);
        hull.addAll(upperHull);
        return hull;
    }

    static long distance(int[] a, int[] b) {
        long dx = a[0] - b[0];
        long dy = a[1] - b[1];
        return dx*dx + dy*dy;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File("./puzzle.txt"));
        List<int[]> redTiles = new ArrayList<>();
        while (input.hasNextLine()) {
            String[] line = input.nextLine().split(",");
            redTiles.add(new int[] {Integer.parseInt(line[0]), Integer.parseInt(line[1])});
        }

        redTiles.sort(Comparator.comparing(a -> a[0]));
        redTiles.sort(Comparator.comparing(a -> a[1]));

        hull = makeConvexHull(redTiles);
        int n = hull.size();
        long maxDist = 0;
        int[] bestA = null;
        int[] bestB = null;
        int j = 1;

        for (int i = 0; i < n; i++) {
            int nextI = (i+1)%n;
            while(true) {
                int nextJ = (j+1)%n;
                long d1 = distance(hull.get(i), hull.get(j));
                long d2 = distance(hull.get(i), hull.get(nextJ));
                if(d1 > d2) {
                    j = nextJ;
                } else break;
            }
            long currDistance =  distance(hull.get(i), hull.get(j));
            if(currDistance > maxDist) {
                maxDist = currDistance;
                bestA = hull.get(i);
                bestB = hull.get(j);
            }
        }
        System.out.println("Furhest points are: ["+bestA[0]+" "+bestA[1]+"] and ["+bestB[0]+" "+bestB[1]+"], with a distance of: "+maxDist);
    }
}