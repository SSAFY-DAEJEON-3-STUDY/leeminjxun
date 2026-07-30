package _1주차;

import java.io.*;
import java.util.*;

public class swea_2819_격자판의숫자이어붙이기 {
    static String[][] board;

    static int[] dx = {0, 0, -1, 1};
    static int[] dy = {-1, 1, 0, 0};

    static Set<String> set;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for(int testCase = 1; testCase <= T; testCase++) {
            board = new String[4][4];
            set = new HashSet<>();

            for(int i = 0; i < 4; i++) {
                st = new StringTokenizer(br.readLine());

                for(int j = 0; j < 4; j++) {
                    board[i][j] = st.nextToken();
                }
            }

            for(int i = 0; i < 4; i++) {
                for(int j = 0; j < 4; j++) {
                    DFS(0, i, j, "");
                }
            }

            sb.append("#").append(testCase).append(" ").append(set.size()).append("\n");
        }

        System.out.print(sb);
    }

    static void DFS(int depth, int x, int y, String num) {
        if(depth == 7) {
            set.add(num);
            return;
        }

        for(int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if(nx < 0 || ny < 0 || nx >= 4 || ny >= 4) continue;

            DFS(depth + 1, nx, ny, num + board[x][y]);
        }
    }
}
