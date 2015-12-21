/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amen.algorithm;

import com.amen.common.Utils;

import java.awt.*;
import java.util.Stack;

/**
 *
 * @author AmeN
 */
public class SmithWaterman {

    int resultsCount = 0;

    private final String sequence1;
    private final String sequence2;

    private Element[][] _scoreMatrix;
    private Double[][] similarityMatrix;

    public SmithWaterman(String seq1, String seq2, Double[][] pSimilarityMatrix) {
        sequence1 = seq1;
        sequence2 = seq2;

        similarityMatrix = pSimilarityMatrix;
    }

    public void run() {
        evaluateScore();
        ElementPrinter.printScoreMatrix(_scoreMatrix, sequence1, sequence2);
    }

    public String establishAlignmentString(String a, String b) {
        StringBuilder alignment = new StringBuilder();
        int tGaps = 0, tIden = 0, tMiss = 0;

        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();
        for (Character val_s1 : a.toCharArray()) {
            for (Character val_s2 : b.toCharArray()) {
                s1.append(val_s1);
                s2.append(val_s2);
                if (val_s1.equals(val_s2)) {
                    alignment.append('|');
                    tIden++;
                } else if (val_s1.equals('-') || val_s2.equals('-')) {
                    alignment.append(' ');
                    tGaps++;
                } else {
                    alignment.append(':');
                    tMiss++;
                }
            }
        }

        System.out.println(s1.toString());
        System.out.println(alignment.toString());
        System.out.println(s2.toString());
        System.out.println();
        System.out.println();
        System.out.println(a.toString());
        System.out.println(b.toString());
//        System.out.println(String.format("aligment result: {0} gaps, {1} ident, {2} missmatched", tGaps, tIden, tMiss));

        return alignment.toString();
    }

    private void evaluateScore() {
        _scoreMatrix = new Element[sequence1.length() + 1][sequence2.length() + 1];

        int gap, gapPenalty = Params.GAPPENALTY, gapLength = Params.GAPLENGTH, gapPenaltyExtension = Params.GAPEXTENSION;
        for (int i = 0; i <= sequence1.length(); i++) {
            for (int j = 0; j <= sequence2.length(); j++) {
                _scoreMatrix[i][j] = new Element();
                gap = gapPenalty + (gapLength - 1) * gapPenaltyExtension;
                if (i != 0 && j != 0) {
                    final double upLeftElementValue = _scoreMatrix[i - 1][j - 1].value;
                    final double upElementValue = _scoreMatrix[i - 1][j].value;
                    final double leftElementValue = _scoreMatrix[i][j - 1].value;
                    boolean elementsMatches = sequence1.charAt(i - 1) == sequence2.charAt(j - 1);
                    if (elementsMatches) {
                        gapLength = 0;
                        _scoreMatrix[i][j].value = Utils.max(
                                0.0,
                                upLeftElementValue + Params.MATCH,
                                upElementValue + gap,
                                leftElementValue + gap
                        );
                    } else {
                        gapLength++;
                        _scoreMatrix[i][j].value = Utils.max(
                                0.0,
                                upLeftElementValue + gap,
                                upElementValue + gap,
                                leftElementValue + gap
                        );
                    }
                }
            }
        }

        double longest = 0.0;
        int bestX = 0, bestY = 0;
        for (int i = 0; i <= sequence1.length(); i++) {
            for (int j = 0; j <= sequence2.length(); j++) {
                if (_scoreMatrix[i][j].value > longest) {
                    longest = _scoreMatrix[i][j].value;
                    bestX = i;
                    bestY = j;
                }
            }
        }

//        generateSequenceAlignment(bestX, bestY);
        traceback(bestX, bestY);
    }

    public void traceback(int bestX, int bestY) {
        StringBuilder line1 = new StringBuilder();
        StringBuilder line2 = new StringBuilder();

        Point lPosition = new Point(bestX, bestY);
        int lMove = evaluateMove(lPosition);
        while (lMove != 0) {
            if (lMove == 1) {
                line1.append(sequence1.charAt(lPosition.x - 1));
                line2.append(sequence2.charAt(lPosition.y - 1));
                lPosition.setLocation((lPosition.x - 1), (lPosition.y - 1));
            } else if (lMove == 1) {
                line1.append(sequence1.charAt(lPosition.x - 1));
                line2.append('-');
                lPosition.setLocation((lPosition.x - 1), (lPosition.y));
            } else {
                line1.append('-');
                line2.append(sequence2.charAt(lPosition.y - 1));
                lPosition.setLocation((lPosition.x), (lPosition.y - 1));
            }
            lMove = evaluateMove(lPosition);
        }
        line1.append(sequence1.charAt(lPosition.x - 1));
        line2.append(sequence2.charAt(lPosition.y - 1));

        System.out.println(line1);
        System.out.println(line2);

    }

    private int evaluateMove(Point lPosition) {
        double scoreDiag, scoreUp, scoreLeft;

        scoreDiag = _scoreMatrix[lPosition.x - 1][lPosition.y - 1].value;
        scoreLeft = _scoreMatrix[lPosition.x][lPosition.y - 1].value;
        scoreUp = _scoreMatrix[lPosition.x - 1][lPosition.y].value;

        if (scoreDiag >= scoreUp && scoreDiag >= scoreLeft) {
            return scoreDiag != 0 ? 1 : 0;
        } else if (scoreUp > scoreDiag && scoreUp >= scoreLeft) {
            return scoreUp != 0 ? 2 : 0;
        } else if (scoreLeft > scoreDiag && scoreLeft > scoreUp) {
            return scoreLeft != 0 ? 3 : 0;
        }


        return 0;
    }

    public void generateSequenceAlignment(int bestX, int bestY) {
        int currentX = bestX;
        int currentY = bestY;
        Stack<String> actions = new Stack<String>();

        while (currentX != 0 && currentY != 0) {
            if (Math.max(_scoreMatrix[currentX - 1][currentY - 1].value,
                    Math.max(_scoreMatrix[currentX - 1][currentY].value, _scoreMatrix[currentX][currentY - 1].value)) == _scoreMatrix[currentX - 1][currentY - 1].value) {
                actions.push("align");
                currentX = currentX - 1;
                currentY = currentY - 1;
            } else if (Math.max(_scoreMatrix[currentX - 1][currentY - 1].value,
                    Math.max(_scoreMatrix[currentX - 1][currentY].value, _scoreMatrix[currentX][currentY - 1].value)) == _scoreMatrix[currentX][currentY - 1].value) {
                actions.push("insert");
                currentY = currentY - 1;
            } else {
                actions.push("delete");
                currentX = currentX - 1;
            }
        }

        String alignOne = "";
        String alignTwo = "";

        Stack<String> backActions = (Stack<String>) actions.clone();
        for (int iterator = 0; iterator < sequence1.length(); iterator++) {
            alignOne = alignOne + sequence1.charAt(iterator);
            if (!actions.empty()) {
                String curAction = actions.pop();

                if (curAction.equals("insert")) {
                    alignOne = alignOne + "-";
                    while (actions.peek().equals("insert")) {
                        alignOne = alignOne + "-";
                        actions.pop();
                    }
                }
            }
        }

        for (int iterator = 0; iterator < sequence2.length(); iterator++) {
            alignTwo = alignTwo + sequence2.charAt(iterator);
            if (!backActions.empty()) {
                String curAction = backActions.pop();
                if (curAction.equals("delete")) {
                    alignTwo = alignTwo + "-";
                    while (backActions.peek().equals("delete")) {
                        alignTwo = alignTwo + "-";
                        backActions.pop();
                    }
                }
            }
        }
        System.out.println(">>>>");
//        System.out.println(alignOne);
        establishAlignmentString(alignOne, alignTwo);
//        System.out.println(alignTwo);
        System.out.println("<<<<");
    }
}
