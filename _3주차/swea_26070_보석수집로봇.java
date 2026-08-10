package _3주차;

import java.io.*;
import java.util.*;

public class swea_26070_보석수집로봇 {
    static int N;
    static int[] dr = {0, 1, 0, -1};
    static int[] dc = {1, 0, -1, 0};
    static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for(int testCase = 1; testCase <= T; testCase++) {
            N = Integer.parseInt(br.readLine());

            int[][] grid = new int[N][N];
            int M = 0;

            for(int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());

                for (int j = 0; j < N; j++) {
                    grid[i][j] = Integer.parseInt(st.nextToken());
                    if(grid[i][j] > M) M = grid[i][j];
                }
            }

            int[][] gemPos = new int[M + 1][2];

            for(int i = 0; i < N; i++) {
                for(int j = 0; j < N; j++) {
                    if(grid[i][j] > 0) {
                        gemPos[grid[i][j]][0] = i;
                        gemPos[grid[i][j]][1] = j;
                    }
                }
            }
            sb.append("#").append(testCase).append(" ").append(solve(gemPos, M)).append("\n");

        }

        System.out.print(sb);
    }

    static int solve(int[][] gemPos, int M) {
        Map<Integer, Integer> curSources = new HashMap<>();
        curSources.put(0 * 2 + 1, 0);    // Key : 시작 방향 과 회전 가능 여부를 인코딩 -> dir * 2 + canturn, 비용
                                        // 초기 시작 방향은 오른쪽 -> dir = 0 회전 없으므로 canTunr = 1
        // 시작 점은 (0,0)
        int curR = 0; int curC = 0;

        for(int gi = 1; gi <= M; gi++) {
            int tr = gemPos[gi][0]; int tc = gemPos[gi][1];
            int[][][][] dist = dijkstra(curR, curC, curSources);

            Map<Integer, Integer> newSources = new HashMap<>();
            for(int d = 0; d < 4; d++) {
                for(int ct = 0; ct < 2; ct++) {
                    int v = dist[tr][tc][d][ct];
                    if(v < INF) newSources.put(d * 2 + ct, v);
                }
            }

            curSources = newSources;
            curR = tr;
            curC = tc;
        }

        int ans = INF;
        for(int v : curSources.values()) ans = Math.min(ans, v);
        return ans;
    }

    static int[][][][] dijkstra(int sr, int sc, Map<Integer, Integer> sources) {
        int[][][][] dist = new int[N][N][4][2];
        for(int[][][] a : dist) {
            for(int[][] b : a) {
                for(int[] c : b) {
                    Arrays.fill(c, INF);
                }
            }
        }

        // pq 를 cost 값에 대해 내림차순으로 정렬하도록 함, (a, b) -> a[0] - b[0]
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);

        for(Map.Entry<Integer, Integer> e : sources.entrySet()) {
            int key = e.getKey();
            int cost = e.getValue();
            // solve 함수에서 인코딩한 Key 값에 대해 디코딩
            int dir = key / 2; int canTurn = key % 2;

            // dist 에 입력될 값들은 Map 의 key & Value 값이기에 중복 여부가 없지만, 방어적 조건문으로 추가
            // 중복이 없다 -> dist[sr][sc][dir][canTurn] 값은 cost 대입 이전에 INF 일 수 밖에 없다.
            if(cost < dist[sr][sc][dir][canTurn]) {
                dist[sr][sc][dir][canTurn] = cost;
                pq.offer(new int[] {cost, sr, sc, dir, canTurn});
            }
        }

        while(!pq.isEmpty()) {
            int[] cur = pq.poll();

            int cost = cur[0], r = cur[1], c = cur[2], dir = cur[3], canTurn = cur[4];

            // 현재 방향 경우의 cost 가 이전의 dist 값보다 큰 경우
            // -> 최솟값을 구하려는 목적과 맞지 않으므로 continue
            if(cost > dist[r][c][dir][canTurn]) continue;

            // 이동 간선 -> 회전 X 이므로 추가 비용은 0
            int nr = r + dr[dir], nc = c + dc[dir];
            if(nr >= 0 && nc >= 0 && nr < N && nc < N) {
                // 이동 이후의 좌표에 대한 dist 값이 현재 이동 전의 값보다 큰 경우 실행
                if(cost < dist[nr][nc][dir][1]) {
                    dist[nr][nc][dir][1] = cost;
                    pq.offer(new int[] {cost, nr, nc, dir, 1});
                }
            }

            // 회전 간선 -> 회전 O, 이동 X 이므로 추가 비용 + 1 -> canturn == 1 일 경우에만
            if(canTurn == 1) {
                // dir 회전
                dir = (dir + 1) % 4;
                // 현재 진행 중인 회전 간선이 기본의 dist 값보다 작은 경우에만 실행
                if(cost + 1 < dist[r][c][dir][0]) {
                    dist[r][c][dir][0] = cost + 1;
                    pq.offer(new int[] {cost + 1, r, c, dir, 0});
                }
            }
        }

        return dist;
    }

}
