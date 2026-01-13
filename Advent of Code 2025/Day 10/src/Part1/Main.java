package Part1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File("./puzzle.txt"));

        List<List<Boolean>> indicators = new ArrayList<>();
        List<List<Boolean>> targetIndicators = new ArrayList<>();
        List<List<List<Integer>>> buttons = new ArrayList<>();

        while (input.hasNextLine()) {
            String line = input.nextLine();

            int startIndicator = line.indexOf("[")+1;
            int endIndicator = line.indexOf("]")-1;
            List<Boolean> tempIndicators = new ArrayList<>();
            List<Boolean> tempTargetIndicators = new ArrayList<>();
            for(int i = startIndicator; i <= endIndicator; i++){
                tempIndicators.add(false);
                boolean sign = line.charAt(i) == '#';
                tempTargetIndicators.add(sign);
            }
            indicators.add(tempIndicators);
            targetIndicators.add(tempTargetIndicators);

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

        int result = 0;

        //Breadth-First Search
        int n = targetIndicators.size();
        for(int i = 0; i < n; i++){
            int startBit = 0;
            int targetBit = 0;
            for(int j = 0; j < targetIndicators.get(i).size(); j++){
                if(targetIndicators.get(i).get(j).equals(true)) targetBit |= 1<<j;
            }

            List<Integer> buttonMasks = new ArrayList<>();
            for(int j = 0; j < buttons.get(i).size(); j++){
                int mask = 0;
                for(int k = 0; k < buttons.get(i).get(j).size(); k++){
                    mask |= 1<<buttons.get(i).get(j).get(k);
                }
                buttonMasks.add(mask);
            }

            Queue<Integer> queue = new ArrayDeque<>();
            Map<Integer, Integer> distances = new HashMap<>();
            int start = 0;
            queue.add(start);
            distances.put(start, 0);

            while(!queue.isEmpty()){
                int current =  queue.poll();
                int d = distances.get(current);
                if(current == targetBit) {
                    result += d;
                    break;
                }
                else {
                    for(int k = 0; k < buttonMasks.size(); k++){
                        int next = current ^ buttonMasks.get(k);
                        if(!distances.containsKey(next)) {
                            distances.put(next, d+1);
                            queue.add(next);
                        }
                    }
                }
            }
        }
        System.out.println(result);
    }
}