package _1주차;

import java.io.*;
import java.util.*;

public class swea_1225_암호생성기 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        StringBuilder sb = new StringBuilder();
        for(int testCase = 1; testCase <= 10; testCase++) {
            int tc = Integer.parseInt(br.readLine());

            Deque<Integer> dq = new ArrayDeque<>();

            st = new StringTokenizer(br.readLine());
            for(int i = 0; i < 8; i++) {
                dq.addLast(Integer.parseInt(st.nextToken()));
            }

            int idx = 1;

            while(!dq.isEmpty() && dq.peekLast() > 0) {

                dq.addLast(dq.poll() - idx);

                idx = (idx + 1) % 6;

                if(idx == 0) idx = 1;
            }

            if(!dq.isEmpty() && dq.peekLast() < 0) {
                dq.pollLast();
                dq.addLast(0);
            }

            sb.append("#").append(testCase);

            while(!dq.isEmpty()) {
                sb.append(" ").append(dq.poll());
            }
            sb.append("\n");
        }

        System.out.print(sb);
    }
}
