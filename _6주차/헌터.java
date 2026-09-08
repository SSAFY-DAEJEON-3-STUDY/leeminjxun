package _5주차;

import java.io.*;
import java.util.*;

public class 헌터 {
    static int N, M, hunterX, hunterY, minDist;
    static int[][] map;
    static position[] pos;
    static boolean[] doneMonster, doneCustomer;

    static class position {
        int mx;
        int my;

        int cx;
        int cy;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for(int testCase = 1; testCase <= T; testCase++) {
            N = Integer.parseInt(br.readLine());

            hunterX = 0; hunterY = 0;

            map = new int[N][N];
            pos = new position[5];
            for(int i = 0; i < 5; i++) {
                pos[i] = new position();
            }

            M = 0;

            for(int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for(int j = 0; j < N; j++) {
                    int n = Integer.parseInt(st.nextToken());

                    int absN = Math.abs(n);
                    M = Math.max(M, absN);


                    if(Math.abs(n) != 0) {
                        if(n > 0) {
                            pos[absN].mx = i;
                            pos[absN].my = j;
                        }

                        if(n < 0) {
                            pos[absN].cx = i;
                            pos[absN].cy = j;
                        }
                    }
                }
            }

            doneMonster = new boolean[M + 1];
            doneCustomer = new boolean[M + 1];

            minDist = Integer.MAX_VALUE;

            dfs(0, 0, 0, 0);

            sb.append(minDist).append("\n");

        }

        System.out.print(sb);
    }

    static void dfs(int depth, int distance, int x, int y) {

        if(distance >= minDist) return;

        if(depth == 2 * M) {

            minDist = Math.min(minDist, distance);

            return ;
        }

        for(int i = 1; i <= M; i++) {
            if(!doneMonster[i]) {
                doneMonster[i] = true;

                int workTime = calcDist(x, y, pos[i].mx, pos[i].my);
                dfs(depth + 1, distance + workTime, pos[i].mx, pos[i].my);

                doneMonster[i] = false;
            }
        }

        for(int i = 1; i <= M; i++) {
            if(doneMonster[i] && !doneCustomer[i]) {
                doneCustomer[i] = true;

                int workTime = calcDist(x, y, pos[i].cx, pos[i].cy);
                dfs(depth + 1, distance + workTime, pos[i].cx, pos[i].cy);

                doneCustomer[i] = false;
            }
        }
    }

    static int calcDist(int x1, int y1, int x2, int y2) {
        int dist = Math.abs(x2 - x1) + Math.abs(y2 - y1);

        return dist;
    }
}
