package _3주차;

import java.io.*;
import java.util.*;

public class swea_1234_비밀번호 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = 10;

        StringBuilder sb = new StringBuilder();
        for(int testCase = 1; testCase <= T; testCase++) {
            st = new StringTokenizer(br.readLine());

            int N = Integer.parseInt(st.nextToken());

            String S = st.nextToken();

            Deque<Integer> dq = new ArrayDeque<>();

            for(int i = 0; i < N; i++) {
                int num = Integer.parseInt(String.valueOf(S.charAt(i)));

                if(dq.isEmpty()) dq.offer(num);
                else {
                    if(dq.peekLast() == num) dq.pollLast();
                    else dq.offer(num);
                }
            }

            sb.append("#").append(testCase).append(" ");

            while(!dq.isEmpty()) sb.append(dq.poll());

            sb.append("\n");
        }

        System.out.print(sb);
    }
}
