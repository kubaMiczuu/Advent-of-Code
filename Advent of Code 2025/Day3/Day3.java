package Day3;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day3 {
    static void main() throws FileNotFoundException {
        Scanner input = new Scanner(new File("Advent of Code 2025/Day3/puzzle.txt"));
        BigInteger sum = BigInteger.ZERO;
        while (input.hasNextLine()) {
            List<String> line = new ArrayList<>(List.of(input.nextLine().split("")));
            int toRemove = line.size() - 12;
            List<String> stack = new ArrayList<>();
            for(int i=0;i<line.size();i++){
                int currentDigit = Integer.parseInt(line.get(i));
                while(toRemove>0 && !stack.isEmpty() && Integer.parseInt(stack.getLast())<currentDigit){
                    stack.removeLast();
                    toRemove--;
                }
                stack.add(String.valueOf(currentDigit));
            }
            if(stack.size()>12){
                stack = stack.subList(0,12);
            }
            String finalNumber = String.join("", stack);
            sum=sum.add(new BigInteger(finalNumber));
        }
        input.close();
        System.out.println(sum);
    }
}
