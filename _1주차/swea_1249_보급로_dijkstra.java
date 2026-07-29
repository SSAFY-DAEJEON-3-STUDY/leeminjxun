package _1주차;

import java.io.*;
import java.util.*;

public class swea_1249_보급로_dijkstra {
    static int N;
    static int[][] map , dist;

    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int T = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for(int testCase = 1; testCase <= T; testCase++) {
            N = Integer.parseInt(br.readLine());

            map = new int[N][N];
            dist = new int[N][N];

            for(int i = 0; i < N; i++) {
                String s = br.readLine();

                for(int j = 0; j < s.length(); j++) {
                    map[i][j] = Integer.parseInt(String.valueOf(s.charAt(j)));
                }
            }

            sb.append("#").append(testCase).append(" ").append(dijkstra()).append("\n");
        }

        System.out.print(sb);
    }

    static int dijkstra() {
        for(int[] row : dist) Arrays.fill(row, Integer.MAX_VALUE);
        dist[0][0] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.add(new int[] {0, 0, 0});

        while(!pq.isEmpty()) {
            int[] cur = pq.poll();

            int cost = cur[0]; int x = cur[1]; int y = cur[2];

            if(cost > dist[x][y]) continue;
            if(x == N - 1 && y == N - 1) return cost;

            for(int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];

                if(nx < 0 || ny < 0 || nx >= N || ny >= N) continue;

                int nCost = cost + map[nx][ny];
                if(dist[nx][ny] > nCost) {
                    dist[nx][ny] = nCost;
                    pq.add(new int[] {nCost, nx, ny});
                }
            }
        }


        return map[N - 1][N - 1];
    }
}
