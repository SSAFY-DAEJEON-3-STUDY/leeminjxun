package _1주차;

import java.io.*;
import java.util.*;

public class swea_1861_정사각형방 {
    static int N, Start, Max;

    static int[][] room;
    static boolean[][] visited;

    static int[] dx = {0, 0, -1, 1};
    static int[] dy = {-1, 1, 0, 0};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for(int testCase = 1; testCase <= T; testCase++) {
            N = Integer.parseInt(br.readLine());

            room = new int[N][N];

            for(int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());

                for(int j = 0; j < N; j++) {
                    room[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            Max = 0;
            Start = Integer.MAX_VALUE;

            for(int i = 0; i < N; i++) {
                for(int j = 0; j < N; j++) {
                    int res = BFS(i, j, 1);

                    if(res > Max) {
                        Max = res;
                        Start = room[i][j];
                    } else if (res == Max) {
                        Start = Math.min(Start, room[i][j]);
                    }
                }
            }

            sb.append("#").append(testCase).append(" ").append(Start).append(" ").append(Max).append("\n");
        }

        System.out.print(sb);
    }

    static int BFS(int x, int y, int value) {
        visited = new boolean[N][N];
        Queue<int []> q = new ArrayDeque<>();
        q.add(new int[] {x, y, value});
        visited[x][y] = true;

        int maxValue = 1;

        while(!q.isEmpty()) {
            int[] cur = q.poll();

            int curX = cur[0]; int curY = cur[1]; int currentValue = cur[2];

            for(int i = 0; i < 4; i++) {
                int nx = curX + dx[i];
                int ny = curY + dy[i];

                if(nx < 0 || ny < 0 || nx >= N || ny >= N) continue;
                if(room[nx][ny] - room[curX][curY] != 1) continue;
                if(visited[nx][ny]) continue;

                q.add(new int[] {nx, ny, currentValue + 1});

                maxValue = Math.max(maxValue, currentValue + 1);
            }
        }

        return maxValue;
    }
}
