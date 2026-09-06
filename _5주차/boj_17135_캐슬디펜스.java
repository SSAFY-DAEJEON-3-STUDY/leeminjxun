package _5주차;

import java.io.*;
import java.util.*;

public class boj_17135_캐슬디펜스 {
    static int N, M, D;
    static int[][] board;
    static int maxKill = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());

        board = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) board[i][j] = Integer.parseInt(st.nextToken());
        }

        dfs(0, new int[3], 0);
        System.out.println(maxKill);
    }

    // 열 인덱스를 오름차순으로만 골라서 중복 조합 방지
    static void dfs(int start, int[] chosen, int count) {
        if (count == 3) {
            maxKill = Math.max(maxKill, simulate(chosen));
            return;
        }
        for (int c = start; c < M; c++) {
            chosen[count] = c;
            dfs(c + 1, chosen, count + 1);
        }
    }

    static int simulate(int[] archerCols) {

        // 원본 배열을 복사해 적 움직임을 구현할 배열
        int[][] map = new int[N][M];
        for (int i = 0; i < N; i++) map[i] = board[i].clone();

        int kills = 0;

        while (true) {
            boolean anyMonster = false;
            for (int[] row : map)
                for (int v : row)
                    if (v == 1) anyMonster = true;
            if (!anyMonster) break;

            // 1) 각 궁수가 목표 하나씩 고른다
            Set<Integer> targets = new HashSet<>();
            for (int ac : archerCols) {
                int bestDist = Integer.MAX_VALUE, bestR = -1, bestC = -1;

                for (int r = 0; r < N; r++) {
                    for (int c = 0; c < M; c++) {
                        if (map[r][c] != 1) continue;

                        // 궁수는 N행 아래(성벽)에 위치 -> (N, ac) (r, c) 의 거리 계산
                        int dist = (N - r) + Math.abs(ac - c);
                        if (dist > D) continue;

                        // 1-1. 최소 dist 가 되는 값을 고른다.
                        // 1-2. 같은 dist 를 가질 경우, 가장 왼쪽(c 가 작은 값) 을 고른다.
                        if (dist < bestDist || (dist == bestDist && c < bestC)) {
                            bestDist = dist;
                            bestR = r;
                            bestC = c;
                        }
                    }
                }

                // dist 의 좌표값을 인코딩 한다
                // -> M 으로 나눌 경우 = bestR, M 으로 나머지 연산을 할 경우 = bestC
                if (bestR != -1) targets.add(bestR * M + bestC);
            }

            // 적을 죽였으므로, 0으로 처리한다.
            kills += targets.size();
            for (int t : targets) map[t / M][t % M] = 0;

            // 2) 몬스터 한 칸씩 아래로 이동 (마지막 줄에 있던 몬스터는 성에 도달해 사라짐)
            for (int r = N - 1; r >= 1; r--) {
                for (int c = 0; c < M; c++) {
                    map[r][c] = map[r - 1][c];
                }
            }
            Arrays.fill(map[0], 0);
        }

        return kills;
    }
}