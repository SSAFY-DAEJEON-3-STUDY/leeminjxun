package _3주차;

import java.io.*;
import java.util.*;

public class swea_4013_특이한자석 {
    static int K;
    static int[][] rotation;
    static boolean[] visited;
    static ArrayList<Integer>[] list;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for(int testCase = 1; testCase <= T; testCase++) {
            // 회전 횟수
            K = Integer.parseInt(br.readLine());
            list = new ArrayList[4];
            rotation = new int[K][2];

            for(int i = 0; i < 4; i++) {
                list[i] = new ArrayList<>();

                st = new StringTokenizer(br.readLine());
                for(int j = 0; j < 8; j++) {
                    list[i].add(Integer.parseInt(st.nextToken()));
                }
            }

            for(int i = 0; i < K; i++) {
                st = new StringTokenizer(br.readLine());
                rotation[i][0] = Integer.parseInt(st.nextToken()) - 1;
                rotation[i][1] = Integer.parseInt(st.nextToken());
            }

            // 회전
            for(int r[] : rotation) {
                visited = new boolean[4];

                Queue<int[]> rotationQ = new ArrayDeque<>();
                int idx = r[0];
                int dir = r[1];

                visited[idx] = true;

                rotationQ.add(new int[] {idx, dir});

                while(!rotationQ.isEmpty()) {
                    int[] cur = rotationQ.poll();

                    int ri = cur[0];
                    int rd = cur[1];

                    int rightIdx = ri + 1;
                    int leftIdx = ri - 1;

                    if(rightIdx < 4 && !visited[rightIdx] && !list[ri].get(2).equals(list[rightIdx].get(6))) {
                        rotationQ.add(new int[] {rightIdx, rd * -1});
                        visited[rightIdx] = true;
                    }

                    if(leftIdx >= 0 && !visited[leftIdx] && !list[ri].get(6).equals(list[leftIdx].get(2))) {
                        rotationQ.add(new int[] {leftIdx, rd * -1});
                        visited[leftIdx] = true;
                    }

                    doRotation(ri, rd);
                }

            }

            int totalSum = 0;

            for(int i = 0; i < 4; i++) {
                totalSum += list[i].getFirst() * (1 << i);
            }

            sb.append("#").append(testCase).append(" ").append(totalSum).append("\n");
        }

        System.out.print(sb);
    }

    static void doRotation(int i, int dir) {
        if(dir == 1) {
            list[i].addFirst(list[i].getLast());
            list[i].removeLast();
        } else {
            list[i].addLast(list[i].getFirst());
            list[i].removeFirst();
        }
    }
}

/**
 * deque 사용으로 양방향 회전 구현?
 * 양 사이드 자석 분별 및 교체
 */
