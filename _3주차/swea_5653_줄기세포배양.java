package _3주차;

import java.io.*;
import java.util.*;

// 줄기 세포에는 다음과 같은 상태 존재

/**
 * - 비활성화 상태 : 배양 후 초기 상태
 * - 할성화 상태 : 초기 비활성 상태 (createTime) + 생명력 시간 후에 활성화 상태로 변한다.
 *              이후 활성화된 세포는 1시간 뒤 상 하 좌 우 로 번식을 시도한다.
 * - 죽은 상태 : 활성화 상태 (생명력 시간) 이 끝난 후 세포는 죽은 상태가 됨.
 */

public class swea_5653_줄기세포배양 {
    static int[] dr = {0, 0, -1, 1};
    static int[] dc = {-1, 1, 0, 0};

    static class Cell {
        int r, c;
        int x;
        int activeTime;
        int deadTime;

        Cell(int r, int c, int x, int createTime) {
            this.r = r;
            this.c = c;
            this.x = x;
            // 번식 이후 x 시간 후 활성화
            this.activeTime = createTime + x;
            // 활성화 상태 이후 x 시간 후 죽은 상태
            this.deadTime = createTime + x * 2;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for(int testCase = 1; testCase <= T; testCase++) {
            st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken()); // 세포 배양 row
            int M = Integer.parseInt(st.nextToken()); // 세포 배양 col
            int K = Integer.parseInt(st.nextToken()); // 세포 번식 시간

            // cell 의 생명력이 1 이여도 번식까지 시간은 최소 2 -> 한 방향으로 K / 2 만큼 번식하므로 양방향으로 K 만큼 공간 부여
            int maxR = N + K + 2;
            int maxC = M + K + 2;

            // (maxR - N) / 2 -> ( (N + K + 2) - N ) / 2 = (K / 2) + 1
            int setR = K / 2 + 1;
            int setC = K / 2 + 1;

            int[][] map = new int[maxR][maxC];

            Queue<Cell> activeQ = new ArrayDeque<>();
            Queue<Cell> inActiveQ = new ArrayDeque<>();

            for(int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for(int j = 0; j < M; j++) {
                    int v = Integer.parseInt(st.nextToken());
                    // 초기 배양 세포들 비활성화 큐에 입력
                    if(v > 0) {
                        int nr = i + setR;
                        int nc = j + setC;
                        map[nr][nc] = v;
                        inActiveQ.add(new Cell(nr, nc, v, 0));
                    }
                }
            }

            for(int t = 1; t <= K; t++) {
                // 동시 번식을 제거하기 위한 생명력 우선순위가 높은 Cell 이 Map 선점
                PriorityQueue<Cell> pq = new PriorityQueue<>((a, b) -> b.x - a.x);

                // 1. 활성화 세포 pq 에 입력 로직
                int activeSize = activeQ.size();
                for(int i = 0; i < activeSize; i++) {
                    // cell 꺼내기
                    Cell cell = activeQ.poll();

                    // 활성화 되고 1초 뒤에 t 와 같다면
                    if(cell.activeTime + 1 == t) {
                        // pq 에 넣어 번식 준비
                        pq.add(cell);
                    }
                    // 활성화 상태가 끝나지 않았다면
                    if(cell.deadTime > t) {
                        // activeQ 에 유지 -> t 반복문이 끝나고 마지막 상태를 확인하기 위함
                        activeQ.add(cell);
                    }
                }

                int inActiveSize = inActiveQ.size();
                for(int i = 0; i < inActiveSize; i++) {
                    Cell cell = inActiveQ.poll();
                    
                    if(cell.activeTime == t) {
                        activeQ.add(cell);
                    } else {
                        inActiveQ.add(cell);
                    }
                }

                while(!pq.isEmpty()) {
                    Cell p = pq.poll();

                    for(int dir = 0; dir < 4; dir++) {
                        int nr = p.r + dr[dir];
                        int nc = p.c + dc[dir];

                        if(map[nr][nc] == 0) {
                            map[nr][nc] = p.x;
                            inActiveQ.add(new Cell(nr, nc, p.x, t));
                        }
                    }
                }

            }

            sb.append("#").append(testCase).append(" ").append(activeQ.size() + inActiveQ.size()).append("\n");

        }

        System.out.print(sb);
    }
}
