package Part1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static java.util.Arrays.sort;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        Scanner input = new Scanner(new File("./puzzle.txt"));

        List<Integer> xCord = new ArrayList<>();
        List<Integer> yCord = new ArrayList<>();
        List<Integer> zCord = new ArrayList<>();

        while (input.hasNextLine()) {
            String line = input.nextLine();
            String[] tokens = line.split(",");
            xCord.add(Integer.parseInt(tokens[0]));
            yCord.add(Integer.parseInt(tokens[1]));
            zCord.add(Integer.parseInt(tokens[2]));
        }

        List<List<Double>> distances = new ArrayList<>();
        List<Double> allDistances = new ArrayList<>();

        for (int i = 0; i < xCord.size(); i++) {
            List<Double> temp = new ArrayList<>();
            for (int j = 0; j < xCord.size(); j++) {
                double dx = xCord.get(j) - xCord.get(i);
                double dy = yCord.get(j) - yCord.get(i);
                double dz = zCord.get(j) - zCord.get(i);
                double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
                temp.add(distance);
                allDistances.add(distance);
            }
            distances.add(temp);
        }

        List<List<Integer>> distanceIndexes = new ArrayList<>();
        Collections.sort(allDistances);

        for (int i = 1000; i < 3000; i += 2) {
            double distance = allDistances.get(i);
            List<Integer> temp = new ArrayList<>();

            for (int j = 0; j < distances.size(); j++) {
                int index = distances.get(j).indexOf(distance);
                if (index > 0) {
                    temp.add(j);
                    temp.add(index);

                    distances.get(j).set(index, 1000000.0);
                    distances.get(index).set(j, 1000000.0);
                    break;
                }
            }
            distanceIndexes.add(temp);
        }

        List<List<Integer>> circuits = new ArrayList<>();

        for (int i = 0; i < distanceIndexes.size() - 1; i++) {
            int firstIndex = distanceIndexes.get(i).get(0);
            int secondIndex = distanceIndexes.get(i).get(1);

            if (circuits.isEmpty()) {
                List<Integer> temp = new ArrayList<>();
                temp.add(firstIndex);
                temp.add(secondIndex);
                circuits.add(temp);
            } else {
                int fID = -1;
                int sID = -1;

                for (int j = 0; j < circuits.size(); j++) {
                    boolean firstCheck = circuits.get(j).contains(firstIndex);
                    boolean secondCheck = circuits.get(j).contains(secondIndex);

                    if (firstCheck && secondCheck) {
                        fID = sID = j;
                        break;
                    }
                    if (firstCheck) fID = j;
                    if (secondCheck) sID = j;
                }

                if (fID < 0 && sID < 0) {
                    List<Integer> temp = new ArrayList<>();
                    temp.add(firstIndex);
                    temp.add(secondIndex);
                    circuits.add(temp);
                } else if (fID >= 0 && sID < 0) {
                    circuits.get(fID).add(secondIndex);
                } else if (fID < 0 && sID >= 0) {
                    circuits.get(sID).add(firstIndex);
                } else if (fID >= 0 && sID >= 0 && fID != sID) {
                    int keep = Math.min(fID, sID);
                    int remove = Math.max(fID, sID);
                    circuits.get(keep).addAll(circuits.get(remove));
                    circuits.remove(remove);
                }
            }
        }

        circuits.sort(Comparator.comparingInt(List::size));

        int result = 1;
        for (int i = circuits.size() - 1; i >= circuits.size() - 3; i--) {
            result *= circuits.get(i).size();
            System.out.println(circuits.get(i));
        }

        System.out.println(result);
    }
}
