package _6주차;

import java.io.*;
import java.util.*;

public class 화분 {
    static int N, P, maxSum;
    static int[][] B;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for(int testCase = 1; testCase <= T; testCase++) {
            st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            P = Integer.parseInt(st.nextToken());

            B = new int[2][N];

            for(int i = 0; i < 2; i++) {
                st = new StringTokenizer(br.readLine());
                for(int j = 0; j < N; j++) {
                    B[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            maxSum = 0;

            dfs(0, 0, false, false);

            sb.append("#").append(testCase).append(" ").append(maxSum).append("\n");
        }

        System.out.print(sb);
    }

    // 이전 값에 a 를 썻다 -> true
    static void dfs(int depth, int sum, boolean a, boolean b) {
        if(depth == N) {
            maxSum = Math.max(maxSum, sum);

            return;
        }

        if(a) dfs(depth + 1, sum + B[0][depth] - P, true, false);
        else dfs(depth + 1, sum + B[0][depth], true, false);

        if(b) dfs(depth + 1, sum + B[1][depth] - P, false, true);
        else dfs(depth + 1, sum + B[1][depth], false, true);
    }
}