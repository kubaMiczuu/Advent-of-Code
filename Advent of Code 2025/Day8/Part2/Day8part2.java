package Day8.Part2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day8part2 {

    public static int[] parent;
    public static int[] rank;

    public static int find(int x) {
        if(parent[x] != x) parent[x] = find(parent[x]);
        return parent[x];
    }

    public static boolean union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);
        if(rootA == rootB) return false;

        if(rank[rootA] < rank[rootB]) parent[rootA] = rootB;
        else if(rank[rootA] > rank[rootB]) parent[rootB] = rootA;
        else {
            parent[rootB] = rootA;
            rank[rootA]++;
        }
        return true;
    }

    static void main() throws FileNotFoundException {
        Scanner input = new Scanner(new File("Advent of Code 2025/Day8/puzzle.txt"));

        List<Integer> xCord = new ArrayList<>();
        List<Integer> yCord = new ArrayList<>();
        List<Integer> zCord = new ArrayList<>();

        while (input.hasNextLine()) {
            String line = input.nextLine();
            String[] tokens = line.split(",");
            xCord.add(Integer.parseInt(tokens[0]));
            yCord.add(Integer.parseInt(tokens[1]));
            zCord.add(Integer.parseInt(tokens[2]));
        }
        input.close();

        int n = xCord.size();
        List<double[]> edges = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                double dx = xCord.get(j) - xCord.get(i);
                double dy = yCord.get(j) - yCord.get(i);
                double dz = zCord.get(j) - zCord.get(i);
                double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
                edges.add(new double[]{distance, i, j});
            }
        }
        edges.sort(Comparator.comparingDouble(a -> a[0]));

        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;

        int components = n;
        long result = -1;
        for(double[] e: edges) {
            int a = (int) e[1];
            int b =  (int) e[2];
            if(union(a,b)) {
                components--;
                if(components==1) {
                    result = (long) xCord.get(a)*xCord.get(b);
                    break;
                }
            }
        }
        System.out.println(result);
    }
}
