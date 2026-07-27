package _1주차;

import java.io.*;
import java.util.*;

public class swea_1974_스도쿠검증 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for(int testCase = 1; testCase <= T; testCase++) {
            sb.append("#").append(testCase).append(" ");

            List<Integer>[] sudoku = new ArrayList[9];
            boolean[] check;

            for(int i = 0; i < 9; i++) {
                st = new StringTokenizer(br.readLine());

                sudoku[i] = new ArrayList<>();
                for(int j = 0; j < 9; j++) {
                    sudoku[i].add(Integer.parseInt(st.nextToken()) - 1);
                }
            }

            boolean isValid = true;

            // 가로 검증
            for(int i = 0; i < 9; i++) {
                check = new boolean[9];

                for(int s : sudoku[i]) {
                    if(check[s]) isValid = false;

                    check[s] = true;
                }
            }

            if(!isValid) {
                sb.append("0").append("\n");
                continue;
            }

            // 세로 검증
            for(int i = 0; i < 9; i++) {
                check = new boolean[9];

                for(List<Integer> sList : sudoku) {
                    if(check[sList.get(i)]) isValid = false;

                    check[sList.get(i)] = true;
                }
            }

            if(!isValid) {
                sb.append("0").append("\n");
                continue;
            }

            for(int i = 0; i <= 6; i += 3) {
                for(int j = 0; j <= 6; j += 3) {
                    check = new boolean[9];

                    for(int row = i; row < i + 3; row++) {
                        for(int col = j; col < j + 3; col++) {
                            if(check[sudoku[row].get(col)]) isValid = false;

                            check[sudoku[row].get(col)] = true;
                        }
                    }
                }
            }

            if(!isValid) {
                sb.append("0").append("\n");
                continue;
            }

            sb.append("1").append("\n");
        }

        System.out.print(sb);
    }
}
