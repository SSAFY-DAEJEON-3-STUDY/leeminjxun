package _5주차;

import java.io.*;
import java.util.*;

public class boj_17472_다리만들기2 {

    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};

    // N x M, label 은 섬의 번호
    static int N, M, label;
    static int[][] map;
    static boolean[][] visited;

    // 가중치 저장 큐 -> 가중치가 낮은 순으로 정렬되어야하기에 우선순위 큐와 람다식 이용
    static PriorityQueue<int[]> edges;

    // MST 를 위한 union-find 로직
    static int[] parent;

    static int find(int x) {

        if(parent[x] == x) return parent[x];

        return parent[x] = find(parent[x]);
    }

    // 두섬이 이미 연결되있으면 false, 연결되어 있지 않다면 연결 후 true
    static boolean union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);

        if(rootA == rootB) return false;

        parent[rootA] = rootB;

        return true;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        visited = new boolean[N][M];

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        label = 1;

        // 라벨링 진행 (BFS)
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < M; j++) {
                if(!visited[i][j] && map[i][j] == 1) {
                    BFS(i, j, label);
                    label++;
                }
            }
        }

        // 라벨링 결과에 따른 가중치 탐색
        edges = new PriorityQueue<>((a, b) -> a[2] - b[2]);

        for(int i = 0; i < N; i++) {
            for(int j = 0; j < M; j++) {
                // 다리 건설 탐색
                if(map[i][j] != 0) {
                    searchIsland(i, j);
                }
            }
        }

        parent = new int[label];
        for(int i = 1; i < label; i++) {
            parent[i] = i;
        }

        int res = 0;
        int usedEdge = 0;

        int islandCount = label  - 1;

        while(!edges.isEmpty()) {
            int[] cur = edges.poll();

            int u = cur[0];
            int v = cur[1];
            int weight = cur[2];

            if(union(u, v)) {
                res += weight;
                usedEdge++;
            }
        }

        System.out.print(usedEdge == islandCount - 1 ? res : -1);


    }

    static void BFS(int x, int y, int label) {
        visited[x][y] = true;
        map[x][y] = label;

        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[] {x, y});

        while(!q.isEmpty()) {
            int[] cur = q.poll();

            for(int dir = 0; dir < 4; dir++) {
                int nx = cur[0] + dx[dir];
                int ny = cur[1] + dy[dir];

                // map 범위 초과 or map 값 0 or 방문 o
                if(nx < 0 || ny < 0 || nx >= N || ny >= M) continue;
                if(visited[nx][ny] || map[nx][ny] == 0) continue;
                visited[nx][ny] = true;
                map[nx][ny] = label;

                q.add(new int[] {nx, ny});
            }
        }
    }

    static void searchIsland(int x, int y) {
        for(int dir = 0; dir < 4; dir++) {
            int nx = x + dx[dir];
            int ny = y + dy[dir];

            if(nx < 0 || ny < 0 || nx >= N || ny >= M) continue;
            if(map[nx][ny] != 0) continue;

            int length = 1;

            while(true) {
                nx += dx[dir];
                ny += dy[dir];
                // 섬을 찾지 못하고 범위 밖으로 나감
                if(nx < 0 || ny < 0 || nx >= N || ny >= M) break;
                // 다른 섬 찾음 -> 0 이 아니고 시작 섬의 값과 같지 않은 경우
                if(map[nx][ny] != 0 && map[nx][ny] != map[x][y]) {
                    if(length >= 2) {
                        int aLabel = map[x][y];
                        int bLabel = map[nx][ny];

                        // 시작섬과 끝섬의 가중치 최소 비교
                        edges.add(new int[] {aLabel, bLabel, length});
                    }

                    break;
                }

                length++;
            }
        }
    }
}

