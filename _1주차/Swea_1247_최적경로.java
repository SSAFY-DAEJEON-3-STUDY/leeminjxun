package _1주차;

import java.io.*;
import java.util.*;

public class Swea_1247_최적경로 {
    static int N, min;

    static int[] company, home;
    static int[][] clients;
    static boolean[] visited;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for(int testCase = 1; testCase <= T; testCase++) {
            N = Integer.parseInt(br.readLine());

            st = new StringTokenizer(br.readLine());

            company = new int[2];
            home = new int[2];
            clients = new int[N][2];
            visited = new boolean[N];

            company[0] = Integer.parseInt(st.nextToken());
            company[1] = Integer.parseInt(st.nextToken());

            home[0] = Integer.parseInt(st.nextToken());
            home[1] = Integer.parseInt(st.nextToken());

            for(int i = 0; i < N; i++) {
                clients[i][0] = Integer.parseInt(st.nextToken());
                clients[i][1] = Integer.parseInt(st.nextToken());
            }

            min = Integer.MAX_VALUE;

            for(int i = 0; i < N; i++) {
                visited[i] = true;
                DFS(1, i, calcDistance(company[0], company[1], clients[i][0], clients[i][1]));
                visited[i] = false;
            }

            sb.append("#").append(testCase).append(" ").append(min).append("\n");
        }

        System.out.println(sb);
    }

    static void DFS(int depth, int idx, int value) {
        if(depth == N) {
            min = Math.min(min, value + calcDistance(clients[idx][0], clients[idx][1], home[0], home[1]));

            return;
        }

        // 가지치기
        if(value >= min) return;

        for(int i = 0; i < N; i++) {
            if(!visited[i]) {
                visited[i] = true;
                DFS(depth + 1, i, value + calcDistance(clients[idx][0], clients[idx][1], clients[i][0], clients[i][1]));
                visited[i] = false;
            }
        }
    }

    static int calcDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }
}
