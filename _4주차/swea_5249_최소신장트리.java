package _4주차;

import java.io.*;
import java.util.*;

public class swea_5249_최소신장트리 {
    static int[] parent;

    static int find(int x) {
        if(parent[x] == x) return x;

        return parent[x] = find(parent[x]);
    }

    static boolean union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);

        if(rootA == rootB) return false;

        parent[rootA] = rootB;
        return true;
    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for(int testCase = 1; testCase <= T; testCase++) {
            st = new StringTokenizer(br.readLine());
            int V = Integer.parseInt(st.nextToken());
            int E = Integer.parseInt(st.nextToken());

            int[][] Edges = new int[E][3];

            for(int i = 0; i < E; i++) {
                st = new StringTokenizer(br.readLine());

                Edges[i][0] = Integer.parseInt(st.nextToken());
                Edges[i][1] = Integer.parseInt(st.nextToken());
                Edges[i][2] = Integer.parseInt(st.nextToken());
            }

            Arrays.sort(Edges, (a, b) -> a[2] - b[2]);

            parent = new int[V + 1];

            for(int i = 0; i <= V; i++) parent[i] = i;

            int mstWeight = 0;
            int usedEdges = 0;

            for(int i = 0; i < E; i++) {
                int u = Edges[i][0], v = Edges[i][1], weight = Edges[i][2];

                if(union(u, v)) {
                    mstWeight += weight;
                    usedEdges++;

                    if(usedEdges == V) break;
                }
            }

            sb.append("#").append(testCase).append(" ").append(mstWeight).append("\n");
        }

        System.out.print(sb);
    }
}
