package _3주차;

import java.io.*;
import java.util.*;

public class swea_5648_원자소멸시뮬레이션 {

    // map 크기 지정 -> 본래 2,000 이지만 0.5 초에 충돌 시뮬레이션을 위해 2배 늘림
    static final int INF = 4001;
    // 음수 인덱스 값 처리를 위한 offSet 변수
    static final int offSet = 1000;

    static int[] dr = {1, -1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    static class Atom {
        int r, c;
        int dir;
        int K;
        boolean isDead;

        Atom(int c, int r, int dir, int K) {
            this.c = (c + offSet) * 2;
            this.r = (r + offSet) * 2;
            this.dir = dir;
            this.K = K;
            this.isDead = false;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        int[][] map = new int[INF][INF];

        StringBuilder sb = new StringBuilder();
        for(int testCase = 1; testCase <= T; testCase++) {
            int N = Integer.parseInt(br.readLine());

            ArrayList<Atom> atoms = new ArrayList<>();

            for(int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());

                int c = Integer.parseInt(st.nextToken());
                int r = Integer.parseInt(st.nextToken());
                int dir = Integer.parseInt(st.nextToken());
                int k = Integer.parseInt(st.nextToken());

                atoms.add(new Atom(c, r, dir, k));
            }

            int totalEnergy = 0;

            // 원자는 최대 4000번 이동이 가능
            for(int i = 0; i < INF; i++) {
                if(atoms.isEmpty()) break;

                // 원자 이동 시뮬레이션
                for(Atom atom : atoms) {
                    atom.r += dr[atom.dir];
                    atom.c += dc[atom.dir];

                    if(atom.r < 0 || atom.c < 0 || atom.r >= INF || atom.c >= INF) {
                        atom.isDead = true;
                        continue;
                    }

                    // map 에 자신의 에너지 값 누적
                    map[atom.r][atom.c] += atom.K;
                }

                ArrayList<Atom> aliveAtoms = new ArrayList<>();

                // 각 원자 위치에 대한 map 값을 통해 충돌 여부 확인
                for(Atom atom : atoms) {
                    if(atom.isDead) continue;

                    if(map[atom.r][atom.c] > atom.K) {
                        totalEnergy += atom.K;
                        atom.isDead = true;
                    } else {
                        aliveAtoms.add(atom);
                    }
                }

                // map 원복
                for(Atom atom : atoms) {
                    if(atom.r >= 0 && atom.c >= 0 && atom.r < INF && atom.c < INF) {
                        map[atom.r][atom.c] = 0;
                    }
                }

                atoms = aliveAtoms;
            }

            sb.append("#").append(testCase).append(" ").append(totalEnergy).append("\n");
        }

        System.out.print(sb);
    }
}
