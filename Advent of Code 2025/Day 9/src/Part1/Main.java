package Part1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File("./puzzle.txt"));
        List<int[]> redTiles = new ArrayList<>();
        while (input.hasNextLine()) {
            String[] line = input.nextLine().split(",");
            redTiles.add(new int[] {Integer.parseInt(line[0]), Integer.parseInt(line[1])});
        }

        long maxArea = 0;
        for (int i = 0; i < redTiles.size(); i++) {
            for (int j = i + 1; j < redTiles.size(); j++) {
                int[] a = redTiles.get(i);
                int[] b = redTiles.get(j);

                long area = (long) (Math.abs(a[0] - b[0]) + 1) * (Math.abs(a[1] - b[1]) + 1);
                if (area > maxArea) {
                    maxArea = area;
                }
            }
        }
        System.out.println(maxArea);
    }
}