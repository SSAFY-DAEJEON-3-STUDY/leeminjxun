package _1주차;

import java.io.*;
import java.util.*;

public class swea_5658_보물상자비밀번호 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        HashSet<String> set;

        StringBuilder sb = new StringBuilder();
        for(int testCase = 1; testCase <= T; testCase++) {
            st = new StringTokenizer(br.readLine());

            int N = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());

            String s = br.readLine();

            Deque<String> dq = new ArrayDeque<>();

            for(char ch : s.toCharArray()) {
                dq.addLast(String.valueOf(ch));
            }

            set = new HashSet<>();

            for(int i = 0; i < 4; i++) {
                String pw = "";
                for(int j = 0; j < N / 4; j++) {
                    String str = String.valueOf(dq.poll());
                    dq.addLast(str);

                    pw += str;
                }
                set.add(pw);
            }

            for(int roof = 0; roof < N / 4; roof++) {
                dq.addLast(dq.poll());

                for(int i = 0; i < 4; i++) {
                    String pw = "";
                    for(int j = 0; j < N / 4; j++) {
                        String str = String.valueOf(dq.poll());
                        dq.addLast(str);

                        pw += str;
                    }
                    set.add(pw);
                }
            }

            List<String> list = new ArrayList<>();

            for(Object ss : set.toArray()) {
                list.add(String.valueOf(ss));
            }

            Collections.sort(list, Collections.reverseOrder());

            sb.append("#").append(testCase).append(" ").append(Integer.parseInt(list.get(K - 1), 16)).append("\n");
        }

        System.out.print(sb);
    }
}
