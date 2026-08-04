package _2주차;

import java.io.*;
import java.util.*;

public class swea_1767_프로세서연결하기 {
    static int N, C, maxCore, minLength;
    static int[][] maxinos;
    static boolean[][] visited;
    static List<int[]> coreList;

    static int[] dx = {0, 0, -1, 1};
    static int[] dy = {-1, 1, 0, 0};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for(int testCase = 1; testCase <= T; testCase++) {
            N = Integer.parseInt(br.readLine());

            maxinos = new int[N][N];
            visited = new boolean[N][N];
            coreList = new ArrayList<>();

            int borderCoreCount = 0;

            for(int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for(int j = 0; j < N; j++) {
                    maxinos[i][j] = Integer.parseInt(st.nextToken());

                    if(maxinos[i][j] == 1) {
                        visited[i][j] = true;
                        if(i == 0 || j == 0 || i == N - 1 || j == N - 1) borderCoreCount++;
                        else coreList.add(new int[] {i, j});
                    }
                }
            }

            C = coreList.size();

            maxCore = 0;
            minLength = Integer.MAX_VALUE;

            DFS(0, 0, 0);

            sb.append("#").append(testCase).append(" ")
                    .append(minLength).append("\n");
        }

        System.out.print(sb);
    }

    static void DFS(int depth, int node, int length) {
        // 가지치기 -> 현재 고른 노드 수 에 나머지 노드를 전부 더 한 값이 maxCore 보다 작을 경우
        // -> 모든 경우의 수를 따져봐도 maxCore 를 갱신하지 못한다. -> 해당 재귀조건을 종료
        if(node + (C - depth) < maxCore) return;

        if(depth == C) {
            if(node > maxCore || (node == maxCore && minLength > length)) {
                maxCore = node;
                minLength = length;
            }
            return;
        }

        int cx = coreList.get(depth)[0]; int cy = coreList.get(depth)[1];

        for(int dir = 0; dir < 4; dir++) {
            List<int[]> path = new ArrayList<>();
            int x = cx; int y = cy;
            boolean isValid = true;

            while(true) {
                x += dx[dir];
                y += dy[dir];

                if(x < 0 || y < 0 || x >= N || y >= N) { isValid = false; break; }
                if(visited[x][y]) { isValid = false; break; }
                path.add(new int[] {x, y});
                if(x == 0 || y == 0 || x == N - 1 || y == N - 1) break;
            }

            if(isValid) {
                for(int[] p : path) visited[p[0]][p[1]] = true;
                DFS(depth + 1, node + 1, length + path.size());
                for(int[] p : path) visited[p[0]][p[1]] = false;
            }
        }

        DFS(depth + 1, node, length);
    }
}
