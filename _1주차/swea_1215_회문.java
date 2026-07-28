package _1주차;

import java.io.*;
import java.util.*;

public class swea_1215_회문 {
    static int N;
    static String[][] block;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        StringBuilder sb = new StringBuilder();
        for(int testCase = 1; testCase <= 10; testCase++) {
            N = Integer.parseInt(br.readLine());

            block = new String[8][8];

            for(int i = 0; i < 8; i++) {
                String Str = br.readLine();

                for(int j = 0; j < Str.length(); j++) {
                    block[i][j] = String.valueOf(Str.charAt(j));
                }
            }

            int cnt = 0;

            for(int i = 0; i < 8; i++) {
                for(int j = 0; j <= 8 - N; j++) {
                    if(rowValid(i, j)) cnt++;

                }
            }

            for(int i = 0; i <= 8 - N; i++) {
                for(int j = 0; j < 8; j++) {
                    if(colValid(i, j)) cnt++;
                }
            }

            sb.append("#").append(testCase).append(" ").append(cnt).append("\n");

        }

        System.out.println(sb);
    }

    static boolean rowValid(int r, int c) {
        StringBuilder A = new StringBuilder();

        for(int i = 0; i < N; i++) {
            A.append(block[r][c + i]);
        }

        StringBuilder reverseA = new StringBuilder(A).reverse();

        return A.toString().equals(reverseA.toString());
    }

    static boolean colValid(int r, int c) {
        StringBuilder A = new StringBuilder();

        for(int i = 0; i < N; i++) {
            A.append(block[r + i][c]);
        }

        StringBuilder reverseA = new StringBuilder(A).reverse();

        return A.toString().equals(reverseA.toString());
    }
}
