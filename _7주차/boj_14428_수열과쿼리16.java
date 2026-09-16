package _7주차;

import java.io.*;
import java.util.*;

public class boj_14428_수열과쿼리16 {
    static int N, M;
    static int[] arr;
    static int[] tree;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        arr = new int[N + 1];
        tree = new int[4 * N];

        st = new StringTokenizer(br.readLine());
        for(int i = 1; i <= N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        build(1, 1, N);

        M = Integer.parseInt(br.readLine());
        for(int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            if(a == 1) {
                // b 인덱스에 해당하는 노드 값을 c 로 update
                update(1, 1, N, b, c);
            } else {
                // (b ~ c) 범위 중 최솟값
                sb.append(query(1, 1, N, b, c)).append("\n");
            }
        }

        System.out.print(sb);
    }

    static void build(int node, int start, int end) {
        if(start == end) {
            // 인덱스 값을 비교해야하기 때문 tree 의 value 는 index 로
            tree[node] = start;
            return;
        }

        int mid = (start + end) / 2;
        build(node * 2, start, mid);
        build(node * 2 + 1, mid + 1, end);

        int leftIdx = tree[node * 2];
        int rightIdx = tree[node * 2 + 1];

        if(arr[leftIdx] == arr[rightIdx]) tree[node] = Math.min(leftIdx, rightIdx);
        else tree[node] = arr[leftIdx] > arr[rightIdx] ? rightIdx : leftIdx;
    }

    static int query(int node, int start, int end, int left, int right) {
        // 구간에 존재하지 않을 경우 -1 을 반환
        if(left > end || right < start) return -1;

        if(left <= start && end <= right) return tree[node];

        int mid = (start + end) / 2;

        int leftNode = query(node * 2, start, mid, left, right);
        int rightNode = query(node * 2 + 1, mid + 1, end, left, right);

        // -1 일 경우 오른쪽 또는 왼쪽 값을 반환한다.
        if(leftNode == -1) return rightNode;
        if(rightNode == -1) return leftNode;

        if(arr[leftNode] == arr[rightNode]) return Math.min(leftNode, rightNode);
        else return arr[leftNode] > arr[rightNode] ? rightNode : leftNode;
    }

    static void update(int node, int start, int end, int idx, int newValue) {
        if(start == end) {
            arr[start] = newValue;
            return;
        }

        int mid = (start + end) / 2;
        if(idx <= mid) {
            update(node * 2, start, mid, idx, newValue);
        } else {
            update(node * 2 + 1, mid + 1, end, idx, newValue);
        }

        int leftIdx = tree[node * 2];
        int rightIdx = tree[node * 2 + 1];

        if(arr[leftIdx] == arr[rightIdx]) tree[node] = Math.min(leftIdx, rightIdx);
        else tree[node] = arr[leftIdx] > arr[rightIdx] ? rightIdx : leftIdx;
    }
}