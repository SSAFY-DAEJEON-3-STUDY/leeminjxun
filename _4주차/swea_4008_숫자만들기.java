package _4주차;

import java.io.*;
import java.util.*;

public class swea_4008_숫자만들기 {
    static int N, max, min;
    static int[] operCount, number;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for(int testCase = 1; testCase <= T; testCase++) {
            N = Integer.parseInt(br.readLine());

            operCount = new int[4];
            number = new int[N];

            st = new StringTokenizer(br.readLine());
            for(int i = 0; i < 4; i++) {
                operCount[i] = Integer.parseInt(st.nextToken());
            }

            st = new StringTokenizer(br.readLine());
            for(int i = 0; i < N; i++) {
                number[i] = Integer.parseInt(st.nextToken());
            }

            max = Integer.MIN_VALUE;
            min = Integer.MAX_VALUE;

            DFS(1, number[0]);

            sb.append("#").append(testCase).append(" ").append(max - min).append("\n");
        }

        System.out.print(sb);
    }

    static void DFS(int depth, int value) {
        if(depth == N) {
            max = Math.max(max, value);
            min = Math.min(min, value);

            return ;
        }

        for(int op = 0; op < 4; op++) {
            if(operCount[op] > 0) {
                operCount[op]--;
                DFS(depth + 1, calc(value, number[depth], op));
                operCount[op]++;
            }
        }
    }

    static int calc(int A, int B, int op) {
        if(op == 0) return A + B;
        else if(op == 1) return A - B;
        else if(op == 2) return A * B;
        else return A / B;
    }
}