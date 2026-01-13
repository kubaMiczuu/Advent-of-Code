package Part1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File("./puzzle.txt"));
        List<Integer> presentSizes = new ArrayList<>();
        List<Integer> regionSizes = new ArrayList<>();
        List<List<Integer>> presentsQuantity = new ArrayList<>();
        int size = 0;
        while (input.hasNextLine()) {
            String line =  input.nextLine();
            if(line.length() < 5){
                if(!line.isEmpty()) {
                    for(int i = 0; i < line.length(); i++) {
                        if(line.charAt(i) == '#') size++;
                    }
                } else {
                    presentSizes.add(size);
                    size = 0;
                }
            } else {
                int index = line.indexOf('x');
                String number1 = line.substring(0, index);
                String number2 = line.substring(index + 1, index + 3);
                int region = Integer.parseInt(number1)*Integer.parseInt(number2);
                regionSizes.add(region);
                int index2 = line.indexOf(':');
                List<Integer> quantities = new ArrayList<>();
                String[] quantity = line.substring(index2+2).split(" ");
                for(int i = 0; i < quantity.length; i++) {
                    quantities.add(Integer.parseInt(quantity[i]));
                }
                presentsQuantity.add(quantities);
            }
        }

        int validTrees = 0;
        for(int i = 0; i < presentsQuantity.size(); i++){
            int maxSize = regionSizes.get(i);
            for(int j = 0; j < presentsQuantity.get(i).size(); j++){
                if(maxSize < 0) break;
                maxSize -= presentsQuantity.get(i).get(j)*presentSizes.get(j);
            }
            if(maxSize > 0) validTrees++;
        }

        System.out.println(validTrees);
    }
}