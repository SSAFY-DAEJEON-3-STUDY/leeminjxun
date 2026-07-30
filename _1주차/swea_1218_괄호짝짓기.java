package _1주차;

import java.io.*;
import java.util.*;


public class swea_1218_괄호짝짓기 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringBuilder sb = new StringBuilder();
        for(int testCase = 1; testCase <= 10; testCase++) {
            sb.append("#").append(testCase).append(" ");

            int N = Integer.parseInt(br.readLine());

            String s = br.readLine();

            Stack<String> stack = new Stack<>();

            for(int i = 0; i < N; i++) {
                String g = String.valueOf(s.charAt(i));

                if(stack.isEmpty()) {
                    stack.push(g);
                } else {
                    if((stack.peek().equals("(") && g.equals(")"))
                            || (stack.peek().equals("[") && g.equals("]"))
                            || (stack.peek().equals("{") && g.equals("}"))
                            || (stack.peek().equals("<") && g.equals(">"))) stack.pop();
                    else stack.push(g);
                }
            }

            if(stack.isEmpty()) sb.append("1");
            else sb.append("0");
            sb.append("\n");

        }

        System.out.print(sb);
    }
}