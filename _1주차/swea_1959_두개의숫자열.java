package _1주차;

import java.io.*;
import java.util.*;

public class swea_1959_두개의숫자열 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for(int testCase = 1; testCase <= T; testCase++) {
            sb.append("#").append(testCase).append(" ");

            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            List<Integer> A = new ArrayList<>();
            List<Integer> B = new ArrayList<>();

            st = new StringTokenizer(br.readLine());
            for(int i = 0; i < a; i++) {
                A.add(Integer.parseInt(st.nextToken()));
            }

            st = new StringTokenizer(br.readLine());
            for(int i = 0; i < b; i++) {
                B.add(Integer.parseInt(st.nextToken()));
            }

            List<Integer> max, min;

            if(a > b) {
                max = A;
                min = B;
            } else {
                max = B;
                min = A;
            }

            int maxMul = Integer.MIN_VALUE;

            for(int i = 0; i <= max.size() - min.size(); i++) {
                int mul = 0;

                for(int j = 0; j < min.size(); j++) {
                    mul += max.get(j + i) * min.get(j);
                }

                maxMul = Math.max(maxMul, mul);
            }

            sb.append(maxMul).append("\n");
        }

        System.out.print(sb);
    }
}