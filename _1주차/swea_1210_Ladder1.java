package _1주차;

import java.io.*;
import java.util.*;

public class swea_1210_Ladder1 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        StringBuilder sb = new StringBuilder();
        for(int testCase = 1; testCase <= 10; testCase++) {
            int tc = Integer.parseInt(br.readLine());

            sb.append("#").append(tc).append(" ");

            int[][] arr = new int[100][100];

            int targetX = 0;
            int targetY = 0;

            for(int i = 0; i < 100; i++) {
                st = new StringTokenizer(br.readLine());
                for(int j = 0; j < 100; j++) {
                    arr[i][j] = Integer.parseInt(st.nextToken());

                    if(arr[i][j] == 2) {
                        targetX = i;
                        targetY = j;
                    }
                }
            }
            // targetX 가 0 일 때 까지
            while(targetX != 0) {
                // 왼쪽 탐색
                if(0 <= targetY - 1 && arr[targetX][targetY - 1] == 1) {
                    while(true) {
                        if(targetY - 1 < 0) break;
                        if(arr[targetX][targetY  - 1] == 0) break;

                        targetY--;
                    }
                } else if(targetY + 1 < 100 && arr[targetX][targetY + 1] == 1) { // 오른쪽 탐색
                    while(true) {
                        if(targetY + 1 >= 100) break;
                        if(arr[targetX][targetY  + 1] == 0) break;

                        targetY++;
                    }
                }

                targetX--;

            }

            sb.append(targetY).append("\n");

            // target 에서 start 로 가기 위해서는
            // y 값 : 항상 - 1
            // x 값 : 좌우 이동
        }

        System.out.print(sb);
    }
}