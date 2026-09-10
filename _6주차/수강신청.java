package _6주차;

import java.io.*;
import java.util.*;

public class 수강신청 {

    static int N;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for(int testCase = 1; testCase <= T; testCase++) {
            N = Integer.parseInt(br.readLine());

            // 선수 과목 구조에 대한 Graph List, Graph.get(선수과목/자식노드).get(수강과목/부모노드)
            List<List<Integer>> graph = new LinkedList<>();
            for(int i = 0; i <= N; i++) {
                graph.add(new LinkedList<>());
            }

            // 진입차수 배열
            int[] inDegree = new int[N + 1];

            // 수강해야하는 과목 인덱스 i
            for(int i = 1; i <= N; i++) {
                st = new StringTokenizer(br.readLine());

                // 선수과목 개수
                int cnt = Integer.parseInt(st.nextToken());

                // 수강해야하는 과목에 대한 선수과목 진입차수
                inDegree[i] = cnt;

                for(int j = 0; j < inDegree[i]; j++) {

                    // prereq : 선수과목
                    int prereq = Integer.parseInt(st.nextToken());

                    graph.get(prereq).add(i);
                }
            }

            int res = solve(inDegree, graph);

            sb.append("#").append(testCase).append(" ").append(res).append("\n");
        }

        System.out.print(sb);
    }

    static int solve(int[] inDegree, List<List<Integer>> graph) {
        Deque<Integer> q = new ArrayDeque<>();

        // 진입차수가 0 인 수강 과목이 첫번째 학기에 실행된다.
        for(int i = 1; i <= N; i++) {
            if(inDegree[i] == 0) q.add(i);
        }

        // 수강한 과목의 개수
        int count = 0;

        // 학기
        int level = 0;

        while(!q.isEmpty()) {
            int levelSize = q.size();
            level++;

            for(int i = 0; i < levelSize; i++) {
                int cur = q.poll();
                count++;

                for(int node : graph.get(cur)) {
                    inDegree[node]--;

                    if(inDegree[node] == 0) {
                        q.add(node);
                    }
                }
            }
        }

        // 수강한 과목의 개수와 전체 과목 개수가 맞지 않다.
        // -> 사이클 구조로 어떠한 과목에 도달하지 못함
        if(count != N) return -1;

        return level;

    }
}
