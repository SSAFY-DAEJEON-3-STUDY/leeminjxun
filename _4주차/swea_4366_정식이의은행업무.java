package _4주차;

import java.io.*;
import java.util.*;

public class swea_4366_정식이의은행업무 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for(int testCase = 1; testCase <= T; testCase++) {
            sb.append("#").append(testCase).append(" ");
            String bi = br.readLine();
            String tri = br.readLine();

            char[] biArr;
            char[] triArr;

            boolean isValid = false;

            Set<Long> set = new HashSet<>();

            for(int i = 0; i < bi.length(); i++) {
                biArr = bi.toCharArray();

                biArr[i] = biArr[i] == '1' ? '0' : '1';

                set.add(Long.parseLong(String.valueOf(biArr), 2));
            }

            for(int i = 0; i < tri.length(); i++) {
                triArr = tri.toCharArray();

                for(char c : new char[] {'0', '1', '2'}) {
                    if(tri.charAt(i) == c) continue;

                    triArr[i] = c;

                    if(set.contains(Long.parseLong(String.valueOf(triArr), 3))) {
                        isValid = true;
                        sb.append(Long.parseLong(String.valueOf(triArr), 3)).append("\n");
                        break;
                    }
                }

                if(isValid) {
                    break;
                }
            }
        }

        System.out.print(sb);
    }
}
