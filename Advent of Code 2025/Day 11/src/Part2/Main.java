package Part2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
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

    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File("./puzzle.txt"));
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

        long result = find(graph, "svr", false, false);
        System.out.println(result);
    }
}