package Part1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    static Map<String, Integer> memo = new HashMap<>();
    static int find(Map<String, List<String>> graph, String value) {
        if(value.equals("out")) return 1;
        if(memo.containsKey(value)) return memo.get(value);

        int total = 0;
        for(String next : graph.get(value)) {
            total += find(graph, next);
        }

        memo.put(value, total);
        return total;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File("./puzzle.txt"));
        Map<String, List<String>> graph = new HashMap<>();
        while (input.hasNextLine()) {
            String line = input.nextLine();
            int endIn = line.indexOf(':');
            String in = line.substring(0, endIn);
            String[] outs = line.substring(endIn + 2).split(" ");
            graph.put(in, Arrays.asList(outs));
        }

        int result = find(graph, "you");
        System.out.println(result);
    }
}