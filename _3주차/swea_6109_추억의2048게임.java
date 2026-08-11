package _3주차;

import java.io.*;
import java.util.*;

public class swea_6109_추억의2048게임 {
    static int N;
    static int[][] grid;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for(int testCase = 1; testCase <= T; testCase++) {
            st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            String op = st.nextToken();

            grid = new int[N][N];

            for(int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for(int j = 0; j < N; j++) {
                    grid[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            if(op.equals("down")) grid = down();
            else if(op.equals("up")) grid = up();
            else if(op.equals("right")) grid = right();
            else grid = left();


            sb.append("#").append(testCase).append("\n");
            for(int[] g : grid) {
                for(int c : g) {
                    sb.append(c).append(" ");
                }
                sb.append("\n");
            }
        }

        System.out.print(sb);
    }

    static int[][] down() {
        Deque<Integer> dq = new ArrayDeque<>();

        int[][] slideGrid = new int[N][N];

        for(int i = 0; i < N; i++) {
            for(int j = N - 1; j >= 0; j--) {
                if(grid[j][i] != 0) {
                    dq.add(grid[j][i]);
                }
            }

            int idx = N;

            while(!dq.isEmpty()) {
                idx--;
                slideGrid[idx][i] = dq.poll();
                if(!dq.isEmpty() && dq.peek() == slideGrid[idx][i]) {
                    slideGrid[idx][i] += dq.poll();
                }
            }
        }

        return slideGrid;
    }

    static int[][] up() {
        Deque<Integer> dq = new ArrayDeque<>();

        int[][] slideGrid = new int[N][N];

        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if(grid[j][i] != 0) {
                    dq.add(grid[j][i]);
                }
            }

            int idx = 0;

            while(!dq.isEmpty()) {
                slideGrid[idx][i] = dq.poll();
                if(!dq.isEmpty() && dq.peek() == slideGrid[idx][i]) {
                    slideGrid[idx][i] += dq.poll();
                }
                idx++;
            }
        }

        return slideGrid;
    }

    static int[][] left() {
        Deque<Integer> dq = new ArrayDeque<>();

        int[][] slideGrid = new int[N][N];

        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if(grid[i][j] != 0) {
                    dq.add(grid[i][j]);
                }
            }

            int idx = 0;

            while(!dq.isEmpty()) {
                slideGrid[i][idx] = dq.poll();

                if(!dq.isEmpty() && dq.peek() == slideGrid[i][idx]) {
                    slideGrid[i][idx] += dq.poll();
                }
                idx++;
            }
        }

        return slideGrid;
    }

    static int[][] right() {
        Deque<Integer> dq = new ArrayDeque<>();

        int[][] slideGrid = new int[N][N];

        for(int i = 0; i < N; i++) {
            for(int j = N - 1; j >= 0; j--) {
                if(grid[i][j] != 0) {
                    dq.add(grid[i][j]);
                }
            }

            int idx = N;

            while(!dq.isEmpty()) {
                idx--;
                slideGrid[i][idx] = dq.poll();

                if(!dq.isEmpty() && dq.peek() == slideGrid[i][idx]) {
                    slideGrid[i][idx] += dq.poll();
                }
            }
        }

        return slideGrid;
    }
}
