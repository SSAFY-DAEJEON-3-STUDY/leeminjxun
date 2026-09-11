package _6주차;

import java.io.*;
import java.util.*;

public class 사과먹기 {
    // 우 -> 하 -> 좌 -> 상
    static int[] dr = {0, 1, 0, -1};
    static int[] dc = {1, 0, -1, 0};

    static int N, appleCount;
    static final int INF = Integer.MAX_VALUE;

    static int[][] map, applePos;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for(int testCase = 1; testCase <= T; testCase++) {
            N = Integer.parseInt(br.readLine());

            appleCount = 0;

            map = new int[N][N];

            for(int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for(int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());

                    if(map[i][j] != 0) {
                        appleCount = Math.max(appleCount, map[i][j]);
                    }
                }
            }

            applePos = new int[appleCount + 1][2];

            // 각 사과 번호에 따른 좌표 값을 applePos 에 저장한다.
            for(int i = 0; i < N; i++) {
                for(int j = 0; j < N; j++) {
                    if(map[i][j] != 0) {
                        applePos[map[i][j]][0] = i;
                        applePos[map[i][j]][1] = j;
                    }
                }
            }

            sb.append("#").append(testCase).append(" ").append(solve()).append("\n");
        }

        System.out.print(sb);
    }

    static int solve() {
        // map 에 key 값은 dir 과 canturn 를 인코딩한 값, value 는 이동 좌료에 대한 비용(cost)을 담는다.
        // canturn (ct) 이 1 일 때 회전 가능, 0 일때 회전 불가능
        Map<Integer, Integer> Source = new HashMap<>();

        // 첫 시작값은 오른쪽 방향 에 회전이 가능하고(ct = 1), 비용이 0 이다.
        // Key 값 인코딩 : dir * 2 + ct
        Source.put(0 * 2 + 1, 0);

        int sr = 0, sc = 0;

        /**
         * sr, sc 는 StartRow, StartCol 으로 시작 좌표 값
         * Source 맵은 해당 좌표에 대해 방향과 회전 가능 여부를 인코딩한 KEY 와 그러한 row, col, dir, canturn 에 대한 cost 를 저장
         */

        // 해당 반복문에서는 appleCount 에 따라서 sr과 sc 를 갱신하고 새로운 목표값인 Source 또한 갱신한다.
        // 매 사과 번호에 대한 다익스트라를 적용해 각각의 최솟값을 구한다. 각 최솟값은 dist 배열에 저장된다.
        for(int i = 1; i <= appleCount; i++) {
            int[][][][] dist = dijkstra(sr, sc, Source);

            // 좌표 (sr,sc) 에 대해 목표가 되는, 도달해야하는 사과번호에 대한 좌표 nr, nc
            int nr = applePos[i][0], nc = applePos[i][1];
            Map<Integer, Integer> newSource = new HashMap<>();

            /**
             * 만약 1번 사과에서 2번 사과로 가기 위한 최소 경우를 찾기 위해서는
             * 1번에서 시작해서 2번 까지 가는 모든 경우의 수를 조회 해야한다.
             * 1~2 번의 최소 경우의 수만 고려하면 된다. 방향과 회전 이라는 조건이 있기 때문이다.
             *
             * 그렇기 때문에 아래 반복문에서는 경우의 수 결과값에 대해서 모든 방향 그리고 모든 회전 가능 여부를 고려하여 map 에 인코딩한다.
             */
            for(int dir = 0; dir < 4; dir++) {
                for(int ct = 0; ct < 2; ct++) {
                    int value = dist[nr][nc][dir][ct];
                    if(value < INF) newSource.put(dir * 2 + ct, value);
                }
            }

            // 다음 목표 좌표를 위한 시작 좌표와 Source 맵 갱신
            sr = nr; sc = nc;
            Source = newSource;
        }

        int ans = INF;

        for(int value : Source.values()) {
            ans = Math.min(ans, value);
        }

        return ans;
    }

    static int[][][][] dijkstra(int r, int c, Map<Integer, Integer> source) {
        // queue 를 생성해 r, c, dir, ct, value 값을 넣고
        // 현재 방향으로 가는 경우와 오른쪽 회전 후 이동하는 경우를 고려한다.

        // dist 는 row와 col 에 따른 모든 dir 그리고 canturn (회전가능 여부) 를 고려한 상태 배열이다.
        int[][][][] dist = new int[N][N][4][2];

        // 상태배열 중 최소를 찾아야하므로, 전체 값을 INF 로 초기화
        for(int[][][] x : dist) {
            for(int[][] y : x) {
                for(int[] z : y) Arrays.fill(z, INF);
            }
        }

        // PQ 의 배열 값은 다음과 같다.
        // new int[] {cost, row, col, dir, canturn}, canturn 은 줄여셔 ct 라 칭하기로 함
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);

        // solve 메서드에서 받은 source 를 통해 진행을 시작
        for(Map.Entry<Integer, Integer> map : source.entrySet()) {
            int key = map.getKey();
            int value = map.getValue();

            // dir 는 곱하기 2 로 인코딩 -> 나누기 2 하면 원본 나옴
            int dir = key / 2;
            // ct 는 더하기 로 인코딩 -> 2로 나머지 연산하면 원본 나옴
            int ct = key % 2;

            // 시작 값에 대한 dir, ct 를 DQ 에 담는다.
            pq.offer(new int[] {value, r, c, dir, ct});
            // 상태배열 값도 갱신해준다. -> 같은 값을 덮어쓰지 않기 위해
            dist[r][c][dir][ct] = value;
        }

        // DQ 순회 시작
        while(!pq.isEmpty()) {
            int[] cur = pq.poll();

            int cost = cur[0], tr = cur[1], tc = cur[2], dir = cur[3], ct = cur[4];
            // 위의 DQ 갱신 시에 아래의 조건을 고려하였기 때문에 이 조건문을 발동 될 경우가 거의 없음 -> 안전장치
            if(cost > dist[tr][tc][dir][ct]) continue;

            // 현재 방향으로 그냥 이동하는 경우 = 회전 가능 여부(ct) 가 0 이든 1 이든 상관이 없다.
            int nr = tr + dr[dir], nc = tc + dc[dir];
            if(nr >= 0 && nc >= 0 && nr < N && nc < N) {

                // 그냥 이동하는 경우, 해당 좌표에 대한 dist 값보다 작을 경우만 이동한다.
                if(cost < dist[nr][nc][dir][1]) {
                    // 이동했기때문에 ct 는 초기화 되어 1이 된다.
                    pq.offer(new int[] {cost, nr, nc, dir, 1});

                    // 이동이 가능할 경우, dist 값을 현재의 cost 로 갱신한다.
                    dist[nr][nc][dir][1] = cost;
                }
            }

            // 회전 하는 경우는 무조건 회전 가능 여부(ct) 가 1 이여야한다.
            if(ct == 1) {
                dir = (dir + 1) % 4;
                // 회전 했을 경우 dist 값과 비교하여 현재 회전한 경우가 더 작다면 DQ 에 현재 값을 넣어 갱신한다.
                if(cost + 1 < dist[tr][tc][dir][0]) {

                    // 회전 했기때문에 cost 는 + 1 을 해준다.
                    dist[tr][tc][dir][0] = cost + 1;

                    // 회전 했기때문에 ct 는 무조건 0 이다.
                    pq.offer(new int[] {cost + 1, tr, tc, dir, 0});
                }
            }
        }


        return dist;
    }
}
