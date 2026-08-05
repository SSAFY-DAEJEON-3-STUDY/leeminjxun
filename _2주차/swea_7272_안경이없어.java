package _2주차;

import java.io.*;
import java.util.*;

public class swea_7272_안경이없어 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        char[] one = {'A', 'D', 'O', 'P', 'Q', 'R'};

        StringBuilder sb = new StringBuilder();
        for(int testCase = 1; testCase <= T; testCase++) {
            sb.append("#").append(testCase).append(" ");

            st = new StringTokenizer(br.readLine());

            String first = st.nextToken();
            int[] f = new int[first.length()];
            for(int i = 0; i < first.length(); i++) {
                boolean check = false;

                if(first.charAt(i) == 'B') {
                    f[i] = 2;
                    continue;
                }

                for(char c : one) {
                    if(first.charAt(i) == c) {
                        check = true;
                        f[i] = 1;
                    }
                }

                if(!check) f[i] = 0;
            }

            String second = st.nextToken();
            int[] s = new int[second.length()];
            for(int i = 0; i < second.length(); i++) {
                boolean check = false;

                if(second.charAt(i) == 'B') {
                    s[i] = 2;
                    continue;
                }

                for(char c : one) {
                    if(second.charAt(i) == c) {
                        check = true;
                        s[i] = 1;
                    }
                }

                if(!check) s[i] = 0;
            }

            if(first.length() != second.length()) {
                sb.append("DIFF").append("\n");
                continue;
            }

            boolean isSame = true;

            for(int i = 0; i < first.length(); i++) {
                if(f[i] != s[i]) {
                    isSame = false;
                    break;
                }
            }

            sb.append(isSame ? "SAME" : "DIFF").append("\n");
        }

        System.out.print(sb);
    }
}
