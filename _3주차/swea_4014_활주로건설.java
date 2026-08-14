package _3주차;

import java.io.*;
import java.util.*;

public class swea_4014_활주로건설 {
    static int N, X;
    static int[][] grid;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for(int testCase = 1; testCase <= T; testCase++) {
            st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            X = Integer.parseInt(st.nextToken());

            grid = new int[N][N];

            for(int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for(int j = 0; j < N; j++) {
                    grid[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            int cnt = 0;

            for(int i = 0; i < N; i++) {
                if(canBuild(grid[i])) cnt++;
            }

            for(int i = 0; i < N; i++) {
                int[] col = new int[N];
                for(int j = 0; j < N; j++) {
                    col[j] = grid[j][i];
                }

                if(canBuild(col)) cnt++;
            }

            sb.append("#").append(testCase).append(" ").append(cnt).append("\n");
        }

        System.out.print(sb);
    }
    // line 에 따른 활주로 건설 가능 여부
    static boolean canBuild(int[] line) {
        boolean[] visited = new boolean[N];

        for(int i = 0; i < N - 1; i++) {
            // 높이가 같다면 통과
            if(line[i] == line[i + 1]) continue;

            // 높이가 다를 경우
            int diff = line[i] - line[i + 1];

            // 높이 차이가 1보다 클 경우
            if(Math.abs(diff) > 1) return false;

            // 높이 차이가 1인 경우
            // 내리막 경사로 설치
            if(diff == 1) {
                // i 를 기준 i + 1 부터 내리막 시작이다
                for(int j = i + 1; j <= i + X; j++) {
                    if(j >= N || line[i + 1] != line[j] || visited[j]) return false;
                    // 경사로 구간 설치
                    visited[j] = true;
                }
            }
            if(diff == -1) { // 오르막 경사로 설치
                // i 를 기준으로 i - X 만큼 뒤로 이동하면서 내리막 경사로 설치
                for(int j = i; j > i - X; j--) {
                    if(j < 0 || line[i] != line[j] || visited[j]) return false;
                    visited[j] = true;
                }
            }

        }

        return true;
    }
}
