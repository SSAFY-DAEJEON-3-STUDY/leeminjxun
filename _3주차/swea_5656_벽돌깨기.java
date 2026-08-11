package _3주차;

import java.io.*;
import java.util.*;

public class swea_5656_벽돌깨기 {
    static int N, W, H, min;

    static int[] dr = {0, 0, -1, 1};
    static int[] dc = {-1, 1, 0, 0};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for(int testCase = 1; testCase <= T; testCase++) {
            st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());
            H = Integer.parseInt(st.nextToken());

            int[][] grid = new int[H][W];

            for(int i = 0; i < H; i++) {
                st = new StringTokenizer(br.readLine());
                for(int j = 0; j < W; j++) {
                    grid[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            min = Integer.MAX_VALUE;

            DFS(0, copyGrid(grid));

            sb.append("#").append(testCase).append(" ").append(min == Integer.MAX_VALUE ? 0 : min).append("\n");
        }

        System.out.print(sb);
    }

    // 구슬 낙하 지점을 재귀를 통해 모든 경우의 수로 계산한다.
    static void DFS(int depth, int[][] grid) {
        if(depth == N) {
            min = Math.min(min, countWall(grid));
            return;
        }

        for(int w = 0; w < W; w++) {
            boolean isValid = false;
            int h = 0;
            for(h = 0; h < H; h++) {
                if(grid[h][w] != 0) {
                    isValid = true;
                    break;
                }
            }
            if(isValid) {
                DFS(depth + 1, BFS(copyGrid(grid), h, w));
            }
        }
    }
    // DFS 로 재귀 선택된 구슬 폭발에 대해 BFS 실행
    static int[][] BFS(int[][] grid, int r, int c) {
        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[] {r, c, grid[r][c]});

        grid[r][c] = 0;

        while(!q.isEmpty()) {
            int[] cur = q.poll();

            int tr = cur[0], tc = cur[1], cost = cur[2];

            for(int i = 1; i < cost; i++) {
                for(int dir = 0; dir < 4; dir++) {
                    int nr = tr + dr[dir] * i;
                    int nc = tc + dc[dir] * i;

                    if(nr < 0 || nc < 0 || nr >= H || nc >= W) continue;

                    if(grid[nr][nc] > 1) {
                        q.add(new int[] {nr, nc, grid[nr][nc]});
                    }

                    grid[nr][nc] = 0;
                }
            }
        }

        Deque<Integer> dq = new ArrayDeque<>();
        int[][] res = new int[H][W];

        for(int i = 0; i < W; i++) {
            for(int j = H - 1; j >= 0; j--) {
                if(grid[j][i] > 0) dq.add(grid[j][i]);
            }
            int idx = H;
            while(!dq.isEmpty()) {
                idx--;
                res[idx][i] = dq.poll();
            }
        }

        return res;
    }

    static int countWall(int[][] grid) {
        int cnt = 0;

        for(int[] r : grid) {
            for(int c : r) {
                if(c > 0) cnt++;
            }
        }

        return cnt;
    }

    // 깊은 복사를 위한 메서드
    // grid.clone() 는 안되는 이유
    // 2차원 함수에서 clone 함수 사용시 1차원 배열에 대한 깊은 복사만 일어난다.
    // 2차원 함수가 1차원 함수에 대한 주소값을 참조하기 때문
    static int[][] copyGrid(int[][] grid) {
        int[][] res = new int[H][W];
        for(int i = 0; i < H; i++) {
            res[i] = grid[i].clone();
        }

        return res;
    }
}
