/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amen.algorithm;

import com.amen.common.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author AmeN
 */
public class NeedlemanWunsch {

    int resultsCount = 0;

    private final String sequence1;
    private final String sequence2;

    private Element[][] _scoreMatrix;
    private Double[][] similarityMatrix;

    public NeedlemanWunsch(String pSeq1, String pSeq2, Double[][] pMatrix) {
        sequence1 = pSeq1;
        sequence2 = pSeq2;
        similarityMatrix = pMatrix;
    }

    public void run() {
        evaluateScore();

        List<String> sequences = generateListOfSequences(sequence1.length(), sequence2.length(), null, null);
        System.out.println("SIMILARITY VALUE = " + _scoreMatrix[sequence1.length()][sequence2.length()].getValue());

        if (sequences != null) {
            for (String s : sequences) {
                System.out.println(s);
            }
        }
        System.out.println();
        ElementPrinter.printScoreMatrix(_scoreMatrix, sequence1, sequence2);
    }

    private void evaluateScore() {
        _scoreMatrix = new Element[sequence1.length() + 1][sequence2.length() + 1];

        for (int y = 0; y <= sequence1.length(); y++) {
            _scoreMatrix[y][0] = new Element();
            _scoreMatrix[y][0].setValue(Params.GAP_PENALTY * y);
        }

        for (int x = 0; x <= sequence2.length(); x++) {
            _scoreMatrix[0][x] = new Element();
            _scoreMatrix[0][x].setValue(Params.GAP_PENALTY * x);
        }

        for (int y = 1; y <= sequence1.length(); y++) {
            for (int x = 1; x <= sequence2.length(); x++) {
                _scoreMatrix[y][x] = new Element();
                double upLeftElementValue = _scoreMatrix[y - 1][x - 1].getValue();
                double upElementValue = _scoreMatrix[y - 1][x].getValue();
                double leftElementValue = _scoreMatrix[y][x - 1].getValue();
                double upLeftElementValueScore = upLeftElementValue + getSimilarityFactorForElements(sequence1.charAt(y - 1), sequence2.charAt(x - 1));
                double upElementValueScore = upElementValue + Params.GAP_PENALTY;
                double leftElementValueScore = leftElementValue + Params.GAP_PENALTY;
                final double max = Utils.max(upLeftElementValueScore, upElementValueScore, leftElementValueScore);
                _scoreMatrix[y][x].setValue(max);

                if (upLeftElementValueScore == max) {
                    _scoreMatrix[y][x].setDiagonal(true);
                } else if (upElementValueScore == max) {
                    _scoreMatrix[y][x].setUp(true);
                } else if (leftElementValueScore == max) {
                    _scoreMatrix[y][x].setLeft(true);
                }

            }
        }
    }

    private double getSimilarityFactorForElements(char first, char second) {
        return 0.0;
        //return similarityMatrix[elementValue(first)][elementValue(second)];
    }

    private int elementValue(char ch) {
        switch (ch) {
            case 'A':
                return 0;
            case 'C':
                return 1;
            case 'G':
                return 2;
            case 'T':
                return 3;
        }
        return -1;
    }

    private List<String> generateListOfSequences(int posX, int posY, String seq1, String seq2) {
        List<String> resultList = new ArrayList<String>();
        StringBuilder alignmentString1 = seq1 == null ? new StringBuilder() : new StringBuilder(seq1);
        StringBuilder alignmentString2 = seq2 == null ? new StringBuilder() : new StringBuilder(seq2);

        int seq1Pos = posX;
        int seq2Pos = posY;

        while (seq1Pos > 0 && seq2Pos > 0) {
            int count = findPossibilitiesCount(seq1Pos, seq2Pos);

            int t_posX = seq1Pos;
            int t_posY = seq2Pos;

            if (_scoreMatrix[t_posX][t_posY].isDiagonal()) {
                if (count > 1) {
                    resultList.addAll(generateListOfSequences(t_posX - 1, t_posY - 1,
                            new StringBuilder(alignmentString1).insert(0, sequence1.charAt(t_posX - 1)).toString(),
                            new StringBuilder(alignmentString2).insert(0, sequence2.charAt(t_posY - 1)).toString()));
                    count--;
                } else {
                    seq1Pos--;
                    seq2Pos--;
                    alignmentString1.insert(0, sequence1.charAt(seq1Pos));
                    alignmentString2.insert(0, sequence2.charAt(seq2Pos));
                }
            }

            if (_scoreMatrix[t_posX][t_posY].isLeft()) {
                if (count > 1) {
                    // Recursion call
                    resultList.addAll(generateListOfSequences(t_posX, t_posY - 1,
                            new StringBuilder(alignmentString1).insert(0, '-').toString(),
                            new StringBuilder(alignmentString2).insert(0, sequence2.charAt(t_posY - 1)).toString()));
                    count--;
                } else {
                    seq2Pos--;
                    alignmentString1.insert(0, '-');
                    alignmentString2.insert(0, sequence2.charAt(seq2Pos));
                }
            }

            if (_scoreMatrix[t_posX][t_posY].isUp()) {
                seq1Pos--;
                alignmentString1.insert(0, sequence1.charAt(seq1Pos));
                alignmentString2.insert(0, '-');
            }
        }

        while (seq1Pos > 0) {
            seq1Pos--;
            alignmentString1.insert(0, sequence1.charAt(seq1Pos));
            alignmentString2.insert(0, '-');
        }

        while (seq2Pos > 0) {
            seq2Pos--;
            alignmentString1.insert(0, '-');
            alignmentString2.insert(0, sequence2.charAt(seq2Pos));
        }

        resultList.add(alignmentString1.toString());
        resultList.add(alignmentString2.toString());

        resultsCount++;

        return resultList;
    }

    private int findPossibilitiesCount(int posX, int posY) {
        int count = 0;
        if (_scoreMatrix[posX][posY].isDiagonal()) {
            count++;
        }
        if (_scoreMatrix[posX][posY].isLeft()) {
            count++;
        }
        if (_scoreMatrix[posX][posY].isUp()) {
            count++;
        }
        return count;
    }
}
