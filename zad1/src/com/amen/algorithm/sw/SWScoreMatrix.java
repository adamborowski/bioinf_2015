/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amen.algorithm.sw;

import com.amen.algorithm.sw.type.ProteinType;
import static com.amen.common.log.Log.Error;
import static com.amen.common.log.Log.Info;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author AmeN
 */
public class SWScoreMatrix {

    private final static int TOTAL_SPACE = 4;

    private final ArrayList<ProteinType> _seq1;
    private final ArrayList<ProteinType> _seq2;

    private final Integer[][] _score_matrix;
    private Integer _score_max;
    private Point _score_pos;

    private final int _rows;
    private final int _cols;
    private Integer score_match, score_notMatch, score_gap;

    public SWScoreMatrix(ArrayList<ProteinType> sequence_1, ArrayList<ProteinType> sequence_2) {
        _score_matrix = new Integer[sequence_1.size() + 1][sequence_2.size() + 1];

        _score_max = 0;

        _seq1 = sequence_1;
        _seq2 = sequence_2;

        _rows = sequence_1.size();
        _cols = sequence_2.size();

        _score_pos = null;
    }

    public void evaluateScoreMatrix(int p_match, int p_notMatch, int p_gap) {
        score_gap = p_gap;
        score_match = p_match;
        score_notMatch = p_notMatch;

        Integer t_score;
        for (int l_row = 0; l_row <= _rows; l_row++) {
            for (int l_col = 0; l_col <= _cols; l_col++) {
                if ((l_row == 0) || (l_col == 0)) {
                    _score_matrix[l_row][l_col] = 0;
                } else {
                    t_score = calculateScore(l_row, l_col);
                    if (t_score > _score_max) {
                        _score_max = t_score;
                        _score_pos = new Point(l_row, l_col);
                    }

                    _score_matrix[l_row][l_col] = t_score;
                }
            }
            //printScoreMatrix();
        }

        if (_score_pos == null) {
            Error(getClass(), "Highest score wasn't found.");
            System.exit(3);
        }
    }

    public Integer calculateScore(Integer p_x, Integer p_y) {
        Integer similarity = (_seq1.get(p_x - 1) == _seq2.get(p_y - 1)) ? score_match : score_notMatch;

        Integer scoreDiag = _score_matrix[p_x - 1][p_y - 1] + similarity;
        Integer scoreUp = _score_matrix[p_x - 1][p_y] + score_gap;
        Integer scoreLeft = _score_matrix[p_x][p_y - 1] + score_gap;

        Info(getClass(), "For x = " + p_x + " , y = " + p_y + " values are: [" + scoreDiag + ", " + scoreUp + ", " + scoreLeft + "]");
        return Math.max(0, Math.max(scoreDiag, Math.max(scoreUp, scoreLeft)));
    }

    public boolean evaluated() {
        return _score_pos != null;
    }

    public void printScoreMatrix() {
        System.out.println("Score matrix:");
        int t_whiteSpaceCnt;
        System.out.print("      ");
        for (int iterator = 0; iterator <= _cols; iterator++) {
            if (iterator == 0) {
                t_whiteSpaceCnt = TOTAL_SPACE - 1 - 1;
            } else {
                t_whiteSpaceCnt = TOTAL_SPACE - _seq2.get(iterator - 1).toString().length();
            }
            for (int it = 0; it < (t_whiteSpaceCnt); it++) {
                System.out.print(" ");
            }
            if (iterator == 0) {
                System.out.print('-');
            } else {
                System.out.print(_seq2.get(iterator - 1).toString());
            }
        }
        System.out.println("");

        for (int l_row = 0; l_row <= _rows; l_row++) {
            if (l_row == 0) {
                t_whiteSpaceCnt = TOTAL_SPACE - 1 - 1;
                System.out.print('-');
            } else {
                t_whiteSpaceCnt = TOTAL_SPACE - _seq1.get(l_row - 1).toString().length() - 1;
                System.out.print(_seq1.get(l_row - 1).toString());
            }
            for (int it = 0; it <= (t_whiteSpaceCnt); it++) {
                System.out.print(" ");
            }
            System.out.print(":");

            for (int l_col = 0; l_col <= _cols; l_col++) {
                t_whiteSpaceCnt = TOTAL_SPACE - String.valueOf(_score_matrix[l_row][l_col] == null ? -1 : _score_matrix[l_row][l_col]).length() - 1;
                for (int it = 0; it <= (t_whiteSpaceCnt); it++) {
                    System.out.print(" ");
                }
                System.out.print(String.valueOf(_score_matrix[l_row][l_col] == null ? -1 : _score_matrix[l_row][l_col]));
            }
            System.out.println("");
        }
    }

    public Integer[][] getScoreMatrix() {
        return _score_matrix;
    }

    public Integer getScoreMax() {
        return _score_max;
    }

    public Point getScorePos() {
        return _score_pos;
    }

    public ArrayList<ProteinType> getSeq1() {
        return _seq1;
    }

    public ArrayList<ProteinType> getSeq2() {
        return _seq2;
    }
}
