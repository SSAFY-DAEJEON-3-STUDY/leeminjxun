package _7주차;

import java.io.*;
import java.util.*;

public class swea_1868_파핑파핑지뢰찾기 {
    // 8 방 탐색
    static int[] dr = {-1, 1, 0, 0, -1, -1, 1, 1};
    static int[] dc = {0, 0, -1, 1, 1, -1, 1, -1};

    static int N;
    static String[][] map;
    static boolean[][] visited;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int T = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for(int testCase = 1; testCase <= T; testCase++) {
            N = Integer.parseInt(br.readLine());

            map = new String[N][N];
            visited = new boolean[N][N];

            for(int i = 0; i < N; i++) {
                String s = br.readLine();
                for(int j = 0; j < N; j++) {
                    map[i][j] = String.valueOf(s.charAt(j));
                }
            }

            int clickCount = 0;

            for(int i = 0; i < N; i++) {
                for(int j = 0; j < N; j++) {
                    if(map[i][j].equals("*")) continue;

                    if(!visited[i][j] && check(i, j) == 0) {
                        visited[i][j] = true;
                        clickCount++;

                        BFS(i, j);
                    }
                }
            }

            for(int i = 0; i < N; i++) {
                for(int j = 0; j < N; j++) {
                    if(map[i][j].equals(".")) clickCount++;
                }
            }

            sb.append("#").append(testCase).append(" ").append(clickCount).append("\n");
        }

        System.out.print(sb);
    }

    static int check(int r, int c) {
        // 지뢰 개수
        int cnt = 0;

        for(int dir = 0; dir < 8; dir++) {
            int nr = r + dr[dir];
            int nc = c + dc[dir];

            if(nr < 0 || nc < 0 || nr >= N || nc >= N) continue;

            if(map[nr][nc].equals("*")) cnt++;
        }

        return cnt;
    }

    static void BFS(int r, int c) {
        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[] {r, c});

        while(!q.isEmpty()) {
            int[] cur = q.poll();

            // 지뢰의 개수 -> 0 일 때만 q 에 넣는다.
            int cnt = 0;
            for(int dir = 0; dir < 8; dir++) {
                int nr = cur[0] + dr[dir];
                int nc = cur[1] + dc[dir];

                if(nr < 0 || nc < 0 || nr >= N || nc >= N || visited[nr][nc]) continue;

                if(map[nr][nc].equals("*")) cnt++;
            }

            if(cnt == 0) {
                for(int dir = 0; dir < 8; dir++) {
                    int nr = cur[0] + dr[dir];
                    int nc = cur[1] + dc[dir];

                    if(nr < 0 || nc < 0 || nr >= N || nc >= N || visited[nr][nc]) continue;

                    visited[nr][nc] = true;
                    q.add(new int[] {nr, nc});
                }
            }

            map[cur[0]][cur[1]] = String.valueOf(cnt);
        }
    }
}
