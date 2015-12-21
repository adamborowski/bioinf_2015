/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amen.algorithm;

/**
 *
 * @author AmeN
 */
public class ElementPrinter {

    private final static int TOTAL_SPACE = 7;

    public static void printScoreMatrix(Element[][] _score_matrix, String _seq1, String _seq2) {
        int _rows = _seq1.length();
        int _cols = _seq2.length();

        System.out.println("Score matrix:");
        System.out.println("");
        int t_whiteSpaceCnt;
        //noinspection Duplicates
        for (int iterator = 0; iterator <= _cols + 1; iterator++) {
            t_whiteSpaceCnt = TOTAL_SPACE - 1;
            for (int it = 0; it < (t_whiteSpaceCnt + 1); it++) {
                System.out.print("─");
            }
        }
        System.out.print("─");
        System.out.println("");
        for (int iterator = 0; iterator < TOTAL_SPACE; iterator++) {
            System.out.print(" ");
        }
        for (int iterator = 0; iterator <= _cols; iterator++) {
            t_whiteSpaceCnt = TOTAL_SPACE - 1;
            for (int it = 0; it < (t_whiteSpaceCnt); it++) {
                System.out.print(" ");
            }
            if (iterator == 0) {
                System.out.print('-');
            } else {
                System.out.print(_seq2.charAt(iterator - 1));
            }
        }
        System.out.println("");
        //noinspection Duplicates
        for (int iterator = 0; iterator <= _cols + 1; iterator++) {
            t_whiteSpaceCnt = TOTAL_SPACE - 1;
            for (int it = 0; it < (t_whiteSpaceCnt + 1); it++) {
                if (iterator == 1 && it == 0) {
                    System.out.print("┬");
                } else {
                    System.out.print("─");
                }
            }
        }
        System.out.print("─");
        System.out.println("");

        for (int l_row = 0; l_row <= _rows; l_row++) {
            t_whiteSpaceCnt = TOTAL_SPACE - 2;
            if (l_row == 0) {
                System.out.print('-');
            } else {
                System.out.print(_seq1.charAt(l_row - 1));
            }
            for (int it = 0; it <= (t_whiteSpaceCnt); it++) {
                System.out.print(" ");
            }
            System.out.print("│");

            for (int l_col = 0; l_col <= _cols; l_col++) {
                t_whiteSpaceCnt = TOTAL_SPACE - String.valueOf(_score_matrix[l_row][l_col].value).length() - 1;
                for (int it = 0; it <= (t_whiteSpaceCnt); it++) {
                    System.out.print(" ");
                }
                System.out.print(String.valueOf(_score_matrix[l_row][l_col].value));
            }
            System.out.println("");
        }
        for (int iterator = 0; iterator <= _cols + 1; iterator++) {
            t_whiteSpaceCnt = TOTAL_SPACE - 1;
            for (int it = 0; it < (t_whiteSpaceCnt + 1); it++) {
                if (iterator == 1 && it == 0) {
                    System.out.print("┴");
                } else {
                    System.out.print("─");
                }
            }
        }
        System.out.print("─");
        System.out.println("");

    }
}
