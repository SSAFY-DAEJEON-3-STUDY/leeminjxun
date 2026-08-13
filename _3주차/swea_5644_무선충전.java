package _3주차;

import java.io.*;
import java.util.*;

public class swea_5644_무선충전 {
    static class BC {
        int x, y, C, P;

        BC(int x, int y, int C, int P) {
            this.x = x;
            this.y = y;
            this.C = C;
            this.P = P;
        }
    }

    static int M, A;
    static int[] pathA, pathB;
    static BC[] bcList;

    static int[] dx = {0, 0, 1, 0, -1};
    static int[] dy = {0, -1, 0, 1, 0};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        StringBuilder sb= new StringBuilder();
        for(int testCase = 1; testCase <= T; testCase++) {
            st = new StringTokenizer(br.readLine());

            M = Integer.parseInt(st.nextToken());
            A = Integer.parseInt(st.nextToken());

            pathA = new int[M + 1];
            pathB = new int[M + 1];

            st = new StringTokenizer(br.readLine());
            for(int i = 1; i <= M; i++) {
                pathA[i] = Integer.parseInt(st.nextToken());
            }
            st = new StringTokenizer(br.readLine());
            for(int i = 1; i <= M; i++) {
                pathB[i] = Integer.parseInt(st.nextToken());
            }

            bcList = new BC[A];

            for(int i = 0; i < A; i++) {
                st = new StringTokenizer(br.readLine());

                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                int C = Integer.parseInt(st.nextToken());
                int P = Integer.parseInt(st.nextToken());

                bcList[i] = new BC(x, y, C, P);
            }

            int ax = 1, ay = 1;
            int bx = 10, by = 10;

            int totalCharge = 0;

            for(int i = 0; i <= M; i++) {
                ax += dx[pathA[i]];
                ay += dy[pathA[i]];
                bx += dx[pathB[i]];
                by += dy[pathB[i]];

                totalCharge += getCharge(ax, ay, bx, by);
            }

            sb.append("#").append(testCase).append(" ");
            sb.append(totalCharge).append("\n");
        }

        System.out.print(sb);
    }

    static int getCharge(int ax, int ay, int bx, int by) {
        List<Integer> validA = new ArrayList<>();
        List<Integer> validB = new ArrayList<>();

        for(int i = 0; i < A; i++) {
            if(getDistance(ax, ay, bcList[i].x, bcList[i].y) <= bcList[i].C) validA.add(i);

            if(getDistance(bx, by, bcList[i].x, bcList[i].y) <= bcList[i].C) validB.add(i);
        }

        int maxGaze = 0;

        if(validA.isEmpty() && validB.isEmpty()) return 0;

        if(!validA.isEmpty() && validB.isEmpty()) {
            for(int a : validA) {
                maxGaze = Math.max(maxGaze, bcList[a].P);
            }

            return maxGaze;
        }

        if(validA.isEmpty() && !validB.isEmpty()) {
            for(int b : validB) {
                maxGaze = Math.max(maxGaze, bcList[b].P);
            }

            return maxGaze;
        }

        for(int a : validA) {
            for(int b : validB) {
                int curGaze = 0;

                if(a == b) {
                    curGaze = bcList[a].P;
                } else {
                    curGaze = bcList[a].P + bcList[b].P;
                }

                maxGaze = Math.max(maxGaze, curGaze);
            }
        }

        return maxGaze;
    }

    static int getDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1-x2) + Math.abs(y1-y2);
    }
}
