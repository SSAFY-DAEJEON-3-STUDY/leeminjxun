package _3주차;

import java.io.*;
import java.util.*;

public class swea_4050_재관이의대량할인 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for(int testCase = 1; testCase <= T; testCase++) {
            int N = Integer.parseInt(br.readLine());

            PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);

            st = new StringTokenizer(br.readLine());
            for(int i = 0; i < N; i++) {
                pq.add(Integer.parseInt(st.nextToken()));
            }

            int idx = 0;
            int totalPrice = 0;

            while(!pq.isEmpty()) {
                int price = pq.poll();
                idx++;

                if(idx % 3 == 0) continue;

                totalPrice += price;

            }

            sb.append("#").append(testCase).append(" ").append(totalPrice).append("\n");

        }

        System.out.print(sb);
    }
}
