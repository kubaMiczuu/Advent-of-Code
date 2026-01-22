package Day6.Part2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day6part2 {
    static void main() throws FileNotFoundException {
        Scanner input = new Scanner(new File("Advent of Code 2025/Day6/puzzle.txt"));
        List<List<String>> rows = new ArrayList<>();
        while (input.hasNextLine()) {
            List<String> row = new ArrayList<>();
            String line = input.nextLine();
            for(int i = 0; i < line.length(); i++){
                row.add(line.charAt(i) + "");
            }
            rows.add(row);
        }
        input.close();

        String[] signs = new String[rows.size()];
        List<Integer> spaceIndexes = new ArrayList<>(){};
        spaceIndexes.add(-1);
        for(int i = 0; i < rows.getFirst().size(); i++){
            for(int j = 0; j < rows.size(); j++){
                signs[j] =  rows.get(j).get(i);
            }
            String firstSign = rows.getFirst().get(i);
            if(firstSign.equals(" ")) {
                boolean isSpace = false;
                for (int j = 1; j < signs.length; j++) {
                    String currentSign = signs[j];
                    if(!currentSign.equals(firstSign)){
                        isSpace = false;
                        break;
                    }
                    isSpace = true;
                }
                if(isSpace) spaceIndexes.add(i);
            }
        }
        long totalResult = 0;
        for(int i = 0; i < spaceIndexes.size(); i++){
            int firstIndex = spaceIndexes.get(i)+1;
            int lastIndex = (i == spaceIndexes.size()-1) ? rows.getFirst().size() : spaceIndexes.get(i+1);
            List<String> cell = new  ArrayList<>();
            for(int j = firstIndex; j < lastIndex; j++){
                StringBuilder number = new StringBuilder();
                for(int k = 0; k < rows.size(); k++) {
                    String currentSign = rows.get(k).get(j);
                    if(currentSign.equals("*") || currentSign.equals("+")) {
                        cell.add(currentSign);
                        break;
                    }
                    if(!currentSign.equals(" ")) number.append(currentSign);
                }
                cell.add(number.toString());
            }
            String operator = cell.getFirst();
            long result = (operator.equals("*")) ? 1 : 0;
            for(int k = 1; k < cell.size(); k++){
                //System.out.println(k + " " + cell.get(k));
                if(operator.equals("*")) result *= Long.parseLong(cell.get(k));
                else if(operator.equals("+")) result += Long.parseLong(cell.get(k));
            }
            totalResult += result;
        }
        System.out.println(totalResult);
    }
}
