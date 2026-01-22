package Day6.Part1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day6part1 {
    static void main() throws FileNotFoundException {
        Scanner input = new Scanner(new File("Advent of Code 2025/Day6/puzzle.txt"));
        List<List<String>> calculations = new ArrayList<>();
        while (input.hasNextLine()) {
            int id = 0;
            String currentLine = input.nextLine();
            List<String> line = new ArrayList<>();
            for (int j = 0; j < currentLine.length(); j++) {
                line.add(currentLine.charAt(j) + "");
            }

            List<String> row = new ArrayList<>();
            StringBuilder number = new StringBuilder();
            for (int i = 0; i < line.size(); i++) {
                String ch = line.get(i);
                if (!ch.equals(" ")) {
                    number.append(ch);
                } else {
                    if (number.length() > 0) {
                        row.add(number.toString());
                        number.setLength(0);
                    }
                }
            }
            if (number.length() > 0) {
                row.add(number.toString());
            }
            calculations.add(row);
        }
        long finalNumber = 0;
        int operatorRow = calculations.size() - 1;
        int numProblems = calculations.get(operatorRow).size();

        for (int col = 0; col < numProblems; col++) {
            String op = calculations.get(operatorRow).get(col);
            long value = (op.equals("*")) ? 1 : 0;
            for (int row = 0; row < operatorRow; row++) {
                if (col < calculations.get(row).size()) {
                    long num = Long.parseLong(calculations.get(row).get(col));
                    if (op.equals("*")) value *= num;
                    else value += num;
                }
            }
            finalNumber += value;
        }
        input.close();
        System.out.println(finalNumber);
    }
}
