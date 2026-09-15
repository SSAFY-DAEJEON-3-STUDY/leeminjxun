package _6주차;

import java.io.*;
import java.util.*;

public class 암벽등반 {

    static int[] dr = {1, -1, 0, 0};
    static int[] dc = {0, 0, 1, -1};

    static int N, M;

    static int[][] map, dist;
    static int[] start, end;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for(int testCase = 1; testCase <= T; testCase++) {
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());

            map = new int[N][M];
            dist = new int[N][M];
            start = new int[2];
            end = new int[2];

            for(int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for(int j = 0; j < M; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());

                    if(map[i][j] == 2) {
                        start[0] = i;
                        start[1] = j;
                    }

                    if(map[i][j] == 3) {
                        end[0] = i;
                        end[1] = j;
                    }
                }
            }

            for(int[] a : dist) Arrays.fill(a, Integer.MAX_VALUE);

            dist[0][0] = 0;

        sb.append("#").append(testCase).append(" ").append(dijkstra()).append("\n");
        }

        System.out.print(sb);
    }

    static int dijkstra() {
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.add(new int[] {0, start[0], start[1]});

        while(!pq.isEmpty()) {
            int[] cur = pq.poll();

            int height = cur[0], r = cur[1], c = cur[2];

            for(int dir = 0; dir < 4; dir++) {
                int nr = r + dr[dir];
                int nc = c + dc[dir];

                if(nr < 0 || nc < 0 || nr >= N || nc >= M) continue;

                if(dir == 2 || dir == 3) {
                    if(map[nr][nc] == 0) continue;

                    if(dist[nr][nc] <= height) continue;

                    dist[nr][nc] = height;
                    pq.add(new int[] {height, nr, nc});
                }

                int cnt = 0;

                if(dir == 0) {
                    for(int idx = nr; idx < N; idx++) {
                        cnt++;

                        if(map[idx][nc] == 1 || map[idx][nc] == 3) {
                            nr = idx;

                            cnt = Math.max(cnt, height);

                            if(dist[nr][nc] <= cnt) break;

                            dist[nr][nc] = cnt;
                            pq.add(new int[] {cnt, nr, nc});
                            break;
                        }
                    }
                }

                if(dir == 1) {
                    for(int idx = nr; idx >= 0; idx--) {
                        cnt++;

                        if(map[idx][nc] == 1 || map[idx][nc] == 3) {
                            nr = idx;

                            cnt = Math.max(cnt, height);

                            if(dist[nr][nc] <= cnt) break;

                            dist[nr][nc] = cnt;
                            pq.add(new int[] {cnt, nr, nc});
                            break;
                        }
                    }
                }
            }
        }

        return dist[end[0]][end[1]];
    }
}
