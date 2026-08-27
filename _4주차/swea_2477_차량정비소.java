package _4주차;

import java.io.*;
import java.util.*;

public class swea_2477_차량정비소 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for(int testCase = 1; testCase <= T; testCase++) {
            st = new StringTokenizer(br.readLine());
            // 접수 창구 개수
            int N = Integer.parseInt(st.nextToken());
            // 정비 창구 개수
            int M = Integer.parseInt(st.nextToken());
            // 고객 수
            int K = Integer.parseInt(st.nextToken());
            // 지갑을 잃어버린 고객의 접수 창구 번호
            int A = Integer.parseInt(st.nextToken());
            // 지갑을 잃어버린 고객의 정비 창구 번호
            int B = Integer.parseInt(st.nextToken());

            // 접수 창구 당 일 처리 시간
            int[] receptionTime = new int[N + 1];
            // 정비 창구 당 일 처리 시간
            int[] repairTime = new int[M + 1];
            // 고객의 도착 시간
            int[] arrivalTime = new int[K + 1];

            st = new StringTokenizer(br.readLine());
            for(int i = 1; i <= N; i++) receptionTime[i] = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(br.readLine());
            for(int i = 1; i <= M; i++) repairTime[i] = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(br.readLine());
            for(int i = 1; i <= K; i++) arrivalTime[i] = Integer.parseInt(st.nextToken());

            int ans = solve(N, M, K, A, B, receptionTime, repairTime, arrivalTime);

            sb.append("#").append(testCase).append(" ").append(ans).append("\n");
        }

        System.out.print(sb);
    }

    static int solve(int N, int M, int K, int A, int B, int[] receptionTime, int[] repairTime, int[] arrivalTime) {

        // 고객 번호에 따른 접수 창구 번호
        int[] usedReception = new int[K + 1];

        // 고객 번호에 따른 정비 창구 번호
        int[] usedRepair = new int[K + 1];

        // 접수 창구 대기열 Queue -> 고객 번호 순으로 정렬
        PriorityQueue<Integer> receptionWait = new PriorityQueue<>();

        // 정비 창구 대기열 -> 먼저 온 순으로 정렬하되, 동시에 도착했을 경우 접수 창구 번호가 작은 순으로 정렬한다.
        // -> 위와 같은 조건 정렬을 위해 단일 Integer 가 아닌 int 배열을 사용한다.
        // new int[] {도착 시간, 접수 창구 번호, 고객 번호}
        PriorityQueue<int[]> repairWait = new PriorityQueue<>(
                (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]
        );

        // 현재 접수 창구 업무가 끝나는 시간, 0 이면 해당 창구가 비어져있다.
        int[] receptionEnd = new int[N + 1];
        // 현재 접수 창구에서 업무를 보고 있는 고객 번호
        int[] receptionCustomer = new int[N + 1];

        // 현재 정비 창구 업무가 끝나는 시간, 0 이면 해당 창구가 비어져있다.
        int[] repairEnd = new int[N + 1];
        // 현재 정비 창구에서 업무를 보고 있는 고객 번호
        int[] repairCustomer = new int[N + 1];

        int doneCount = 0;
        int t = 0;
        int maxTime = 10_000;

        while(doneCount < K && t <= maxTime) {

            // 정비 창구 완료 처리 -> doneCount++, usedRepair 처리
            for(int i = 1; i <= M; i++) {
                if(repairCustomer[i] != 0 && repairEnd[i] == t) {
                    int cust = repairCustomer[i];
                    doneCount++;
                    // cust 고객 번호에 해당하는 고객이 i 번에 해당하는 정비 창구를 이용했다.
                    usedRepair[cust] = i;
                    repairCustomer[i] = 0;
                }
            }

            // 접수 창구 완료 처리 -> repairWait 큐로 이동
            for(int i = 1; i <= N; i++) {
                if(receptionCustomer[i] != 0 && receptionEnd[i] == t) {
                    int cust = receptionCustomer[i];
                    usedReception[cust] = i;
                    // 접수 창구 처리가 완료되었으니 정비 창구로 이동한다.
                    repairWait.offer(new int[] {t, i, cust});
                    receptionCustomer[i] = 0;
                }
            }

            // 도착 고객 처리 -> receptionWait 큐로 이동
            for(int i = 1; i <= K; i++) {
                if(arrivalTime[i] == t) {
                    receptionWait.offer(i);
                }
            }

            // repairWait 처리 -> repairCustomer 처리
            while(!repairWait.isEmpty()) {
                int freeIdx = -1;
                for(int i = 1; i <= M; i++) {
                    if(repairCustomer[i] == 0) {
                        freeIdx = i; break;
                    }
                }

                if(freeIdx == -1) break;

                int[] head = repairWait.poll();
                int cust = head[2];

                repairCustomer[freeIdx] = cust;
                repairEnd[freeIdx] = t + repairTime[freeIdx];
            }

            // receptionWait 처리 -> receptionCustomer 처리
            while(!receptionWait.isEmpty()) {
                int freeIdx = -1;
                for(int i = 1; i <= N; i++) {
                    if(receptionCustomer[i] == 0) {
                        freeIdx = i; break;
                    }
                }

                if(freeIdx == -1) break;

                int cust = receptionWait.poll();

                receptionCustomer[freeIdx] = cust;
                receptionEnd[freeIdx] = t + receptionTime[freeIdx];
            }

            t++;
        }

        int result = 0;
        boolean found = false;

        for(int i = 1; i <= K; i++) {
            if(usedReception[i] == A && usedRepair[i] == B) {
                result += i;
                found = true;
            }
        }

        return found ? result : -1;

    }
}
