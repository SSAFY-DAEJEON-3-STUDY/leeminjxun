package _7주차;

import java.util.*;

public class programmers_선인장숨기기 {
    public int[] solution(int m, int n, int h, int w, int[][] drops) {
        int INF = drops.length + 1;

        int[][] map = new int[m][n];
        for(int[] row : map) Arrays.fill(row, INF);
        int idx = 1;
        for(int[] drop : drops) map[drop[0]][drop[1]] = idx++;

        // 슬라이딩 row, col 범위
        int rows = m - h + 1;
        int cols = n - w + 1;

        int[] dq = new int[Math.max(m, n)];

        int[][] rowMin = new int[m][cols];
        for(int i = 0; i < m; i++) {
            int head = 0; int tail = 0;
            for(int j = 0; j < n; j++) {
                while(head < tail && map[i][dq[tail - 1]] >= map[i][j]) tail--;
                dq[tail++] = j;
                // head 가 범위 밖으로 나갔을 경우
                // tail 과 근접하도록 따라간다.
                if(dq[head] <= j - w) head++;
                // 슬라이드 범위를 충족
                if(j >= w - 1) rowMin[i][j - w + 1] = map[i][dq[head]];
            }
        }

        int[][] colMin = new int[rows][cols];
        for(int j = 0; j < cols; j++) {
            int head = 0; int tail = 0;
            for(int i = 0; i < m; i++) {
                while(head < tail && rowMin[dq[tail - 1]][j] >= rowMin[i][j]) tail--;
                dq[tail++] = i;
                // dq[head] 가 범위 밖으로 나갔을 경우
                // tail 과 근접하도록 따라간다.
                if(dq[head] <= i - h) head++;
                // 슬라이드 범위를 충족
                if(i >= h - 1) colMin[i - h + 1][j] = rowMin[dq[head]][j];
            }
        }

        int best = -1;
        int[] ans = new int[2];
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                if(colMin[i][j] > best) {
                    best = colMin[i][j];
                    ans[0] = i;
                    ans[1] = j;
                }
            }
        }

        return ans;
    }
}
