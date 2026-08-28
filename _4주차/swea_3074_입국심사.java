package _4주차;

import java.io.*;
import java.util.*;

public class swea_3074_입국심사 {

    static int N, M;
    static long minTime;
    static long[] time;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for(int testCase = 1; testCase <= T; testCase++) {
            st = new StringTokenizer(br.readLine());

            // 입국 심사대 개수
            N = Integer.parseInt(st.nextToken());
            // 입국 대기자 수
            M = Integer.parseInt(st.nextToken());

            time = new long[N];
            minTime = Integer.MAX_VALUE;

            for(int i = 0; i < N; i++) {
                time[i] = Integer.parseInt(br.readLine());
                minTime = Math.min(minTime, time[i]);
            }

            sb.append("#").append(testCase).append(" ").append(solve()).append("\n");
        }

        System.out.print(sb);
    }

    static long solve() {
        long ans = 0;

        long low = 1;
        long high = M * minTime;

        while(low <= high) {
            long mid = low + (high - low) / 2;

            if(isValid(mid)) {
                ans = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return ans;
    }

    static boolean isValid(long T) {
        long count = 0;

        for(long t : time) {
            count += T / t;

            if(count >= M) return true;
        }

        return count >= M;
    }
}
