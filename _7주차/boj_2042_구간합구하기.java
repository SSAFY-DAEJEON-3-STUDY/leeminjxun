package _7주차;

import java.io.*;
import java.util.*;

public class boj_2042_구간합구하기 {
    static int N, M, K;
    static long[] tree;
    static int[] arr;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 원소의 개수
        N = Integer.parseInt(st.nextToken());
        // update 횟수
        M = Integer.parseInt(st.nextToken());
        // 구간 합 횟수
        K = Integer.parseInt(st.nextToken());

        arr = new int[N];
        tree = new long[4 * N];

        for(int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(br.readLine());
        }

        // segment tree init
        build(1, 0, N - 1);

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < M + K; i++) {
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            if(a == 1) {
                // b 인덱스를 c 의 값으로 Update
                update(1, 0, N - 1, b - 1, c);
            } else {
                // b 인덱스부터 c 인덱스 까지의 구간합 Query
                sb.append(query(1, 0, N - 1, b - 1, c - 1)).append("\n");
            }
        }

        System.out.print(sb);
    }

    static void build(int node, int start, int end) {
        if(start == end) {
            tree[node] = arr[start];

            return;
        }

        int mid = (start + end) / 2;
        build(node * 2, start, mid);
        build(node * 2 + 1, mid + 1, end);

        tree[node] = tree[node * 2] + tree[node * 2 + 1];
    }

    static long query(int node, int start, int end, int left, int right) {
        // 현재 노드의 구간 이 left, right 와 아예 겹치지 않는 경우
        if(left > end || right < start) return 0;

        // 현재 노드의 구간 이 left, right 범위 안에 있는 경우
        if(left <= start && end <= right) return tree[node];

        // 현재 노드의 구간이 left, right 범위에 걸쳐 있는 경우
        // -> 겹치지 않는 경우와 범위 안에 있는 경우로 나눠질 때 까지 이분탐색 진행
        int mid = (start + end) / 2;
        long leftNode = query(node * 2, start, mid, left, right);
        long rightNode = query(node * 2 + 1, mid + 1, end, left, right);
        return leftNode + rightNode;
    }

    static void update(int node, int start, int end, int index, int newValue) {
        if(start == end) {
            tree[node] = newValue;
            return;
        }

        int mid = (start + end) / 2;
        if(index <= mid) {
            // index 가 작거나 같은 경우 왼쪽 노드 를 탐색한다.
            update(node * 2, start, mid, index, newValue);
        } else {
            // index 가 더 큰 경우 오른쪽 노드를 탐색한다.
            update(node * 2 + 1, mid + 1, end, index, newValue);
        }

        tree[node] = tree[node * 2] + tree[node * 2 + 1];
    }
}
