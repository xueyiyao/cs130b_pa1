import java.util.Scanner;

public class mcb2 {
    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        int m = s.nextInt();
        
        int[][] cb = new int[n][m]; 

        //Read Checkboard
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                cb[i][j] = s.nextInt();
            }
        }

        if(m == 1 || n == 1){
            if(cb[0][0] == 0){
                long countWithOddStart = oneRowOrColumn(1, cb);
                if(countWithOddStart == Long.MAX_VALUE){
                    System.out.println(-1);
                } else {System.out.println(countWithOddStart);}
            }
            else{
                long countWithStart = oneRowOrColumn(cb[0][0], cb);
                if(countWithStart == Long.MAX_VALUE){
                    System.out.println(-1);
                } else {System.out.println(countWithStart);}
            }
            s.close();
            return;
        }

        long ans = 0;
        if(cb[0][0] == 0){
            long ansN1 = fillCB(cb, true, 1);
            long ansN2 = fillCB(cb, true, 2);
            long ansT1 = fillCB(cb, false, 1);
            long ansT2 = fillCB(cb, false, 2);

            if(ansN1 == Long.MAX_VALUE && ansN2 == Long.MAX_VALUE && ansT1 == Long.MAX_VALUE && ansT2 == Long.MAX_VALUE){
                ans = -1;
            }
            else {ans = Math.min(Math.min(ansN1, ansN2), Math.min(ansT1, ansT2));}
        } else {
            long ansN = fillCB(cb, true, cb[0][0]);
            long ansT = fillCB(cb, false, cb[0][0]);
            if(ansN == Long.MAX_VALUE && ansT == Long.MAX_VALUE){
                ans = -1;
            } else {ans = Math.min(ansN, ansT);}
        }
        System.out.println(ans);
        s.close();
    }

        //returns sum of grid
    public static long fillCB(int[][] cb, boolean isNormal, int start){
        //make copy in case we need old cb
        int[][] newCB = copyCB(cb);
        long count = 0;
        newCB[0][0] = start;
        if(isNormal){
            for(int i = 0; i < newCB.length; i++){
                for(int j = 0; j < newCB[0].length; j++){
                    if(newCB[i][j]==0){
                        if(j==0){
                            newCB[i][j] = newCB[i-1][j] + 2;
                        } else {
                            newCB[i][j] = newCB[i][j-1] + 1;
                        }

                        if(checkFailed(i, j, newCB, isNormal)==2){
                            newCB[i][j] = newCB[i-1][j] + 2;
                        }
                    }

                    if(checkFailed(i, j, newCB, isNormal)!=0){
                        return Long.MAX_VALUE;
                    }
                    count += newCB[i][j];
                    //print(newCB);
                }   
            }
        }
        else{
            for(int j = 0; j < newCB[0].length; j++){
                for(int i = 0; i < newCB.length; i++){
                    if(newCB[i][j]==0){
                        if(i==0){
                            newCB[i][j] = newCB[i][j-1] + 2;
                        } else {
                            newCB[i][j] = newCB[i-1][j] + 1;
                        }

                        if(checkFailed(i, j, newCB, isNormal)==1){
                            newCB[i][j] = newCB[i][j-1] + 2;
                        }
                    }

                    if(checkFailed(i, j, newCB, isNormal)!=0){
                        return Long.MAX_VALUE;
                    }
                    count += newCB[i][j];
                    //print(newCB);
                }   
            }
        }
        return count;
    }

    //i is rows, j is columns
    public static int checkFailed(int i, int j, int[][] cb, boolean isNormal){
        //check number to the left
        if(j-1 >= 0 && cb[i][j-1] >= cb[i][j]) { return 1; }
        //check number to the top
        if(i-1 >= 0 && cb[i-1][j] >= cb[i][j]) { return 2; }
        //check parity of top left corner
        if(i-1 >= 0 && j-1 >= 0 && cb[i-1][j-1]%2 == cb[i][j]%2){ return 3; }
        if(isNormal == true){
            //check parity of top right corner
            if(i-1 >= 0 && j+1 < cb[0].length && cb[i-1][j+1]%2 == cb[i][j]%2){ return 4; }
        } else {
            //check parity of bottom left corner
            if(j-1 >= 0 && i+1 < cb.length && cb[i+1][j-1]%2 == cb[i][j]%2){ return 5; }
        }
        return 0;
    }

    public static long oneRowOrColumn(int start, int[][] cb){
        long count = 0;
        int[][] cb2 = copyCB(cb);
        cb2[0][0] = start;
        count+=start;
        if(cb2[0].length == 1){
            for(int i = 1; i < cb2.length; i++){
                if(cb2[i][0] == 0){
                    cb2[i][0] = cb2[i-1][0] + 1;
                }
                if(cb2[i][0] <= cb2[i-1][0]){ return Long.MAX_VALUE; }
                count += cb2[i][0];
            }
        } else if (cb2.length == 1){
            for(int j = 1; j < cb2[0].length; j++){
                if(cb2[0][j] == 0){
                    cb2[0][j] = cb2[0][j-1] + 1;
                }
                if(cb2[0][j] <= cb2[0][j-1]){ return Long.MAX_VALUE; }
                count += cb2[0][j];
            }
        }
        return count;
    }

    //Copies checkerboard and outputs a deep copy
    public static int[][] copyCB(int[][] cb){
        int[][] newCB = new int[cb.length][cb[0].length];
        for(int i = 0; i < cb.length; i++){
            newCB[i] = cb[i].clone();
        }
        return newCB;
    }

    //Prints the checkerboard
    public static void print(int[][] cb){
        int n = cb.length;
        int m = cb[0].length;
        for(int i = 0; i < n; i++){
            String temp = "";
            for(int j = 0; j < m; j++){
                if(j == m-1){
                    temp += cb[i][j];
                }
                else{
                    temp += cb[i][j] + " ";
                }
            }
            System.out.println(temp);
        }
    }
}