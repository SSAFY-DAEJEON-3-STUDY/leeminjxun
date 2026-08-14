package _3주차;

import java.io.*;
import java.util.*;

public class swea_4012_요리사 {
    static int N, minDiff;
    static int[][] Combo;
    static boolean[] visited;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for(int testCase = 1; testCase <= T; testCase++) {
            N = Integer.parseInt(br.readLine());

            Combo = new int[N][N];
            visited = new boolean[N];

            for(int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for(int j = 0; j < N; j++) {
                    Combo[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            minDiff = Integer.MAX_VALUE;

            DFS(0, 0);

            sb.append("#").append(testCase).append(" ").append(minDiff).append("\n");
        }

        System.out.print(sb);
    }

    static void DFS(int start, int depth) {
        if(depth == N / 2) {
            minDiff = Math.min(minDiff, getCombo());
            return;
        }

        // 순열이 아닌 조합 사용 -> 이전 사용된 숫자는 버린다
        for(int i = start; i < N; i++) {
            visited[i] = true;
            DFS(i + 1, depth + 1);
            visited[i] = false;
        }
    }

    static int getCombo() {
        List<Integer> aList = new ArrayList<>();
        List<Integer> bList = new ArrayList<>();

        for(int i = 0; i < N; i++) {
            if(visited[i]) aList.add(i);
            else bList.add(i);
        }

        int totalA = 0;
        int totalB = 0;

        for(int a : aList) {
            for(int b : aList) {
                if(a == b) continue;
                totalA += Combo[a][b];
            }
        }

        for(int a : bList) {
            for(int b : bList) {
                if(a == b) continue;
                totalB += Combo[a][b];
            }
        }

        return Math.abs(totalA - totalB);
    }
}
