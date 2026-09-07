package _5주차;

import java.io.*;
import java.util.*;

public class boj_17070_파이프옮기기1 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());

        int[][] map = new int[N + 1][N + 1];

        for(int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 1; j <= N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 1. 가로로 놓여진 경우
        // 2. 세로로 놓어진 경우
        // 3. 대각선으로 놓여진 경우
        int[][][] dp = new int[N + 1][N + 1][3];

        // 1, 2 좌표에 가로 방향으로 파이프가 놓여져있음.
        dp[1][2][0] = 1;

        for(int i = 1; i <= N; i++) {
            for(int j = 2; j <= N; j++) {
                if(map[i][j] == 1) continue;

                // (i, j) 에 파이프가 가로로 놓아져있을 경우
                // 이전 (i, j - 1) 값에서 파이프가 가로로 온 경우와 대각선으로 온 경우
                dp[i][j][0] = Math.max(dp[i][j][0], dp[i][j - 1][0] + dp[i][j - 1][2]);

                // (i, j) 에 파이프가 세로로 놓아져있을 경우
                dp[i][j][1] = Math.max(dp[i][j][1], dp[i - 1][j][1] + dp[i - 1][j][2]);

                if(map[i][j - 1] == 1 || map[i - 1][j] == 1) continue;

                // (i, j) 에 파이프가 대각선으로 놓아져잇는 경우
                dp[i][j][2] = Math.max(dp[i][j][2], dp[i - 1][j - 1][0] + dp[i - 1][j - 1][1] + dp[i - 1][j - 1][2]);
            }
        }

        System.out.println(dp[N][N][0] + dp[N][N][1] + dp[N][N][2]);
    }
}

