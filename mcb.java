import java.util.Scanner;

public class mcb {
    public static void main(String args[]){
        Scanner s = new Scanner(System.in);
        //System.out.println("Enter dimensions of checkerboard:");
        int n = s.nextInt();
        int m = s.nextInt();
        
        int[][] cb = new int[n][m]; 
        //System.out.println("Enter checkerboard:");

        //Read Checkboard
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                cb[i][j] = s.nextInt();
            }
        }

        //If there is only one column or row
        if(m == 1 || n == 1){
            if(cb[0][0] == 0){
                long countWithOddStart = oneRowOrColumn(1, cb);
                long countWithEvenStart = oneRowOrColumn(2, cb); //IS THIS NECESSARY?
                System.out.println(Math.min(countWithOddStart, countWithEvenStart));
            }
            else{
                System.out.println(oneRowOrColumn(cb[0][0], cb));
            }
            s.close();
            return;
        }

        //Determine Orientation of Checkerboard along with the inital starting number
        boolean isNormal1 = true, isNormal2 = true, isTranspose1 = true, isTranspose2 = true;
        boolean is1 = false, is2 = false;
        for (int i = 0 ; i < n; i++){
            for(int j = 0; j < m; j++){
                if(cb[i][j] != 0){
                    if(cb[i][j]%2 != (j+1)%2 && cb[i][j]%2 != (i+1)%2){
                        is2 = true;
                    }

                    if( ( (j+1)%2 == 1 && cb[i][j]%2 != 1 ) || ( (j+1)%2 == 0 && cb[i][j]%2 != 0 ) ){ 
                        //odd columns not all odd or even columns not all even
                        isNormal1 = false;
                    }
                    if( ( (j+1)%2 == 1 && cb[i][j]%2 != 0 ) || ( (j+1)%2 == 0 && cb[i][j]%2 != 1 ) ){
                        //odd columns not all even or even columns not all odd
                        isNormal2 = false;
                    }
                    if( ( (i+1)%2 == 1 && cb[i][j]%2 != 1 ) || ( (i+1)%2 == 0 && cb[i][j]%2 != 0 ) ){ 
                        //odd rows not all odd or even rows not all even
                        isTranspose1 = false;
                    }
                    if( ( (i+1)%2 == 1 && cb[i][j]%2 != 0 ) || ( (i+1)%2 == 0 && cb[i][j]%2 != 1 ) ){
                        //odd rows not all odd or even rows not all even
                        isTranspose2 = false;
                    }
                }
            }
        }
        if(cb[0][0] != 0 && cb[0][0]%2 == 0){is2 = true;}
        else if(cb[0][0] != 0 && cb[0][0]%2 == 1){is1 = true;}

        if(is1 == true){
            isNormal2 = false;
            isTranspose2 = false;
        }
        else if(is2 == true){
            isNormal1 = false;
            isTranspose1 = false;
        }

        // System.out.println(String.format("isNormal1: %b", isNormal1));
        // System.out.println(String.format("isNormal2: %b", isNormal2));
        // System.out.println(String.format("isTranspose1: %b", isTranspose1));
        // System.out.println(String.format("isTranspose2: %b", isTranspose2));
        // System.out.println(String.format("is1: %b", is1));
        // System.out.println(String.format("is2: %b", is2));

        if(!isNormal1 && !isNormal2 && !isTranspose1 && !isTranspose2){
            System.out.println(-1);
            s.close();
            return;
        }

        //initialize first element
        if(cb[0][0] == 0){
            if(is1 == true){ cb[0][0] = 1; }
            else if(is2 == true){ cb[0][0] = 2; }
            else if(is1 == false && is2 == false){ cb[0][0] = 1; }
        }

        long ans = 0;

        if(isNormal1 && isTranspose1){
            long ansRows = fillCB(cb, true, 1);
            long ansCols = fillCB(cb, false, 1);
            if(ansRows != -1 && ansCols != -1){ans += Math.min(ansRows, ansCols);}
            else if(ansRows != -1){ans += ansRows;}
            else if(ansCols != -1){ans += ansCols;}
            else{ ans = -1; }
        }
        else if(isNormal2 && isTranspose2){
            long ansRows = fillCB(cb, true, 2);
            long ansCols = fillCB(cb, false, 2);
            if(ansRows != -1 && ansCols != -1) {ans += Math.min(ansRows, ansCols);}
            else if(ansRows != -1){ans += ansRows;}
            else if(ansCols != -1){ans += ansCols;}
            else{ ans = -1; }
        }
        else if(isNormal1 && isTranspose2){
            long ansN1 = fillCB(cb, true, 1);
            long ansT2 = fillCB(cb, false, 2);
            if(ansN1 != -1 && ansT2 != -1) {ans += Math.min(ansN1,ansT2);}
            else if(ansN1 != -1){ ans += ansN1; }
            else if(ansT2 != -1){ ans += ansT2; }
            else{ ans = -1; }
        }
        else if(isNormal2 && isTranspose1){
            long ansN2 = fillCB(cb, true, 2);
            long ansT1 = fillCB(cb, false, 1);
            if(ansN2 != -1 && ansT1 != -1) {ans += Math.min(ansN2,ansT1);}
            else if(ansN2 != -1){ ans += ansN2; }
            else if(ansT1 != -1){ ans += ansT1; }
            else{ ans = -1; }
        }
        else{
            ans += fillCB(cb, (isNormal1 || isNormal2), cb[0][0]);
        }

        System.out.println(ans); 
        s.close();
    }

    public boolean checkType(long ans){
        return true;
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
                        return -1;
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
                        return -1;
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
                if(cb2[i][0] <= cb2[i-1][0]){ return -1; }
                count += cb2[i][0];
            }
        } else if (cb2.length == 1){
            for(int j = 1; j < cb2[0].length; j++){
                if(cb2[0][j] == 0){
                    cb2[0][j] = cb2[0][j-1] + 1;
                }
                if(cb2[0][j] <= cb2[0][j-1]){ return -1; }
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
/*
//Problem Cases
0 0 5 0
0 0 0 0

1 0 5
0 0 0
3 0 0
*/


/* 
    public static int getNum(int i, int j, int[][] cb, boolean isNormal){
        if(isNormal == true){
            if(i-1 >= 0 && j == 0){
                return Math.max(cb[i-1][j], cb[i-1][j+1]) + 1;
            }
            if(j-1 >= 0){
                return cb[i][j-1] + 1;
            }
            return -1;
        } else {
            if(j-1 >= 0 && i == 0){
                return Math.max(cb[i][j-1], cb[i+1][j-1]) + 1;
            }
            if(i-1 >= 0){
                return cb[i-1][j] + 1;
            }
            return -1;
        }
    }
*/