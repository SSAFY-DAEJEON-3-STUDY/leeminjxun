package _1주차;

import java.io.*;
import java.util.*;

public class swea_3421_수제버거장인 {
    static int N, M, cnt;
    static boolean[][] badComb;

    static boolean[] visited;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for(int testCase = 1; testCase <= T; testCase++) {
            st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());

            badComb = new boolean[N + 1][N + 1];

            for(int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());

                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());

                badComb[a][b] = true;
                badComb[b][a] = true;
            }

            visited = new boolean[N + 1];
            cnt = 0;

            DFS(1);

            sb.append("#").append(testCase).append(" ").append(cnt).append("\n");
        }

        System.out.print(sb);
    }

    // 분기마다 고른 재료들을 넘겨주는 방식 >> visited
    static void DFS(int depth) {
        if(depth == N + 1) {
            cnt++;
            return;
        }

        // 현재 depth 에 해당하는 재료를 고르지 않는다.
        DFS(depth + 1);

        // 현재 depth 에 해당하는 재료를 고른다
        // >> 고르기 위해서는 badComb 조합을 확인해야한다.
        if(valid(depth)) {
            visited[depth] = true;
            DFS(depth + 1);
            visited[depth] = false;
        }
    }

    // idx 번째 재료를 고른다 가정한 상태로 모든 재료에 대한 조합을 비교한다.
    static boolean valid(int idx) {
        for(int i = 1; i <= N; i++) {
            // i 번째 재료를 고른 상태 & badComb 에 i 와 idx 조합이 있음
            // >> 해당 조합의 햄버거는 만들 수 없다.
            if(visited[i] && badComb[i][idx]) return false;
        }

        return true;
    }
}
