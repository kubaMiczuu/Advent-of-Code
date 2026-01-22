package Day7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day7 {
    static void main() throws FileNotFoundException {
        Scanner input = new Scanner(new File("Advent of Code 2025/Day7/puzzle.txt"));
        List<List<Character>> puzzle = new ArrayList<>();
        while (input.hasNextLine()) {
            String line = input.nextLine();
            List<Character> sign = new ArrayList<>();
            for (int i = 0; i < line.length(); i++) {
                char ch = line.charAt(i);
                sign.add(ch);
            }
            puzzle.add(sign);
        }
        input.close();

        int rows = puzzle.size();
        int cols = puzzle.get(rows - 1).size();
        long[][] memo = new long[rows][cols];
        for (int i = 0; i < rows; i++) {
            Arrays.fill(memo[i], -1);
        }

        int startCol = puzzle.getFirst().indexOf('S');
        long result = splitBeam(puzzle, 1, startCol, memo);
        System.out.println(result);

        for(int i=0; i<memo.length;i++) {
            for(int j=0; j<memo[i].length;j++) {
                System.out.print(memo[i][j] + " ");
            }
            System.out.println();
        }
    }
    public static long splitBeam(List<List<Character>> puzzle, int row, int col, long[][] memo) {
        int i = row;
        int j = col;

        while (true) {
            if (i >= puzzle.size() || j < 0 || j >= puzzle.get(i).size()) {
                return 1;
            }

            if (memo[i][j] != -1) return memo[i][j];

            char ch = puzzle.get(i).get(j);

            if (ch == '^') {
                long left = splitBeam(puzzle, i + 1, j - 1, memo);
                long right = splitBeam(puzzle, i + 1, j + 1, memo);
                memo[i][j] = left + right;
                return memo[i][j];
            }
            i++;
        }
    }
}
