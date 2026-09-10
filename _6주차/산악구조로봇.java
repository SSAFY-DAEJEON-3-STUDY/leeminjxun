package _6주차;

import java.io.*;
import java.util.*;

public class 산악구조로봇 {
    static int[] dr = {0, 1, 0, -1};
    static int[] dc = {1, 0, -1, 0};

    static int N, minGas;
    static int[][] arr, dist;
    static boolean[][] visited;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for(int testCase = 1; testCase <= T; testCase++) {
            N = Integer.parseInt(br.readLine());

            arr = new int[N][N];
            dist = new int[N][N];
            visited = new boolean[N][N];

            for(int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());

                for(int j = 0; j < N; j++) {
                    arr[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            for(int i = 0; i < N; i++) Arrays.fill(dist[i], Integer.MAX_VALUE);

            minGas = Integer.MAX_VALUE;

            dfs(0, 0, 0);

//			for(int i = 0; i < N; i++) {
//				for(int j = 0; j < N; j++) {
//					sb.append(dist[i][j] + " ");
//				}
//				sb.append("\n");
//			}

            sb.append("#").append(testCase).append(" ").append(dist[N - 1][N - 1]).append("\n");
        }

        System.out.print(sb);
    }

    static void dfs(int r, int c, int gas) {
        if(r == N - 1 && c == N - 1) {
            dist[r][c] = Math.min(dist[r][c], gas);


            // minGas = Math.min(minGas, gas);

            return ;
        }
        if(!visited[r][c]) {
            visited[r][c] = true;

            for(int dir = 0; dir < 4; dir++) {
                int nr = r + dr[dir];
                int nc = c + dc[dir];

                if(nr < 0 || nc < 0 || nr >= N || nc >= N || visited[nr][nc]) continue;

                int diff = arr[r][c] - arr[nr][nc];
                int nextGas = gas;

                if(diff > 0) {
                    nextGas += 0;
                } else if(diff < 0) {
                    nextGas += Math.abs(diff) * 2;
                } else {
                    nextGas += 1;
                }

                if(nextGas < dist[nr][nc]) {
                    dist[nr][nc] = nextGas;
                    dfs(nr, nc, nextGas);
                }
            }

            visited[r][c] = false;
        }

    }
}
