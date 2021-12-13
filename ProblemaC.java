import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;

/**
 * @author Daniel Zambrano Huertas
 */
public class ProblemaC {
    private static int N;
    private static String[] m;
    private static int[][] dp;
    private static int[][] G;
    private static int[][] path;

    public static String minimal_super_string(String[] pM){
        N = pM.length;
        m = pM;
        G = new int[N][N];
        for (int l=0; l<N;l++){
            Arrays.fill(G[l],-1);
        }
        // calculate suffix and preffix of all the entries in m and builda a solution graph
        for (int i=0; i<N; i++){
            for (int j=0;j<N;j++){
                if(G[i][j] == -1 && G[j][i] == -1 ) {
                    G[i][j] = check_suffix_prefix(m[i], m[j]);
                    G[j][i] = check_suffix_prefix(m[j], m[i]);
                }
            }
        }
        // init the dp array
        dp = new int[1 << N][N];
        path = new int[1 << N][N];
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
                                path[i][j] = k;
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
            current -= (1<<last);
            last = path[temp][last];
        }

        int i = stack.pop();
        msg.append(m[i]);

        while (!stack.isEmpty()){
            int j = stack.pop();
            msg.append(m[j].substring(m[j].length() - G[i][j]));
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
    public static void main(String[] args) throws Exception {
        try (
                InputStreamReader is = new InputStreamReader(System.in);
                BufferedReader br = new BufferedReader(is);
        ) {
            String line = br.readLine();

            while(line!=null && line.length()>0 && !"0".equals(line)) {
                final String [] data_str = line.split(" ");

                final int[] params = { Integer.parseInt(data_str[0]), Integer.parseInt(data_str[1]) };
                String[] pM = new String[params[0]];
                for (int i=0; i < params[0]; i++){
                    line = br.readLine();
                    pM[i] = line.split(" ")[0];
                }
                String respuesta = ProblemaC.minimal_super_string(pM);
                System.out.println(respuesta);
                line = br.readLine();
            }
        }
    }

}
