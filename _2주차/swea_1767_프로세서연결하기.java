package _2주차;

import java.io.*;
import java.util.*;

public class swea_1767_프로세서연결하기 {
    static int[] dr = {0, -1, 0, 1};
    static int[] dc = {-1, 0, 1, 0};

    static int N, C, maxCore, minLength;
    static boolean[][] visited;
    static List<int[]> coreList;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for(int testCase = 1; testCase <= T; testCase++) {
            N = Integer.parseInt(br.readLine());

            visited = new boolean[N][N];
            coreList = new ArrayList<>();

            for(int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for(int j = 0; j < N; j++) {
                    int v = Integer.parseInt(st.nextToken());
                    if(v == 1) {
                        visited[i][j] = true;
                        if(i == 0 || j == 0 || i == N - 1 || j == N - 1) continue;
                        coreList.add(new int[] {i, j});
                    }
                }
            }

            C = coreList.size();

            maxCore = 0;
            minLength = Integer.MAX_VALUE;

            DFS(0, 0, 0);

            sb.append("#").append(testCase).append(" ").append(minLength).append("\n");
        }

        System.out.println(sb);
    }

    static void DFS(int depth, int core, int length) {
        if(core + (C - core) < maxCore) return;

        if(depth == C) {
            if(core == maxCore) {
                minLength = Math.min(minLength, length);
            } else if(core > maxCore) {
                maxCore = core;
                minLength = length;
            }

            return;
        }

        for(int d = 0; d < 4; d++) {
            int nr = coreList.get(depth)[0];
            int nc = coreList.get(depth)[1];

            List<int[]> path = new ArrayList<>();

            boolean isValid = true;

            // 전선 또는 core 를 만나면 해당 선은 무조건 false
            while(true) {
                nr += dr[d];
                nc += dc[d];

                if(nr < 0 || nc < 0 || nr >= N || nc >= N) break;
                if(visited[nr][nc]) {
                    isValid = false;
                    break;
                }

                path.add(new int[] {nr, nc});
            }

            if(isValid) {
                for(int[] p : path) visited[p[0]][p[1]] = true;
                DFS(depth + 1, core + 1, length + path.size());
                for(int[] p : path) visited[p[0]][p[1]] = false;
            }
        }
        DFS(depth + 1, core, length);
    }
}
