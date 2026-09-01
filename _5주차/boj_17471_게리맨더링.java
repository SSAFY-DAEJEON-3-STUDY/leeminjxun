package _5주차;

import java.io.*;
import java.util.*;

public class boj_17471_게리맨더링 {
    static int N, M, aCount, bCount, minRes;

    static int[] region;
    static List<Integer>[] linkNode;

    static int[] parent;

    static boolean[] group;

    static int find(int x) {
        if(parent[x] == x) return x;

        return parent[x] = find(parent[x]);
    }

    static void union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);

        if(rootA != rootB) parent[rootA] = rootB;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());

        region = new int[N];
        group = new boolean[N];

        linkNode = new ArrayList[N];

        parent = new int[N];

        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++) {
            region[i] = Integer.parseInt(st.nextToken());
        }

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            M = Integer.parseInt(st.nextToken());

            linkNode[i] = new ArrayList<>();

            for(int j = 0; j < M; j++) {
                linkNode[i].add(Integer.parseInt(st.nextToken()) - 1);
            }
        }

        minRes = Integer.MAX_VALUE;

        DFS(0);

        System.out.print(minRes == Integer.MAX_VALUE ? -1 : minRes);
    }

    static void DFS(int idx) {
        if(idx == N) {
            check();

            return;
        }
        // A 에 포함
        group[idx] = true;
        DFS(idx + 1);

        // B 에 포함
        group[idx] = false;
        DFS(idx + 1);
    }

    static void check() {
        boolean hasA = false;
        boolean hasB = false;
        for(int i = 0; i < N; i++) {
            if(group[i]) hasA = true;
            else hasB = true;
        }

        if(!hasA || !hasB) return;

        for(int i = 0; i < N; i++) parent[i] = i;

        for(int u = 0; u < N; u++) {
            for(int v : linkNode[u]) {
                if(group[u] == group[v]) {
                    union(u, v);
                }
            }
        }

        Set<Integer> set = new HashSet<>();

        for(int i = 0; i < N; i++) {
            set.add(find(i));
        }

        if(set.size() != 2) return;

        aCount = 0; bCount = 0;

        for(int i = 0; i < N; i++) {
            if(group[i]) aCount += region[i];
            else bCount += region[i];
        }

        minRes = Math.min(minRes, Math.abs(aCount - bCount));

        return;
    }
}
