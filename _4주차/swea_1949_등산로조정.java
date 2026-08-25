package _4주차;

import java.io.*;
import java.util.*;

public class swea_1949_등산로조정 {
    static int[] dr = {0, -1, 0, 1};
    static int[] dc = {-1, 0, 1, 0};

    static int N, K, MaxValue, MaxResult;
    static int[][] map;
    static boolean[][] visited;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for(int testCase = 1; testCase <= T; testCase++) {
            st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());

            map = new int[N][N];
            visited = new boolean[N][N];

            MaxValue = 0;

            for(int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for(int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                    MaxValue = Math.max(map[i][j], MaxValue);
                }
            }

            MaxResult = 1;

            for(int i = 0; i < N; i++) {
                for(int j = 0; j < N; j++) {
                    if(map[i][j] == MaxValue) {
                        visited[i][j] = true;
                        DFS(i, j, false, 1);
                        visited[i][j] = false;
                    }
                }
            }

            sb.append("#").append(testCase).append(" ").append(MaxResult).append("\n");
        }

        System.out.print(sb);
    }

    static void DFS(int r, int c, boolean isDig, int value) {
        for(int i = 0; i < 4; i++) {
            int nr = r + dr[i];
            int nc = c + dc[i];

            if(nr < 0 || nc < 0 || nr >= N || nc >= N || visited[nr][nc]) continue;

            if(map[r][c] > map[nr][nc]) {                   // map[nr][nc] 가 더 작은 경우
                visited[nr][nc] = true;
                DFS(nr, nc, isDig, value + 1);
                visited[nr][nc] = false;
            } else if(map[r][c] <= map[nr][nc] && !isDig) {  // 현재 값보다 다음 값이 더 크거나 같고 땅을 파지 않은 경우
                // 땅을 파서 이동할 수 있는지 확인
                int diff = map[nr][nc] - map[r][c];

                if(diff < K) {
                    map[nr][nc] = map[r][c] - 1;
                    visited[nr][nc] = true;
                    DFS(nr, nc, true, value + 1);
                    visited[nr][nc] = false;
                    map[nr][nc] = map[r][c] + diff;
                }
            }
        }

        MaxResult = Math.max(MaxResult, value);

        return;
    }
}
