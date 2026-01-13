package Day1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File("./puzzle.txt"));
        int counter = 0;
        while (input.hasNextLine()) {
            input.nextLine();
            counter++;
        }
        input = new Scanner(new File("./puzzle.txt"));
        int[] key = new int[counter];
        int index=0;
        while(input.hasNextLine()) {
            String line = input.nextLine();
            if(line.startsWith("L")) {
                key[index] = Integer.parseInt("-"+line.substring(1));
            } else if(line.startsWith("R")) {
                key[index] = Integer.parseInt(line.substring(1));
            }
            index++;
        }

        int pointingAt = 50;
        int howMany = 0;
        for (int i = 0; i < key.length; i++) {
            for (int s = 0; s < Math.abs(key[i]); s++) {
                pointingAt += (key[i] > 0 ? 1 : -1);
                if (pointingAt == 100) pointingAt = 0;
                if (pointingAt == -1) pointingAt = 99;
                if (pointingAt == 0) howMany++;
            }
        }
        System.out.println(howMany);
    }
}