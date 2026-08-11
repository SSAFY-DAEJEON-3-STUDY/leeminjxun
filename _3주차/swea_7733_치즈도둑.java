package _3주차;

import java.io.*;
import java.util.*;

public class swea_7733_치즈도둑 {
    static int N, maxCnt;
    static int[][] grid;
    static boolean[][] visited, melted;

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

            int maxDay = 0;

            for(int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for(int j = 0; j < N; j++) {
                    grid[i][j] = Integer.parseInt(st.nextToken());
                    maxDay = Math.max(maxDay, grid[i][j]);
                }
            }

            List<int[]>[] bucket = new List[maxDay];
            for(int i = 0; i < maxDay; i++) bucket[i] = new ArrayList<>();

            Set<Integer> isAlive = new HashSet<>();
            for(int i = 0; i < N; i++) {
                for(int j = 0; j < N; j++) {
                    bucket[grid[i][j] - 1].add(new int[] {i, j});
                    isAlive.add(i * N + j);
                }
            }

            maxCnt = 1;
            melted = new boolean[N][N];

            for(int day = 0; day < maxDay; day++) {
                if(bucket[day].isEmpty()) continue;

                for(int[] cell : bucket[day]) {
                    melted[cell[0]][cell[1]] = true;
                    isAlive.remove(cell[0] * N + cell[1]);
                }

                int cnt = 0;
                visited = new boolean[N][N];

                for(int id : isAlive) {
                    int r = id / N, c = id % N;
                    if(!visited[r][c]) {
                        BFS(r, c);
                        cnt++;
                    }
                }

                maxCnt = Math.max(maxCnt, cnt);

            }

            sb.append("#").append(testCase).append(" ").append(maxCnt).append("\n");
        }

        System.out.print(sb);
    }

    static void BFS(int row, int col) {
        Queue<int[]> q = new ArrayDeque<>();

        visited[row][col] = true;
        q.add(new int[] {row, col});

        while(!q.isEmpty()) {
            int[] cur = q.poll();

            int tr = cur[0], tc = cur[1];

            for(int dir = 0; dir < 4; dir++) {
                int nr = tr + dr[dir];
                int nc = tc + dc[dir];

                if(nr < 0 || nc < 0 || nr >= N || nc >= N) continue;
                if(melted[nr][nc] || visited[nr][nc]) continue;

                visited[nr][nc] = true;
                q.add(new int[] {nr, nc});
            }
        }

    }
}
