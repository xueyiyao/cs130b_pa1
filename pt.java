import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class pt{
    public static void main (String[] args){
        Scanner s = new Scanner(System.in);
        int numTrees = s.nextInt();
        ArrayList<Integer> arr = new ArrayList<>();
        int ans = 0;
        for(int i=0; i < numTrees; i++){
            arr.add(s.nextInt());
        }

        Collections.sort(arr, Collections.reverseOrder());

        for (int i = 1; i <= numTrees; i++){
            int temp = i + arr.get(i-1);
            if(temp>ans){
                ans = temp;
            }
        }
        System.out.println(ans + 1);
        s.close();
    }
}