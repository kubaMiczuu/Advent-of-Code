package Day2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day2 {
    static void main() throws FileNotFoundException {
        Scanner input = new Scanner(new File("Advent of Code 2025/Day2/puzzle.txt"));
        String text = input.next();
        input.close();
        String[] ranges = text.split(",");
        int rangeCounter = 0;
        for(String range : ranges){
            rangeCounter++;
        }
        String[] firstID = new String[rangeCounter];
        String[] lastID = new String[rangeCounter];

        long invalidsSum=0;
        for(int i = 0; i < rangeCounter; i++){
            int index = ranges[i].indexOf("-");
            firstID[i] = ranges[i].substring(0,index);
            lastID[i] = ranges[i].substring(index+1);

            long startID = Long.parseLong(firstID[i]);
            long endID = Long.parseLong(lastID[i]);
            for(long j=startID; j<=endID; j++) {
                String nextID = String.valueOf(j);
                int idLen = nextID.length();
                for(int k = 2; k<=idLen; k++){
                    if(idLen%k==0) {
                        String[] parts = new String[k];
                        for(int l=0; l<k; l++){
                            parts[l] = nextID.substring(l*(idLen/k),(l+1)*(idLen/k));
                        }
                        boolean allTheSame = true;
                        for(int l=0; l<k; l++){
                            if(!parts[l].equals(parts[0])) {
                                allTheSame = false;
                                break;
                            }
                        }
                        if(allTheSame){
                            invalidsSum+=Long.parseLong(nextID);
                            break;
                        }
                    }
                }
            }
        }
        System.out.println(invalidsSum);
    }
}
