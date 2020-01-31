import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class mp {
    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        int numOfTests = s.nextInt();

        outerloop:
        for(int n = 0; n < numOfTests; n++){
            int m = s.nextInt();
            int p = s.nextInt();
            if(p%2 == 1){ p -= 1; }
            int simultaneousJobs = p/2;
            ArrayList<ArrayList<Integer>> schedule = new ArrayList<>();

             
            for(int i = 0; i < m; i++){
                ArrayList<Integer> pianoMoveOrder = new ArrayList<>();
                pianoMoveOrder.add(s.nextInt());
                pianoMoveOrder.add(s.nextInt());
                schedule.add(pianoMoveOrder);
            }

            Collections.sort(schedule, new Comparator<ArrayList<Integer>> () {
                @Override
                public int compare(ArrayList<Integer> a, ArrayList<Integer> b) {
                    if(a.get(1) != b.get(1)){return a.get(1).compareTo(b.get(1));}
                    return a.get(0).compareTo(b.get(0)); 
                }
            });
            
            //checking if weekend work can be avoided
            ArrayList<ArrayList<Integer>> newSchedule = copySchedule(schedule);
            for(int i = 1; i <= 100; i++){
                //System.out.println(String.format("---Day %d---", i));
                if(newSchedule.get(0).get(1) < i){
                    break;
                }
                for(int j = 0; j < simultaneousJobs; j++){
                    if((i%7 != 0 && i%7 != 6) && i >= newSchedule.get(0).get(0) && i <= newSchedule.get(0).get(1)){
                        newSchedule.remove(0);
                        //System.out.println("Printing Schedule:");
                        //print(newSchedule);
                        if(newSchedule.isEmpty()){
                            System.out.println("fine");
                            continue outerloop;
                        }
                    }
                }
            }

            //System.out.println("\nChecking if weekend work will suffice...");
            for(int i = 1; i <= 100; i++){
                //System.out.println(String.format("---Day %d---", i));
                if(schedule.get(0).get(1) < i){
                    System.out.println("serious trouble");
                    continue outerloop;
                }
                for(int j = 0; j < simultaneousJobs; j++){
                    if(i >= schedule.get(0).get(0) && i <= schedule.get(0).get(1)){
                        schedule.remove(0);
                        //System.out.println("Printing Schedule:");
                        //print(schedule);
                        if(schedule.isEmpty()){
                            System.out.println("weekend work");
                            continue outerloop;
                        }
                    }
                }
            }
        }
        s.close();
    }

    public static void print(ArrayList<ArrayList<Integer>> schedule){
        for(int i = 0; i < schedule.size(); i++){
            System.out.println(schedule.get(i).get(0) + " " + schedule.get(i).get(1));
        }
    }

    public static ArrayList<ArrayList<Integer>> copySchedule(ArrayList<ArrayList<Integer>> schedule){
        ArrayList<ArrayList<Integer>> newSchedule = new ArrayList<>();
        for(int i = 0; i < schedule.size(); i++){
            ArrayList<Integer> copiedOrder = new ArrayList<>();
            copiedOrder.add(schedule.get(i).get(0));
            copiedOrder.add(schedule.get(i).get(1));
            newSchedule.add(copiedOrder);
        }
        return newSchedule;
    }
}