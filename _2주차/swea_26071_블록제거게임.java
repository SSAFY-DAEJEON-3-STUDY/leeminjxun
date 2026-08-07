package _2주차;

import java.io.*;
import java.util.*;

public class swea_26071_블록제거게임 {
    static int N, Max;
    static int[] block;
    static boolean[] visited;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for(int testCase = 1; testCase <= T; testCase++) {
            N = Integer.parseInt(br.readLine());

            block = new int[N];
            visited = new boolean[N];

            st = new StringTokenizer(br.readLine());
            for(int i = 0; i < N; i++) {
                block[i] = Integer.parseInt(st.nextToken());
            }

            Max = 0;

            DFS(0, 0);

            sb.append("#").append(testCase).append(" ").append(Max).append("\n");

        }

        System.out.print(sb);
    }

    static void DFS(int depth, int sum) {
        if(depth == N) {
            Max = Math.max(Max, sum);
            return;
        }

        for(int i = 0; i < N; i++) {
            if(!visited[i]) {
                int right = rightValue(i);
                int left = leftValue(i);

                int currentSum = 0;

                if(right != 0 && left != 0) currentSum = right * left;
                else if (right != 0 && left == 0) currentSum = right;
                else if (right == 0 && left != 0) currentSum = left;
                else currentSum = block[i];

                visited[i] = true;
                DFS(depth + 1, sum + currentSum);
                visited[i] = false;
            }
        }
    }

    static int leftValue(int idx) {
        int lValue = 0;

        while(true) {
            idx--;

            if(idx < 0) return 0;

            if(!visited[idx]) {
                lValue = block[idx];
                break;
            }
        }

        return lValue;

    }

    static int rightValue(int idx) {
        int rValue = 0;

        while(true) {
            idx++;

            if(idx >= N) return 0;

            if(!visited[idx]) {
                rValue = block[idx];
                break;
            }
        }

        return rValue;
    }
}
