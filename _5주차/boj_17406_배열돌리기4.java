package _5주차;

import java.io.*;
import java.util.*;

public class boj_17406_배열돌리기4 {

    // 좌 -> 하 -> 우 -> 상
    static int[] dr = {0, 1, 0, -1};
    static int[] dc = {1, 0, -1, 0};

    static int N, M, K, min;
    static int[][] A;
    static int[][] rotation;

    static boolean[] visited;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        A = new int[N][M];

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < M; j++) {
                A[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // r, c, s
        rotation = new int[K][3];
        visited = new boolean[K];

        for(int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());

            rotation[i][0] = Integer.parseInt(st.nextToken()) - 1;
            rotation[i][1] = Integer.parseInt(st.nextToken()) - 1;
            rotation[i][2] = Integer.parseInt(st.nextToken());
        }

        min = Integer.MAX_VALUE;

        DFS(0, new int[K]);

        System.out.print(min);
    }

    static void DFS(int depth, int[] sel) {
        if(depth == K) {
            // A 계산 함수

            int[][] arr = new int[N][M];
            for(int i = 0; i < N; i++) arr[i] = A[i].clone();

            int res = sum(rotate(arr, sel));

            min = Math.min(min, res);

            return;
        }

        for(int i = 0; i < K; i++) {
            if(!visited[i]) {
                visited[i] = true;
                sel[depth] = i;
                DFS(depth + 1, sel);
                visited[i] = false;
            }
        }

    }

    // 배열 돌리기
    static int[][] rotate(int[][] arr, int[] sel) {
        for(int i = 0; i < K; i++) {
            int r = rotation[sel[i]][0];
            int c = rotation[sel[i]][1];
            int s = rotation[sel[i]][2];

            for(int j = 1; j <= s; j++) {
                int nr = r - j;
                int nc = c - j;

                Deque<Integer> border = new ArrayDeque<>();

                for(int dir = 0; dir < 4; dir++) {
                    for(int idx = 0; idx < 2 * j; idx++) {
                        nr += dr[dir];
                        nc += dc[dir];

                        border.add(arr[nr][nc]);
                    }
                }

                border.addFirst(border.pollLast());

                for(int dir = 0; dir < 4; dir++) {
                    for(int idx = 0; idx < 2 * j; idx++) {
                        nr += dr[dir];
                        nc += dc[dir];

                        arr[nr][nc] = border.poll();
                    }
                }
            }
        }

        return arr;
    }

    // 배열 값 게산
    static int sum(int[][] arr) {
        int minSum = Integer.MAX_VALUE;

        for(int[] row : arr) {
            int sum = 0;
            for(int col : row) {
                sum += col;
            }

            minSum = Math.min(minSum, sum);
        }

        return minSum;
    }
}
