/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amen.algorithm;

import com.amen.common.Utils;

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

    private void evaluateScore() {
        _scoreMatrix = new Element[sequence1.length() + 1][sequence2.length() + 1];

        int gap, gapPenalty = Params.GAP_PENALTY, gapLength = Params.GAP_LENGTH, gapPenaltyExtension = Params.GAP_PENALTY_EXTENSION;
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

        generateSequenceAlignment(bestX, bestY);
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

        System.out.println("Alignments: " + alignOne + " <-> " + alignTwo);
    }
}
