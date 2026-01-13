import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static int counter = 0;
    public static boolean found = false;
    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File("./puzzle.txt"));
        List<List<String>> puzzle = new ArrayList<>();
        while (input.hasNextLine()) {
            String currentLine = input.nextLine();
            int i = 0;
            List<String> line = new ArrayList<>();
            for(int j = 0; j < currentLine.length(); j++){
                line.add(currentLine.charAt(j) + "");
            }
            puzzle.add(line);
            i++;
        }

        do {
            puzzle = remove(puzzle);
        } while(found);

        System.out.println("Counter: " + counter);
    }

    public static List<List<String>> remove(List<List<String>> puzzle) {
        found = false;
        for(int i = 0; i < puzzle.size(); i++){
            for(int j = 0; j < puzzle.get(i).size(); j++){
                int atCounter = 0;
                if(puzzle.get(i).get(j).equals("@")) {
                    if((i == 0 && j == 0) ||
                            (i == 0 && j == puzzle.get(i).size()-1)||
                            (i == puzzle.size()-1 && j == 0) ||
                            (i == puzzle.size()-1 && j == puzzle.get(i).size()-1)) {
                        puzzle.get(i).set(j, "x");
                        found = true;
                        counter++;
                    }
                    else if(i == 0) {
                        if(puzzle.get(i).get(j-1).equals("@")) atCounter++;
                        if(puzzle.get(i).get(j+1).equals("@")) atCounter++;
                        if(puzzle.get(i+1).get(j-1).equals("@")) atCounter++;
                        if(puzzle.get(i+1).get(j).equals("@")) atCounter++;
                        if(puzzle.get(i+1).get(j+1).equals("@")) atCounter++;
                        if(atCounter < 4) {
                            puzzle.get(i).set(j, "x");
                            atCounter = 0;
                            found = true;
                            counter++;
                        }
                    } else if(i == puzzle.get(i).size()-1){
                        if(puzzle.get(i).get(j-1).equals("@")) atCounter++;
                        if(puzzle.get(i).get(j+1).equals("@")) atCounter++;
                        if(puzzle.get(i-1).get(j-1).equals("@")) atCounter++;
                        if(puzzle.get(i-1).get(j).equals("@")) atCounter++;
                        if(puzzle.get(i-1).get(j+1).equals("@")) atCounter++;
                        if(atCounter < 4) {
                            puzzle.get(i).set(j, "x");
                            atCounter = 0;
                            found = true;
                            counter++;
                        }
                    } else if(j == 0) {
                        if(puzzle.get(i-1).get(j).equals("@")) atCounter++;
                        if(puzzle.get(i+1).get(j).equals("@")) atCounter++;
                        if(puzzle.get(i-1).get(j+1).equals("@")) atCounter++;
                        if(puzzle.get(i).get(j+1).equals("@")) atCounter++;
                        if(puzzle.get(i+1).get(j+1).equals("@")) atCounter++;
                        if(atCounter < 4) {
                            puzzle.get(i).set(j, "x");
                            atCounter = 0;
                            found = true;
                            counter++;
                        }
                    } else if(j == puzzle.get(i).size()-1) {
                        if(puzzle.get(i-1).get(j).equals("@")) atCounter++;
                        if(puzzle.get(i+1).get(j).equals("@")) atCounter++;
                        if(puzzle.get(i-1).get(j-1).equals("@")) atCounter++;
                        if(puzzle.get(i).get(j-1).equals("@")) atCounter++;
                        if(puzzle.get(i+1).get(j-1).equals("@")) atCounter++;
                        if(atCounter < 4) {
                            puzzle.get(i).set(j, "x");
                            atCounter = 0;
                            found = true;
                            counter++;
                        }
                    } else {
                        if(puzzle.get(i-1).get(j-1).equals("@")) atCounter++;
                        if(puzzle.get(i-1).get(j).equals("@")) atCounter++;
                        if(puzzle.get(i-1).get(j+1).equals("@")) atCounter++;
                        if(puzzle.get(i).get(j-1).equals("@")) atCounter++;
                        if(puzzle.get(i).get(j+1).equals("@")) atCounter++;
                        if(puzzle.get(i+1).get(j-1).equals("@")) atCounter++;
                        if(puzzle.get(i+1).get(j).equals("@")) atCounter++;
                        if(puzzle.get(i+1).get(j+1).equals("@")) atCounter++;
                        if(atCounter < 4) {
                            puzzle.get(i).set(j, "x");
                            atCounter = 0;
                            found = true;
                            counter++;
                        }
                    }
                }
            }
        }
        return puzzle;
    }
}