package Day11.Part2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day11part2 {
    static Map<String, Long> memo = new HashMap<>();
    static long find(Map<String, List<String>> graph, String value, boolean seenFft, boolean seenDac) {
        if (value.equals("fft")) seenFft = true;
        if (value.equals("dac")) seenDac = true;
        if(value.equals("out")) {
            return (seenFft && seenDac) ? 1 : 0;
        }

        String key = value + "|" + seenFft + "|" + seenDac;
        if(memo.containsKey(key)) return memo.get(key);

        long total = 0;
        for(String next : graph.get(value)) {
            total += find(graph, next, seenFft, seenDac);
        }

        memo.put(key, total);
        return total;
    }

    static void main() throws FileNotFoundException {
        Scanner input = new Scanner(new File("Advent of Code 2025/Day11/puzzle.txt"));
        Map<String, List<String>> graph = new HashMap<>();
        while (input.hasNextLine()) {
            String line = input.nextLine();
            int endIn = line.indexOf(':');
            String in = line.substring(0, endIn);
            String[] outs = line.substring(endIn + 2).split(" ");
            graph.put(in, Arrays.asList(outs));
            for (String o : outs) {
                graph.putIfAbsent(o, new ArrayList<>());
            }
        }
        input.close();

        long result = find(graph, "svr", false, false);
        System.out.println(result);
    }
}
