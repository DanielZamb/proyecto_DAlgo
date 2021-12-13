package Problema3;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Stack;

public class Problema3 {
    private static int N;
    private static String[] m;
    private static int[][] dp;
    private static int[][] G;
    private static int[][] eurelian_path;

    public static String minimal_super_string(String[] pM){
        N = pM.length;
        m = pM;
        G = new int[N][N];
        Arrays.fill(G,-1);
        // calculate suffix and preffix of all the entries in m and builda a solution graph
        for (int i=0; i<N; i++){
            for (int j=0;j<N;j++){
                if(G[i][j] ==-1 && G[j][i] ==-1 ) {
                    G[i][j] = check_suffix_prefix(m[i], m[j]);
                    System.out.print("G[i][j]:" + G[i][j]);
                    G[j][i] = check_suffix_prefix(m[j], m[i]);
                    System.out.print("G[j][i]:" + G[j][i]);
                }
            }
        }
        // init the dp array
        dp = new int[1 << N][N];
        eurelian_path = new int[1 << N][N];
        int last = -1;
        int min = Integer.MAX_VALUE;
        int inf = Integer.MAX_VALUE;

        for(int i = 1; i < (1 << N) ;  i++ ){
           Arrays.fill(dp[i], inf);
           for(int j=0; j < N; j++){
               if ((i&(1 << j))>0){
                    int previous = i - (1<<j);
                    if (previous == 0)
                        dp[i][j] = m[j].length();
                    else{
                        for (int k=0; k<N;k++){
                            if(dp[previous][k]< inf && (dp[previous][k] + G[k][j]< dp[i][j])){
                                dp[i][j] = dp[previous][k] + G[k][j];
                                eurelian_path[i][j] = k;
                            }
                        }
                    }

               }
               if (i == (1<<N)-1 && dp[i][j] < min){
                   min = dp[i][j];
                   last = j;
               }
           }
        }

        StringBuilder msg = new StringBuilder();
        int current = (1 << N) - 1;

        Stack<Integer> stack = new Stack<>();

        while(current>0){
            stack.push(last);
            int temp = current;
            current = current - (1<<last);
            last = eurelian_path[temp][last];
        }

        int i = stack.pop();
        msg.append(m[i]);

        while (!stack.isEmpty()){
            int j = stack.pop();
            msg.append(m[i].substring(m[i].length() - G[i][j]));
            i = j;
        }
        return msg.toString();

    }

    //Checks if s is prefix of t and t is suffix of s. init i in 1 in order to leave at maximum 1 extra char at the combination of s and t
    public static int check_suffix_prefix(String s, String t){
        for (int i=1 ; i<s.length() ; i++){
            if (t.startsWith(s.substring(i))){
                //return t.length() - s.length() + i; caso k diferentes para cad m[i]
                return i;
            }
        }
        return t.length();
    }


}
