package _3주차;

import java.io.*;
import java.util.*;

public class swea_2805_농작물수확하기 {
    static int N;
    static int[][] grid;
    static boolean[][] visited;

    static int[] dr = {0, 0, -1, 1};
    static int[] dc = {-1, 1, 0, 0};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for(int testCase = 1; testCase <= T; testCase++) {
            N = Integer.parseInt(br.readLine());

            grid = new int[N][N];
            visited = new boolean[N][N];

            for(int i = 0; i < N; i++) {
                String s = br.readLine();
                for(int j = 0; j < s.length(); j++) {
                    grid[i][j] = Integer.parseInt(String.valueOf(s.charAt(j)));
                }
            }

            int mid = N / 2;
            int sum = 0;

            Queue<int[]> q = new ArrayDeque<>();
            q.add(new int[] {mid, mid});
            visited[mid][mid] = true;

            sum += grid[mid][mid];

            for(int i = 0; i < mid; i++) {
                int qSize = q.size();

                for(int j = 0; j < qSize; j++) {
                    int[] cur = q.poll();

                    int r = cur[0], c = cur[1];

                    for(int dir = 0; dir < 4; dir++) {
                        int nr = r + dr[dir];
                        int nc = c + dc[dir];

                        if(nr < 0 || nc < 0 || nr >= N || nc >= N || visited[nr][nc]) continue;

                        visited[nr][nc] = true;
                        sum += grid[nr][nc];
                        q.add(new int[] {nr, nc});
                    }
                }
            }

            sb.append("#").append(testCase).append(" ").append(sum).append("\n");
        }

        System.out.print(sb);
    }
}
