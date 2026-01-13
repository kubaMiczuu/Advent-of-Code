import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main{
  public static void main(String[] args) throws FileNotFoundException {
    Scanner input = new Scanner(new File("./puzzle.txt"));
    List<Long> firstID = new ArrayList<>();
    List<Long> lastID = new ArrayList<>();
    List<Long> avID = new ArrayList<>();
    List<long[]> ranges = new ArrayList<>();
    while(input.hasNextLine()){
      String line = input.nextLine();
      if(!line.equals("")) {
        int index = line.indexOf("-");
        if(index>0) {
          long startID = Long.parseLong(line.substring(0, index));
          firstID.add(startID);
          long endID = Long.parseLong(line.substring(index+1));
          lastID.add(endID);
          ranges.add(new long[]{startID,endID});
        } else {
          long id = Long.parseLong(line);
          avID.add(id);
        }
      }
    }
    long counter = 0;
//    for(int i=0;i<avID.size();i++) {
//      long id = avID.get(i);
//      for(int j=0;j<firstID.size();j++) {
//        long startID = firstID.get(j);
//        long endID = lastID.get(j);
//        if(startID < id && endID > id) {
//          counter++
//          break;
//        }
//      }
//    }
    ranges.sort(Comparator.comparingLong(l -> l[0]));
    long start = ranges.get(0)[0];
    long end = ranges.get(0)[1];
    for(int i=1;i<ranges.size();i++){
      if(ranges.get(i)[0]<=end+1){
        end = Math.max(end,ranges.get(i)[1]);
      } else {
        counter+=(end-start+1);
        start = ranges.get(i)[0];
        end = ranges.get(i)[1];
      }
    }
    counter+=(end-start+1);
    System.out.println(counter);
  }
}