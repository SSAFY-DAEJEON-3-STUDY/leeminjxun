package _1주차;

import java.io.*;
import java.util.*;

public class swea_1249_보급로 {
    static int N, res;
    static int[][] map, dist;

    static int[] dx = {0, 0, -1, 1};
    static int[] dy = {-1, 1, 0, 0};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for(int testCase = 1; testCase <= T; testCase++) {
            N = Integer.parseInt(br.readLine());

            map = new int[N][N];
            // 경로의 최소 합을 저장하는 배열
            dist = new int[N][N];

            for(int i = 0; i < N; i++) {
                String s = br.readLine();
                for(int j = 0; j < s.length(); j++) {
                    map[i][j] = Integer.parseInt(String.valueOf(s.charAt(j)));
                }
            }

            for(int[] row : dist) Arrays.fill(row, Integer.MAX_VALUE);

            DFS(0, 0, 0);

            sb.append("#").append(testCase).append(" ").append(dist[N - 1][N- 1]).append("\n");

        }

        System.out.print(sb);
    }

    static void DFS(int x, int y, int sum) {
        for(int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if(nx < 0 || ny < 0 || nx >= N || ny >= N) continue;

            // 가치치기 >> 다음 dist 보다 현재 경로의 dist 가 작을 경우에만 재귀호출
            int nSum = sum + map[nx][ny];
            if(dist[nx][ny] > nSum) {
                // dist 최소합 갱신
                dist[nx][ny] = nSum;
                DFS(nx, ny, nSum);
            }

        }

    }
}